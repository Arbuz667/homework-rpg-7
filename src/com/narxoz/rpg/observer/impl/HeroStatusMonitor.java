package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {

    private List<Hero> party;

    public HeroStatusMonitor(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP
                && event.getType() != GameEventType.HERO_DIED) {
            return;
        }

        System.out.println("[STATUS MONITOR] Current party status:");
        for (Hero h : party) {
            if (h.isAlive()) {
                System.out.println("  " + h.getName() + " - HP: " + h.getHp() + "/" + h.getMaxHp());
            } else {
                System.out.println("  " + h.getName() + " - DEAD");
            }
        }
    }
}