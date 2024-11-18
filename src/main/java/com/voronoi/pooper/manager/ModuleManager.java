package com.voronoi.pooper.manager;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final ModuleManager instance = new ModuleManager();
    private final List<MyModule> modules = new ArrayList<>();

    private ModuleManager() {}

    public static ModuleManager getInstance() {
        return instance;
    }

    public void registerModule(MyModule module) {
        modules.add(module);
    }

    public List<MyModule> getModules() {
        return modules;
    }

    public MyModule getModuleByName(String name) {
        for (MyModule module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void enableModule(String name) {
        MyModule module = getModuleByName(name);
        if (module != null) {
            module.setEnabled(true);
        }
    }

    public void disableModule(String name) {
        MyModule module = getModuleByName(name);
        if (module != null) {
            module.setEnabled(false);
        }
    }

    public List<MyTrigger> getAllEnabledTriggers(TriggerType triggerType) {
        List<MyTrigger> triggers = new ArrayList<>();
        for (MyModule module : modules) {
            if (module.isEnabled()) {
                for (MyTrigger trigger : module.getTriggers()) {
                    if (trigger.getTriggerType() == triggerType) {
                        triggers.add(trigger);
                    }
                }
            }
        }
        return triggers;
    }
}
