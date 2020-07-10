package com.Jacksonnn.DCEvents;

import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Event {
    String eventName;
    Player eventStaff;
    Object eventType;
    Location pos;

    ArrayList<EventPlayer> eventPlayers = new ArrayList<>();

    public Event(String name, Player staff, Location pos) {
        this.eventName = name;
        this.eventStaff = staff;
        this.pos = pos;

        GeneralMethods.events.add(this);
        eventType = this;
    }

    public String getEventName() {
        return eventName;
    }

    public Player getEventStaff() {
        return eventStaff;
    }

    public ArrayList<EventPlayer> getEventPlayers() {
        return eventPlayers;
    }

    public EventPlayer getEventPlayer(String name) {
        for (EventPlayer player : eventPlayers) {
            if (player.getName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    public void addPlayer(Player player) {
        EventPlayer ePlayer = new EventPlayer(eventPlayers.size() + 1, player);
    }

    public void addPlayer(CommandSender sender, String name) {
        for (EventPlayer ePlayer : this.getEventPlayers()) {
            if (ePlayer.getPlayer().getName().equalsIgnoreCase(name)) {
                sender.sendMessage(GeneralMethods.getEventsPrefix() + "Error! Player is already apart of event...");
                return;
            }
        }

        EventPlayer player = new EventPlayer(eventPlayers.size() + 1, Bukkit.getPlayer(name));
        eventPlayers.add(player);
        sender.sendMessage(GeneralMethods.getEventsPrefix() + name + " has joined the event, " + this.getEventName() + ".");
        player.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() + "You have been signed up for the " + getEventName() + " event hosted by " + getEventStaff().getName() + ".");
    }

    public void addPlayer(CommandSender sender, EventPlayer player) {
        if (eventPlayers.contains(player)) {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Player" + player.getName() + " has already joined the event!");
        } else {
            eventPlayers.add(player);
            player.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() + "You have been signed up for the " + getEventName() + " event hosted by " + getEventStaff().getName() + ".");
        }
    }

    public void removePlayer(CommandSender sender, String name) {
        EventPlayer player = getEventPlayer(name);
        if (player != null) {
            eventPlayers.remove(player);
            sender.sendMessage(GeneralMethods.getEventsPrefix() + name + " has left the event, " + this.getEventName() + ".");
            player.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() + "You have been removed from the " + getEventName() + " event.");
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Player" + name + " is not apart of this event!");
        }
    }

    public void removePlayer(CommandSender sender, EventPlayer player) {

        if (eventPlayers.contains(player)) {
            eventPlayers.remove(player);
            player.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() + "You have been removed from the " + getEventName() + " event.");
        } else {
            sender.sendMessage(GeneralMethods.getEventsPrefix() + "Player" + player.getName() + " is not apart of this event!");
        }
    }

    public void removePlayer(EventPlayer player) {
        if (this.eventPlayers.contains(player)) {
            this.eventPlayers.remove(player);
            player.getPlayer().sendMessage(GeneralMethods.getEventsPrefix() + "You have been removed from the " + getEventName() + " event.");
            Bukkit.getServer().getLogger().info(GeneralMethods.getEventsPrefix() + "Successfully removed player, " + player.getName() + ", from event, " + eventName);
        } else {
            Bukkit.getServer().getLogger().info(GeneralMethods.getEventsPrefix() + "Player" + player.getName() + " is not apart of any events.");
        }
    }

    public Object getEventType() {
        return this.eventType;
    }

    public void setEventType(Object type) {
        this.eventType = type;
    }

    public void setHost(Player staff) {
        this.eventStaff = staff;
    }

    public Event getEvent() {
        return this;
    }

    public void setPOS(Location pos) {
        this.pos = pos;
    }

    public Location getPOS() {
        return this.pos;
    }

    public void remove() {
        GeneralMethods.removeEvent(this);
    }
}
