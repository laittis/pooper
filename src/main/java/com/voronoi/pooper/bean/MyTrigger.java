package com.voronoi.pooper.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTrigger {
    private String name;
    private String description;
    private final Pattern pattern;
    private final TriggerBody triggerBody;
    private final boolean isAction;
    private final boolean isGag;
    private final boolean isExpand; // Use original text if true
    private final TriggerType triggerType;

    public MyTrigger(String name, String description, String regexp, TriggerBody triggerBody, boolean isAction, boolean isGag, boolean isExpand, TriggerType triggerType) {
        this.name = name;
        this.description = description;
        this.pattern = Pattern.compile(regexp);
        this.triggerBody = triggerBody;
        this.isAction = isAction;
        this.isGag = isGag;
        this.isExpand = isExpand;
        this.triggerType = triggerType;
    }

    public Matcher matcher(String s) {
        return pattern.matcher(s);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TriggerBody getTriggerBody() {
        return triggerBody;
    }

    public boolean isAction() {
        return isAction;
    }

    public boolean isGag() {
        return isGag;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }
}
