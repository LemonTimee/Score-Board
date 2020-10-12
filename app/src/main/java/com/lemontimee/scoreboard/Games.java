package com.lemontimee.scoreboard;

import java.io.Serializable;

public class Games implements Serializable {

    private int game_id;
    private String game_name;
    private String game_winner;
    private String  finish_score;
    private String  finish_round;
    private String create_date;

    public Games(int game_id, String game_name, String game_winner, String  finish_score, String  finish_round, String create_date) {
        this.game_id = game_id;
        this.game_name = game_name;
        this.game_winner = game_winner;
        this.finish_score = finish_score;
        this.finish_round = finish_round;
        this.create_date = create_date;
    }

    public Games() {
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getGame_winner() {
        return game_winner;
    }

    public void setGame_winner(String game_winner) {
        this.game_winner = game_winner;
    }

    public String  getFinish_score() {
        return finish_score;
    }

    public void setFinish_score(String finish_score) {
        this.finish_score = finish_score;
    }

    public String getFinish_round() {
        return finish_round;
    }

    public void setFinish_round(String finish_round) {
        this.finish_round = finish_round;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
