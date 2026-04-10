package com.narxoz.rpg.strategy;

public class BossPhase3Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        // double damage, basically ignores defense
        int dmg = basePower * 2;
        return dmg;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        // barely defending anymore
        int def = (int)(baseDefense * 0.3);
        return def;
    }

    @Override
    public String getName() {
        return "Phase3-Desperate";
    }
}