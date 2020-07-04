package com.Jacksonnn.DCEvents.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager {
    public static Config langConfig;
    public static Config defaultConfig;
    public static Config bannedWords;
    public static Config announcer;

    public ConfigManager() {
        defaultConfig = new Config(new File("config.yml"));
        langConfig = new Config(new File("language.yml"));
        configCheck(ConfigType.DEFAULT);
        configCheck(ConfigType.LANGUAGE);
    }

    public static void configCheck(ConfigType type) {
        FileConfiguration config;
        if (type == ConfigType.DEFAULT) {
            config = defaultConfig.get();

            config.addDefault("Events.Tournament.Pos1.world", "Events");
            config.addDefault("Events.Tournament.Pos1.x", 0);
            config.addDefault("Events.Tournament.Pos1.y", 0);
            config.addDefault("Events.Tournament.Pos1.z", 0);

            config.addDefault("Events.Tournament.Pos2.world", "Events");
            config.addDefault("Events.Tournament.Pos2.x", 0);
            config.addDefault("Events.Tournament.Pos2.y", 0);
            config.addDefault("Events.Tournament.Pos2.z", 0);

            config.addDefault("Events.Tournament.Spectator.world", "Events");
            config.addDefault("Events.Tournament.Spectator.x", 0);
            config.addDefault("Events.Tournament.Spectator.y", 0);
            config.addDefault("Events.Tournament.Spectator.z", 0);

            defaultConfig.save();
        } else if (type == ConfigType.LANGUAGE) {
            config = langConfig.get();

            //  /dcevents add <event> <player(s)>
            //  /dcevents remove <event> <player(s)>
            //  /dcevents startEvent <event>
            //  /dcevents endEvent <event>
            //  /dcevents eventList
            //  /dcevents playerList <event>
            //  /dcevents broadcast <message>
            //  /dcevents teleport

            config.addDefault("Events.CommandDescriptions.AddCommand", "Adds player(s) to a certain eventlist.");
            config.addDefault("Events.CommandDescriptions.RemoveCommand", "Removes player(s) from a certain eventlist.");
            config.addDefault("Events.CommandDescriptions.StartEventCommand", "Creates an event.");
            config.addDefault("Events.CommandDescriptions.EndEventCommand", "Ends an event.");
            config.addDefault("Events.CommandDescriptions.EventList", "Lists all current events.");
            config.addDefault("Events.CommandDescriptions.PlayerList", "Lists all players participating in a certain event.");
            config.addDefault("Events.CommandDescriptions.Broadcast", "Uses the [EventBroadcast] chat function.");
            config.addDefault("Events.CommandDescriptions.Help", "Shows all possible commands and their arguments.");
            config.addDefault("Events.CommandDescriptions.Teleport", "Teleports all active players in an event to the executor.");

            langConfig.save();
        }
    }
}