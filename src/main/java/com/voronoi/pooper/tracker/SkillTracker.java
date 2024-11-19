package com.voronoi.pooper.tracker;

import com.voronoi.pooper.annotation.Save;
import com.voronoi.pooper.bean.Tracker;
import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.util.TextUtil;

public class SkillTracker extends Tracker {
    private long skillStartTime;
    private boolean skillInProgress = false;
    private String currentSkill = null;
    private String currentTarget = null;

    @Save
    private int skillCount = 0;

    @Save
    private boolean reportSkill = false;

    public SkillTracker() {
        super();
    }

    public void onSkillStart() {
        skillStartTime = System.currentTimeMillis();
        skillCount++;
        skillInProgress = true;

        String message = TextUtil.BOLD + TextUtil.YELLOW + " --- SKILL START --- "  + TextUtil.RESET
                + " (" + TextUtil.YELLOW + skillCount + TextUtil.RESET + ")";
        MessageManager.getInstance().printMessage(message);

    }

    public void onSkillEnd() {
        long duration = System.currentTimeMillis() - skillStartTime;
        reset();

        String message = TextUtil.BOLD + TextUtil.BGYELLOW + " --- SKILL DONE --- " + TextUtil.RESET
                + " [" + TextUtil.YELLOW + TextUtil.formatDuration(duration) + TextUtil.RESET + "]";

        MessageManager.getInstance().printMessage(message);
    }

    public void onSkillInterrupt() {
        reset();

        String message = TextUtil.BGRED + " --- SKILL INTERRUPTED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public void onSkillStopped() {
        reset();

        String message = TextUtil.BGRED + " --- SKILL STOPPED --- " + TextUtil.RESET;
        MessageManager.getInstance().printMessage(message);
    }

    public void setSkill(String skill, String target) {
        skillInProgress = true;
        currentSkill = skill;
        currentTarget = target;

        String message = TextUtil.BOLD + "'" + skill + "'" + TextUtil.RESET;
        if (target != null) {
            message += " -> " + TextUtil.BOLD + target + TextUtil.RESET;
        }

        MessageManager.getInstance().printMessage(message);

        if (reportSkill) {
            String report = currentSkill;
            if (currentTarget != null) {
                report += " -> " + currentTarget;
            }
            MessageManager.getInstance().reportMessage(report);
        }
    }

    @Override
    public void reset() {
        skillStartTime = 0;
        skillInProgress = false;
        currentSkill = null;
        currentTarget = null;
    }

    public void onSkillConceal() {
        if (skillInProgress) {
            String message = TextUtil.BOLD + TextUtil.GREEN + " - skill concealed - "  + TextUtil.RESET;
            MessageManager.getInstance().printMessage(message);
        }
    }

    public void toggleSkillReport() {
        reportSkill = !reportSkill;
        String message = "Skill report is now " + (reportSkill ? TextUtil.BOLD + TextUtil.GREEN + "ON" + TextUtil.RESET : TextUtil.BOLD + TextUtil.RED + "OFF" + TextUtil.RESET);
        MessageManager.getInstance().printMessage(message);
    }
}

