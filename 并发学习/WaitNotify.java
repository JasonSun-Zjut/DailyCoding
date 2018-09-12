
import java.util.LinkedList;


/**
 * @author
 * @date /09/11
 */
public class WaitNotify {

    static class Customer implements Runnable {
        private Object lock;
        private LinkedList<Object> list;

        public Customer(Object lock, LinkedList<Object> list) {
            this.lock = lock;
            this.list = list;
        }

        public void run() {
            while (true) {
                synchronized (lock) {
                    while (list.isEmpty()) {
                        System.out.println("仓库为空，无法进行消费");
                        try {
                            lock.wait();
                            System.out.println(Thread.currentThread().getName()+"被唤醒");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                    list.removeFirst();
                    System.out.println("消耗了一个货物，现在仓库中有:"+list.size()+"个货物");
                }
            }
        }
    }

    static class Producer implements Runnable {
        private Object lock;
        private LinkedList<Object> list;
        private int capacity;

        public Producer(Object lock, LinkedList<Object> list,int capacity) {
            this.lock = lock;
            this.list = list;
            this.capacity=capacity;
        }

        public void run() {
            while (true) {
                synchronized (lock) {
                    while (list.size()==capacity)
                    {
                        System.out.println("仓库已经满了，不能继续生产");
                        try {
                            lock.wait();
                            System.out.println(Thread.currentThread().getName()+"被唤醒");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    lock.notify();
                    list.addFirst(new Object());
                    System.out.println("向仓库中加入一个货物,现在仓库中有:"+list.size()+"个货物");
                }
            }
        }
    }

    public static void main(String[] args) {
        Object lock = new Object();
        LinkedList<Object> linkedList=new LinkedList<Object>();
        Thread producerThread=new Thread(new Producer(lock,linkedList,10),"生产者");
        Thread customerThread=new Thread(new Customer(lock,linkedList),"消费者");
        customerThread.start();
        producerThread.start();
    }
}
