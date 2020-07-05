package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.ChatColor;
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
            sender.sendMessage(eventPrefix + this.getProperUse());
            return;
        }

        String reqEvent = args.get(0);
        Event event = GeneralMethods.getEvent(reqEvent);

        if (event == null) {
            sender.sendMessage(eventPrefix + "There is no event by the name of " + reqEvent + ".");
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

        sender.sendMessage(eventPrefix + "Information for the " + ChatColor.BOLD + event.getEventName() + ChatColor.YELLOW + " event:");
        sender.sendMessage(ChatColor.GRAY + "Event Name: " + ChatColor.GREEN + event.getEventName() + ChatColor.GRAY + " | EventHost: " + ChatColor.GREEN + event.getEventStaff());
        sender.sendMessage(ChatColor.GRAY + "Event Type: " + ChatColor.GREEN + eventType + ChatColor.GRAY + " | Players (" + ChatColor.GREEN + event.getEventPlayers().size() + ChatColor.GRAY + "):");

        String eventPlayers = ChatColor.GREEN + " ";
        for (EventPlayer player : event.getEventPlayers()) {
            eventPlayers += player.getName() + ChatColor.GRAY + ", " + ChatColor.GREEN;
        }

        eventPlayers += ChatColor.BLUE + event.getEventStaff();

        sender.sendMessage(eventPlayers);
    }
}