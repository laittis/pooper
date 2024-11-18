package com.voronoi.pooper.manager;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.mythicscape.batclient.interfaces.ParsedResult;
import com.voronoi.pooper.bean.MyTrigger;
import com.voronoi.pooper.bean.TriggerType;

import java.util.List;
import java.util.regex.Matcher;

public class TriggerProcessor {
    private final BatClientPlugin batClientPlugin;

    public TriggerProcessor(BatClientPlugin batClientPlugin) {
        this.batClientPlugin = batClientPlugin;
    }

    public String processCommandTrigger(String input) {
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

    public ParsedResult processScreenTrigger(ParsedResult parsedResult) {
        List<MyTrigger> triggers = ModuleManager.getInstance().getAllEnabledTriggers(TriggerType.SCREEN);

        for (MyTrigger trigger : triggers) {
            String textToMatch = trigger.isExpand() ? parsedResult.getOriginalText() : parsedResult.getStrippedText();
            Matcher matcher = trigger.matcher(textToMatch);
            if (matcher.find()) {
                trigger.getTriggerBody().body(batClientPlugin, matcher);
                if (trigger.isGag()) {
                    parsedResult.setOriginalText("");
                    return parsedResult;
                }
            }
        }
        return parsedResult;
    }
}
