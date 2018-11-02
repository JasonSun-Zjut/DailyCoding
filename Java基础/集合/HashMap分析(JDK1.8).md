## HashMap分析(JDK1.8)

[TOC]

------

##### 1.HashMap的成员变量

```java
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
static final int MAXIMUM_CAPACITY = 1 << 30;
static final float DEFAULT_LOAD_FACTOR = 0.75f;
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
transient Node<K,V>[] table;
transient int size;
transient int modCount;
int threshold;
final float loadFactor;
```

1. DEFAULT_INITIAL_CAPACITY:默认情况下初始化大小为16
2. MAXIMUM_CAPACITY:最大容量
3. DEFAULT_LOAD_FACTOR:默认的负载因子
4. Node:是HashMap的一个静态内部类，存储了hash，Key，value和下一个节点的引用。
5. Node<K,V>[] table:HashMap底层是一个Node类型的数组
6. size:表示当前桶数组中存储了多少Node变量
7. modCount:用来记录对HashMap结构修改的次数
8. threshold:桶数组的负载容量,当size大于threshold时便要进行扩容
9. loadFactor:负载因子，默认为0.75f

------

##### 2.HashMap的构造函数

```java
public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }


 public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

  public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
```



HashMap提供了三种构造函数，最终都通过调用 HashMap(int initialCapacity, float loadFactor)这个构造函数进行初始化，在这个构造函数中通过tableSizeFor(initialCapacity)对初始容量进行了处理。

```java
static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
//返回了离初始容量最近的2^n的数
```

![](https://github.com/Jason194113/DailyCoding/blob/master/Screenshots/TIM%E5%9B%BE%E7%89%8720181102201739.png)

可以看到HashMap的构造函数只初始化了loadFactor和threshold，并没有对桶数组进行初始化。

------

##### 3.HashMap的put()方法

```java
//首先使用hash()对key进行了hash处理
public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
//将key的hashcode值与自身右移16位后进行了异或运算
static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

//putVal，第一次putVal方法的调用会初始化桶数组
 if ((tab = table) == null || (n = tab.length) == 0)
     n = (tab = resize()).length;
//resize(),初始化时使用构造方法中初始化的threshold作为同数组的最大容量
 else if (oldThr > 0) 
      newCap = oldThr;
//计算负载容量
if (newThr == 0) {
      float ft = (float)newCap * loadFactor;

//初始化桶数组之后，进行寻址，使用了(n-1)&hash作为寻址方式，若对应槽为空，则构造节点插入
if ((p = tab[i = (n - 1) & hash]) == null)
     tab[i] = newNode(hash, key, value, null);
    
//如果对应槽不为空
 Node<K,V> e; K k;
            //首先判断对应槽的第一个节点的Key是否相等，e变量保存了key相同的节点
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //红黑树的处理
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);     
            //对链表一直遍历
            else {
                for (int binCount = 0; ; ++binCount) {
                    //如果遍历到了链表的尾部，则构造新节点进行尾插入
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) 
                            treeifyBin(tab, hash);
                        break;
                    }
                    //对节点进行判断
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
    
 //插入节点后，需要判断size是否超过了负载容量，如果超过了需要进行桶数组的resize()
     if (++size > threshold)
          resize();
```

![](https://github.com/Jason194113/DailyCoding/blob/master/Screenshots/TIM%E5%9B%BE%E7%89%8720181102192847.png)



------

##### 4.HashMap的扩容机制

```java
//newCap为oldCap的两倍  
else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold


//原数组的key-val移动到新数组中
if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                    //如果当前槽只有一个key-val对，那么重新计算在新数组中的槽位置插入
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { 
                        //根据e.hash & oldCap的情况将分为 loHead和hiHead两条链表
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        //loHead这条链表索引位置在新数组中不变
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        //hiHead这条链表索引位置在新数组中为 j + oldCap
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
```



由于桶数组使用的时2^n作为扩容，因此元素的位置要么不变，要么移动原数组的长度。

下图为桶数组大小从16扩容到32时，桶数组中节点的移动情况

![](https://github.com/Jason194113/DailyCoding/blob/master/Screenshots/TIM%E5%9B%BE%E7%89%8720181102195814.png)



------

#### 小结：

1. HashMap桶数组的初始化发生在第一次put()操作执行的时候。
2. HashMap会对初始容量进行处理，保证桶数组的容量为最接近的2^n。
3. HashMap对key的hash处理为key的hashCode与自身右移16位后进行异或操作
4. JDK1.8在HashMap中引入了红黑树来避免拉链过长。         
5. HashMap是线程不安全的