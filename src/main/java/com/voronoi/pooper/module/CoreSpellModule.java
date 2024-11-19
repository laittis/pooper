package com.voronoi.pooper.module;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.tracker.SpellTracker;

public class CoreSpellModule extends MyModule {
    private final SpellTracker spellTracker;
    public CoreSpellModule() {
        super("GeneralSpellModule", "Tracks general spell usage and status. Command: 'pooper spell'");
        spellTracker = new SpellTracker();
    }

    @Override
    public void init() {
        // Register triggers related to spells
        registerSpellStartTrigger();
        registerSpellEndTrigger();
        registerSpellFailTrigger();
        registerSpellFumbleTrigger();
        registerSpellInterruptTrigger();
        registerSpellStoppedTrigger();
        registerCeremonyTrigger();
        registerCastInfoTrigger();
        registerSpellRoundsTrigger();
        registerSpellConcealTrigger();

        // Register command triggers
        registerSpellModuleCommands();
    }

    /**
     * Registers the command triggers for the spell module.
     */
    private void registerSpellModuleCommands() {
        registerTrigger(new MyTrigger(
                "spellReport",
                "pooper spell report - toggles spell report on/off",
                "^pooper spell report$",
                (batClientPlugin, matcher) -> {
                    spellTracker.toggleSpellReport();
                },
                true, true, false, TriggerType.COMMAND
        ));

        registerTrigger(new MyTrigger(
                "spellAddNonReportable",
                "pooper spell exclude <spell> - adds a spell to the non-reportable list",
                "^pooper spell exclude ([a-z ]+)$",
                (batClientPlugin, matcher) -> {
                    spellTracker.addNonReportableSpell(matcher.group(1));
                },
                true, true, false, TriggerType.COMMAND
        ));

        registerTrigger(new MyTrigger(
                "spellRemoveNonReportable",
                "pooper spell include <spell> - removes a spell from the non-reportable list",
                "^pooper spell include ([a-z ]+)$",
                (batClientPlugin, matcher) -> {
                    spellTracker.removeNonReportableSpell(matcher.group(1));
                },
                true, true, false, TriggerType.COMMAND
        ));
    }

    /**
     * Registers the trigger for when a spell is concealed.
     */
    private void registerSpellConcealTrigger() {
        registerTrigger(new MyTrigger(
                "spellConceal",
                "Triggered when a spell is concealed.",
                "^You surreptitiously conceal your spell casting\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellConceal();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when spell rounds are displayed.
     */
    private void registerSpellRoundsTrigger() {
        registerTrigger(new MyTrigger(
                "spellRounds",
                "Triggered when a spell rounds are displayed.",
                "^([A-Z][a-z ]+): (#+)$",
                (batClientPlugin, matcher) -> {
                    // Spell name, rounds
                    spellTracker.onSpellRounds(matcher.group(1), matcher.group(2));
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell starts.
     */
    private void registerCastInfoTrigger() {
        registerTrigger(new MyTrigger(
                "castInfo1",
                "Triggered when a spell is cast.",
                "^You are casting '([a-z ]+)'\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.setSpell(matcher.group(1), null);
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "castInfo2",
                "Triggered when a spell is cast.",
                "^You are casting '([a-z ]+)' at '([A-Za-z0-9_ ,.'-]+)'\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.setSpell(matcher.group(1), matcher.group(2));
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a ceremony is performed.
     */
    private void registerCeremonyTrigger() {
        registerTrigger(new MyTrigger(
                "ceremony",
                "Triggered when a ceremony is performed.",
                "^You perform the ceremony\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.setCeremony(true);
                },
                false, false, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell starts.
     */
    private void registerSpellStartTrigger() {
        registerTrigger(new MyTrigger(
                "spellStart",
                "Triggered when a spell starts.",
                "^You start chanting\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellStart();
                    batClientPlugin.getClientGUI().doCommand("@@cast info");
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell ends.
     */
    private void registerSpellEndTrigger() {
        registerTrigger(new MyTrigger(
                "spellEnd",
                "Triggered when a spell ends.",
                "^You are done with the chant\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellEnd();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell fails.
     */
    private void registerSpellFailTrigger() {
        registerTrigger(new MyTrigger(
                "spellFail1",
                "Triggered when a spell fails.",
                "^You (fail miserably in your|stutter the magic words and fail the) spell\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFail2",
                "Triggered when a spell fails.",
                "^You .* (spell misfires|spell fizzles)\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFail3",
                "Triggered when a spell fails.",
                "^You stumble and lose your concentration\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFail4",
                "Triggered when a spell fails.",
                "^Your (spell just sputters|concentration fails and so does your spell|mind plays a trick with you and you fail in your spell|concentration drifts away as you think you feel a malignant aura)\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFail5",
                "Triggered when a spell fails.",
                "^Something touches you and spoils your concentration ruining the spell\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFail6",
                "Triggered when a spell fails.",
                "^The spell fails\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFail();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell fumbles.
     */
    private void registerSpellFumbleTrigger() {
        registerTrigger(new MyTrigger(
                "spellFumble1",
                "Triggered when a spell fumbles.",
                "^You fumble the spell\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFumble();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellFumble2",
                "Triggered when a spell fumbles.",
                "^You falter and fumble the spell. Amazingly it fires upon ",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellFumble();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    /**
     * Registers the trigger for when a spell is interrupted.
     */
    private void registerSpellInterruptTrigger() {
        registerTrigger(new MyTrigger(
                "spellInterrupt1",
                "Triggered when a spell is interrupted.",
                "^You(r movement prevents you from casting| have insufficient strength to cast| lose your concentration and cannot cast) the spell\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellInterrupt();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellInterrupt2",
                "Triggered when a spell is interrupted.",
                "^You (get hit SO HARD that you have to stop your spell|lose your concentration and stop your spell casting|massage your wounds and forget your spell)\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellInterrupt();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellInterrupt3",
                "Triggered when a spell is interrupted.",
                "^The ground shakes violently! EARTHQUAKE!$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellInterrupt();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }

    private void registerSpellStoppedTrigger() {
        registerTrigger(new MyTrigger(
                "spellStopped1",
                "Triggered when a spell is stopped.",
                "^You interrupt the (spell|chant in order to start a new chant)\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));

        registerTrigger(new MyTrigger(
                "spellStopped2",
                "Triggered when a spell is stopped.",
                "^You stop concentrating on the spell and begin searching for a proper place to rest\\.$",
                (batClientPlugin, matcher) -> {
                    spellTracker.onSpellStopped();
                },
                false, true, false, TriggerType.SCREEN
        ));
    }
}
