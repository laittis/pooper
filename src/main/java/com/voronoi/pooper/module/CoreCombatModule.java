package com.voronoi.pooper.module;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.tracker.CombatTracker;

public class CoreCombatModule extends MyModule {
    public CoreCombatModule() {
        super("CoreCombatModule", "Tracks combat-related events.");
    }

    @Override
    public void init() {
        // Register triggers related to combat
        registerCombatRoundTrigger();
        registerCombatEndTrigger();
        registerCombatTargetTrigger();
    }
    // ^\*{10,25} (Round \d+|Round \d+ \(\d+\)) \*{10,25}$

    private void registerCombatRoundTrigger() {
        registerTrigger(new MyTrigger(
                "combatRound",
                "Triggered at the start of each combat round.",
                "^\\*{10,25} (Round \\d+|Round \\d+ \\(\\d+\\)) \\*{10,25}$",
                (batClientPlugin, matcher) -> {
                    // if round 1 then onCombatStart
                    if (matcher.group(1).contains("Round 1")) {
                        CombatTracker.onCombatStart();
                    }
                    CombatTracker.onCombatRound();
                },
                false, false, false, TriggerType.SCREEN
        ));
    }

    private void registerCombatEndTrigger() {
        registerTrigger(new MyTrigger(
                "combatEnd",
                "Triggered at the end of combat.",
                "^You are not in combat right now\\.$",
                (batClientPlugin, matcher) -> {
                    CombatTracker.onCombatEnd();
                },
                false, false, false, TriggerType.SCREEN
        ));
    }

    // ^You are now targetting ([A-Za-z ,.'-]+)\.$
    private void registerCombatTargetTrigger() {
        registerTrigger(new MyTrigger(
                "combatTarget",
                "Triggered when you target a new enemy.",
                "^You are now targetting ([A-Za-z ,.'-]+)\\.$",
                (batClientPlugin, matcher) -> {
                    CombatTracker.onCombatTarget(matcher.group(1));
                },
                false, false, false, TriggerType.SCREEN
        ));
    }
}
