package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class List implements EventSubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public java.util.List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events list";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.ListCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.ListEvents";
    }

    @Override
    public void execute(CommandSender sender, java.util.List<String> args) {
        //  /events list
        sender.sendMessage(GeneralMethods.getEventsPrefix() + "List of Events:");

        for (Event event : GeneralMethods.getEvents()) {
            sender.sendMessage(ChatColor.GRAY + event.getEventName() + " (" + event.getEventPlayers().size() + ") - " + ChatColor.GREEN + event.getEventStaff().getName());
        }

    }
}