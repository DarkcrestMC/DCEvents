package com.Jacksonnn.DCEvents;

import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;

public class GeneralMethods {

    // #00A9DB [ #EB1749 &l DC Events &r #00A9DB ] #C9C9C9 DCEvents Commands:

    // #1E5C26 [ #24D530 DC Events #1E5C26 ] #C9C9C9 You have successfully...

    // #660000 [ #D6221E DC Events #660000 ] #C9C9C9 You have caused an exception...

    private static String accentColor = ChatColor.of("#C9C9C9").toString();

    private static String errorColor = ChatColor.of("#660000") + "[" + ChatColor.of("#D6221E") + ChatColor.BOLD + "DC Events" + ChatColor.RESET + ChatColor.of("#660000") + "]" + accentColor + " ";

    private static String successColor = ChatColor.of("#1E5C26") + "[" + ChatColor.of("#24D530") + ChatColor.BOLD + "DC Events" + ChatColor.RESET + ChatColor.of("#1E5C26") + "]" + accentColor + " ";

    private static String eventPrefix = ChatColor.of("#00A9DB") + "[" + ChatColor.of("#EB1749") + ChatColor.BOLD + "DC Events" + ChatColor.RESET + ChatColor.of("#00A9DB") + "]" + accentColor + " ";

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

    public static void removeAllEvents() {
        int sentinel = 500;
        int i = 0;
        while (!events.isEmpty() && i++ != sentinel)
            events.get(0).remove();
    }

    public static boolean isTournament(Event e) {
        if (e instanceof Tournament) {
            return true;
        } else {
            return false;
        }
    }

    public static String getEventsPrefix() {
        return eventPrefix;
    }

    public static String getEventsAccentColor() {
        return accentColor;
    }
    public static String getErrorPrefix() {
        return errorColor;
    }
    public static String getSuccessPrefix() {
        return successColor;
    }
}
