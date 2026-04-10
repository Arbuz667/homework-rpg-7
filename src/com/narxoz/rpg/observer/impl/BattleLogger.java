package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameObserver;

public class BattleLogger implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        System.out.println("[LOG] " + event.getType()
                + " | source: " + event.getSourceName()
                + " | value: " + event.getValue());
    }
}