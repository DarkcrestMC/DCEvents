package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Remove implements EventSubCommand {

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("removeplayer");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events remove <name> <player(s)>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.RemoveCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.RemovePlayer";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events remove <eventName> <player(s)>
         if (args.size() >= 2) {
            String eventName = args.get(0);
            args.remove(eventName);

            Event reqEvent = GeneralMethods.getEvent(eventName);

            if (reqEvent == null) {
                sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! There is no event by the name of " + eventName + ".");
                return;
            }
            for (String player : args) {
                reqEvent.removePlayer(sender, player);
            }
         } else {
             sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! " + getProperUse());
         }
    }
}