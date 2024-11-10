import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Table {
    private static final int PHILOSOPHERS_NUM = 5;
    private final Philosopher[] philosophers = new Philosopher[PHILOSOPHERS_NUM];
    private final Lock[] forks = new ReentrantLock[PHILOSOPHERS_NUM];

    public Table() {
        for (int i = 0; i < PHILOSOPHERS_NUM; i++) {
            forks[i] = new ReentrantLock();
        }

        // Инициализация философов
        for (int i = 0; i < PHILOSOPHERS_NUM; i++) {
            Lock leftFork = forks[i];
            Lock rightFork = forks[(i + 1) % PHILOSOPHERS_NUM];

            // Назначаем философов, последнему даем вилки в обратном порядке, чтобы избежать deadlock
            if (i == PHILOSOPHERS_NUM - 1) {
                philosophers[i] = new Philosopher(i, rightFork, leftFork);
            } else {
                philosophers[i] = new Philosopher(i, leftFork, rightFork);
            }
        }
    }

    public void startDinner() {
        Thread[] threads = new Thread[PHILOSOPHERS_NUM];
        for (int i = 0; i < PHILOSOPHERS_NUM; i++) {
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        // Ожидание завершения работы всех философов
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("ВСЕ ФИЛОСОФЫ ЗАВЕРШИЛИ ПРИЕМ ПИЩИ.");
    }
}
