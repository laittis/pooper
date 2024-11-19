package com.voronoi.pooper.manager;

public class AreaManager {
    // singleton
    private static final AreaManager instance = new AreaManager();

    private AreaManager() {}

    public static AreaManager getInstance() {
        return instance;
    }
}
