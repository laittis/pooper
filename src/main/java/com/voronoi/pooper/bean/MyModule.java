package com.voronoi.pooper.bean;

import java.util.ArrayList;
import java.util.List;

public abstract class MyModule {
    private final String name;
    private final String description;
    private boolean enabled;
    protected final List<MyTrigger> triggers = new ArrayList<>();

    /**
     * Constructor for MyModule
     * @param name Name of the module
     * @param description Description of the module
     */
    public MyModule(String name, String description) {
        this.name = name;
        this.description = description;
        this.enabled = true; // Modules are enabled by default
    }

    /**
     * Register a trigger with the module
     * @param trigger Trigger to register
     */
    public void registerTrigger(MyTrigger trigger) {
        triggers.add(trigger);
    }

    /**
     * List of triggers for the module
     * @return List of triggers
     */
    public List<MyTrigger> getTriggers() {
        return triggers;
    }

    /**
     * Get the name of the module
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the module
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Check if the module is enabled
     * @return boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the module to be enabled or disabled
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void init();

    /**
     * Get help text for the module
     * @return String
     */
    public String getHelp() {
        StringBuilder helpText = new StringBuilder("--- " + name + " Help ---\n" + description + "\n");
        for (MyTrigger trigger : triggers) {
            helpText.append("Trigger: ").append(trigger.getName()).append(" - ").append(trigger.getDescription()).append("\n");
        }
        return helpText.toString();
    }
}
