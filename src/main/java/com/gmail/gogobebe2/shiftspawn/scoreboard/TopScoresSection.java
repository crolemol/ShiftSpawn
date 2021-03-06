package com.gmail.gogobebe2.shiftspawn.scoreboard;

import com.gmail.gogobebe2.shiftspawn.Participant;
import com.gmail.gogobebe2.shiftspawn.ShiftSpawn;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Arrays;

public class TopScoresSection extends MultipleScoreboardSection {

    public TopScoresSection(Scoreboard scoreboard, Objective objective, ShiftSpawn plugin) {
        super(scoreboard, objective, plugin);
    }

    @Override
    public void display() {
        super.display();
        Participant[] participants =
                getPlugin().getParticipants().toArray(new Participant[getPlugin().getParticipants().size()]);
        Arrays.sort(participants);

        String pref = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Scores:";
        addScore(pref + StringUtils.repeat(" ", 16 - pref.length())
                + ChatColor.DARK_GREEN + ChatColor.ITALIC + "score" + ChatColor.LIGHT_PURPLE + " : "
                + ChatColor.DARK_RED + ChatColor.ITALIC + "kills", participants.length + 1);

        for (int pIndex = 0; pIndex < participants.length; pIndex++) {
            Participant participant = participants[pIndex];
            String prefix = ChatColor.DARK_PURPLE + " " + participant.getPlayer().getName() + ": ";
            addScore(prefix + StringUtils.repeat(" ", 17 - (participant.getPlayer().getName().length()
                    + getDigitsInString(participant.getPlayer().getName())))
                    + ChatColor.DARK_GREEN + ChatColor.BOLD + participant.getScore()
                    + ChatColor.DARK_PURPLE + " : "
                    + ChatColor.DARK_RED + ChatColor.BOLD + participant.getKills(), pIndex + 1);
        }
    }

    private int getDigitsInString(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) if (Character.isDigit(string.charAt(i))) count++;
        return count;
    }
}
