package Droid;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DroidFactory {
    private List<BaseDroid> droids;

    public DroidFactory() {
        droids = new ArrayList<>();
    }

    public BaseDroid createDroid(Scanner scanner) {
        BaseDroid droid = null;
        int choice;
        boolean droidCreated = false;

        do {
            System.out.println("\n ─ Оберіть тип для створення Дроїда:\n");
            System.out.println("┌────── Доступні типи дроїдів: ───────┐");
            System.out.println("  1 Берсерк");
            System.out.println("  2 Медик");
            System.out.println("  3 Снайпер");
            System.out.println("  4 Стелс");
            System.out.println("  0 Вийти");
            System.out.println("└─────────────────────────────────────┘");
            System.out.print("❯ ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
                choice = -1;
            }

            if (choice != 0) {
                droid = new BaseDroid("", 0, 0);
            }

            switch (choice) {
                case 1:
                    int readyChargeValue;
                    enterDroidData(scanner, droid);

                    System.out.print("○ Введіть к-ть ходів до готовності сильної атаки (мін. к-ть - 3): ");
                    readyChargeValue = scanner.nextInt();
                    if(readyChargeValue < 3){
                        readyChargeValue = 3;
                        System.out.println("\n Значення готовності атаки змінено на " + readyChargeValue + " оскільки ви задали менше 3");
                    }
                    droid = new DroidBerserk(droid.getName(), droid.getHealth(), droid.getDamage(), readyChargeValue);
                    break;
                case 2:
                    int healingAmount;
                    int selfHealingLimit;
                    boolean validInput = false;
                    enterDroidData(scanner, droid);

                    do {
                        System.out.print("○ Введіть значення кількості лікування для Медика (1-50): ");
                        healingAmount = scanner.nextInt();
                        System.out.print("○ Введіть максимальну кількість лікувань себе для Медика (1 або більше): ");
                        selfHealingLimit = scanner.nextInt();

                        if (healingAmount >= 1 && healingAmount <= 50 && selfHealingLimit >= 1) {
                            validInput = true;
                        } else {
                            System.out.println("\u001B[31m Неправильний ввід. Перевірте діапазони для healingAmount та selfHealingLimit.\u001B[0m");
                        }
                    } while (!validInput);

                    droid = new DroidMedic(droid.getName(), droid.getHealth(), droid.getDamage(), healingAmount, selfHealingLimit);
                    break;
                case 3:
                    double accuracy;
                    double range;
                    enterDroidData(scanner, droid);

                    while (true) {
                        System.out.print("○ Введіть точність для Снайпера (0.1-1.0): ");
                        accuracy = scanner.nextDouble();
                        System.out.print("○ Введіть дальність стрільби для Снайпера (1-1000): ");
                        range = scanner.nextDouble();

                        if (accuracy >= 0.1 && accuracy <= 1.0 && range >= 1 && range <= 1000) {
                            droid = new DroidSniper(droid.getName(), droid.getHealth(), droid.getDamage(), accuracy, range);
                            break;
                        } else {
                            System.out.println("\u001B[31m Неправильний ввід. Перевірте діапазони для точності та дальності стрільби.\u001B[0m");
                        }
                    }
                    break;
                case 4:
                    int invisibilityDuration;
                    double stealthSpeed;

                    do {
                        enterDroidData(scanner, droid);

                        System.out.print("○ Введіть тривалість невидимості для Стелса (максимум " + 10 + "): ");
                        invisibilityDuration = scanner.nextInt();
                        System.out.print("○ Введіть швидкість невидимості для Стелса (максимум " + 1.5 + "): ");
                        stealthSpeed = scanner.nextDouble();

                        if (invisibilityDuration <= 0 || invisibilityDuration > 10 || stealthSpeed <= 0 || stealthSpeed > 1.5) {
                            System.out.println("\u001B[31m Неправильний ввід. Перевірте обмеження для тривалості і швидкості невидимості.\u001B[0m");
                        }
                    } while (invisibilityDuration <= 0 || invisibilityDuration > 10 || stealthSpeed <= 0 || stealthSpeed > 1.5);

                    droid = new DroidStealth(droid.getName(), droid.getHealth(), droid.getDamage(), invisibilityDuration, stealthSpeed);
                    break;
                    case 0:
                    System.out.println("\u001B[31m Вихід з меню створення дроїда.\u001B[0m");
                    break;
                default:
                    System.out.println("\u001B[31m Неправильний вибір. Спробуйте ще раз.\u001B[0m");
            }

            if (droid != null && !droidCreated) {
                System.out.println("\u001B[32mСтворений дроїд: " + droid.getName() + "\u001B[0m");
                droidCreated = true;
            }
        } while (droid == null && choice != 0);

        if (droid != null) {
            droids.add(droid);
        }

        return droid;
    }

    public void enterDroidData(Scanner scanner, BaseDroid droid) {
        System.out.print("○ Введіть ім'я: ");
        droid.setName(scanner.next());

        do {
            System.out.print("○ Введіть к-ть здоров'я [50-200]: ");
            if (scanner.hasNextInt()) {
                int health = scanner.nextInt();
                if (health >= 50 && health <= 200) {
                    droid.setHealth(health);
                    break;
                } else {
                    System.out.println("\u001B[31m Помилка вводу. К-ть здоров'я повинна бути в межах від " + 50 + " до " + 200 + ".\u001B[0m");
                }
            } else {
                System.out.println("\u001B[31m Помилка вводу. Будь ласка, введіть ціле число для к-ть здоров'я.\u001B[0m");
                scanner.next();
            }
        } while (true);

        do {
            System.out.print("○ Введіть к-ть пошкодження [10-50]: ");
            if (scanner.hasNextInt()) {
                int damage = scanner.nextInt();
                if (damage >= 10 && damage <= 50) {
                    droid.setDamage(damage);
                    break;
                } else {
                    System.out.println("\u001B[31m Помилка вводу. К-ть пошкодження повинна бути в межах від " + 10 + " до " + 50 + ".\u001B[0m");
                }
            } else {
                System.out.println("\u001B[31m Помилка вводу. Будь ласка, введіть ціле число для к-ть пошкодження.\u001B[0m");
                scanner.next();
            }
        } while (true);
    }

    public List<BaseDroid> getDroids() {
        return droids;
    }
}
