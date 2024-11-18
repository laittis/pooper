package com.voronoi.pooper.bean;

import com.mythicscape.batclient.interfaces.BatClientPlugin;

import java.util.regex.Matcher;

public interface TriggerBody {
    void body(BatClientPlugin batClientPlugin, Matcher matcher);
}
