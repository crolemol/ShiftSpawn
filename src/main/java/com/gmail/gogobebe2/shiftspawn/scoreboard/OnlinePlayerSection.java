package com.gmail.gogobebe2.shiftspawn.scoreboard;

import com.gmail.gogobebe2.shiftspawn.ShiftSpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class OnlinePlayerSection extends SingleScoreboardSection {
    public OnlinePlayerSection(Scoreboard scoreboard, Objective objective, ShiftSpawn plugin) {
        super(scoreboard, objective, plugin);
    }

    @Override
    public void display() {
        super.display();
        setLabel(ChatColor.AQUA + "" + ChatColor.ITALIC + "Players online: " + ChatColor.DARK_AQUA
                + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), 0);
    }
}
