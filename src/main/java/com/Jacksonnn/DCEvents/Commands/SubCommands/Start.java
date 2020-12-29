package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.Games.BlockParty.BlockParty;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Start implements EventSubCommand {

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("create");
        aliases.add("startevent");
        aliases.add("run");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events start <name> <type>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.StartEventCommand");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.StartEvent";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events start <eventName> [<type>]
        if (!(sender instanceof Player)) {
            sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! You must be a player to execute this command.");
            return;
        }
        Player player = (Player)sender;
        if (args.size() == 1) {
            for (Event gEvent : GeneralMethods.getEvents()) {
                if (gEvent.getEventName().equalsIgnoreCase(args.get(0))) {
                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! There is already an event by this name!");
                    return;
                }
            }
            Event event = new Event(args.get(0), player, ((Player) sender).getLocation());
            sender.sendMessage(GeneralMethods.getSuccessPrefix() + "Successfully created event, " + event.getEventName() + ", by, " + event.getEventStaff().getName());
            sender.sendMessage(" ");
            Bukkit.broadcastMessage(GeneralMethods.getEventsPrefix() + "Now starting event, " + event.getEventName() + ", hosted by, " + event.getEventStaff().getName() + ". -Console");
        } else if (args.size() == 2) {
            String name = args.get(0);
            String eventType = args.get(1);

            switch (eventType.toLowerCase()) {
                case "tourney":
                case "tournament":
                    Location spectator = new Location(Bukkit.getServer().getWorld(ConfigManager.defaultConfig.get().getString("Events.Tournament.Spectator.world")),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Spectator.x"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Spectator.y"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Spectator.z"));
                    Location pos1 = new Location(Bukkit.getServer().getWorld(ConfigManager.defaultConfig.get().getString("Events.Tournament.Pos1.world")),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos1.x"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos1.y"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos1.z"));
                    Location pos2 = new Location(Bukkit.getServer().getWorld(ConfigManager.defaultConfig.get().getString("Events.Tournament.Pos2.world")),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos2.x"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos2.y"),
                            ConfigManager.defaultConfig.get().getDouble("Events.Tournament.Pos2.z"));

                    new Tournament(name, player, spectator, pos1, pos2);
                    break;
                case "bp":
                case "block":
                case "party":
                case "blockparty":
                    spectator = player.getLocation();
                    new BlockParty(name, player, spectator);
                    break;
                default:
                    sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! " + eventType + " is not an event type.");
                    return;
            }
//            sender.sendMessage(GeneralMethods.getSuccessPrefix() + "Successfully created event, " + name + ", by, " + staff.getName());
//            sender.sendMessage(" "); // redundant
            Bukkit.broadcastMessage(GeneralMethods.getEventsPrefix() + "Now starting " + name + " hosted by " + player.getName() + "!");
            TextComponent joinText = new TextComponent(ChatColor.DARK_GREEN + "     [ " + ChatColor.GREEN + ChatColor.ITALIC + ChatColor.UNDERLINE + "Click to Join!" + ChatColor.DARK_GREEN + " ]");
            joinText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dce join " + name));
            joinText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.AQUA + "PRO TIP: Type \"/dce join\" to join!")));
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(joinText);
            }
        } else {
            sender.sendMessage(GeneralMethods.getErrorPrefix() + "Error! " + getProperUse());
        }
    }
}
