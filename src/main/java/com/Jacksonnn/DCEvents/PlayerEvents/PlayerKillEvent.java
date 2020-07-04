package com.Jacksonnn.DCEvents.PlayerEvents;

import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        for (Event event : GeneralMethods.events) {
            for (EventPlayer eventPlayer : event.getEventPlayers()) {
                if (eventPlayer.getPlayer() == player) {
                    event.removePlayer(eventPlayer);
                }
            }
        }
    }
}
