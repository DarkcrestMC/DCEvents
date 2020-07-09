package com.Jacksonnn.DCEvents.Games.Tournament;

import com.Jacksonnn.DCEvents.Commands.SubCommands.Broadcast;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.DCEvents;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Commands implements CommandExecutor {

    /*
        /tournament start <name>
        /tournament end <name>
        /tournament list (-p <tournyName>)
        /tournament run <name>
        /tournament addplayer <tourny> <player(s)>
        /tournament removeplayer <tourny> <player(s)>
        /tournament setSpectator <tourny>
        /tournament setPos1 <tourny>
        /tournament setPos2 <tourny>
        /tournament tp <tourny> <player> [pos1|pos2|spectator]
        /tournament tphere <player|all>
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration defaultConfig = ConfigManager.defaultConfig.get();
        FileConfiguration langConfig = ConfigManager.langConfig.get();

        if (sender instanceof Player) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("start")) {
            /*
                - Create new Tournament();
                - Set Spectator Spot;
                - Announce Tournament Creation;
             */

                    if (args[1] != null) {
                        String tournyName = args[1];

                        String pos1World = defaultConfig.getString("Events.Tournament.Pos1.world");
                        double pos1X = defaultConfig.getDouble("Events.Tournament.Pos1.x");
                        double pos1Y = defaultConfig.getDouble("Events.Tournament.Pos1.y");
                        double pos1Z = defaultConfig.getDouble("Events.Tournament.Pos1.z");
                        Location pos1 = new Location(Bukkit.getWorld(pos1World), pos1X, pos1Y, pos1Z);

                        String pos2World = defaultConfig.getString("Events.Tournament.Pos2.world");
                        double pos2X = defaultConfig.getDouble("Events.Tournament.Pos2.x");
                        double pos2Y = defaultConfig.getDouble("Events.Tournament.Pos2.y");
                        double pos2Z = defaultConfig.getDouble("Events.Tournament.Pos2.z");
                        Location pos2 = new Location(Bukkit.getWorld(pos2World), pos2X, pos2Y, pos2Z);

                        String spectatorWorld = defaultConfig.getString("Events.Tournament.Spectator.world");
                        double spectatorX = defaultConfig.getDouble("Events.Tournament.Spectator.x");
                        double spectatorY = defaultConfig.getDouble("Events.Tournament.Spectator.y");
                        double spectatorZ = defaultConfig.getDouble("Events.Tournament.Spectator.z");
                        Location spectator = new Location(Bukkit.getWorld(spectatorWorld), spectatorX, spectatorY, spectatorZ);

                        new Tournament(tournyName, (Player) sender, pos1, pos2, spectator);
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully created new tournament, " + tournyName + ".");

                        ArrayList<String> broadcastArgs = new ArrayList<>();

                        String message = "New tournament is starting called " + tournyName + ".";

                        for (String msg : message.split(" ")) {
                            broadcastArgs.add(msg);
                        }

                        new Broadcast().execute(sender, broadcastArgs);

                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "You should name your tournament...");
                    }
                } else if (args[0].equalsIgnoreCase("end")) {
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(args[1]);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        GeneralMethods.removeEvent(event);
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully ended the tournament.");
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }

                } else if (args[0].equalsIgnoreCase("list")) {
                    if (args.length == 3) {
                        if (StringUtils.join(args, " ").contains("-p")) {
                            String tournamentName = "null";

                            for (String arg : args) {
                                if (!arg.equalsIgnoreCase("list") || !arg.equalsIgnoreCase("-p")) {
                                    tournamentName = arg;
                                }
                            }

                            Event event = null;
                            try {
                                event = GeneralMethods.getEvent(tournamentName);
                            } catch (Exception e) {
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                                e.printStackTrace();
                            }

                            if (GeneralMethods.isTournament(event)) {
                                Tournament tournament = (Tournament) event;

                                String eventPlayers = ChatColor.GREEN + " ";
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Participants in " + tournament.getName() + " (" + tournament.getPlayers().size() + "):");
                                for (EventPlayer ePlayer : tournament.getPlayers()) {
                                    eventPlayers += ChatColor.GREEN + ePlayer.getName() + ", ";
                                }

                                eventPlayers += ChatColor.BLUE + tournament.getTournamentHost().getName();

                                sender.sendMessage(eventPlayers);

                            } else {
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                            }
                        }
                    } else {
                        ArrayList<Tournament> tournaments = new ArrayList<>();
                        for (Event event : GeneralMethods.getEvents()) {
                            if (GeneralMethods.isTournament(event)) {
                                tournaments.add((Tournament) event);
                            }
                        }

                        String tournamentList = ChatColor.GREEN + " ";
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Active Tournaments:");

                        for (Tournament t : tournaments) {
                            tournamentList += t.getName() + ChatColor.BLUE + " (" + t.getTournamentHost() + ")" + ChatColor.GREEN + ", ";
                        }
                        sender.sendMessage(tournamentList);
                    }
                } else if (args[0].equalsIgnoreCase("run")) {

                } else if (args[0].equalsIgnoreCase("addplayer")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;

                        try {
                            for (String arg : args) {
                                if (!arg.equalsIgnoreCase(tournament.getName()) || !arg.equalsIgnoreCase(args[0])) {
                                    Player reqPlayer = Bukkit.getPlayer(arg);

                                    if (reqPlayer != null) {
                                        tournament.addPlayer(reqPlayer);
                                    }
                                }
                            }

                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully added player(s) to tournament, " + tournament.getName() + ".");

                        } catch (Exception e) {
                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is an incorrect player name...");
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("removeplayer")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;

                        try {
                            for (String arg : args) {
                                if (!arg.equalsIgnoreCase(tournament.getName()) || !arg.equalsIgnoreCase(args[0])) {
                                    Player reqPlayer = Bukkit.getPlayer(arg);

                                    if (reqPlayer != null) {
                                        tournament.removePlayer(reqPlayer);
                                    }
                                }
                            }

                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully removed player(s) to tournament, " + tournament.getName() + ".");
                        } catch (Exception e) {
                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is an incorrect player name...");
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("setSpectator")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;

                        Player pSender = (Player) sender;

                        Location pSenderLoc = pSender.getLocation();

                        tournament.setSpectator(pSenderLoc);
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully set the Spectator location for " + tournament.getName() + ".");
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("setPos1")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;

                        Player pSender = (Player) sender;

                        Location pSenderLoc = pSender.getLocation();

                        tournament.setPos1(pSenderLoc);
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully set the Pos1 location for " + tournament.getName() + ".");
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("setPos2")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;

                        Player pSender = (Player) sender;

                        Location pSenderLoc = pSender.getLocation();

                        tournament.setPos2(pSenderLoc);
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully set the Pos2 location for " + tournament.getName() + ".");
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("tp")) {
                    // /tournament   tp    <tourny>   <player>    [pos1|pos2|spectator]
                    //             arg[0]   args[1]    arg[2]           arg[3]
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        Tournament tournament = (Tournament) event;
                        if (args.length == 4) {
                            try {
                                Player player = Bukkit.getPlayer(args[1]);
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "args[0]:" + args[0] + " args[1]: " + args[1] + " args[2]: " + args[2] + " args[3]: " + args[3]);

                                if (args[3].equalsIgnoreCase("pos1")) {
                                    player.teleport(tournament.getPos1());
                                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully sent player to Pos1.");
                                } else if (args[3].equalsIgnoreCase("pos2")) {
                                    player.teleport(tournament.getPos2());
                                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully sent player to Pos2.");
                                } else if (args[3].equalsIgnoreCase("spectator")) {
                                    player.teleport(tournament.getSpectator());
                                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully sent player to spectator area.");
                                } else {
                                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Valid Usage: /tournament tp <tourny> <player> <pos1|pos2|spectator>");
                                }
                            } catch (Exception e) {
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Something went wrong... is the right player name?");
                                e.printStackTrace();
                            }
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else if (args[0].equalsIgnoreCase("tphere")) {
                    String tournamentName = args[1];
                    Event event = null;
                    try {
                        event = GeneralMethods.getEvent(tournamentName);
                    } catch (Exception e) {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "There is no event by that name.");
                        e.printStackTrace();
                    }

                    if (GeneralMethods.isTournament(event)) {
                        if (args.length == 3) {
                            Tournament tournament = (Tournament) event;

                            Player pSender = (Player) sender;
                            Location pSenderLoc = pSender.getLocation();

                            ArrayList<EventPlayer> tPlayers = tournament.getPlayers();

                            if (args[2].equalsIgnoreCase("all")) {
                                for (EventPlayer player : tPlayers) {
                                    player.getPlayer().teleport(pSenderLoc);
                                }
                                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully teleported all players to your location.");
                            } else {
                                try {
                                    Player player = Bukkit.getPlayer(args[1]);
                                    if (tPlayers.contains(player)) {
                                        player.teleport(pSenderLoc);
                                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "Successfully teleported player to your location.");
                                    } else {
                                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That player isn't apart of the tournament...");
                                    }
                                } catch (Exception e) {
                                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Something went wrong... is that the right player name?");
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Valid Usage: /tournament tphere <tourny> <player|all>");
                        }
                    } else {
                        sender.sendMessage(GeneralMethods.getEventsPrefix() + "That event isn't a tournament!!");
                    }
                } else /* HELP */ {
                /*
                    /tournament start <name>
                    /tournament end <name>
                    /tournament list (-p <tournyName>)
                    /tournament run <name>
                    /tournament addplayer <tourny> <player(s)>
                    /tournament removeplayer <tourny> <player(s)>
                    /tournament setSpectator <tourny>
                    /tournament setPos1 <tourny>
                    /tournament setPos2 <tourny>
                    /tournament tp <tourny> <player> [pos1|pos2|spectator]
                    /tournament tphere <tourny> <player|all>
                 */
                    sender.sendMessage(GeneralMethods.getEventsPrefix() + "Command list for /tournament:");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament start <name>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament end <tourny>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament list [-p <tourny>]");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament run <tourny>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament addPlayer <tourny> <player(s)>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament removePlayer <tourny> <player(s)>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setSpectator <tourny>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setPos1 <tourny>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setPos2 <tourny>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament tp <tourny> <player> <pos1|pos2|spectator>");
                    sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament tphere <tourny> <player|all>");
                }
            } else {
                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Command list for /tournament:");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament start <name>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament end <tourny>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament list [-p <tourny>]");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament run <tourny>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament addPlayer <tourny> <player(s)>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament removePlayer <tourny> <player(s)>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setSpectator <tourny>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setPos1 <tourny>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament setPos2 <tourny>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament tp <tourny> <player> <pos1|pos2|spectator>");
                sender.sendMessage(org.bukkit.ChatColor.YELLOW + "/tournament tphere <tourny> <player|all>");
            }
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "These commands can only be executed by a player.");
        }
        return true;
    }
}
