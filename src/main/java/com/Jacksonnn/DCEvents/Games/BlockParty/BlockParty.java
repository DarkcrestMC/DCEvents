package com.Jacksonnn.DCEvents.Games.BlockParty;

import com.Jacksonnn.DCEvents.DCEvents;
import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockParty extends Event {

    private final BlockPartyRunnable runnable;
    private boolean isRunning = false;

    public BlockParty(String name, Player staff, Location pos) {
        super(name, staff, pos);
        runnable = new BlockPartyRunnable(this);
    }

    public void clickedSign(Player staffMember, Sign sign) {
        if (isRunning) {
            staffMember.sendMessage(GeneralMethods.getEventsPrefix() + "Error! Your Block Party event is already running.");
            return;
        }
        if (getEventPlayers().size() < 2) {
            staffMember.sendMessage(GeneralMethods.getEventsPrefix() + "Error! You need at least 2 players to play Block Party." +
                    " Use '/events add " + getEventName() + " [player name]' to add a player to your event.");
            return;
        }
        try {
            String[] corner1String = sign.getLine(1).split(" ");
            String[] corner2String = sign.getLine(2).split(" ");
            if (corner1String.length != 3 || corner2String.length != 3)
                return;
            int[] corner1Ints = new int[3];
            int[] corner2Ints = new int[3];
            for (int i = 0; i < 3; i++) {
                corner1Ints[i] = Integer.parseInt(corner1String[i]);
                corner2Ints[i] = Integer.parseInt(corner2String[i]);
            }
            runnable.setCorners(new Location(sign.getWorld(), corner1Ints[0], corner1Ints[1], corner1Ints[2]),
                    new Location(sign.getWorld(), corner2Ints[0], corner2Ints[1], corner2Ints[2]));
            isRunning = true;
            runnable.runTaskTimer(DCEvents.getDCEvents(), 1, 1);
        } catch (Exception e) {
            staffMember.sendMessage(GeneralMethods.getEventsPrefix() + "Error! The sign could not be parsed. Ensure that" +
                    " the second and third lines are the block coordinates of the corners. The numbers should NOT have" +
                    " commas and should be separated by spaces. For example: '32 64 -1644'");
        }
    }

    @Override public void remove() {
        super.remove();
        if (!runnable.isCancelled())
            runnable.cancel();
    }
}
