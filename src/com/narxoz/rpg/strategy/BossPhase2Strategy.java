package com.narxoz.rpg.strategy;

public class BossPhase2Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        int dmg = basePower + (int)(basePower * 0.4);
        return dmg;
    }

    @Override
    public int calculateDefense(int baseDefense) {
        // slightly less careful with defense now
        int def = (int)(baseDefense * 0.8);
        return def;
    }

    @Override
    public String getName() {
        return "Phase2-Aggressive";
    }
}