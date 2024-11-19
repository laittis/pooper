package com.voronoi.pooper;

import com.mythicscape.batclient.interfaces.*;
import com.voronoi.pooper.bean.MyModule;
import com.voronoi.pooper.manager.MessageManager;
import com.voronoi.pooper.manager.ModuleManager;
import com.voronoi.pooper.manager.SettingsManager;
import com.voronoi.pooper.manager.TriggerProcessor;
import com.voronoi.pooper.module.CoreModule;
import com.voronoi.pooper.module.CoreSkillModule;
import com.voronoi.pooper.module.CoreSpellModule;
import com.voronoi.pooper.util.TextUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PluginMain extends BatClientPlugin implements ActionListener, BatClientPluginTrigger, BatClientPluginCommandTrigger, BatClientPluginUtil, BatClientPluginNetwork {
    public static final String PLUGIN_NAME = "Pooper";
    private static final String PLUGIN_VERSION = loadVersion();
    private static final String PLUGIN_AUTHOR = "Voronoi";
    private static final String PLUGIN_DESCRIPTION = "Pooper is a plugin for the BatMUD client that converts poop into gold.";

    private TriggerProcessor triggerProcessor;

    @Override
    public void loadPlugin() {
        this.getClientGUI().printText("generic", TextUtil.colorText("--- Loading " + getName() + " ---\n", TextUtil.GREEN));
        this.getClientGUI().printText("generic", TextUtil.colorText("### Version: " + PLUGIN_VERSION + "\n", TextUtil.WHITE));
        this.getClientGUI().printText("generic", TextUtil.colorText("### Author: " + PLUGIN_AUTHOR + "\n", TextUtil.WHITE));
        this.getClientGUI().printText("generic", TextUtil.colorText("### Description: " + PLUGIN_DESCRIPTION + "\n", TextUtil.WHITE));

        // Hook up the protocol listener
        this.getPluginManager().addProtocolListener(this);

        //Message manager
        MessageManager.getInstance().setPlugin(this);

        // Trigger management
        triggerProcessor = TriggerProcessor.getInstance();
        ModuleManager moduleManager = ModuleManager.getInstance();

        // Initialize and register module
        MyModule coreModule = new CoreModule();
        coreModule.init();
        moduleManager.registerModule(coreModule);

        MyModule CoreSkillModule = new CoreSkillModule();
        CoreSkillModule.init();
        moduleManager.registerModule(CoreSkillModule);

        MyModule CoreSpellModule = new CoreSpellModule();
        CoreSpellModule.init();
        moduleManager.registerModule(CoreSpellModule);

        // Load all modules
        SettingsManager.getInstance().load(getBaseDirectory());

        this.getClientGUI().printText("generic", TextUtil.colorText("--- Loaded " + getName() + " ---\n", TextUtil.GREEN));
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    private static String loadVersion() {
        Properties properties = new Properties();
        try (InputStream input = PluginMain.class.getClassLoader().getResourceAsStream("version.properties")) {
            if (input == null) {
                return "Unknown Version";
            }
            properties.load(input);
            return properties.getProperty("version", "Unknown Version");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Unknown Version";
        }
    }

    @Override
    public void connect() {
        // Start timers etc.
    }

    @Override
    public void disconnect() {
        // Stop timers etc. Reset state.
    }

    @Override
    public String trigger(String s) {
        return triggerProcessor.processCommandTrigger(this, s);
    }

    @Override
    public ParsedResult trigger(ParsedResult parsedResult) {
        return triggerProcessor.processScreenTrigger(this, parsedResult);
    }

    @Override
    public void clientExit() {
        // Clean up and save state.
        SettingsManager.getInstance().save(getBaseDirectory());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO: if theres need for actions to be handled, implement here ( Like for displaying room information differently )
        // now just let's try to print out the action command
        //String input = event.getActionCommand();
        //String[] values = input.split( ";;", - 1 );
        // Print out the event values and such in nice formatted way
        // this.getClientGUI().printText("generic", TextUtil.colorText("Action Command: " + event.getActionCommand() + "\n", TextUtil.WHITE));
    }
}
