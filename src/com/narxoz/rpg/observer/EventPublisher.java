package com.narxoz.rpg.observer;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private List<GameObserver> observers = new ArrayList<>();

    public void register(GameObserver o) {
        observers.add(o);
    }

    public void publish(GameEvent event) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onEvent(event);
        }
    }
}