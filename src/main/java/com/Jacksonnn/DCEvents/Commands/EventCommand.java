package com.Jacksonnn.DCEvents.Commands;

import com.Jacksonnn.DCEvents.Commands.SubCommands.*;
import com.Jacksonnn.DCEvents.DCEvents;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class EventCommand implements CommandExecutor {

    //  /dcevents add <event> <player(s)>
    //  /dcevents remove <event> <player(s)>
    //  /dcevents startEvent <event>
    //  /dcevents endEvent <event>
    //  /dcevents eventList
    //  /dcevents playerList <event>
    //  /dcevents broadcast <message>
    //  /dcevents help

    private List<EventSubCommand> subCommands = new ArrayList<>();
    private DCEvents plugin;

    public EventCommand(DCEvents dcevents) {
        this.plugin = dcevents;
        registerSubCommand();
    }

    private void registerSubCommand() {
        subCommands.add(new Add());
        subCommands.add(new Broadcast());
        subCommands.add(new Countdown());
        subCommands.add(new End());
        subCommands.add(new Help());
        subCommands.add(new Info());
        subCommands.add(new com.Jacksonnn.DCEvents.Commands.SubCommands.List());
        subCommands.add(new Remove());
        subCommands.add(new Start());
        subCommands.add(new Teleport());
        subCommands.add(new Reload());
        subCommands.add(new Join());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("DCCore.AllowEvents")) {
            if (args.length >= 1) {
                for (EventSubCommand subCommand : subCommands) {
                    if (subCommand.getAliases().contains(args[0]) || subCommand.getName().equalsIgnoreCase(args[0])) {
                        if (sender.hasPermission(subCommand.getPermission())) {
                            subCommand.execute(sender, buildArguments(args));
                        } else {
                            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Insufficient permission to execute this command. (Step 2)");
                        }
                    }
                }
            } else {
                new Help().execute(sender, buildArguments(args));
            }
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Insufficient permission to execute these commands. (Step 1)");
        }
        return true;
    }

    private List<String> buildArguments(String[] args) {
        List<String> bArgs = new ArrayList<>();
        int i = 0;
        for (String arg : args) {
            if (i == 0) {

            } else {
                bArgs.add(arg);
            }
            i++;
        }
        return bArgs;
    }
}
