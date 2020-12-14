package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Leave implements EventSubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("exit");
        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/dcevents leave [eventName]";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.LeaveCommannd");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Player.Leave";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int i = 0;
            if (args.size() == 0) {
                for (Event event : GeneralMethods.getEvents()) {
                    for (EventPlayer ePlayer : event.getEventPlayers()) {
                        if (ePlayer.getName().equalsIgnoreCase(player.getName())) {
                            event.removePlayer(ePlayer);
                            i++;
                        }
                    }
                }
            } else {
                for (String eventName : args) {
                    for (Event event : GeneralMethods.getEvents()) {
                        if (event.getEventName().equalsIgnoreCase(eventName)) {
                            for (EventPlayer ePlayer : event.getEventPlayers()) {
                                if (ePlayer.getName().equalsIgnoreCase(player.getName())) {
                                    event.removePlayer(ePlayer);
                                    i++;
                                } else {
                                    sender.sendMessage (GeneralMethods.getErrorPrefix() + "You aren't apart of the event: " + event.getEventName() + ".");
                                }
                            }
                        }
                    }
                }
            }
            if (i == 0) {
                sender.sendMessage(GeneralMethods.getErrorPrefix() + "You aren't apart of any events!");
            } else {
                sender.sendMessage(GeneralMethods.getSuccessPrefix() + "You have been removed from " + i + (i > 1 ? " events." : " event."));
            }
        } else {
            sender.sendMessage(GeneralMethods.getErrorPrefix() + "You must be a player to run this command!");
        }
    }
}
