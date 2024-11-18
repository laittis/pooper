package com.voronoi.pooper.module;

import com.mythicscape.batclient.interfaces.ClientGUI;
import com.voronoi.pooper.PluginMain;
import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.manager.ModuleManager;
import com.voronoi.pooper.util.TextUtil;

public class CoreModule extends MyModule {
    public CoreModule() {
        super("CoreModule", "Core commands for the Pooper plugin.");
    }

    @Override
    public void init() {
        registerTrigger(new MyTrigger(
                "pooperCommand",
                "Handles the main 'pooper' command and its subcommands.",
                "^pooper(?:\\s+(\\w+)(?:\\s+(\\w+))?)?$",
                (batClientPlugin, matcher) -> {
                    String subCommand = matcher.group(1);
                    String moduleName = matcher.group(2);

                    ClientGUI clientGUI = batClientPlugin.getClientGUI();

                    if (subCommand == null) {
                        displayHelp(clientGUI);
                    } else if (subCommand.equalsIgnoreCase("help")) {
                        if (moduleName == null) {
                            displayHelp(clientGUI);
                        } else {
                            MyModule module = ModuleManager.getInstance().getModuleByName(moduleName);
                            if (module != null) {
                                clientGUI.printText(PluginMain.PLUGIN_NAME, module.getHelp());
                            } else {
                                clientGUI.printText(PluginMain.PLUGIN_NAME, "Module not found: " + moduleName + "\n");
                            }
                        }
                    } else if (subCommand.equalsIgnoreCase("enable")) {
                        if (moduleName != null) {
                            ModuleManager.getInstance().enableModule(moduleName);
                            clientGUI.printText(PluginMain.PLUGIN_NAME, "Module enabled: " + moduleName + "\n");
                        } else {
                            clientGUI.printText(PluginMain.PLUGIN_NAME, "Specify a module to enable.\n");
                        }
                    } else if (subCommand.equalsIgnoreCase("disable")) {
                        if (moduleName != null) {
                            ModuleManager.getInstance().disableModule(moduleName);
                            clientGUI.printText(PluginMain.PLUGIN_NAME, "Module disabled: " + moduleName + "\n");
                        } else {
                            clientGUI.printText(PluginMain.PLUGIN_NAME, "Specify a module to disable.\n");
                        }
                    } else {
                        clientGUI.printText(PluginMain.PLUGIN_NAME, "Unknown command: " + subCommand + "\n");
                        displayHelp(clientGUI);
                    }
                },
                true, // isAction
                true, // isGag
                false, // isExpand
                TriggerType.COMMAND
        ));
    }

    private void displayHelp(ClientGUI clientGUI) {
        clientGUI.printText(PluginMain.PLUGIN_NAME, TextUtil.colorText("--- Pooper Help ---\n", TextUtil.GREEN));
        clientGUI.printText(PluginMain.PLUGIN_NAME, "pooper help - Display this help message\n");
        clientGUI.printText(PluginMain.PLUGIN_NAME, "pooper help <module> - Display help for a specific module\n");
        clientGUI.printText(PluginMain.PLUGIN_NAME, "pooper enable <module> - Enable a module\n");
        clientGUI.printText(PluginMain.PLUGIN_NAME, "pooper disable <module> - Disable a module\n");
        clientGUI.printText(PluginMain.PLUGIN_NAME, "Available module:\n");

        for (MyModule module : ModuleManager.getInstance().getModules()) {
            String status = module.isEnabled() ? "[Enabled]" : "[Disabled]";
            clientGUI.printText(PluginMain.PLUGIN_NAME, "- " + module.getName() + " " + status + ": " + module.getDescription() + "\n");
        }
    }
}
