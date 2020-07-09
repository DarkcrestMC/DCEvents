package com.Jacksonnn.DCEvents.PlayerEvents;

import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.Games.BlockParty.BlockParty;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractWithBlockEvent implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        if (block == null)
            return;
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            if (sign.getLine(0).equalsIgnoreCase("[blockparty]")) {
                for (Event event : GeneralMethods.getEvents()) {
                    if (event instanceof BlockParty) {
                        BlockParty blockParty = (BlockParty)event;
                        if (e.getPlayer() == blockParty.getEventStaff()) {
                            blockParty.clickedSign(e.getPlayer(), sign);
                            return;
                        }
                    }
                }
                e.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() +
                        "Error! You are not hosting an event by the type 'BlockParty,'");
            }
        }
    }
}
