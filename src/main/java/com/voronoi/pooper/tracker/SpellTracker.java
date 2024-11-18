package com.voronoi.pooper.tracker;

import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class SpellTracker {
    private static long spellStartTime;
    private static int spellCount = 0;
    private static boolean spellInProgress = false;
    private static String currentSpell = null;
    private static String currentTarget = null;
    private static boolean hasCeremony = false;
    private static int currentRound = 0;
    private static boolean reportSpell = false;

    // TODO: Hardcoded list, should be configurable and loadable from a file
    // Correctly instantiate the list using ArrayList
    private static final List<String> nonReportableSpells = new ArrayList<>();

    // Static initializer block to populate the list
    static {
        nonReportableSpells.add("spider demon conjuration");
        nonReportableSpells.add("cure light wounds");
    }

    public static void setCeremony(boolean ceremony) {
        hasCeremony = ceremony;
    }

    public static void onSpellStart() {
        spellStartTime = System.currentTimeMillis();
        spellCount++;
        spellInProgress = true;

        String message = TextUtil.BOLD + TextUtil.YELLOW + " --- SPELL START --- "  + TextUtil.RESET
                + (hasCeremony ? " (" +TextUtil.BOLD + TextUtil.GREEN + "Ceremony" + TextUtil.RESET + ") ": "")
                + " (" + TextUtil.YELLOW + spellCount + TextUtil.RESET + ")";
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSpellEnd() {
        long duration = System.currentTimeMillis() - spellStartTime;
        reset();

        String message = TextUtil.BOLD + TextUtil.BGYELLOW + " --- SPELL DONE --- " + TextUtil.RESET
                + " [" + TextUtil.YELLOW + TextUtil.formatDuration(duration) + TextUtil.RESET + "]";

        MessageManager.getInstance().printMessage(message);
    }

    public static void onSpellInterrupt() {
        reset();

        String message = TextUtil.BGRED + " --- SPELL INTERRUPTED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSpellStopped() {
        reset();

        String message = TextUtil.BGRED + " --- SPELL STOPPED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSpellFail() {
        reset();

        String message = TextUtil.BGRED + " ### SPELL FAILED " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSpellFumble() {
        reset();

        String message = TextUtil.BGRED + " ### SPELL FUMBLED" + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void setSpell(String spell, String target) {
        spellInProgress = true;
        currentSpell = spell;
        currentTarget = target;

        String message = TextUtil.BOLD + "'" + spell + "'" + TextUtil.RESET;
        if (target != null) {
            message += " -> " + TextUtil.BOLD + target + TextUtil.RESET;
        }

        MessageManager.getInstance().printMessage(message);
    }

    public static void reset() {
        spellInProgress = false;
        spellStartTime = 0;
        currentSpell = null;
        currentTarget = null;
        hasCeremony = false;
        currentRound = 0;
    }

    public static void onSpellRounds(String spell, String rounds) {
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

    public static void onSpellConceal() {
        if (spellInProgress) {
            String message = TextUtil.BOLD + TextUtil.GREEN + " - spell concealed - " + TextUtil.RESET;
            MessageManager.getInstance().printMessage(message);
        }
    }

    public static void toggleSpellReport() {
        reportSpell = !reportSpell;
        String message = "Spell report is now " + (reportSpell ? TextUtil.BOLD + TextUtil.GREEN + "ON" + TextUtil.RESET : TextUtil.BOLD + TextUtil.RED + "OFF" + TextUtil.RESET);
        MessageManager.getInstance().printMessage(message);
    }
}

