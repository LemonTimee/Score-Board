package com.lemontimee.scoreboard;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class LogDao {

    public ArrayList<Log> AllLogs(Database database){

        ArrayList<Log> logArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM Log",null);

        while (c.moveToNext()){

            Log log = new Log(c.getInt(c.getColumnIndex("log_id")),
                    c.getInt(c.getColumnIndex("game_id")),
                    c.getInt(c.getColumnIndex("player_id")),
                    c.getInt(c.getColumnIndex("score")),
                    c.getLong(c.getColumnIndex("create_date")));

            logArrayList.add(log);

        }

        sqLiteDatabase.close();
        return logArrayList;

    }

    public void returnGameIdInsertLogTable(Database database){

        int gameId = 0;

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id) FROM Game " , null);

        while(c.moveToLast()){

            gameId = Integer.parseInt(c.getString(c.getColumnIndex("game_id")));

        }

        android.util.Log.e("game id",""+gameId);

        ContentValues values = new ContentValues();

        values.put("game_id",gameId);

        sqLiteDatabase.insertOrThrow("Log",null,values);

        sqLiteDatabase.close();

    }

    public void addLog(Database database, int game_id, int player_id, int score, String create_date){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("game_id",game_id);
        values.put("player_id",player_id);
        values.put("score",score);
        values.put("create_date",create_date);

        sqLiteDatabase.insertOrThrow("Log",null,values);

        sqLiteDatabase.close();

    }

    public void addScore(Database database, int score, int log_id){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("score",score);

        sqLiteDatabase.update("Log",values,"log_id=? ",new String[]{String.valueOf(log_id)});

        sqLiteDatabase.close();

    }

    public int returnLastGameId(Database database){

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT MAX(game_id) FROM Log " , null);

        c.moveToFirst();

        //int gameId = c.getInt(c.getColumnIndex("game_id"));
        int gameId = c.getInt(0);

        c.close();
        return gameId;
    }

    public int returnLogId (Database database, String player_name, int game_id) {

        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery(" SELECT Log.log_id FROM Log, Player WHERE Log.player_id = Player.player_id and Log.game_id = " + game_id + " and Player.player_name = " + " player_name " , null);

      //  c.moveToFirst();

        int LogID = 5;

        while (c.moveToFirst()){

            LogID = c.getInt(c.getColumnIndex("log_id"));
        }

        c.close();
        return LogID;

    }

}
