package com.narxoz.rpg.combatant;

import com.narxoz.rpg.strategy.CombatStrategy;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private CombatStrategy strategy;   // added for hw7

    public Hero(String name, int hp, int attackPower, int defense, CombatStrategy strategy) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.strategy = strategy;
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }

    public CombatStrategy getStrategy()             { return strategy; }
    public void setStrategy(CombatStrategy strategy) { this.strategy = strategy; }

    public void takeDamage(int amount) {
        hp = hp - amount;
        if (hp < 0) hp = 0;
    }

    public void heal(int amount) {
        hp = hp + amount;
        if (hp > maxHp) hp = maxHp;
    }
}