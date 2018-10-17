/**
 * @author
 * @date /10/17
 */
public class DeadLock {
    static  class LockTask implements  Runnable{
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public static Object LockOne=new Object();
        public static Object LockTwo=new Object();
        public boolean flag;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
        public void run() {
            if(flag==true)
            {
                synchronized (LockOne)
                {
                    System.out.println(Thread.currentThread().getName()+"获取到了LockOne");
                    try {
                        Thread.sleep(1000);
                        synchronized (LockTwo)
                        {
                            System.out.println(Thread.currentThread().getName()+"获取到了LockTwo");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                synchronized (LockTwo)
                {
                    System.out.println(Thread.currentThread().getName()+"获取到了LockTwo");
                    try {
                        Thread.sleep(1000);
                        synchronized (LockOne)
                        {
                            System.out.println(Thread.currentThread().getName()+"获取到了LockOne");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        LockTask deadLock=new LockTask();
        deadLock.setFlag(true);
        LockTask deadLockTwo=new LockTask();
        deadLockTwo.setFlag(false);
        Thread thread=new Thread(deadLock,"Thread-1");
        Thread threadTwo=new Thread(deadLockTwo,"Thread-2");
        thread.start();
        threadTwo.start();
    }
}
