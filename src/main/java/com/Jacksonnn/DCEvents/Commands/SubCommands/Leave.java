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
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.LeaveCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Player.Leave";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to leave an event!");
            return;
        }
        Player player = (Player)sender;
        boolean removedFromEvent = false;
        if (args.size() == 0) {
            for (Event event : GeneralMethods.getEvents()) {
                for (EventPlayer ePlayer : event.getEventPlayers()) {
                    if (ePlayer.getPlayer() == player) {
                        event.removePlayer(ePlayer);
                        removedFromEvent = true;
                        break;
                    }
                }
            }
        } else {
            for (String eventName : args) {
                eventName = eventName.toLowerCase();
                for (Event event : GeneralMethods.getEvents()) {
                    if (event.getEventName().toLowerCase().equals(eventName)) {
                        for (EventPlayer ePlayer : event.getEventPlayers()) {
                            if (ePlayer.getPlayer() == player) {
                                event.removePlayer(ePlayer);
                                removedFromEvent = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!removedFromEvent) {
            if (args.size() == 0) sender.sendMessage(GeneralMethods.getErrorPrefix() + "You are not in an event!");
            else sender.sendMessage(GeneralMethods.getErrorPrefix() + "You are not in the " + args.get(0) + " event!");
        }
    }
}
