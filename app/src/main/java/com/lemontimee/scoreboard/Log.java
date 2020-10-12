package com.lemontimee.scoreboard;

import java.io.Serializable;

public class Log implements Serializable {

    private int log_id;
    private int game_id;
    private int player_id;
    private int score;
    private long create_date;

    public Log(int log_id, int game_id, int player_id, int score, long create_date) {
        this.log_id = log_id;
        this.game_id = game_id;
        this.player_id = player_id;
        this.score = score;
        this.create_date = create_date;
    }

    public Log() {
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getCreate_date() {
        return create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }
}
