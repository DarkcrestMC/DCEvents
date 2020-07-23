package com.Jacksonnn.DCEvents.Commands.SubCommands;

import com.Jacksonnn.DCEvents.Commands.EventSubCommand;
import com.Jacksonnn.DCEvents.Configuration.ConfigManager;
import com.Jacksonnn.DCEvents.GeneralMethods;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Broadcast implements EventSubCommand {

    @Override
    public String getName() {
        return "broadcast";
    }

    @Override
    public List<String> getAliases() {
        ArrayList<String> aliases = new ArrayList<>();

        aliases.add("bcast");
        aliases.add("bc");
        aliases.add("broadc");

        return aliases;
    }

    @Override
    public String getProperUse() {
        return "/events start <name> <type>";
    }

    @Override
    public String getDescription() {
        return ConfigManager.langConfig.get().getString("Events.CommandDescriptions.Broadcast");
    }

    @Override
    public String getPermission() {
        return "DCEvents.Host.Broadcast";
    }

    @Override
    public void execute(CommandSender sender, List<String> args) {
        //  /events broadcast <msg>
        String[] wordsOfMess = new String[args.size()];

        for (int i = 0; i < args.size(); i++) {
            wordsOfMess[i] = args.get(i);
        }
        if (sender instanceof Player) {
            Bukkit.getServer().broadcastMessage(GeneralMethods.getEventsPrefix() + ChatColor.translateAlternateColorCodes('&', StringUtils.join(wordsOfMess, ' ')) + GeneralMethods.getEventsAccentColor() + " -" + sender.getName());
        } else {
            Bukkit.getServer().broadcastMessage(GeneralMethods.getEventsPrefix() + ChatColor.translateAlternateColorCodes('&', StringUtils.join(wordsOfMess, ' ')) + GeneralMethods.getEventsAccentColor() + " -Console");
        }
    }
}