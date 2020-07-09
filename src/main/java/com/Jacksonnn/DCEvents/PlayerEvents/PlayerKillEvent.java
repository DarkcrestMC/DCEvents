package com.Jacksonnn.DCEvents.PlayerEvents;

import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.Games.Tournament.Tournament;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class PlayerKillEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        for (Event event : GeneralMethods.events) {
            ArrayList<EventPlayer> eventPlayers = event.getEventPlayers();
            for (int i = 0; i < eventPlayers.size(); i++) {
                EventPlayer eventPlayer = eventPlayers.get(i);
                if (eventPlayer.getPlayer() == player) {
                    if (GeneralMethods.isTournament(event)) {
                        eventPlayer.getPlayer().teleport(((Tournament) event).getSpectator());
                    } else {
                        eventPlayer.getPlayer().teleport(event.getPOS());
                    }
                    eventPlayers.remove(i--);
                }
            }
        }
    }
}
