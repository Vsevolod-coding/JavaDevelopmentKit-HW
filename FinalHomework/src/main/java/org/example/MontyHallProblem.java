package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MontyHallProblem {
    private static final int DOORS = 3; // Количество дверей (ячейки массива)
    private static final Random random = new Random();
    private static final int GAMES_CNT = 1000; // Количество игр, которые будут сыграны

    public static void main(String[] args) {
        Map<Integer, Boolean> resultsWithSwitch = new HashMap<>();
        Map<Integer, Boolean> resultsWithoutSwitch = new HashMap<>();

        int winsWithSwitchDoor = 0; // Счетчик побед при изменении выбора
        int winsWithoutSwitchDoor = 0; // Счетчик побед без изменения выбора

        // Симуляция игр с изменением выбора
        for (int i = 1; i <= GAMES_CNT; i++) {
            boolean win = startWithChangeChoice() == 1;
            resultsWithSwitch.put(i, win);
            if (win) {
                winsWithSwitchDoor += 1;
            }
        }

        // Симуляция игр без изменения выбора
        for (int i = 1; i <= GAMES_CNT; i++) {
            boolean win = startWithoutChangingChoice() == 1;
            resultsWithoutSwitch.put(i, win);
            if (win) {
                winsWithoutSwitchDoor += 1;
            }
        }

        // Вывод результатов для стратегии с изменением выбора
        System.out.println("Результат С изменением выбора:");
        for (Map.Entry<Integer, Boolean> entry : resultsWithSwitch.entrySet()) {
            System.out.println("Игра " + entry.getKey() + ": " + (entry.getValue() ? "Win" : "Loss"));
        }
        System.out.println("Всего игр " + GAMES_CNT + ". Побед с изменением двери: " + winsWithSwitchDoor +
                ", Проигрышей: " + (GAMES_CNT - winsWithSwitchDoor));


        // Вывод результатов для стратегии без изменения выбора
        System.out.println("\nРезультат БЕЗ изменения выбора:");
        for (Map.Entry<Integer, Boolean> entry : resultsWithoutSwitch.entrySet()) {
            System.out.println("Игра " + entry.getKey() + ": " + (entry.getValue() ? "Win" : "Loss"));
        }
        System.out.println("Всего игр " + GAMES_CNT + ". Побед без изменения двери: " + winsWithoutSwitchDoor +
                ", Проигрышей: " + (GAMES_CNT - winsWithoutSwitchDoor));
    }


    // Метод для игры с изменением выбора двери
    private static int startWithChangeChoice() {
        int[] doors = new int[DOORS];
        int winnerIndex = random.nextInt(DOORS); // Выбираем случайную дверь как выигрышную
        doors[winnerIndex] = 1; // Помещаем приз за выбранной дверью (1 обозначает выигрыш)

        int playerChoice = random.nextInt(DOORS); // Игрок выбирает случайную дверь

        // Ведущий выбирает пустую дверь, которая не выбрана игроком и не является выигрышной
        int doorToOpen;
        do {
            doorToOpen = random.nextInt(DOORS);
        } while (doorToOpen == playerChoice || doors[doorToOpen] == 1);

        // Игрок меняет выбор на оставшуюся закрытую дверь
        int newChoice;
        do {
            newChoice = random.nextInt(DOORS);
        } while (newChoice == playerChoice || newChoice == doorToOpen);

        return doors[newChoice]; // Возвращает результат выбора игрока после смены двери (1 - win, 0 - loose)
    }


    // Метод для игры без изменения выбора двери
    private static int startWithoutChangingChoice() {
        int[] doors = new int[DOORS];
        int winnerIndex = random.nextInt(DOORS);
        doors[winnerIndex] = 1;

        int playerChoice = random.nextInt(DOORS);

        return doors[playerChoice];
    }
}