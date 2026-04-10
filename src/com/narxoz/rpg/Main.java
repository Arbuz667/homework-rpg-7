package com.narxoz.rpg;

import com.narxoz.rpg.boss.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.impl.AchievementTracker;
import com.narxoz.rpg.observer.impl.BattleLogger;
import com.narxoz.rpg.observer.impl.HeroStatusMonitor;
import com.narxoz.rpg.observer.impl.LootDropper;
import com.narxoz.rpg.observer.impl.PartySupport;
import com.narxoz.rpg.strategy.AggressiveStrategy;
import com.narxoz.rpg.strategy.BalancedStrategy;
import com.narxoz.rpg.strategy.DefensiveStrategy;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EventPublisher publisher = new EventPublisher();

        Hero warrior = new Hero("Warrior", 120, 30, 15, new AggressiveStrategy());
        Hero paladin = new Hero("Paladin", 100, 20, 25, new DefensiveStrategy());
        Hero ranger  = new Hero("Ranger",  90,  25, 10, new BalancedStrategy());

        List<Hero> party = Arrays.asList(warrior, paladin, ranger);

        DungeonBoss boss = new DungeonBoss("Cursed Lord", 300, 35, 12, publisher);

        publisher.register(boss);

        publisher.register(new BattleLogger());
        publisher.register(new AchievementTracker());
        publisher.register(new PartySupport(party));
        publisher.register(new HeroStatusMonitor(party));
        publisher.register(new LootDropper());

        // switch ranger strategy mid setup to show runtime switching
        ranger.setStrategy(new AggressiveStrategy());
        System.out.println("Ranger switched strategy to: " + ranger.getStrategy().getName());

        // run the encounter
        DungeonEngine engine = new DungeonEngine(party, boss, publisher);
        EncounterResult result = engine.run();

        // print results
        System.out.println("\n=== ENCOUNTER RESULT ===");
        System.out.println("Heroes won: " + result.isHeroesWon());
        System.out.println("Rounds played: " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
    }
}