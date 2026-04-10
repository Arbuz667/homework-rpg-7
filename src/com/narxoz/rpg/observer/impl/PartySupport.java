package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartySupport implements GameObserver {

    private List<Hero> heroes;
    private Random random = new Random();
    private static final int HEAL = 30;

    public PartySupport(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP) {
            return;
        }

        List<Hero> alive = new ArrayList<>();
        for (Hero h : heroes) {
            if (h.isAlive()) {
                alive.add(h);
            }
        }

        if (alive.size() == 0) {
            return;
        }

        int idx = random.nextInt(alive.size());
        Hero target = alive.get(idx);
        target.heal(HEAL);

        System.out.println("[PARTY SUPPORT] " + event.getSourceName()
                + " is low! Healed " + target.getName()
                + " for " + HEAL + " HP (now " + target.getHp() + ")");
    }
}