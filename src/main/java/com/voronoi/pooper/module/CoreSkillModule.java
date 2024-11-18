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
        registerCastInfoTrigger();
        registerSkillStartTrigger();
        registerSkillEndTrigger();
        registerSkillFailTrigger();
        registerSkillFumbleTrigger();
        registerSkillInterruptTrigger();
        registerSkillStoppedTrigger();
        registerSkillConcealTrigger();

        // TODO: Kata, Focus

        // Register command triggers
        registerSkillReportCommandTrigger();
    }

    private void registerSkillReportCommandTrigger() {
        registerTrigger(new MyTrigger(
                "skillReport",
                "pooper skill report - toggles skill report on/off",
                "^pooper skill report$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.toggleSkillReport();
                },
                true, true, false, TriggerType.COMMAND
        ));
    }

    private void registerSkillConcealTrigger() {
        // ^You surreptitiously conceal your skill use.
        registerTrigger(new MyTrigger(
                "skillConceal",
                "Triggered when a skill is concealed.",
                "^You surreptitiously conceal your skill use\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillConceal();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerCastInfoTrigger() {
        registerTrigger(new MyTrigger(
                "castInfoSkill",
                "Triggered when a skill is used and cast info is displayed.",
                "^You are using \\'([a-z ]+)\\'.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.setSkill(matcher.group(1), null);
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "castInfoSkill",
                "Triggered when a skill is used and cast info is displayed.",
                "^You are using \\'([a-z ]+)\\' at \\'(.+?)\\'.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.setSkill(matcher.group(1), matcher.group(2));
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerSkillStartTrigger() {
        registerTrigger(new MyTrigger(
                "skillStart",
                "Triggered when a skill starts.",
                "^You start concentrating on the skill\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillStart();
                    batClientPlugin.getClientGUI().doCommand("@@cast info");
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerSkillEndTrigger() {
        registerTrigger(new MyTrigger(
                "skillEnd",
                "Triggered when a skill ends.",
                "^You are prepared to do the skill\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillEnd();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerSkillFailTrigger() {

    }

    private void registerSkillFumbleTrigger() {

    }

    private void registerSkillInterruptTrigger() {
        registerTrigger(new MyTrigger(
                "skillInterrupt",
                "Triggered when a skill is interrupted.",
                "^(Your movement prevents you from doing the skill|You lose your concentration and cannot do the skill)\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillInterrupt();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerSkillStoppedTrigger() {
        registerTrigger(new MyTrigger(
                "skillStopped1",
                "Triggered when a skill is stopped.",
                "^You (decide to change the skill to (a )?new one|stop concentrating on the skill and begin searching for a proper place to rest)\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "skillStopped2",
                "Triggered when a skill is stopped.",
                "^You break your skill attempt\\.$",
                (batClientPlugin, matcher) -> {
                    SkillTracker.onSkillStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }
}
