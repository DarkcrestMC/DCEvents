package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Reload implements EventSubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/dcevents reload";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.ReloadCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Admin.Reload";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        ConfigManager.defaultConfig.reload();
        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Reloaded config.yml.");
        ConfigManager.langConfig.reload();
        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Reloaded language.yml.");
    }
}
