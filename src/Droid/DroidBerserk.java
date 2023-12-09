package Droid;

import java.util.Random;

public class DroidBerserk extends BaseDroid {
    private int charge;
    private int roundsToCharge;

    public DroidBerserk(String name, int health, int damage, int roundsToCharge) {
        super(name, health, damage);
        this.roundsToCharge = roundsToCharge;
        this.charge = 0;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getRoundsToCharge() {
        return roundsToCharge;
    }

    public void setRoundsToCharge(int roundsToCharge) {
        this.roundsToCharge = roundsToCharge;
    }

    @Override
    public int attack() {
        if (isCharged()) {
            return berserkAttack();
        } else {
            System.out.println(" " + this.name + " заряжається.");
            charge++;
            return standardAttack();
        }
    }

    private int berserkAttack() {
        Random random = new Random();
        int totalDamage = 0;
        int comboCount = 2 + random.nextInt(4);

        for (int i = 0; i < comboCount; i++) {
            int damage = random.nextInt(this.damage / 2, this.damage);
            totalDamage += damage;
        }

        System.out.println(" " + this.name + " виконує комбо з " + comboCount + " ударами і завдає " + totalDamage + " пошкодження.");
        charge = 0;

        return totalDamage;
    }

    private boolean isCharged() {
        return charge >= roundsToCharge;
    }

    @Override
    public String toString() {
        return super.toString() + "\n К-ть раундів до сильної атаки: " + roundsToCharge;
    }
}
