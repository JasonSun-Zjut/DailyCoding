package Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author
 * @date /08/20
 */
class Store {
    /**
     * 仓库的容量
     */
    private int capacity;
    /**
     * 当前货物的个数
     */
    private  int size;
    private Lock lock;
    /**
     * customerCondition:当消费者发现仓库里的货物为0时，阻塞在这个条件上。
     * producerCondition:当生产者发现货物个数超过了仓库的容量，阻塞在这个条件上。
     * lessCondition:当消费者发现仓库里的货物不够时，阻塞在这个条件上
     * */
    private Condition customerCondition;
    private Condition producerCondition;
    private Condition lessCondition;

    public Store(int capacity) {
        this.capacity = capacity;
        size = 0;
        lock = new ReentrantLock();
        customerCondition = lock.newCondition();
        producerCondition = lock.newCondition();
        lessCondition=lock.newCondition();
    }

    /**
     * 生产货物
     */
    public void produce(int num) {
        lock.lock();
        try {
            while (true) {
                while (size >= capacity) {
                    producerCondition.await();
                }
                size=size+num;
                System.out.println("向仓库内添加了" + num + "个货物");
                customerCondition.signal();
                lessCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 取出货物
     */
    public void push(int num) {
        lock.lock();
        try {
            while (true) {
                while (size <= 0) {
                    customerCondition.await();
                }
                while(size-num<0)
                {
                      lessCondition.await();
                }
                size = size - num;
                System.out.println("消费了" + num + "个货物");
                producerCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Customer implements Runnable {
    private Store store;
    private int num;

    public void setNum(int num) {
        this.num = num;
    }

    public Customer(Store store) {
        this.store = store;
    }

    public void run() {
        store.push(num);
    }
}

class Producer implements Runnable {
    private Store store;
    private int num;

    public Producer(Store store) {
        this.store = store;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void run() {
        store.produce(num);
    }
}

public class ReentrantLockCustorPro {
    public static void main(String[] args) {
        Store store = new Store(10);
        Customer customer = new Customer(store);
        customer.setNum(7);
        Producer producer = new Producer(store);
        producer.setNum(3);
        new Thread(customer, "CustomerThread").start();
        new Thread(producer, "ProducerThread").start();
    }
}
