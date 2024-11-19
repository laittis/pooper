package com.voronoi.pooper.manager;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.mythicscape.batclient.interfaces.ParsedResult;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;

import java.util.List;
import java.util.regex.Matcher;

public class TriggerProcessor {
    // Singleton
    private TriggerProcessor() {}
    private static final TriggerProcessor instance = new TriggerProcessor();
    public static TriggerProcessor getInstance() {
        return instance;
    }


    public String processCommandTrigger(BatClientPlugin batClientPlugin, String input) {
        List<MyTrigger> triggers = ModuleManager.getInstance().getAllEnabledTriggers(TriggerType.COMMAND);

        for (MyTrigger trigger : triggers) {
            Matcher matcher = trigger.matcher(input);
            if (matcher.matches()) {
                trigger.getTriggerBody().body(batClientPlugin, matcher);
                if (trigger.isGag()) {
                    return ""; // Gag the command input
                }
            }
        }
        return input; // Return original input if no trigger matched or not gagged
    }

    public ParsedResult processScreenTrigger(BatClientPlugin batClientPlugin, ParsedResult parsedResult) {
        List<MyTrigger> triggers = ModuleManager.getInstance().getAllEnabledTriggers(TriggerType.SCREEN);

        for (MyTrigger trigger : triggers) {
            //String textToMatch = trigger.isExpand() ? parsedResult.getOriginalText() : parsedResult.getStrippedText();
            Matcher matcher = trigger.matcher(parsedResult);
            if (matcher.find()) {
                trigger.getTriggerBody().body(batClientPlugin, matcher);
                if (trigger.isGag()) {
                    parsedResult.setStrippedText(""); // Gag the parsed result
                    parsedResult.setOriginalText(""); // Gag the parsed result
                    return null; // Gag the parsed result
                }
            }
        }
        return parsedResult;
    }
}
