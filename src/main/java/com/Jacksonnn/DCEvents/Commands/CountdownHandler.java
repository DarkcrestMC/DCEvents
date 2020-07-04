package com.Jacksonnn.DCEvents.Commands;

import com.Jacksonnn.DCEvents.DCEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownHandler extends BukkitRunnable {
    int ticksLeft;

    boolean isRunning = false;

    public CountdownHandler() {
        runTaskTimer(DCEvents.getDCEvents(), 1L, 1L);
    }

    public void start(int startCount) {
        this.isRunning = true;
        this.ticksLeft = startCount * 20;
    }

    public void run() {
        if (this.isRunning && this.ticksLeft-- % 20 == 0)
            if (this.ticksLeft <= 0) {
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.MAGIC + " ------ " + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + "GO!" + ChatColor.LIGHT_PURPLE + ChatColor.MAGIC + " ------ ");
                this.isRunning = false;
            } else {
                String number = ((this.ticksLeft + 1) / 20) + "";
                Bukkit.broadcastMessage(ChatColor.DARK_RED + " --" + ChatColor.RED + "--" + ChatColor.GOLD + "-- " + ChatColor.AQUA + ChatColor.ITALIC + number + ChatColor.GOLD + " --" + ChatColor.RED + "--" + ChatColor.DARK_RED + "-- ");
            }
    }
}
