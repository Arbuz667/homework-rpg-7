package com.narxoz.rpg.boss;

import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.BossPhase1Strategy;
import com.narxoz.rpg.strategy.BossPhase2Strategy;
import com.narxoz.rpg.strategy.BossPhase3Strategy;
import com.narxoz.rpg.strategy.CombatStrategy;

public class DungeonBoss implements GameObserver {

    private String name;
    private int hp;
    private int maxHp;
    private int attackPower;
    private int defense;
    private int currentPhase;
    private CombatStrategy strategy;
    private EventPublisher publisher;

    private CombatStrategy phase1Strategy = new BossPhase1Strategy();
    private CombatStrategy phase2Strategy = new BossPhase2Strategy();
    private CombatStrategy phase3Strategy = new BossPhase3Strategy();

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventPublisher publisher) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.publisher = publisher;
        this.currentPhase = 1;
        this.strategy = phase1Strategy;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.BOSS_PHASE_CHANGED) {
            return;
        }

        int newPhase = event.getValue();

        // dont switch if already in this phase
        if (newPhase == currentPhase) {
            return;
        }

        currentPhase = newPhase;

        if (newPhase == 2) {
            strategy = phase2Strategy;
        } else if (newPhase == 3) {
            strategy = phase3Strategy;
        }

        System.out.println("[BOSS] " + name + " switches to " + strategy.getName()
                + " (phase " + currentPhase + ")");
    }

    public void takeDamage(int amount) {
        int hpBefore = hp;
        hp = hp - amount;
        if (hp < 0) {
            hp = 0;
        }

        int threshold60 = (int)(maxHp * 0.6);
        if (hpBefore > threshold60 && hp <= threshold60 && currentPhase < 2) {
            publisher.publish(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 2));
        }

        int threshold30 = (int)(maxHp * 0.3);
        if (hpBefore > threshold30 && hp <= threshold30 && currentPhase < 3) {
            publisher.publish(new GameEvent(GameEventType.BOSS_PHASE_CHANGED, name, 3));
        }

        if (hp == 0) {
            publisher.publish(new GameEvent(GameEventType.BOSS_DEFEATED, name, 0));
        }
    }

    public void fireEvent(GameEvent event) {
        publisher.publish(event);
    }

    public String getName()             { return name; }
    public int getHp()                  { return hp; }
    public int getMaxHp()               { return maxHp; }
    public int getAttackPower()         { return attackPower; }
    public int getDefense()             { return defense; }
    public boolean isAlive()            { return hp > 0; }
    public int getCurrentPhase()        { return currentPhase; }
    public CombatStrategy getStrategy() { return strategy; }
}