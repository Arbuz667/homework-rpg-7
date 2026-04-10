package com.narxoz.rpg.engine;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DungeonEngine {

    private List<Hero> heroes;
    private DungeonBoss boss;
    private EventPublisher publisher;
    private Set<String> lowHpAlreadyFired = new HashSet<>();
    private static final int MAX_ROUNDS = 50;

    public DungeonEngine(List<Hero> heroes, DungeonBoss boss, EventPublisher publisher) {
        this.heroes = heroes;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult run() {
        int round = 0;

        while (round < MAX_ROUNDS) {
            if (!boss.isAlive()) break;
            if (!anyoneAlive()) break;

            round++;
            System.out.println("\n--- Round " + round + " ---");

            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;

                int rawDmg = hero.getStrategy().calculateDamage(hero.getAttackPower());
                int bossDef = boss.getStrategy().calculateDefense(boss.getDefense());
                int dmg = rawDmg - bossDef;
                if (dmg < 1) dmg = 1;

                boss.takeDamage(dmg);

                publisher.publish(new GameEvent(GameEventType.ATTACK_LANDED, hero.getName(), dmg));
                System.out.println("  " + hero.getName() + " hits " + boss.getName()
                        + " for " + dmg + " dmg | boss hp: " + boss.getHp()
                        + " | boss strategy: " + boss.getStrategy().getName());

                if (!boss.isAlive()) break;
            }

            if (!boss.isAlive()) break;

            for (Hero hero : heroes) {
                if (!hero.isAlive()) continue;

                int rawDmg = boss.getStrategy().calculateDamage(boss.getAttackPower());
                int heroDef = hero.getStrategy().calculateDefense(hero.getDefense());
                int dmg = rawDmg - heroDef;
                if (dmg < 1) dmg = 1;

                hero.takeDamage(dmg);

                publisher.publish(new GameEvent(GameEventType.ATTACK_LANDED, boss.getName(), dmg));
                System.out.println("  " + boss.getName() + " hits " + hero.getName()
                        + " for " + dmg + " dmg | hero hp: " + hero.getHp());

                // check hero conditions
                if (!hero.isAlive()) {
                    publisher.publish(new GameEvent(GameEventType.HERO_DIED, hero.getName(), 0));
                    System.out.println("  " + hero.getName() + " has died!");
                } else {
                    double hpPercent = (double) hero.getHp() / hero.getMaxHp();
                    if (hpPercent < 0.3 && !lowHpAlreadyFired.contains(hero.getName())) {
                        lowHpAlreadyFired.add(hero.getName());
                        publisher.publish(new GameEvent(GameEventType.HERO_LOW_HP,
                                hero.getName(), hero.getHp()));
                    }
                }
            }
        }

        boolean won = !boss.isAlive();
        int survivors = 0;
        for (Hero h : heroes) {
            if (h.isAlive()) survivors++;
        }

        return new EncounterResult(won, round, survivors);
    }

    private boolean anyoneAlive() {
        for (Hero h : heroes) {
            if (h.isAlive()) return true;
        }
        return false;
    }
}