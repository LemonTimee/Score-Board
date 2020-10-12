package com.lemontimee.scoreboard;

import java.io.Serializable;

public class Players implements Serializable {

    private int player_id;
    private String player_name;
    private String create_date;

    public Players(int player_id, String player_name, String create_date) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.create_date = create_date;
    }

    public Players() {
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
