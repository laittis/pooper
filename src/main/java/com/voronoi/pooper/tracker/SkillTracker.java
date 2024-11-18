package com.voronoi.pooper.tracker;

public class SkillTracker {
    private static long skillStartTime;
    private static int skillCount = 0;
    private static boolean skillInProgress = false;

    public static void onSkillStart() {
        skillStartTime = System.currentTimeMillis();
        skillCount++;
        skillInProgress = true;
        printSkillStartMessage();

    }

    public static void onSkillEnd() {
        long duration = System.currentTimeMillis() - skillStartTime;
        skillInProgress = false;
        printSkillEndMessage(duration);
    }

    private static void printSkillStartMessage() {

    }

    private static void printSkillEndMessage(long duration) {

    }
}

