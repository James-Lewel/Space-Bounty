package com.example.spacebounty;

public class Spacecraft_Stats {
    private int level = 0;

    private double hp;
    private double maxHp;

    private double attack;
    private double defense;
    private double speed;

    Spacecraft_Stats(double maxHp, double attack, double defense, double speed)
    {
        this.hp = maxHp;
        this.maxHp = maxHp;

        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public double getHp() { return hp; }
    public void setHp(double hp) { this.hp = hp; }

    public double getMaxHp() { return maxHp; }
    public void setMaxHp(double maxHp) { this.maxHp = maxHp; }

    public double getAttack() { return attack; }
    public void setAttack(double attack) { this.attack = attack; }

    public double getDefense() { return defense; }
    public void setDefense(double defense) { this.defense = defense; }

    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

}
