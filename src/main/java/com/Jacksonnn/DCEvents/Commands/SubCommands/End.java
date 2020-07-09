package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class End implements EventSubCommand {

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("stop");
        aliases.add("die");
        aliases.add("kill");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events end <name>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.EndEventCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.EndEvent";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (args.size() == 1) {
            String reqEvent = args.get(0);
            Event event = GeneralMethods.getEvent(reqEvent);

            if (event == null) {
                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Error! That is not an active event. Check the list to confirm.");
                return;
            }

            event.remove();
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully terminated the event.");
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Error! " + getProperUse());
        }
    }
}