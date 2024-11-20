package com.voronoi.pooper.bean;

import com.mythicscape.batclient.interfaces.BatClientPlugin;
import com.mythicscape.batclient.interfaces.ParsedResult;

import java.util.regex.Matcher;

/**
 * Interface for the body of a trigger
 */
public interface TriggerBody {
    void body(BatClientPlugin batClientPlugin, Matcher matcher, ParsedResult parsedResult);
}
