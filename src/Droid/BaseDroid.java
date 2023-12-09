package Droid;

import java.util.Random;

public class BaseDroid {
    protected String name;
    protected int health;
    protected int damage;
    protected int maxHealth;

    public BaseDroid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public int getMaxHealth() { return maxHealth; }

    public Boolean isAlive(){
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public int attack() {
        return standardAttack();
    }

    public int standardAttack() {
        Random random = new Random();
        int damage = this.damage - random.nextInt(1, this.damage - (int)((double)1/2 * (double)this.damage));
        System.out.println(" Дроїдом " + this.name + " нанесено пошкодження: " + damage);
        return damage;
    }

    @Override
    public String toString() {
        return "\n Назва дроїда: " + this.name + "\n Тип дроїда: " + this.getClass().getSimpleName() + "\n Кількість здоров'я: " + this.health + "\n Кількість пошкоджень: " + this.damage;
    }
}
