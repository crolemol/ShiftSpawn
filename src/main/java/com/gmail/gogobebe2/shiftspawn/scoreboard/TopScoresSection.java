package com.gmail.gogobebe2.shiftspawn.scoreboard;

import com.gmail.gogobebe2.shiftspawn.Participant;
import com.gmail.gogobebe2.shiftspawn.ShiftSpawn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class TopScoresSection extends ScoreboardSection {
    public TopScoresSection(Participant participant, Objective objective, ShiftSpawn plugin) {
        super(participant, objective, plugin);
    }

    @Override
    public void arrangeSection() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[Bukkit.getOnlinePlayers().size()]);
        setLabel(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Everyone's scores:", onlinePlayers.length + 2);
        for (int pIndex = 0; pIndex < onlinePlayers.length; pIndex++) {
            Player player = onlinePlayers[pIndex];
            Score score = getObjective().getScore(ChatColor.DARK_PURPLE + "- " + player.getName() + ": "
                    + ChatColor.GOLD  + ChatColor.BOLD + getPlugin().getParticipant(player).getScore());
            score.setScore(pIndex + 1);
        }
    }
}
