package Arena;

import Droid.BaseDroid;
import Droid.DroidMedic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BattleArena {
    // Вибір потоку перед початком бою
    private void chooseBattleStream() {
        FileWork.chooseStream();
    }

    public void battle1v1(BaseDroid droid1, BaseDroid droid2) {
        chooseBattleStream();

        System.out.println("\n\u001B[32mПочинається бій 1 на 1 між " + droid1.getName() + " та " + droid2.getName() + "\u001B[0m");

        int round = 1;

        while (droid1.isAlive() && droid2.isAlive()) {
            System.out.println("\n\u001B[32mРаунд " + round + ":\u001B[0m");

            performDroidAttack(droid1, droid2);
            if (!droid2.isAlive()) { displayHealth(droid1, droid2); displayWinner(droid1); break; }

            performDroidAttack(droid2, droid1);
            if (!droid1.isAlive()) { displayHealth(droid2, droid1); displayWinner(droid2); break; }

            displayHealth(droid1, droid2);

            round++;
        }
    }

    public void teamBattle(List<BaseDroid> team1, List<BaseDroid> team2) {
        chooseBattleStream();

        System.out.println("\n\u001B[32mПочинається командний бій!\u001B[0m");

        int round = 1;

        while (!team1.isEmpty() && !team2.isEmpty()) {
            System.out.println("\n\u001B[32mРаунд " + round + ":\u001B[0m");

            for (BaseDroid droid : team1) {
                if (!droid.isAlive()) continue;

                List<BaseDroid> availableTargets = getAvailableTargets(team2);
                if (!availableTargets.isEmpty()) {
                    BaseDroid target = getRandomTarget(availableTargets);
                    performDroidAttack(droid, target);
                    if (!target.isAlive()) {
                        team2.remove(target);
                    }
                }
            }

            for (BaseDroid droid : team2) {
                if (!droid.isAlive()) continue;

                List<BaseDroid> availableTargets = getAvailableTargets(team1);
                if (!availableTargets.isEmpty()) {
                    BaseDroid target = getRandomTarget(availableTargets);
                    performDroidAttack(droid, target);
                    if (!target.isAlive()) {
                        team1.remove(target);
                    }
                }
            }

            healTeam(team1);
            healTeam(team2);

            displayHealth(team1, team2);

            round++;
        }

        if (team1.isEmpty()) {
            displayWinner("КОМАНДА 2");
        } else {
            displayWinner("КОМАНДА 1");
        }
    }

    private void displayWinner(BaseDroid winner) {
        System.out.println("\n\u001B[32m" + winner.getName() + " ВИГРАВ бій!\u001B[0m");
        System.out.println("\n\u001B[32m=========== Бій завершено! ============\u001B[0m");
        FileWork.restoreConsoleStream();
    }

    private void displayWinner(String winner) {
        System.out.println("\n\u001B[32m" + winner + " ВИГРАЛА бій!\u001B[0m");
        System.out.println("\n\u001B[32m=========== Бій завершено! ============\u001B[0m");
        FileWork.restoreConsoleStream();
    }

    private void performDroidAttack(BaseDroid attacker, BaseDroid target) {
        if (attacker.isAlive()) {
            int damage = attacker.attack();
            target.takeDamage(damage);
        } else {
            System.out.println(attacker.getName() + " не може атакувати, оскільки він мертвий.");
        }
    }

    private void displayHealth(BaseDroid droid1, BaseDroid droid2) {
        System.out.println(" ------");
        System.out.println(" " + droid1.getName() + ": Здоров'я = " + droid1.getHealth());
        System.out.println(" " + droid2.getName() + ": Здоров'я = " + droid2.getHealth());
    }

    private void displayHealth(List<BaseDroid> team1, List<BaseDroid> team2) {
        System.out.println(" ------");
        if (!team1.isEmpty()) {
            System.out.println(" Команда 1 Здоров'я:");
            getTeamHealth(team1);
        }
        if (!team2.isEmpty()) {
            System.out.println(" Команда 2 Здоров'я:");
            getTeamHealth(team2);
        }
    }

    private void getTeamHealth(List<BaseDroid> team) {
        for (BaseDroid droid : team) {
            System.out.println("   " + droid.getName() + " (" + droid.getHealth() + ")");
        }
    }

    private BaseDroid getRandomTarget(List<BaseDroid> team) {
        Random random = new Random();
        return team.get(random.nextInt(team.size()));
    }

    private List<BaseDroid> getAvailableTargets(List<BaseDroid> team) {
        List<BaseDroid> availableTargets = new ArrayList<>();
        for (BaseDroid droid : team) {
            if (droid.isAlive()) {
                availableTargets.add(droid);
            }
        }
        return availableTargets;
    }

    private void healTeam(List<BaseDroid> team) {
        for (BaseDroid droid : team) {
            if (droid instanceof DroidMedic && droid.isAlive()) {
                for (BaseDroid otherDroid : team) {
                    if (otherDroid.isAlive() && otherDroid != droid) {
                        int health = otherDroid.getHealth();
                        if (health > 0 && health < 100) {
                            ((DroidMedic) droid).heal(otherDroid);
                        }
                    }
                }
            }
        }
    }
}
