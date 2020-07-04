package com.Jacksonnn.DCEvents.EventPlayer;

import org.bukkit.entity.Player;

import java.util.UUID;

public class EventPlayer {
    int id;
    Player player;

    public EventPlayer(int id, Player player) {
        this.id = id;
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }

    public int getID() {
        return id;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return player.getUniqueId();
    }

}
