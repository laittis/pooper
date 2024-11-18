package com.voronoi.pooper.manager;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.voronoi.pooper.util.TextUtil;

public class MessageManager {
    private static final MessageManager instance = new MessageManager();
    private BatClientPlugin plugin;

    private MessageManager() {}

    public static MessageManager getInstance() {
        return instance;
    }

    public void setPlugin(BatClientPlugin plugin) {
        this.plugin = plugin;
    }

    public void printMessage(String message, String color) {
        if (plugin != null) {
            plugin.getClientGUI().printText("generic", TextUtil.colorText(message, color));
        } else {
            System.err.println("Plugin instance not set in MessageManager.");
        }
    }

    public void printStatusMessage(String message, String color) {
        if (plugin != null) {
            plugin.getClientGUI().printText("generic", TextUtil.colorText("---- ", TextUtil.WHITE) + TextUtil.colorText(message, color) + TextUtil.colorText(" ----\n", TextUtil.WHITE));
        } else {
            System.err.println("Plugin instance not set in MessageManager.");
        }
    }

    public void printEventMessage(String message, String color) {
        if (plugin != null) {
            plugin.getClientGUI().printText("generic", TextUtil.colorText("### ", TextUtil.WHITE) + TextUtil.colorText(message, color) +"\n");
        } else {
            System.err.println("Plugin instance not set in MessageManager.");
        }
    }


    public void printError(String message) {
        printMessage(message, TextUtil.RED);
    }

    public void printInfo(String message) {
        printMessage(message, TextUtil.GREEN);
    }
}
