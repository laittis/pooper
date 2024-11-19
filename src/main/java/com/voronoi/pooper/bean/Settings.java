package com.voronoi.pooper.bean;

import com.voronoi.pooper.manager.SettingsManager;

import java.io.File;

public abstract class Settings {

    /**
     * Constructor that automatically registers this instance with the SettingsManager.
     */
    protected Settings() {
        SettingsManager.getInstance().register(this);
    }

    /**
     * Returns the file where this settings object should store its data.
     * By default, the file name is derived from the class name.
     *
     * @param baseDirectory The base directory where settings files are stored.
     * @return The file where settings will be saved.
     */
    public File getSaveFile(String baseDirectory) {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".xml";
        return new File(baseDirectory, fileName);
    }
}
