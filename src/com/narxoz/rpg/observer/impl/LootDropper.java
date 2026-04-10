package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.Random;

public class LootDropper implements GameObserver {

    private String[] normalLoot = {"Iron Shield", "Mana Potion", "Worn Gauntlets", "Shadow Cloak"};
    private String[] bossLoot = {"Cursed Crown", "Legendary Axe", "Dragon Armor", "Ring of Oblivion"};
    private Random rng = new Random();

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.BOSS_PHASE_CHANGED) {
            String item = normalLoot[rng.nextInt(normalLoot.length)];
            System.out.println("[LOOT] Phase change drop: " + item);
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            String item = bossLoot[rng.nextInt(bossLoot.length)];
            System.out.println("[LOOT] Boss defeated! Legendary drop: " + item);
        }
    }
}