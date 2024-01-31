package View;

import java.util.concurrent.CyclicBarrier;

public class ViewDeprecated {
    // Runnable task for each thread
    private static class Task implements Runnable {
        private CyclicBarrier barrier;

        public Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting at the barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // Creates a CyclicBarrier with 3 parties and a barrier action that will be executed when all threads reach the barrier
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("All parties are at the barrier, let's play!"));

        Thread t1 = new Thread(new Task(barrier), "Thread 1");
        Thread t2 = new Thread(new Task(barrier), "Thread 2");
        Thread t3 = new Thread(new Task(barrier), "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
