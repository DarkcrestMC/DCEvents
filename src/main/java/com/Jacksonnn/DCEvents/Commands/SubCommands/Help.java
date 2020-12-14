package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Help implements EventSubCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("?");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events help";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.Help");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Help";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events help
        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Event Commands: ");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events add <eventName> <player(s)>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events broadcast <message>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events countdown <seconds>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events end <eventName>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events help");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events info <eventName>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events list");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events remove <eventName> <player(s)>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events start <eventName> [<type>]");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events teleport <eventName>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events reload");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events join <eventName>");
        sender.sendMessage(GeneralMethods.getEventsAccentColor() + "/events leave [eventName]");
    }
}