package Droid;

import java.util.ArrayList;
import java.util.List;

public class DroidStealth extends BaseDroid {
    private int invisibilityDuration;
    private double stealthSpeed;

    public DroidStealth(String name, int health, int damage, int invisibilityDuration, double stealthSpeed) {
        super(name, health, damage);
        this.invisibilityDuration = invisibilityDuration;
        this.stealthSpeed = stealthSpeed;
    }

    public int getInvisibilityDuration() { return invisibilityDuration; }

    public void setInvisibilityDuration(int invisibilityDuration) { this.invisibilityDuration = invisibilityDuration; }

    public double getStealthSpeed() { return stealthSpeed; }

    public void setStealthSpeed(double stealthSpeed) { this.stealthSpeed = stealthSpeed; }

    public void activateStealth(int duration, double speed) {
        System.out.println(" " + getName() + " активує режим невидимості з тривалістю " + duration + " і швидкістю " + speed);
    }

    // Метод для виявлення невидимих супротивників у битві
    public List<BaseDroid> detectInvisibleEnemies(List<BaseDroid> enemies) {
        List<BaseDroid> invisibleEnemies = new ArrayList<>();
        for (BaseDroid enemy : enemies) {
            if (enemy instanceof DroidStealth && ((DroidStealth) enemy).getInvisibilityDuration() > 0) {
                invisibleEnemies.add(enemy);
            }
        }
        return invisibleEnemies;
    }

    // Метод для уникнення атаки невидимих супротивників під час невидимості
    public void avoidInvisibleEnemyAttacks(List<BaseDroid> invisibleEnemies) {
        for (BaseDroid enemy : invisibleEnemies) {
            if (Math.random() < 0.3) {
                System.out.println(" " + getName() + " уникає атаку від невидимого супротивника " + enemy.getName());
            }
        }
    }

    public int attack() {
        if (invisibilityDuration > 0) {
            double damage = stealthSpeed * getDamage();
            System.out.println(" " + getName() + " атакує в режимі невидимості і завдає " + (int) damage + " пошкоджень ");
            invisibilityDuration--;
            return (int) damage;
        } else {
            standardAttack();
            return 0;
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n Тривалість невидимості: " + invisibilityDuration + "\n Швидкість невидимості: " + stealthSpeed;
    }
}

