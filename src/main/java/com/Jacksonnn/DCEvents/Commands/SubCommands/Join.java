package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Join implements EventSubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();

        aliases.add("");
        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/dcevents join [eventName]";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.JoinCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Player.Join";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to join an event!");
            return;
        }
        Player player = (Player) sender;

        if (args.size() == 0) {

            if (GeneralMethods.getEvents().size() > 1) {
                sender.sendMessage(GeneralMethods.getErrorPrefix() + "Provide the name of the event you wish to join.");
                return;
            } else if (GeneralMethods.getEvents().isEmpty()) {
                sender.sendMessage(GeneralMethods.getErrorPrefix() + "There is no event to join.");
                return;
            }

            Event event = GeneralMethods.getEvents().get(0);
            boolean hasPlayer = false;
            for (EventPlayer ePlayer : event.getEventPlayers()) {
                if (ePlayer.getPlayer() == player) {
                    hasPlayer = true;
                    break;
                }
            }

            if (hasPlayer) player.sendMessage(GeneralMethods.getErrorPrefix() + "You are already a part of the " + event.getEventName() + " event.");
            else {
                event.addPlayer(player);
                player.sendMessage(GeneralMethods.getSuccessPrefix() + "You joined the " + event.getEventName() + " event!");
            }
        } else {
            if (args.size() == 1) {
                String eventName = args.get(0);
                Event reqEvent = GeneralMethods.getEvent(eventName);

                if (reqEvent == null) {
                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! That is not an active event. Please check list for active events.");
                    return;
                }

                for (EventPlayer ePlayer : reqEvent.getEventPlayers()) {
                    if (ePlayer.getPlayer() == player) {
                        sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! You are already apart of this event...");
                        return;
                    }
                }

                reqEvent.addPlayer(player);
                sender.sendMessage(GeneralMethods.getSuccessPrefix() + "You have joined the " + reqEvent.getEventName() + " event!");

                Player host = reqEvent.getEventStaff();

//                if (host == null || !host.isOnline()) { // why?
//                    Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "<DCEVENTS ERROR>: EventHost is not on to host event. Please end event and create a new instance with an active host.");
//                    LocalDateTime timestamp = LocalDateTime.now();
//                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "There has been a big error, please ask a staff member with console access to check logs for '<DCEVENTS ERROR>'. Timestamp: " + timestamp.getHour() + " : " + timestamp.getMinute() + " : " + timestamp.getSecond() + ".");
//
//                    Bukkit.broadcast(GeneralMethods.getEventsPrefix().replace("DC Events", "EventHosts") + "Player " + player.getName() + " has joined the " + reqEvent.getEventName() + " event.", "DCCore.AllowEvents");
//                    return;
//                }

                 if (host != null && host.isOnline()) host.sendMessage(GeneralMethods.getEventsPrefix() + "Player " + player.getName() + " has joined the " + reqEvent.getEventName() + " event.");
            } else {
                sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! " + getProperUse());
            }
        }


//        if (sender instanceof Player) {
//            Player player = (Player) sender;
//
//            if (args.size() == 1) {
//                String eventName = args.get(0);
//                Event reqEvent = GeneralMethods.getEvent(eventName);
//
//                if (reqEvent == null) {
//                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! That is not an active event. Please check list for active events.");
//                    return;
//                }
//
//                for (EventPlayer ePlayer : reqEvent.getEventPlayers()) {
//                    if (ePlayer.getPlayer() == player) {
//                        sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! You are already apart of this event...");
//                        return;
//                    }
//                }
//
//                reqEvent.addPlayer(player);
//                sender.sendMessage(GeneralMethods.getSuccessPrefix() + "You have joined the " + reqEvent.getEventName() + " event!");
//
//                Player host = reqEvent.getEventStaff();
//
//                if (host == null || !host.isOnline()) {
//                    Bukkit.getServer().getLogger().info(ChatColor.DARK_RED + "<DCEVENTS ERROR>: EventHost is not on to host event. Please end event and create a new instance with an active host.");
//                    LocalDateTime timestamp = LocalDateTime.now();
//                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "There has been a big error, please ask a staff member with console access to check logs for \'<DCEVENTS ERROR>\'. Timestamp: " + timestamp.getHour() + " : " + timestamp.getMinute() + " : " + timestamp.getSecond() + ".");
//
//                    Bukkit.broadcast(GeneralMethods.getEventsPrefix().replace("DC Events", "EventHosts") + "Player " + player.getName() + " has joined the " + reqEvent.getEventName() + " event.", "DCCore.AllowEvents");
//                    return;
//                }
//
//                host.sendMessage(GeneralMethods.getEventsPrefix() + "Player " + player.getName() + " has joined the " + reqEvent.getEventName() + " event.");
//            } else {
//                sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! " + getProperUse());
//            }
//        } else {
//            sender.sendMessage(GeneralMethods.getErrorPrefix() + "You must be a player to run this command!");
//        }
    }
}
