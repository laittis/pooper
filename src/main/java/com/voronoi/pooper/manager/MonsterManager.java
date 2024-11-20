package com.voronoi.pooper.manager;

import com.voronoi.pooper.annotation.Save;
import com.voronoi.pooper.bean.Settings;

import java.util.HashMap;
import java.util.Map;

public class MonsterManager extends Settings {
    // singleton
    private static final MonsterManager instance = new MonsterManager();

    private MonsterManager() {

    }

    public static MonsterManager getInstance() {
        return instance;
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
}
