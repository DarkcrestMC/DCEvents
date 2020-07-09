package com.Jacksonnn.DCEvents.PlayerEvents;

import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements Listener {

    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        for (Event event : GeneralMethods.events) {
            for (EventPlayer eventPlayer : event.getEventPlayers()) {
                if (eventPlayer.getPlayer() == player) {

                    if (GeneralMethods.isTournament(event)) {
                        eventPlayer.getPlayer().teleport(((Tournament) event).getSpectator());
                    } else {
                        eventPlayer.getPlayer().teleport(event.getPOS());
                    }
                    
                    event.removePlayer(eventPlayer);
                }
            }
        }
    }
}