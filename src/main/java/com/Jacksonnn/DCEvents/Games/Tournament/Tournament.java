package com.Jacksonnn.DCEvents.Games.Tournament;

import com.Jacksonnn.DCEvents.Event;
import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Tournament extends Event {
    private String name;
    private Location spectator;
    private Location pos1;
    private Location pos2;
    private Event eventSuper;

    public Tournament(String name, Player tournamentHost, Location spectator, Location pos1, Location pos2) {
        //pos in event super is spectator pos -- should be spectator for ALL Event Types.
        super(name, tournamentHost, spectator);
        this.name = name;
        this.spectator = spectator;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.eventSuper = super.getEvent();
        this.eventSuper.setEventType(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getTournamentHost() {
        return super.getEventStaff();
    }

    public ArrayList<EventPlayer> getPlayers() {
        return super.getEventPlayers();
    }

    public void addPlayer(Player player) {
        super.addPlayer(player);
    }

    public void removePlayer(Player player) {
        Event event = super.getEvent();

        for (EventPlayer ePlayer : event.getEventPlayers()) {
            if (ePlayer.getPlayer() == player) {
                event.removePlayer(ePlayer);
                break;
            }
        }
    }

    public Location getSpectator() {
        if (this.spectator == eventSuper.getPOS()) {
            return spectator;
        } else {
            return eventSuper.getPOS();
        }
    }

    public void setSpectator(Location spectator) {
        this.spectator = spectator;
        this.eventSuper.setPOS(spectator);
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
}
