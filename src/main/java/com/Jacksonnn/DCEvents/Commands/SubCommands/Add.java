package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Add implements EventSubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("addplayer");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events add <name> <player(s)>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.AddCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.AddPlayer";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events add <eventName> <player(s)>

        if (args.size() >= 2) {
            String eventName = args.get(0);
            args.remove(eventName);

            Event reqEvent = GeneralMethods.getEvent(eventName);

            if (reqEvent == null) {
                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Error! That is not an active event. Check the list to confirm.");
                return;
            }

            for (String player : args) {
                reqEvent.addPlayer(sender, player);
            }
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Error! " + getProperUse());
        }
    }
}