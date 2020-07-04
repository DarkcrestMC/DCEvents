package com.Jacksonnn.DCEvents.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface EventSubCommand {
    String getName();

    List<String> getAliases();

    String getProperUse();

    String getDescription();

    String getPermission();

    void execute(CommandSender sender, List<String> args);
}
