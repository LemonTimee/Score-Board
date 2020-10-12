package com.lemontimee.scoreboard;

import java.io.Serializable;

public class LogPlayer implements Serializable {

    private int score;
    private String player_name;

    public LogPlayer(int score, String player_name) {
        this.score = score;
        this.player_name = player_name;
    }

    public LogPlayer() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
}
