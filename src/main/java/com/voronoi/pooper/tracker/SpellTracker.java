package com.voronoi.pooper.tracker;

import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.util.TextUtil;

public class SpellTracker {
    private static long spellStartTime;
    private static int spellCount = 0;
    private static boolean spellInProgress = false;
    private static String currentSpell = null;
    private static String currentTarget = null;
    private static boolean hasCeremony = false;

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

        // We could add reporting of rounds here to party report channel when rounds hit some nubmer, but what the heck
    }

    public static void onSpellConceal() {
        if (spellInProgress) {
            String message = TextUtil.BOLD + TextUtil.GREEN + " - spell concealed - " + TextUtil.RESET;
            MessageManager.getInstance().printMessage(message);
        }
    }
}

