package com.Jacksonnn.DCEvents.Games.BlockParty;

import com.Jacksonnn.DCEvents.EventPlayer.EventPlayer;
import com.Jacksonnn.DCEvents.GeneralMethods;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BlockPartyRunnable extends BukkitRunnable {

    private static final Material[] wool = new Material[] {Material.WHITE_CONCRETE, Material.BLACK_CONCRETE,
            Material.BLUE_CONCRETE, Material.BROWN_CONCRETE, Material.CYAN_CONCRETE, Material.GRAY_CONCRETE, Material.GREEN_CONCRETE,
            Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.LIME_CONCRETE, Material.MAGENTA_CONCRETE,
            Material.ORANGE_CONCRETE, Material.PINK_CONCRETE, Material.PURPLE_CONCRETE, Material.RED_CONCRETE, Material.YELLOW_CONCRETE};
    private static final ChatColor[] chatColors = new ChatColor[] {ChatColor.WHITE, ChatColor.BLACK,
            ChatColor.DARK_BLUE, ChatColor.GOLD, ChatColor.DARK_AQUA, ChatColor.DARK_GRAY, ChatColor.DARK_GREEN,
            ChatColor.AQUA, ChatColor.GRAY, ChatColor.GREEN, ChatColor.LIGHT_PURPLE,
            ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_PURPLE, ChatColor.DARK_RED, ChatColor.YELLOW};
    private static final String[] woolNames = new String[] {"White", "Black",
            "Blue", "Brown", "Cyan", "Gray", "Green",
            "Light Blue", "Light Gray", "Lime", "Magenta",
            "Orange", "Pink", "Purple", "Red", "Yellow"};
    private static final int LENGTH_OF_SHUFFLE = 100;
    private static final float CHANCE_TO_MISCOLOR_TEXT = 0.05f;

    private final Random random;
    private final BlockParty blockParty;
    private ArrayList<Player> alivePlayers;
    private ArrayList<Player> winners;
    private Block[] blocks;
    private int y;

    private int stage = 0;
    private int frame = 1;

    private int soundFrequency = 2; // while shuffling, every X ticks, make a sound
    private int timeAllottedToBeProper = 50; // how much time players have to stand on the right color before they die
    private int timeBetweenDeathAndShuffle = 60;

    private int colorsUsed = 0;
    private int[] colors = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    private int currentColor = 0;

    public BlockPartyRunnable(BlockParty blockParty) {
        random = new Random();
        this.blockParty = blockParty;
        alivePlayers = new ArrayList<>();
        winners = new ArrayList<>();
    }

    @Override public void run() {
        switch (stage) {
            case 1: // check if winner, shuffle, display color
                if (frame == 0) {
                    if (checkIfWinner())
                        return;
                    if (colorsUsed < 2)
                        addColor();
                    if (colorsUsed < 15)
                        addColor();
                }
                if (frame % soundFrequency == 0)
                    playSound();
                shuffle();
                if (++frame == LENGTH_OF_SHUFFLE) {
                    frame = timeAllottedToBeProper;
                    displayColor();
                    stage++;
                    timeAllottedToBeProper -= 2;
                }
                break;
            case 3: // kill improper players
                killImproperPlayers();
                frame = timeBetweenDeathAndShuffle;
                stage = 0;
                break;
            case 0: // wait
            case 2: // wait
                if (--frame <= 0)
                    stage++;
                break;
        }
    }

    private boolean checkIfWinner() {
        if (alivePlayers.isEmpty()) {
            declareWinners(winners);
            return true;
        } else if (alivePlayers.size() == 1) {
            declareWinners(alivePlayers.get(0));
            return true;
        }
        return false;
    }
    private void declareWinners(Player player) {
        Bukkit.getServer().broadcastMessage(GeneralMethods.eventPrefix + "Congratulations to " + player.getName() +
                " for winning the " + blockParty.getEventName() + " event!");
        blockParty.remove();
    }
    private void declareWinners(ArrayList<Player> players) {
        StringBuilder message = new StringBuilder(GeneralMethods.eventPrefix).append("Congratulations to ");
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0)
                message.append(player.getName());
            else if (players.size() == 2)
                message.append(" and ").append(player.getName());
            else if (i == players.size() - 1)
                message.append(", and ").append(player.getName());
            else
                message.append(", ").append(player.getName());
        }
        message.append(" for winning the ").append(blockParty.getEventName()).append(" event!");
        Bukkit.getServer().broadcastMessage(GeneralMethods.eventPrefix + message.toString());
        blockParty.remove();
    }
    private void addColor() {
        int index = random.nextInt(16-colorsUsed)+colorsUsed;
        int newColor = colors[index];
        colors[index] = colors[colorsUsed];
        colors[colorsUsed] = newColor;
        colorsUsed++;
    }
    private void playSound() {
        float pitch = random.nextFloat() * 1.5f + 0.5f;
        for (Player player : alivePlayers)
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, SoundCategory.VOICE, 0.7f, pitch);
    }
    private void shuffle() {
//        float amountToShuffle = (float)blocks.length/LENGTH_OF_SHUFFLE;
//        int startRange =
        int startRange = blocks.length * frame / LENGTH_OF_SHUFFLE;
        int endRange = Math.max(startRange, blocks.length * (frame+1) / LENGTH_OF_SHUFFLE - 1);
        for (int i = startRange; i <= endRange; i++)
            randomizeBlock(blocks[i]);
    }
    private void randomizeBlock(Block block) {
        block.setType(wool[colors[random.nextInt(colorsUsed)]]);
    }
    private void displayColor() {
        currentColor = colors[random.nextInt(colorsUsed)];
        ChatColor chatColor;
        if (random.nextFloat() <= CHANCE_TO_MISCOLOR_TEXT)
            chatColor = chatColors[random.nextInt(16)];
        else
            chatColor = chatColors[currentColor];
        for (Player player : alivePlayers) {
            player.sendTitle("", chatColor + woolNames[currentColor] + "!", 0, frame, 0);
        }
    }
    private void killImproperPlayers() {
        winners = (ArrayList<Player>)alivePlayers.clone();
        Material safeWool = wool[currentColor];
        for (int i = 0; i < alivePlayers.size(); i++) {
            Player alivePlayer = alivePlayers.get(i);
            if (!alivePlayer.isOnline()) {
                alivePlayers.remove(i--);
                continue;
            }
            Block block = alivePlayer.getWorld().getBlockAt(alivePlayer.getLocation().getBlockX(),
                    y, alivePlayer.getLocation().getBlockZ());
            if (block.getType() != safeWool) {
                alivePlayer.setHealth(0);
                alivePlayers.remove(i--);
            }
        }
    }

    public void setCorners(Location corner1, Location corner2) {
        y = corner1.getBlockY();
        World world = corner1.getWorld();
        if (world == null) {
            Player staff = Bukkit.getPlayer(blockParty.getEventStaff());
            if (staff != null)
                staff.sendMessage(GeneralMethods.eventPrefix +
                        "Error! Somehow, the Block Party event attempted to set its boundaries without a world!");
            blockParty.remove();
            return;
        }
        int startX = Math.min(corner1.getBlockX(), corner2.getBlockX());
        int startZ = Math.min(corner1.getBlockZ(), corner2.getBlockZ());
        int endX = Math.max(corner1.getBlockX(), corner2.getBlockX());
        int endZ = Math.max(corner1.getBlockZ(), corner2.getBlockZ());
        int size = (endX-startX+1)*(endZ-startZ+1);
        if (size > 128 * 128) {
            Player staff = Bukkit.getPlayer(blockParty.getEventStaff());
            if (staff != null)
                staff.sendMessage(GeneralMethods.eventPrefix +
                        "Error! The region selected for Block Party is too big! Attempted size: " + size + "");
            blockParty.remove();
            return;
        }
        blocks = new Block[size];
        int i = 0;
        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                blocks[i++] = world.getBlockAt(x, y, z);
            }
        }

        for (EventPlayer player : blockParty.getEventPlayers())
            if (player.getPlayer().isOnline())
                alivePlayers.add(player.getPlayer());
    }
}
