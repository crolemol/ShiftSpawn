package com.gmail.gogobebe2.shiftspawn.scoreboards;

import com.gmail.gogobebe2.shiftspawn.Participant;
import com.gmail.gogobebe2.shiftspawn.ShiftSpawn;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public abstract class ScoreboardSection {
    private Score heading;
    private Score score;
    private Scoreboard scoreboard;
    private Objective objective;
    private Participant participant;
    private ShiftSpawn plugin;

    public ScoreboardSection(Participant participant, Objective objective, ShiftSpawn plugin) {
        this.participant = participant;
        this.scoreboard = participant.getPlayer().getScoreboard();
        this.objective = objective;
        this.plugin = plugin;
    }

    public abstract void displaySection();

    public void deleteSection() {
        scoreboard.resetScores(heading.getEntry());
    }

    public void setSectionIndex(int index) {
        heading.setScore(index);
    }

    public void saveSection() {
        participant.getPlayer().setScoreboard(scoreboard);
    }

    public Score getHeading() {
        return heading;
    }

    public Score getScore() {
        return score;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public Participant getParticipant() {
        return participant;
    }

    public ShiftSpawn getPlugin() {
        return plugin;
    }

    public void setHeading(Score heading) {
        this.heading = heading;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}