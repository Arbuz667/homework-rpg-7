package com.narxoz.rpg.observer.impl;

import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;

public class AchievementTracker implements GameObserver {

    private int attackCount = 0;
    private int heroDeathCount = 0;
    private boolean gotFirstBlood = false;
    private boolean gotRelentless = false;
    private boolean gotBossSlayer = false;
    private boolean gotNoManLeft = false;

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() == GameEventType.ATTACK_LANDED) {
            attackCount++;

            if (!gotFirstBlood && attackCount == 1) {
                System.out.println("[ACHIEVEMENT] \"First Blood\" - first attack of the battle!");
                gotFirstBlood = true;
            }

            if (!gotRelentless && attackCount >= 10) {
                System.out.println("[ACHIEVEMENT] \"Relentless\" - 10 attacks landed!");
                gotRelentless = true;
            }
        }

        if (event.getType() == GameEventType.HERO_DIED) {
            heroDeathCount++;
        }

        if (event.getType() == GameEventType.BOSS_DEFEATED) {
            if (!gotBossSlayer) {
                System.out.println("[ACHIEVEMENT] \"Boss Slayer\" - defeated the cursed boss!");
                gotBossSlayer = true;
            }

            if (!gotNoManLeft && heroDeathCount == 0) {
                System.out.println("[ACHIEVEMENT] \"No Man Left Behind\" - everyone survived!");
                gotNoManLeft = true;
            }
        }
    }
}