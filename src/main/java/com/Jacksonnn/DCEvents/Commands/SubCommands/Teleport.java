package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Teleport implements EventSubCommand {

    @Override
    public String getName() {
        return "teleport";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("tp");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events teleport <name>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.TeleportCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.Teleport";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events teleport <eventName> - Pulls up a clickable menu
        //  /events teleport here <eventName> <player|all>
        //  /events teleport pos1 <eventName <player|all>
        //  /events teleport pos2 <eventName> <player|all>
        //  /events teleport spectator <eventName> <player|all>

        if (args.size() == 1) {
            String eventName = args.get(0);
            Event reqEvent = GeneralMethods.getEvent(eventName);

            if (reqEvent == null) {
                sender.sendMessage(GeneralMethods.eventPrefix + "Error! That is not a currently active event.");
                return;
            }

            sender.sendMessage(GeneralMethods.eventPrefix + reqEvent.getEventName() + " Teleport Menu:");

            BaseComponent base = new TextComponent("");

            TextComponent begin = new TextComponent("-=(");
            begin.setColor(ChatColor.DARK_GRAY);

            TextComponent end = new TextComponent(")=- ");
            end.setColor(ChatColor.DARK_GRAY);

            TextComponent hereMSG = new TextComponent("HERE");
            hereMSG.setColor(ChatColor.DARK_GREEN);
            hereMSG.setBold(true);
            hereMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent("Click to tp players to you.")}));
            hereMSG.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/events teleport here " + reqEvent.getEventName() + " player|all"));

            TextComponent pos1MSG = new TextComponent("POS1");
            pos1MSG.setColor(ChatColor.BLUE);
            pos1MSG.setBold(true);
            hereMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent("Click to tp players to POS1.")}));
            pos1MSG.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/events teleport pos1 " + reqEvent.getEventName() + " player|all"));

            TextComponent pos2MSG = new TextComponent("POS2");
            pos2MSG.setColor(ChatColor.RED);
            pos2MSG.setBold(true);
            hereMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent("Click to tp players to POS2.")}));
            pos2MSG.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/events teleport pos2 " + reqEvent.getEventName() + " player|all"));

            TextComponent specMSG = new TextComponent("SPEC");
            specMSG.setColor(ChatColor.GOLD);
            specMSG.setBold(true);
            hereMSG.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent("Click to tp players to the spectator area.")}));
            specMSG.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/events teleport spec " + reqEvent.getEventName() + " player|all"));

            base.addExtra(begin);
            base.addExtra(hereMSG);
            base.addExtra(end);

            base.addExtra(begin);
            base.addExtra(pos1MSG);
            base.addExtra(end);

            base.addExtra(begin);
            base.addExtra(pos2MSG);
            base.addExtra(end);

            base.addExtra(begin);
            base.addExtra(specMSG);
            base.addExtra(end);

            sender.spigot().sendMessage(base);
        } else if (args.size() == 3) {
            String eventName = args.get(1);
            Event reqEvent = GeneralMethods.getEvent(eventName);

            if (reqEvent == null) {
                sender.sendMessage(GeneralMethods.eventPrefix + "Error! That is not a currently active event.");
                return;
            }

            if (args.get(0).equalsIgnoreCase("here")) {
                if (args.get(2).equalsIgnoreCase("all")) {
                    for (EventPlayer ePlayer : reqEvent.getEventPlayers()) {
                        if (sender instanceof Player) {
                            Location loc = ((Player) sender).getLocation();

                            ePlayer.getPlayer().teleport(loc);
                            sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + ePlayer.getName() + " to your locaiton.");
                            ePlayer.getPlayer().sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                        }
                    }
                } else {
                    String playerName = args.get(2);

                    int errorLog = -1;
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.getName().equalsIgnoreCase(playerName)) {
                            player.teleport(((Player) sender).getLocation());
                            sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + player.getName() + " to your locaiton.");
                            player.sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                            errorLog = 0;
                        }
                    }
                    if (errorLog == -1) {
                        sender.sendMessage(GeneralMethods.eventPrefix + "Error! There is no online player by the name of " + playerName + ".");
                    }
                }
            } else if (args.get(0).equalsIgnoreCase("pos1")) {
                //if is certain type of event...
                if (GeneralMethods.isTournament(reqEvent)) {
                    Tournament tournament = (Tournament) reqEvent;
                    if (args.get(2).equalsIgnoreCase("all")) {
                        Location loc = tournament.getPos1();

                        for (EventPlayer ePlayer : tournament.getEventPlayers()) {
                            ePlayer.getPlayer().teleport(loc);
                            sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + ePlayer.getName() + " to POS1.");
                            ePlayer.getPlayer().sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                        }
                    } else {
                        Location loc = tournament.getPos1();

                        int errorLog = -1;
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (player.getName().equalsIgnoreCase(args.get(2))) {
                                player.teleport(loc);
                                sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + player.getName() + " to POS1.");
                                player.sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                                errorLog = 0;
                            }
                        }
                        if (errorLog == -1) {
                            sender.sendMessage(GeneralMethods.eventPrefix + "Error! There is no online player by the name of " + args.get(2) + ".");
                        }
                    }
                } else {
                    sender.sendMessage(GeneralMethods.eventPrefix + "Error! This command isn\'t applicable to this type of event.");
                }
            } else if (args.get(0).equalsIgnoreCase("pos2")) {
                //if is certain type of event...
                if (GeneralMethods.isTournament(reqEvent)) {
                    Tournament tournament = (Tournament) reqEvent;
                    if (args.get(2).equalsIgnoreCase("all")) {
                        Location loc = tournament.getPos2();

                        for (EventPlayer ePlayer : tournament.getEventPlayers()) {
                            ePlayer.getPlayer().teleport(loc);
                            sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + ePlayer.getName() + " to POS2.");
                            ePlayer.getPlayer().sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                        }
                    } else {
                        Location loc = tournament.getPos2();

                        int errorLog = -1;
                        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                            if (player.getName().equalsIgnoreCase(args.get(2))) {
                                player.teleport(loc);
                                sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + player.getName() + " to POS2.");
                                player.sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                                errorLog = 0;
                            }
                        }
                        if (errorLog == -1) {
                            sender.sendMessage(GeneralMethods.eventPrefix + "Error! There is no online player by the name of " + args.get(2) + ".");
                        }
                    }
                } else {
                    sender.sendMessage(GeneralMethods.eventPrefix + "Error! This command isn\'t applicable to this type of event.");
                }
            } else if (args.get(0).equalsIgnoreCase("spec")) {
                if (args.get(2).equalsIgnoreCase("all")) {
                    Location loc = reqEvent.getPOS();

                    for (EventPlayer ePlayer : reqEvent.getEventPlayers()) {
                        ePlayer.getPlayer().teleport(loc);
                        sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + ePlayer.getName() + " to SPEC.");
                        ePlayer.getPlayer().sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                    }
                } else {
                    Location loc = reqEvent.getPOS();

                    int errorLog = -1;
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        if (player.getName().equalsIgnoreCase(args.get(2))) {
                            player.teleport(loc);
                            sender.sendMessage(GeneralMethods.eventPrefix + "Teleported " + player.getName() + " to SPEC.");
                            player.sendMessage(GeneralMethods.eventPrefix + "You\'ve been teleported for the " + reqEvent.getEventName() + " event.");
                            errorLog = 0;
                        }
                    }
                    if (errorLog == -1) {
                        sender.sendMessage(GeneralMethods.eventPrefix + "Error! There is no online player by the name of " + args.get(2) + ".");
                    }
                }
            } else {
                sender.sendMessage(GeneralMethods.eventPrefix + "Error! " + getProperUse());
            }
        } else {
            sender.sendMessage(GeneralMethods.eventPrefix + "Error! " + getProperUse());
        }
    }
}