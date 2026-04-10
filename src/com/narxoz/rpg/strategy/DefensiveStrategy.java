package com.narxoz.rpg.strategy;

public class DefensiveStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        int dmg = (int)(basePower * 0.7);
        return dmg;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        int def = baseDefense + (baseDefense / 2);
        return def;
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}