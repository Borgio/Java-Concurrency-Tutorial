package Chapter_1_ThreadAPI.Part_9_Thread_Local;

/**
 * If specific initialization process is required, pass special supplier to withInitial() method.
 */
public class Ex_4_ThreadLocal_correctly_initialized {

    private static volatile ThreadLocal<Integer> runCount = ThreadLocal.withInitial(() -> 0);

    private static volatile boolean isActive = true;

    public static void main(String[] args) {
        var t1 = new Thread(new Worker(), "Thread_1");
        var t2 = new Thread(new Worker(), "Thread_2");
        var t3 = new Thread(new Worker(), "Thread_3");
        var t4 = new Thread(new Worker(), "Thread_4");
        var t5 = new Thread(new Worker(), "Thread_5");

        // Priorities only serve as hints to scheduler, it is up to OS implementation to decide
        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(5);
        t4.setPriority(3);
        t5.setPriority(1);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        //  Make the Starvation Thread sleep for 5 seconds
        //  then set isActive to false to stop all threads
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isActive = false;

    }

    private static class Worker implements Runnable {
        public void run() {
            while (isActive) {
                try {
                    doWork();
                } catch (Exception e) {
                    System.out.format("%s was interrupted...\n", Thread.currentThread().getName());
                    e.printStackTrace();
                }
            }
            System.out.format("Worker was terminated with final result %s: Current runCount is %d...\n", Thread.currentThread().getName(), runCount.get());
        }

        private void doWork() {
            runCount.set(runCount.get() + 1);
            System.out.format("%s: Current runCount is %d...\n", Thread.currentThread().getName(), runCount.get());
        }
    }
}