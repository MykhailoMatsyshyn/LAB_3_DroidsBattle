package Menu;

import Arena.BattleArena;
import Droid.BaseDroid;
import Droid.DroidFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuBar {
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        DroidFactory droidFactory = new DroidFactory();
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n\u001B[36m╔═══════════════ Меню: ═══════════════╗\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 1 Створити дроїда                   \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 2 Показати список створених дроїдів \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 3 Запустити бій 1 на 1              \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 4 Запустити бій команда на команду  \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 5 Відтворити бій з файлу            \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m║\u001B[0m 6 Вийти з програми                  \u001B[36m║\u001B[0m");
            System.out.println("\u001B[36m╚═════════════════════════════════════╝\u001B[0m");
            System.out.print("❯ ");

            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m Помилка вводу. Будь ласка, введіть\n числове значення.\u001B[0m");
                scanner.nextLine();
                continue;
            }

            List<BaseDroid> droids = droidFactory.getDroids();

            switch (choice) {
                case 1:
                    droidFactory.createDroid(scanner);
                    break;
                case 2:
                    System.out.println("┌───── Список створених дроїдів: ─────┐");
                    for (int i = 0; i < droids.size(); i++) {
                        BaseDroid droid = droids.get(i);
                        System.out.println(droid.toString());
                    }
                    System.out.println("\n└─────────────────────────────────────┘");
                    break;
                case 3:
                    start1v1Battle(droids, scanner);
                    break;
                case 4:
                    startTeamBattle(new ArrayList<>(droids), scanner);
                    break;
                case 5:
                    System.out.println("\n=======================================");
                    System.out.println("Виведення бою з файлу:");
                    System.out.println("=======================================");
                    reproduceBattle("D:\\droids_buttle.txt");
                    System.out.println("=======================================");
                    break;
                case 6:
                    isRunning = false;
                    System.out.println("\u001B[31m Виконання програми завершено.\u001B[0m");
                    break;
                default:
                    System.out.println("\u001B[31m Невірний вибір. Спробуйте ще раз.\u001B[0m");
            }
        }
    }

    public void reproduceBattle(String filePath) {
        try {
            File file = new File(filePath);

            if (file.length() == 0) {
                System.out.println("Файл порожній.");
                return;
            }

            try (FileReader fileReader = new FileReader(filePath);
                 Scanner scan = new Scanner(fileReader)) {

                while (scan.hasNextLine()) {
                    System.out.println(scan.nextLine());
                }
            }
        } catch (IOException e) {
            System.err.println("Помилка під час читання файлу: " + e.getMessage());
        }
    }

    public void start1v1Battle(List<BaseDroid> droids, Scanner scanner) {
        if (droids.size() < 2) {
            System.out.println("\nНе вистачає дроїдів для бою.");
        } else {
            System.out.println("\n○ Оберіть двох дроїдів для бою:");

            System.out.println("\n  Оберіть першого дроїда:");
            for (int i = 0; i < droids.size(); i++) {
                System.out.println(i + 1 + ". " + droids.get(i).getName());
            }

            int firstDroidIndex;
            while (true) {
                System.out.print("❯ ");
                if (scanner.hasNextInt()) {
                    firstDroidIndex = scanner.nextInt() - 1;

                    if (firstDroidIndex >= 0 && firstDroidIndex < droids.size()) {
                        break;
                    } else {
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                    }
                } else {
                    System.out.println("Введіть номер дроїда у вірному форматі.");
                    scanner.next();
                }
            }

            System.out.println("\n  Оберіть другого дроїда:");
            for (int i = 0; i < droids.size(); i++) {
                System.out.println(i + 1 + ". " + droids.get(i).getName());
            }

            int secondDroidIndex;
            while (true) {
                System.out.print("❯ ");
                if (scanner.hasNextInt()) {
                    secondDroidIndex = scanner.nextInt() - 1;

                    if (secondDroidIndex >= 0 && secondDroidIndex < droids.size() && secondDroidIndex != firstDroidIndex) {
                        break;
                    } else {
                        System.out.println("Обрані однакові дроїди. Спробуйте ще раз.");
                    }
                } else {
                    System.out.println("Введіть номер дроїда у вірному форматі.");
                    scanner.next();
                }
            }

            BaseDroid firstDroid = droids.get(firstDroidIndex);
            BaseDroid secondDroid = droids.get(secondDroidIndex);

            BattleArena battleArena = new BattleArena();
            battleArena.battle1v1(firstDroid, secondDroid);
        }
    }

    private List<BaseDroid> selectDroidsForTeam(List<BaseDroid> availableDroids, int numTeam, Scanner scanner) {
        List<BaseDroid> team = new ArrayList<>();
        int maxDroidsToSelect = 3;

        System.out.println("\nОберіть дроїдів для команди " + numTeam + " за номером:");

        while (team.size() < maxDroidsToSelect) {
            System.out.println("Доступні дроїди:");

            for (int i = 0; i < availableDroids.size(); i++) {
                System.out.println(i + 1 + ". " + availableDroids.get(i).getName());
            }

            int droidIndex;
            while (true) {
                System.out.print("❯ ");

                if (scanner.hasNextInt()) {
                    droidIndex = scanner.nextInt() - 1;

                    if (droidIndex >= 0 && droidIndex < availableDroids.size()) {
                        team.add(availableDroids.get(droidIndex));
                        availableDroids.remove(droidIndex);
                        break;
                    } else {
                        System.out.println("Неправильний вибір. Спробуйте ще раз.");
                    }
                } else {
                    System.out.println("Введіть номер дроїда у правильному форматі.");
                    scanner.next();
                }
            }
        }

        return team;
    }

    public void startTeamBattle(List<BaseDroid> availableDroids, Scanner scanner) {
        if (availableDroids.size() < 6) {
            System.out.println("\n Не вистачає дроїдів для бою.");
        } else {
            System.out.println("\n○ Оберіть дві команди для бою:");
            List<BaseDroid> team1 = selectDroidsForTeam(availableDroids, 1, scanner);
            List<BaseDroid> team2 = selectDroidsForTeam(availableDroids, 2, scanner);

            System.out.println("\nСклад команди 1:");
            displayTeam(team1);

            System.out.println("\nСклад команди 2:");
            displayTeam(team2);

            BattleArena battleArena = new BattleArena();
            battleArena.teamBattle(team1, team2);
        }
    }

    private void displayTeam(List<BaseDroid> team) {
        for (BaseDroid droid : team) {
            System.out.println(droid.getName() + " (Здоров'я: " + droid.getHealth() + ", Пошкодження: " + droid.getDamage() + ")");
        }
    }
}
