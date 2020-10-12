package com.lemontimee.scoreboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class PlayersDao {

    public ArrayList<Players> returnAllPlayers(Database database){  //bütün kayıtlı oyuncuları dönecek.

        ArrayList<Players> playersArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM Player",null);  // LIMIT 10

        while (c.moveToNext()){

            Players players = new Players(c.getInt(c.getColumnIndex("player_id")),
                    c.getString(c.getColumnIndex("player_name")),
                    c.getString(c.getColumnIndex("create_date")));

            playersArrayList.add(players);

        }

        c.close();
        sqLiteDatabase.close();
        return playersArrayList;
    }

    public ArrayList<Players> returnPlayersForGames(Database database){  //bütün kayıtlı oyuncuları dönecek.

        ArrayList<Players> playersArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT Game.game_id, Player.player_id, Player.player_name, Player.create_date "
                                                    + " FROM Player , Log , Game  where Player.player_id = Log.player_id "
                                                        + " AND Log.game_id = Game.game_id "
                                                            +  " AND Game.game_id = (SELECT MAX(game_id) FROM Game) ",null);

        while (c.moveToNext()){

            Players players = new Players(c.getInt(c.getColumnIndex("player_id")),
                    c.getString(c.getColumnIndex("player_name")),
                    c.getString(c.getColumnIndex("create_date")));

            playersArrayList.add(players);

        }

        c.close();
        sqLiteDatabase.close();
        return playersArrayList;
    }

    public void addNewPlayer(Database database, String player_name,String create_date){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("player_name",player_name);
        values.put("create_date",create_date);

        sqLiteDatabase.insertOrThrow("Player",null,values);

        sqLiteDatabase.close();

    }

    public void editPlayer(Database database, String playerName, int player_id){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("player_name",playerName);

        sqLiteDatabase.update("Player",null,"player_id=?",new String[]{String.valueOf(player_id)});
        sqLiteDatabase.close();

    }

    public void deletePlayer(Database database,int player_id){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        sqLiteDatabase.delete("Player","player_id=?",new String[]{String.valueOf(player_id)});

        sqLiteDatabase.close();
    }

    public int returnPlayerId(Database database, String player_name){

        int playerId = 0;

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT player_id FROM Player WHERE player_name = " + "player_name", null);


        while(c.moveToNext()){

            //playerId = c.getInt(c.getColumnIndex("player_id"));

            //playerId = Integer.parseInt(c.getString(c.getColumnIndex("player_id")));

            playerId = Integer.parseInt(c.getString(c.getColumnIndex("player_id")));

            Log.e("id",""+playerId);
        }

        c.close();
        return playerId;
    }

    public int returnPlayerIdWithName(Database database, String player_name){

        int playerId;

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT player_id FROM Player WHERE player_name = " + "player_name", null);

        c.moveToLast();

        playerId = c.getInt(0);

        Log.e("dbdenDönenID",""+playerId);

        c.close();
        return playerId;
    }

    public int returnLastPlayerId(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(player_id) FROM Player" , null);

        c.moveToFirst();

        int playerId = c.getInt(0);

        Log.e("playerId",""+playerId);

        c.close();
        return playerId;
    }

    public LinkedHashSet<String> returnLastPlayers(Database database){  //10 kayıtlı oyuncuyu dönecek.

        LinkedHashSet<String> stringLinkedHashSet = new LinkedHashSet<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM Player order by player_id desc limit 40",null);  // LIMIT 10

        while (c.moveToNext()){

            String a = c.getString(c.getColumnIndex("player_name"));

            stringLinkedHashSet.add(a);

        }

        c.close();
        sqLiteDatabase.close();
        return stringLinkedHashSet;
    }

}
