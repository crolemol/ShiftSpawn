package com.gmail.gogobebe2.shiftspawn;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ShiftSpawn extends JavaPlugin {
    public final static String MIN_PLAYERS_KEY = "Minimum players before game starts";
    public final static String TIME_BEFORE_START_KEY = "Time before games starts";
    public final static String GAME_TIME = "Game time";
    public final static String ALPHA_CORE_ID = "Alpha Core Block ID";
    private Game game;
    private int spawnIndex;
    private List<Participant> participants = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("shiftspawn")) {
            if (!sender.hasPermission("shiftspawn.*")) {
                sender.sendMessage(ChatColor.RED + "You sneaky kid. You aren't allowed to use this command silly!");
                return true;
            } else if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Now how the hell would that work? Idiot, you have to be a player to use this command!!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Enter a spawn id /shiftspawn <'main':number>");
                return true;
            }
            String id = args[0].toLowerCase();
            if (!id.equalsIgnoreCase("main")) {
                try {
                    if (Integer.parseInt(args[0]) < 0) {
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(ChatColor.DARK_RED + args[0] + ChatColor.RED + " is not an appropriate number or is not main!");
                    return true;
                }
            }
            final Location LOCATION = player.getLocation();
            final String WORLD = LOCATION.getWorld().getName();
            final double X = LOCATION.getX();
            final double Y = LOCATION.getY();
            final double Z = LOCATION.getZ();
            final float PITCH = LOCATION.getPitch();
            final float YAW = LOCATION.getYaw();

            getConfig().set("Spawns." + id + ".World", WORLD);
            getConfig().set("Spawns." + id + ".X", X);
            getConfig().set("Spawns." + id + ".Y", Y);
            getConfig().set("Spawns." + id + ".Z", Z);
            getConfig().set("Spawns." + id + ".Pitch", PITCH);
            getConfig().set("Spawns." + id + ".Yaw", YAW);
            saveConfig();
            player.sendMessage(ChatColor.GREEN + "Set " + (id.equalsIgnoreCase("main") ? "the main spawn." : "the spawn id " + id));
            return true;
        }
        return false;
    }

    public String getNextSpawnIndex() {
        List<String> ids = new ArrayList<>();
        ids.addAll(getConfig().getConfigurationSection("Spawns").getKeys(false));
        if (ids.contains("main")) {
            ids.remove("main");
        }
        Collections.sort(ids);
        Iterator<String> iterator = ids.iterator();
        if (!iterator.hasNext()) {
            return ids.get(0);
        } else {
            return iterator.next();
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Starting up ShiftSpawn. If you need me to update this plugin, email at gogobebe2@gmail.com");
        saveDefaultConfig();
        this.game = new Game(this);
        game.setGameState(GameState.WAITING);
        game.startTimer(false);
        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.AQUA + "You have been kicked while the game gets setup.");
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling ShiftSpawn. If you need me to update this plugin, email at gogobebe2@gmail.com");
        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.kickPlayer(ChatColor.AQUA + "You have been kicked while game restarts.");
            }
        }
    }

    public Game getGame() {
        return this.game;
    }

    public int getSpawnIndex() {
        return this.spawnIndex;
    }

    public void setSpawnIndex(int spawnIndex) {
        this.spawnIndex = spawnIndex;
    }

    public List<Participant> getParticipants() {
        return this.participants;
    }

    public boolean containsPlayer(Player player) {
        if (participants.isEmpty()) {
            return false;
        }
        for (Participant participant : participants) {
            if (participant.getPlayer().equals(player)) {
                return true;
            }
        }
        return false;
    }

    public Participant getParticipant(Player player) {
        for (Participant participant : participants) {
            if (participant.getPlayer().equals(player)) {
                return participant;
            }
        }
        return null;
    }
}
