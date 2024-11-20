package com.voronoi.pooper.module;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.tracker.SkillTracker;

public class CoreSkillModule extends MyModule {
    private final SkillTracker skillTracker;

    public CoreSkillModule() {
        super("GeneralSkillModule", "Tracks general skill usage and status. Command: 'pooper skill'");
        skillTracker = new SkillTracker();
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
        registerSkillModuleCommands();
    }

    /**
     * Registers the command triggers for the skill module.
     */
    private void registerSkillModuleCommands() {
        registerTrigger(new MyTrigger(
                "skillReport",
                "pooper skill report - toggles skill report on/off",
                "^pooper skill report$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.toggleSkillReport();
                },
                true, true, false, TriggerType.COMMAND
        ));
    }

    /**
     * Registers the triggers related to skill concealment.
     */
    private void registerSkillConcealTrigger() {
        // ^You surreptitiously conceal your skill use.
        registerTrigger(new MyTrigger(
                "skillConceal",
                "Triggered when a skill is concealed.",
                "^You surreptitiously conceal your skill use\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillConceal();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the triggers related to skill casting information.
     */
    private void registerCastInfoTrigger() {
        registerTrigger(new MyTrigger(
                "castInfoSkill",
                "Triggered when a skill is used and cast info is displayed.",
                "^You are using \\'([a-z ]+)\\'.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.setSkill(matcher.group(1), null);
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "castInfoSkill",
                "Triggered when a skill is used and cast info is displayed.",
                "^You are using \\'([a-z ]+)\\' at \\'(.+?)\\'.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.setSkill(matcher.group(1), matcher.group(2));
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the triggers related to skill start.
     */
    private void registerSkillStartTrigger() {
        registerTrigger(new MyTrigger(
                "skillStart",
                "Triggered when a skill starts.",
                "^You start concentrating on the skill\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillStart();
                    batClientPlugin.getClientGUI().doCommand("@@cast info");
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the triggers related to skill end.
     */
    private void registerSkillEndTrigger() {
        registerTrigger(new MyTrigger(
                "skillEnd",
                "Triggered when a skill ends.",
                "^You are prepared to do the skill\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillEnd();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the triggers related to skill fail.
     */
    private void registerSkillFailTrigger() {

    }

    /**
     * Registers the triggers related to skill fumble.
     */
    private void registerSkillFumbleTrigger() {

    }

    /**
     * Registers the triggers related to skill interrupt.
     */
    private void registerSkillInterruptTrigger() {
        registerTrigger(new MyTrigger(
                "skillInterrupt",
                "Triggered when a skill is interrupted.",
                "^(Your movement prevents you from doing the skill|You lose your concentration and cannot do the skill)\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillInterrupt();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the triggers related to skill stopped.
     */
    private void registerSkillStoppedTrigger() {
        registerTrigger(new MyTrigger(
                "skillStopped1",
                "Triggered when a skill is stopped.",
                "^You (decide to change the skill to (a )?new one|stop concentrating on the skill and begin searching for a proper place to rest)\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "skillStopped2",
                "Triggered when a skill is stopped.",
                "^You break your skill attempt\\.$",
                (batClientPlugin, matcher, parsedResult) -> {
                    skillTracker.onSkillStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }
}
