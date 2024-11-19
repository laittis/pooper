package com.voronoi.pooper.bean;

import com.voronoi.pooper.manager.SettingsManager;

import java.io.File;

public abstract class Tracker extends Settings {

    /**
     * Constructor to automatically register the tracker.
     */
    protected Tracker() {
        super();
        SettingsManager.getInstance().register(this);
    }

    /**
     * Override this to define the specific file name for the tracker.
     */
    @Override
    public File getSaveFile(String baseDirectory) {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".xml";
        return new File(baseDirectory, fileName);
    }

    /**
     * Called when the tracker needs to reset its state.
     */
    public abstract void reset();
}
