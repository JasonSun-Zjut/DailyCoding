# 基于Reentranlock的AQS分析

- **AbstractQueuedSynchronizer的整体分析**
- **Reentranlock中对AQS的实现**
- **对Lock()的分析**
- **对UnLock()的分析**

-------------------
#### AbstractQueuedSynchronizer的整体分析

 1. AQS内部含有一个Node类，正是由这个Node构成了同步队列，官方注释对同步队列的形象描述。    +------+  prev
    +-----+       +-----+  head |      | <---- |     | <---- |     |  tail    +------+       +-----+       +-----+
![AQS的一个内部类Node](https://github.com/Jason194113/JavaSeLearn/blob/master/Screenshots/Node.JPG)
 2. **AQS提供了5个供子类重写的方法，针对共享锁和独占锁的实现**
       tryAcquire(int arg)
       tryAcquireShared(int arg)
       tryRelease(int arg)
       tryReleaseShared(int arg)
       isHeldExclusively()
      
 3. **AQS中的模板方法**
     **这些模板方法将会调用指定的由子类进行重写的方法。**
     acquire(int arg)
     acquireInterruptibly(int arg)
     将调用重写的tryAcquire(int args)
     
     acquireShared(int arg)
     acquireSharedInterruptibly(int arg)
     调用重写的tryAcquireShared(int args)
    
     releaseShared(int arg)
     调用重写的tryReleaseShared(int arg)
     release(int arg)
     调用重写的tryRelease(int arg)
     
 4. state
   state作为同步变量，子类对同步变量有不同的实现方式


  
-------------------
#### Reentranlock中对AQS的实现
由于是基于Reentranlock的非公平锁的AQS分析，所以首先来看看Reentranlock是如何基于AQS进行构造的。

 **1. Syc**
    **Syc作为Reentranlock的内部类实现了继承了AQS，并重写了         tryRelease和tryAcquire方法，同时定义了lock()抽象方法，供子类的具体情况去实现**
    
    `protected final boolean tryRelease(int releases) {
            int c = getState() - releases;  /**Reentranlock使用state0和1来分别代表当前锁是否被占有*/
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
         protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }` 
        
 2. NonfairSync
     NonfairSync继承了Sync，从类名的定义就能明白这是非公平锁的实现，NonfairSync重写了AQS中的tryAcquire方法和Sync中的lock()方法。
 
```
         final void lock() {
        /** 之前提到了State变量代表当前锁的占有情况
            这里使用CAS操作尝试获取锁*/
              setExclusiveOwnerThread(Thread.currentThread());
              /**获取成功则把锁的占有线程更改为当前线程*/
            else
                acquire(1);
                /**调用AQS中的acquire方法*/
        }

        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
            /**调用了Sync中的定义的nonfairTryAcquire方法*/
        }  
```


-------------------
## Lock()分析
**之前分析到，非公平锁的lock()实现为利用CAS尝试获取锁，成功则占有锁，失败则执行AQS中定义的acquire()方法.**

 **- acquire()**
`
 public final void acquire(int arg) {  /**arg=1*/
        if (!tryAcquire(arg) &&   
        /**之前提到过acquire为模板方法，这里便是执行子类重写的tryacquire()方法,子类的具体实现为Sync类中的nonfairTryAcquire方法*/
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }
`

```
final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();/**得到同步变量的值*/
            if (c == 0) {/**如果是0，表示当前没有线程占有锁*/
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                  /**利用CAS尝试获取锁，成功则返回true*/
                }
            }
            /**对重复获取锁的判断*/
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
```

 **- addWaiter()**
   

```
    private Node addWaiter(Node mode) {
    //将当前线程包装成一个Node节点，Node中维护了一个waitstatus变量
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        //初始化同步队列
        enq(node);
        return node;
    }


        private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                //构造了一个头节点
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                  //将当前线程的节点尾插入到同步队列
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }
```

 **-acquireQueued()**
   **这个方法是AQS中重要的方法，描述了节点进入同步队列后的一系列操作**
   

```
    final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
            //获得刚插入的前继节点
                final Node p = node.predecessor();
                //1.如果前继节点是head，那么就尝试再次获取锁
                //2.如果前继节点不是head，或者尝试获取锁失败 
                if (p == head && tryAcquire(arg)) {
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                //判断在获取锁失败以后，能否进入阻塞
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }

   private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        //获得前继节点的等待状态，节点默认的waitstatus为0
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
           
            return true;
        if (ws > 0) {
           
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
          
             //利用CAS操作将前继节点的waitstatus更改为SIGNAL
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        //在将前继节点的waitstatus设置为SIGNAL后，
        //节点会再循环一次，判断能否获取锁
        return false;
    }
```

**在将前继节点的waitstatus设置为SIGNAL后，表明前继节点会在释放锁之后唤醒后继节点，那么当前节点就可以安心的进入阻塞状态，等待前继节点的唤醒。**

```
private final boolean parkAndCheckInterrupt() {
         //利用LockSupport这个辅助类来阻塞当前线程
        LockSupport.park(this);
        return Thread.interrupted();
    }
```
**当这里我们已经分析完了，线程在成功竞争到锁的情况和竞争锁失败以后所执行的操作。线程在竞争锁失败以后，会以Node节点的形式尾插入到同步队列中，在同步队列中只有前继节点是head的Node节点才有机会尝试竞争锁，其他节点在设置前继节点为SIGNAL后都将进入阻塞状态。**

-------------------
### UnLock()

 - unlock()调用了AQS中的模板方法release()
   
```
    public final boolean release(int arg) {
          //tryRelease为AQS提供给子类进行重写的方法
        if (tryRelease(arg)) {
            Node h = head;
            //判断head的值和waitStatus
            if (h != null && h.waitStatus != 0)
                //唤醒头节点的后继节点
                unparkSuccessor(h);
            return true;
        }
        return false;
    }


       //ReentranLock中unfairSync的tryRelease()
     protected final boolean tryRelease(int releases) {
            //通过将state的值置为0，表示释放了锁
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }




     private void unparkSuccessor(Node node) {
     
        int ws = node.waitStatus;
        if (ws < 0)
        //将头节点的waitStatus更新为0
            compareAndSetWaitStatus(node, ws, 0);
            
        Node s = node.next;
         //如果head的后继节点为null，或者已经被取消了
        if (s == null || s.waitStatus > 0) {
            s = null;
            //采用从队列尾开始向前遍历，寻找一个waitStatus<0的节点
            for (Node t = tail; t != null && t != node; t = t.prev)          
                if (t.waitStatus <= 0)
                    s = t;
        }
        if (s != null)
            //唤醒指定节点
            LockSupport.unpark(s.thread);
    }
```
     
 - **阻塞节点被唤醒后**
 

```
 private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        //阻塞节点被唤醒后，首先会判断在阻塞过程中是否被中断过
        return Thread.interrupted();
    }

                //再次尝试获取锁
                for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    //获取成功，将当前节点设置为头节点
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
```
**到这里ReentranLock的lock和unlock的整体过程已经分析完了，不过还存在很多细节没有顾及到，比如对中断的处理等。**

-------------------
     

