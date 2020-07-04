package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.DCEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Countdown implements EventSubCommand {
    @Override
    public String getName() {
        return "countdown";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("cd");
        aliases.add("count");
        aliases.add("cdown");
        aliases.add("countd");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events countdown <number>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.CountdownCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.Countdown";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        int num;
        if (args.size() < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /countdown <seconds>");
        }
        try {
            num = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + args.get(0) + " is not a number!");
            num = 0;
        }

        DCEvents.getCountdownHandler().start(num);
    }
}
