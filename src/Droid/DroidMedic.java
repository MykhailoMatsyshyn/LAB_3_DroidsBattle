package Droid;

public class DroidMedic extends BaseDroid {
    private int healingAmount;
    private int selfHealingLimit;

    public DroidMedic(String name, int health, int damage, int healingAmount, int selfHealingLimit) {
        super(name, health, damage);
        this.healingAmount = healingAmount;
        this.selfHealingLimit = selfHealingLimit;
    }

    public int getHealingAmount() { return healingAmount; }

    public void setHealingAmount(int healingAmount) { this.healingAmount = healingAmount; }

    public void heal(BaseDroid target) {
        int currentHealth = target.getHealth();
        int previousHealth = currentHealth;
        currentHealth += healingAmount;
        if (currentHealth > target.getMaxHealth()) {
            currentHealth = target.getMaxHealth();
        }
        target.setHealth(currentHealth);

        if (target.equals(this)) {
            System.out.println(" " + this.name + " лікує себе і відновлює " + (currentHealth - previousHealth) + " здоров'я. Поточне здоров'я: " + currentHealth);
        } else {
            System.out.println(" " + this.name + " лікує " + target.getName() + " і відновлює " + (currentHealth - previousHealth) + " здоров'я."); // ... Поточне здоров'я " + target.getName() + ": " + currentHealth
        }
    }

    public void selfHealing() {
        if (selfHealingLimit > 0) {
            heal(this);
            selfHealingLimit--;
        } else {
            System.out.println(" " + this.name + " більше не може лікувати себе в цьому бою.");
        }
    }

    public int attack() {
        selfHealing();
        return standardAttack();
    }

    @Override
    public String toString() {
        return super.toString() + "\n Кількість лікування: " + healingAmount + "\n Можлива к-ть лікування себе:" + selfHealingLimit;
    }
}

