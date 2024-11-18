package com.voronoi.pooper.bean;

import java.util.ArrayList;
import java.util.List;

public abstract class MyModule {
    private final String name;
    private final String description;
    private boolean enabled;
    protected final List<MyTrigger> triggers = new ArrayList<>();

    public MyModule(String name, String description) {
        this.name = name;
        this.description = description;
        this.enabled = true; // Modules are enabled by default
    }

    public void registerTrigger(MyTrigger trigger) {
        triggers.add(trigger);
    }

    public List<MyTrigger> getTriggers() {
        return triggers;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void init();

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        StringBuilder helpText = new StringBuilder("--- " + name + " Help ---\n" + description + "\n");
        for (MyTrigger trigger : triggers) {
            helpText.append("Trigger: ").append(trigger.getName()).append(" - ").append(trigger.getDescription()).append("\n");
        }
        return helpText.toString();
    }
}
