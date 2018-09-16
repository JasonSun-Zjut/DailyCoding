package Interrupt;

/**
 * @author
 * @date /09/12
 */
public class InterruptDemo {
    static class RunnableThread implements Runnable {
        /**
         * interrupt()对处于Runnable状态的线程只会将线程的中断标志位设为true
         * 需要程序自行根据中断标志位进行处理
         */
        private Object lock;

        public RunnableThread(Object lock) {
            this.lock = lock;
        }

        public void run() {
            synchronized (lock) {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("当前线程被中断");
                        break;
                    }
                }
            }
        }
    }

    static class BlockThread implements Runnable {
        /**
         * interrupt()对于Blocked状态的线程，只会设置中断位的标识位true.
         */
        private Object lock;

        public BlockThread(Object lock) {
            this.lock = lock;
        }
        /**
         * 进入synchronized同步块获取锁时，是不会对中断进行相应的处理
         * */
        public void run() {
            synchronized (lock) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("在获取锁的过程中发生了线程中断");
                    return;
                }
            }
        }
    }

    static class WaitThread implements Runnable {
        /**
         * 处于Wait状态的线程,接收到中断会抛出InterruptedException异常，同时会清空中断的标志位
         * 由于抛出InterruptedException后会重置标志位，所以需要回复中断标志
         */
        private Object lock;

        public WaitThread(Object lock) {
            this.lock = lock;
        }

        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("在wait状态下发生了线程中断");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
