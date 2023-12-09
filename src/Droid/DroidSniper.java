package Droid;

import java.util.Random;

public class DroidSniper extends BaseDroid {
    private double accuracy;
    private double range;

    public DroidSniper(String name, int health, int damage, double accuracy, double range) {
        super(name, health, damage);
        this.accuracy = accuracy;
        this.range = range;
    }

    public double getAccuracy() { return accuracy; }

    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }

    public double getRange() { return range; }

    public void setRange(double range) { this.range = range; }

    public int calculateDamageAtRange(double distance) {
        if (distance <= range) {
            double damage = (accuracy * getDamage()) * (1.0 - distance / range);
            return (int) damage;
        } else {
            System.out.println(" " + this.name + " не може влучити в ціль на такій відстані");
            return 0;
        }
    }

    public int attack() {
        Random random = new Random();
        double distanceToTarget = random.nextDouble() * range;

        int damage = calculateDamageAtRange(distanceToTarget);

        if (damage != 0) {
            System.out.println(" " + this.name + " наніс пошкодження " + damage + " на відстані " + (int)distanceToTarget);
        }

        return damage;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Точність: " + accuracy + "\n Дальність стрільби: " + range;
    }
}
