package com.voronoi.pooper.tracker;

import com.voronoi.pooper.annotation.Save;
import com.voronoi.pooper.bean.Tracker;
import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.util.TextUtil;

import java.util.HashSet;
import java.util.Set;

public class SpellTracker extends Tracker {
    private long spellStartTime;
    private boolean spellInProgress = false;
    private String currentSpell = null;
    private String currentTarget = null;
    private boolean hasCeremony = false;
    private int currentRound = 0;

    @Save
    private int spellCount = 0;

    @Save
    private boolean reportSpell = false;

    @Save
    private final Set<String> nonReportableSpells = new HashSet<>();

    public SpellTracker() {
        super();
        nonReportableSpells.add("spider demon conjuration");
    }

    /**
     * Set spell ceremony state
     * @param ceremony
     */
    public void setCeremony(boolean ceremony) {
        hasCeremony = ceremony;
    }

    /**
     * Spell started
     */
    public void onSpellStart() {
        spellStartTime = System.currentTimeMillis();
        spellCount++;
        spellInProgress = true;

        String message = TextUtil.BOLD + TextUtil.YELLOW + " --- SPELL START --- "  + TextUtil.RESET
                + (hasCeremony ? " (" +TextUtil.BOLD + TextUtil.GREEN + "Ceremony" + TextUtil.RESET + ") ": "")
                + " (" + TextUtil.YELLOW + spellCount + TextUtil.RESET + ")";
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Spell ended
     */
    public void onSpellEnd() {
        long duration = System.currentTimeMillis() - spellStartTime;
        reset();

        String message = TextUtil.BOLD + TextUtil.BGYELLOW + " --- SPELL DONE --- " + TextUtil.RESET
                + " [" + TextUtil.YELLOW + TextUtil.formatDuration(duration) + TextUtil.RESET + "]";

        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Spell interrupted
     */
    public void onSpellInterrupt() {
        reset();

        String message = TextUtil.BGRED + " --- SPELL INTERRUPTED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Spell stopped
     */
    public void onSpellStopped() {
        reset();

        String message = TextUtil.BGRED + " --- SPELL STOPPED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Spell failed
     */
    public void onSpellFail() {
        reset();

        String message = TextUtil.BGRED + " ### SPELL FAILED " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Spell fumbled
     */
    public void onSpellFumble() {
        reset();

        String message = TextUtil.BGRED + " ### SPELL FUMBLED" + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Set spell in progress
     * @param spell
     * @param target
     */
    public void setSpell(String spell, String target) {
        spellInProgress = true;
        currentSpell = spell;
        currentTarget = target;

        String message = TextUtil.BOLD + "'" + spell + "'" + TextUtil.RESET;
        if (target != null) {
            message += " -> " + TextUtil.BOLD + target + TextUtil.RESET;
        }

        MessageManager.getInstance().printMessage(message);
    }

    @Override
    public void reset() {
        spellInProgress = false;
        spellStartTime = 0;
        currentSpell = null;
        currentTarget = null;
        hasCeremony = false;
        currentRound = 0;
    }

    /**
     * Report spell rounds
     * @param spell
     * @param rounds
     */
    public void onSpellRounds(String spell, String rounds) {
        // spell name as string
        // rounds as string, rounds are represented by '#' characters
        // Print somethinging like "SpellName: #### [rounds]"
        String message = TextUtil.YELLOW + spell + ": " + rounds + TextUtil.RESET;
        int roundCount = rounds.length();
        if (roundCount > 0) {
            message += " [" + TextUtil.YELLOW + roundCount + TextUtil.RESET + "]";
        }
        MessageManager.getInstance().printMessage(message);
        currentRound = roundCount;

        // We could add reporting of rounds here to party report channel when rounds hit some nubmer, but what the heck
        if (reportSpell && roundCount > 0 && roundCount < 3) {
            // Normalize spell name to lower case for consistent comparison
            String spellLower = spell.toLowerCase();
            if (!nonReportableSpells.contains(spellLower)) {
                String report = spell + " in " + roundCount;
                MessageManager.getInstance().reportMessage(report);
            }
        }
    }

    /**
     * Report spell to party report channel
     */
    public void onSpellConceal() {
        if (spellInProgress) {
            String message = TextUtil.BOLD + TextUtil.GREEN + " - spell concealed - " + TextUtil.RESET;
            MessageManager.getInstance().printMessage(message);
        }
    }

    /**
     * Toggle spell report on/off
     */
    public void toggleSpellReport() {
        reportSpell = !reportSpell;
        String message = "Spell report is now " + (reportSpell ? TextUtil.BOLD + TextUtil.GREEN + "ON" + TextUtil.RESET : TextUtil.BOLD + TextUtil.RED + "OFF" + TextUtil.RESET);
        MessageManager.getInstance().printMessage(message);
    }

    /**
     * Add a spell to the non-reportable list
     * @param spell
     */
    public void addNonReportableSpell(String spell) {
        if (spell == null) {
            return;
        }
        nonReportableSpells.add(spell.toLowerCase());
    }

    /**
     * Remove a spell from the non-reportable list
     * @param spell
     */
    public void removeNonReportableSpell(String spell) {
        if (spell == null) {
            return;
        }
        nonReportableSpells.remove(spell.toLowerCase());
    }
}

