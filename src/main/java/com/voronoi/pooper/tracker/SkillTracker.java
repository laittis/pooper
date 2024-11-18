package com.voronoi.pooper.tracker;

import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.util.TextUtil;

public class SkillTracker {
    private static long skillStartTime;
    private static int skillCount = 0;
    private static boolean skillInProgress = false;
    private static String currentSkill = null;
    private static String currentTarget = null;

    public static void onSkillStart() {
        skillStartTime = System.currentTimeMillis();
        skillCount++;
        skillInProgress = true;

        String message = TextUtil.BOLD + TextUtil.YELLOW + " --- SKILL START --- "  + TextUtil.RESET
                + " (" + TextUtil.YELLOW + skillCount + TextUtil.RESET + ")";
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSkillEnd() {
        long duration = System.currentTimeMillis() - skillStartTime;
        reset();

        String message = TextUtil.BOLD + TextUtil.BGYELLOW + " --- SKILL DONE --- " + TextUtil.RESET
                + " [" + TextUtil.YELLOW + TextUtil.formatDuration(duration) + TextUtil.RESET + "]";

        MessageManager.getInstance().printMessage(message);
    }

    public static void onSkillInterrupt() {
        reset();

        String message = TextUtil.BGRED + " --- SKILL INTERRUPTED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void onSkillStopped() {
        reset();

        String message = TextUtil.BGRED + " --- SKILL STOPPED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public static void setSkill(String skill, String target) {
        skillInProgress = true;
        currentSkill = skill;
        currentTarget = target;

        String message = TextUtil.BOLD + "'" + skill + "'" + TextUtil.RESET;
        if (target != null) {
            message += " -> " + TextUtil.BOLD + target + TextUtil.RESET;
        }

        MessageManager.getInstance().printMessage(message);
    }

    public static void reset() {
        skillStartTime = 0;
        skillInProgress = false;
        currentSkill = null;
        currentTarget = null;
    }

    public static void onSkillConceal() {
        if (skillInProgress) {
            String message = TextUtil.BOLD + TextUtil.GREEN + " - skill concealed - "  + TextUtil.RESET;
            MessageManager.getInstance().printMessage(message);
        }
    }
}

