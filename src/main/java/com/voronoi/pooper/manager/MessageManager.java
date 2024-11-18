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

    public void printMessage(String message) {
        if (plugin != null) {
            plugin.getClientGUI().printText(
                    "generic",
                    TextUtil.BOLD + TextUtil.WHITE + "Pooper: " + TextUtil.RESET + message + "\n"
            );
        } else {
            System.err.println("Plugin instance not set in MessageManager.");
        }
    }

    public void reportMessage(String report) {
        if (plugin != null) {
            plugin.getClientGUI().doCommand("@@party report " + report);
        } else {
            System.err.println("Plugin instance not set in MessageManager.");
        }
    }
}
