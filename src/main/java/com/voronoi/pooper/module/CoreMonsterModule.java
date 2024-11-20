package com.voronoi.pooper.module;

import com.mythicscape.batclient.interfaces.ClientGUI;

import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;
import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.manager.MonsterManager;
import com.voronoi.pooper.util.TextUtil;

import java.awt.*;
import java.awt.font.TextAttribute;


public class CoreMonsterModule extends MyModule {
    public CoreMonsterModule() {
        super("CoreMonsterModule", "Tracks monster status and actions.  Command: 'pooper monster'");
    }

    @Override
    public void init() {
        registerTriggerCommands();
        registerMonsterRoomTrigger();
    }

    private void registerTriggerCommands() {
        registerTrigger(new MyTrigger(
                "monsterCommand",
                "Handles the 'pooper monster' module commands and its subcommands.",
                "^pooper monster(?:\\s+(view|add|addshort|remove|query|list)(?:\\s+(\\w+)(?:\\s+(\\w+))?)?)?$",
                (batClientPlugin, matcher, parsedResult) -> {
                    String subCommand = matcher.group(1);  // Main subcommand
                    String arg1 = matcher.group(2);        // First argument (e.g., long name or monster name)
                    String arg2 = matcher.group(3);        // Second argument (e.g., short name)

                    ClientGUI clientGUI = batClientPlugin.getClientGUI();

                    if (subCommand == null || subCommand.isEmpty()) {
                        displayHelp(clientGUI);
                    } else {
                        switch (subCommand) {
                            case "view":
                                // Handle view logic here
                                if (arg1 != null) {
                                    MonsterManager.getInstance().viewMonster(arg1);
                                } else {
                                    clientGUI.printText("generic", "Please provide a monster name to view.\n");
                                }
                                break;
                            case "add":
                                if (arg1 != null) {
                                    MonsterManager.getInstance().addMonster(arg1);
                                } else {
                                    clientGUI.printText("generic", "Please provide a monster name to add.\n");
                                }
                                break;
                            case "addshort":
                                if (arg1 != null && arg2 != null) {
                                    MonsterManager.getInstance().addMonsterWithShortName(arg1, arg2);
                                } else {
                                    clientGUI.printText("generic", "Usage: pooper monster addshort [longName] [shortName]\n");
                                }
                                break;
                            case "remove":
                                if (arg1 != null) {
                                    MonsterManager.getInstance().removeMonster(arg1);
                                } else {
                                    clientGUI.printText("generic", "Please provide a monster name to remove.\n");
                                }
                                break;
                            case "query":
                                if (arg1 != null) {
                                    MonsterManager.getInstance().queryMonster(arg1);
                                } else {
                                    clientGUI.printText("generic", "Please provide a monster name to query.\n");
                                }
                            case "list":
                                String monsters = MonsterManager.getInstance().listMonsters();
                                clientGUI.printText("generic", monsters + "\n");
                                break;
                            default:
                                displayHelp(clientGUI);
                        }
                    }
                },
                true, // isAction
                true, // isGag
                false, // isExpand
                TriggerType.COMMAND
        ));

    }

    private void registerMonsterRoomTrigger() {

        // Register the trigger
        // Supporting 'ansi theme' a and c, didn't bother to add more, I think most use 'a' anyways.
        registerTrigger(new MyTrigger(
                "monsterInRoom",
                "Handles the monster room trigger.",
                "^\u001b\\[(1;31m|1;32m|35m)([A-Za-z0-9, '\\s-()<>-]+)\u001b\\[0m$",
                (batClientPlugin, matcher, parsedResult) -> {
                    // if match 1 is 1;31m, then it's an aggressive monster
                    // if match 1 is 1;32m, then it's a regular monster
                    if (matcher.group(1).equals("1;31m")) {
                        // aggressive monster
                        TextUtil.appendText(parsedResult, " (aggressive)\n", Color.RED, Color.WHITE);
                    } else if (matcher.group(1).equals("1;32m") || matcher.group(1).equals("35m")) {
                        // regular monster
                        TextUtil.appendText(parsedResult, " (normal)\n", Color.GREEN, Color.WHITE);
                    }
                },
                false, // isAction
                false, // isGag
                true, // isExpand
                TriggerType.SCREEN
        ));
    }

    private void displayHelp(ClientGUI clientGUI) {
        MessageManager messageManager = MessageManager.getInstance();
        messageManager.printMessage("Usage: 'pooper monster [view|add|addshort|remove|query|list] [monsterName]'");
        messageManager.printMessage("- monster module supports ansi theme a and c");
    }
}
