package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

import static com.Jacksonnn.DCEvents.GeneralMethods.*;

public class Info implements EventSubCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("eventinfo");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events info <eventName>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.InfoCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.EventInfo";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events info <eventName>

        if (args.size() == 0) {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + this.getProperUse());
            return;
        }

        String reqEvent = args.get(0);
        Event event = GeneralMethods.getEvent(reqEvent);

        if (event == null) {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by the name of " + reqEvent + ".");
            return;
        }

        String eventType;

        if (event.getEventType() instanceof Tournament) {
            eventType = "Tournament";
        } else if (event.getEventType() == null){
            eventType = "Event";
        } else {
            eventType = "Event";
        }

        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Information for the " + ChatColor.BOLD + event.getEventName() + ChatColor.RESET + GeneralMethods.getEventsAccentColor() + " event:");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "Event Name: " + ChatColor.WHITE + event.getEventName() + GeneralMethods.getEventsAccentColor() + " | EventHost: " + ChatColor.WHITE + event.getEventStaff().getName());
        sender.sendMessage(GeneralMethods.getEventsAccentColor()+ "Event Type: " + ChatColor.WHITE + eventType + GeneralMethods.getEventsAccentColor() + " | Players (" + ChatColor.WHITE + event.getEventPlayers().size() + GeneralMethods.getEventsAccentColor() + "):");

        String eventPlayers = ChatColor.of("#00A9DB") + " ";
        for (EventPlayer player : event.getEventPlayers()) {
            eventPlayers += player.getName() + GeneralMethods.getEventsAccentColor() + ", " + ChatColor.of("#00A9DB");
        }

        eventPlayers += ChatColor.of("#EB1749") + " " + ChatColor.BOLD + event.getEventStaff().getName();

        sender.sendMessage(eventPlayers);
    }
}