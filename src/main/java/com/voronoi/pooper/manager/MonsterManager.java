package com.voronoi.pooper.manager;

import com.voronoi.pooper.annotation.Save;
import com.voronoi.pooper.bean.Settings;

import java.util.HashMap;
import java.util.Map;

public class MonsterManager extends Settings {
    // singleton
    private static final MonsterManager instance = new MonsterManager();

    private final Map<String, String> ansiColorMap = new HashMap<>();
    @Save
    private String regularMonsterColor;
    @Save
    private String aggressiveMonsterColor;

    private MonsterManager() {
        initializeAnsiColorMap();
        regularMonsterColor = ansiColorMap.getOrDefault("magenta", "35");
        aggressiveMonsterColor = ansiColorMap.getOrDefault("light_red", "91");
    }

    public static MonsterManager getInstance() {
        return instance;
    }

    private void initializeAnsiColorMap() {
        ansiColorMap.put("black", "30");
        ansiColorMap.put("red", "31");
        ansiColorMap.put("green", "32");
        ansiColorMap.put("yellow", "33");
        ansiColorMap.put("blue", "34");
        ansiColorMap.put("magenta", "35");
        ansiColorMap.put("cyan", "36");
        ansiColorMap.put("white", "37");
        ansiColorMap.put("light_black", "90");
        ansiColorMap.put("light_red", "91");
        ansiColorMap.put("light_green", "92");
        ansiColorMap.put("light_yellow", "93");
        ansiColorMap.put("light_blue", "94");
        ansiColorMap.put("light_magenta", "95");
        ansiColorMap.put("light_cyan", "96");
        ansiColorMap.put("light_white", "97");
    }

    public void viewMonster(String monsterName) {
        // Find monster with name or short name and display it
    }

    public void addMonster(String monsterName) {
        // Add monster with name
    }

    public void addMonsterWithShortName(String monsterName, String monsterShortName) {
        // Add monster with name and short name or find monster with name and set short name
    }

    public void removeMonster(String monsterName) {
        // Find monster with name or short name and remove it
    }

    public String listMonsters() {
        // List all monsters
        return "";
    }

    public void queryMonster(String arg1) {
        // Find monster with name or short name and display it
        // if not found, try to query https://batshoppe.dy.fi/monsieinfo.php
    }

    public void setRegularMonsterColor(String color) {
        regularMonsterColor = ansiColorMap.get(color);
    }

    public void setAggressiveMonsterColor(String color) {
        aggressiveMonsterColor = ansiColorMap.get(color);
    }

    public String getRegularMonsterColorAnsi() {
        return ansiColorMap.getOrDefault(regularMonsterColor, "35");
    }

    public String getAggressiveMonsterColorAnsi() {
        return ansiColorMap.getOrDefault(aggressiveMonsterColor, "91");
    }

}
