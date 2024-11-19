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

    /**
     * Constructor for MyTrigger
     * @param name Name of the trigger
     * @param description Description of the trigger
     * @param regexp Regular expression for the trigger
     * @param triggerBody Body of the trigger
     * @param isAction Whether the trigger is an action
     * @param isGag Whether the trigger is a gag
     * @param isExpand Whether the trigger should use the original text
     * @param triggerType Type of the trigger
     */
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

    /**
     * Regex matcher for the trigger
     * @param s
     * @return Matcher
     */
    public Matcher matcher(String s) {
        return pattern.matcher(s);
    }

    /**
     * Get the name of the trigger
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the trigger
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the trigger body
     * @return TriggerBody
     */
    public TriggerBody getTriggerBody() {
        return triggerBody;
    }

    /**
     * Get whether the trigger is an action
     * @return boolean
     */
    public boolean isAction() {
        return isAction;
    }

    /**
     * Get whether the trigger is a gag
     * @return boolean
     */
    public boolean isGag() {
        return isGag;
    }

    /**
     * Get whether the trigger should use the original text
     * @return boolean
     */
    public boolean isExpand() {
        return isExpand;
    }

    /**
     * Get the type of the trigger
     * @return TriggerType
     */
    public TriggerType getTriggerType() {
        return triggerType;
    }
}
