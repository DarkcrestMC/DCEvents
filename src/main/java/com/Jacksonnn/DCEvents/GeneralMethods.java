package com.Jacksonnn.DCEvents;

import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class GeneralMethods {
    public static String eventPrefix = ChatColor.GREEN + "[" + ChatColor.BLUE + "DC Events" + ChatColor.GREEN + "]" + ChatColor.YELLOW + " ";
    public static ArrayList<Event> events = new ArrayList<>();

    public static Event getEvent(String name) {
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(name)) {
                return event;
            }
        }
        return null;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void addEvent(Event e) {
        events.add(e);
    }

    public static void removeEvent(Event e) {
        events.remove(e);
    }

    public static boolean isTournament(Event e) {
        if (e instanceof Tournament) {
            return true;
        } else {
            return false;
        }
    }
}
