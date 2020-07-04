package com.Jacksonnn.DCEvents;

import com.Jacksonnn.DCEvents.Commands.CountdownHandler;
import com.Jacksonnn.DCEvents.Commands.EventCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Games.Tournament.Commands;
import com.Jacksonnn.DCEvents.PlayerEvents.PlayerKillEvent;
import com.Jacksonnn.DCEvents.PlayerEvents.PlayerLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DCEvents extends JavaPlugin {

    private PluginManager pm = Bukkit.getServer().getPluginManager();
    private static DCEvents plugin;
    private static CountdownHandler cdHandler;

    @Override
    public void onEnable() {
        //EVENTS COMMAND
        plugin = this;

        EventCommand eventCommand = new EventCommand(this);
        this.getCommand("dcevents").setExecutor(eventCommand);

        this.getCommand("tournament").setExecutor(new Commands());

        //EVENTS LISTENERS
        pm.registerEvents(new PlayerKillEvent(), this);
        pm.registerEvents(new PlayerLeaveEvent(), this);

        new ConfigManager();

        cdHandler = new CountdownHandler();

        Bukkit.getServer().getLogger().info(ChatColor.DARK_AQUA + "DCEvents has been enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().getLogger().info(ChatColor.DARK_AQUA + "DCEvents has been disabled.");
    }

    public static DCEvents getDCEvents() {
        return plugin;
    }

    public static CountdownHandler getCountdownHandler() {
        return cdHandler;
    }
}
