package com.voronoi.pooper.module;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.tracker.SkillTracker;

public class CoreSkillModule extends MyModule {
    public CoreSkillModule() {
        super("GeneralSkillModule", "Tracks general skill usage and status.");
    }

    @Override
    public void init() {
        // Register triggers related to skills
        //registerCastInfoTrigger();
        registerSkillStartTrigger();
        //registerSkillEndTrigger();
        //registerSkillFailTrigger();
        //registerSkillFumbleTrigger();
        //registerSkillInterruptTrigger();

    }

    private void registerSkillStartTrigger() {
        registerTrigger(new MyTrigger(
                "skillStart",
                "Triggered when a skill starts.",
                "^You start concentrating on the skill\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillStart();
                },
                false, false, false, TriggerType.SCREEN
        ));
    }

}
