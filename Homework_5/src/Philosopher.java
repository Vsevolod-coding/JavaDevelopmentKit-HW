import java.util.concurrent.locks.Lock;

public class Philosopher implements Runnable {
    private final int id;

    private final Lock leftFork;
    private final Lock rightFork;

    private int mealEaten = 0;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (mealEaten < 3) {
                think();
                eat();
            }
            System.out.println("Философ " + id + " завершил прием пищи.\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Философ " + id + " был прерван.\n");
        }
    }

    private void eat() throws InterruptedException{
        leftFork.lock();
        try {
            rightFork.lock();
            try {
                System.out.println("Философ " + id + " ест.");
                mealEaten++;
                Thread.sleep(3000);
            } finally {
                rightFork.unlock();
            }
        } finally {
            leftFork.unlock();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Философ " + id + " размышляет...");
        Thread.sleep(3000);
    }
}
