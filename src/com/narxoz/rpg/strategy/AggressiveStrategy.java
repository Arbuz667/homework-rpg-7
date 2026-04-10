package com.narxoz.rpg.strategy;

public class AggressiveStrategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        int result = basePower + (basePower / 2);
        return result;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        int result = (int)(baseDefense * 0.7);
        return result;
    }

    @Override
    public String getName() {
        return "Aggressive";
    }
}