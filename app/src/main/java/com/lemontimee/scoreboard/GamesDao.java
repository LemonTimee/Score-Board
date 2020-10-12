package com.lemontimee.scoreboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class GamesDao {

    public ArrayList<Games> returnAllGames(Database database){  //bütün kayıtlı oyunları dönecek.

        ArrayList<Games> gamesArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT MAX(game_id) FROM Game",null);

        while (c.moveToNext()){

            Games games = new Games(c.getInt(c.getColumnIndex("game_id")),
                    c.getString(c.getColumnIndex("game_name")),
                    c.getString(c.getColumnIndex("game_winner")),
                    c.getString(c.getColumnIndex("finish_score")),
                    c.getString(c.getColumnIndex("finish_round")),
                    c.getString(c.getColumnIndex("create_date")));

            gamesArrayList.add(games);

        }

        sqLiteDatabase.close();
        return gamesArrayList;

    }

    public void addNewGame(Database database, String game_name, String finish_score,String finish_round,String create_date){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("game_name",game_name);
        values.put("finish_score",finish_score);
        values.put("finish_round",finish_round);
        values.put("create_date",create_date);

        sqLiteDatabase.insertOrThrow("Game",null,values);



        sqLiteDatabase.close();

    }

    public void addGameScore(Database database, int finish_score){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("finish_score",finish_score);

        sqLiteDatabase.insertOrThrow("Game",null,values);

        sqLiteDatabase.close();

    }

    public int returnGameIdWithPlayer(Database database, String player_name){

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

    public int returnGameId(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id) FROM Game" , null);

        c.moveToFirst();

        int gameId = c.getInt(0);

        Log.e("gameId",""+gameId);

        c.close();
        return gameId;
    }

    public int returnFinishScore(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id), finish_score FROM Game " , null);

        c.moveToFirst();

        int finish_score = c.getInt(c.getColumnIndex("finish_score"));

        Log.e("finish_score",""+finish_score);

        c.close();
        return finish_score;
    }

    public int returnFinishRound(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id), finish_round FROM Game " , null);

        c.moveToFirst();

        int finish_round = c.getInt(c.getColumnIndex("finish_round"));

        Log.e("finish_round",""+finish_round);

        c.close();
        return finish_round;
    }

    public ArrayList<LogPlayer> returnScoreForRound(Database database){  //bütün kayıtlı oyuncuları dönecek.

        ArrayList<LogPlayer> LogPlayersArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(Log.score) as score, Player.player_name "
                                                     + " FROM Player , Log , Game  where Player.player_id = Log.player_id "
                                                          + " AND Log.game_id = Game.game_id "
                                                             +  " AND Game.game_id = (SELECT MAX(game_id) FROM Game) ",null);

        while (c.moveToNext()){

            LogPlayer logPlayer = new LogPlayer(c.getInt(c.getColumnIndex("score")),
                    c.getString(c.getColumnIndex("player_name")));

            LogPlayersArrayList.add(logPlayer);

        }

        c.close();
        sqLiteDatabase.close();
        return LogPlayersArrayList;
    }

    public String returnGameName(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id), game_name FROM Game " , null);

        c.moveToFirst();

        String game_name = c.getString(c.getColumnIndex("game_name"));

        Log.e("game_name",""+game_name);

        c.close();
        return game_name;
    }


}
