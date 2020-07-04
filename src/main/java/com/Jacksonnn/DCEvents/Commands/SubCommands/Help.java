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
        sender.sendMessage(GeneralMethods.eventPrefix + "Event Commands: ");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events add <eventName> <player(s)>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events broadcast <message>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events countdown <seconds>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events end <eventName>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events help");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events info <eventName>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events list");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events remove <eventName> <player(s)>");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events start <eventName> [<type>]");
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/events teleport <eventName>");
    }
}