package com.lemontimee.scoreboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {

        super(context, "Score Board.sqlite", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE Player "
                + " ( "
                + " player_id " + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + " player_name " + " TEXT NOT NULL, "
                + " create_date " + " TEXT "
                + " ); "
        );

        db.execSQL(" CREATE TABLE Game "
                        + " ( "
                        + " game_id " + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                        + " game_name " + " TEXT NOT NULL, "
                        + " game_winner " + " TEXT, "
                        + " finish_score " + " TEXT, "
                        + " finish_round " + " TEXT, "
                        + " create_date " + " TEXT "
                    //    + " FOREIGN KEY (game_winner) REFERENCES Player (player_name) "
                        + " ); "
        );


       /* db.execSQL("CREATE TABLE \"Log\" (\n" +
                "\t\"log_id\"\tINTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t\"game_id\"\tINTEGER,\n" +
                "\t\"player_id\"\tINTEGER,\n" +
                "\t\"score\"\tINTEGER,\n" +
                "\t\"create_date\"\tTEXT,\n" +
              //  "\tFOREIGN KEY(\"player_id\") REFERENCES \"Player\"(\"player_id\")\n" +
                "\tFOREIGN KEY(\"game_id\") REFERENCES \"Game\"(\"game_id\")\n" +
                ");");*/

        db.execSQL(" CREATE TABLE Log "
                + " ( "
                + " log_id " + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + " game_id " + " INTEGER, "
                + " player_id " + " INTEGER, "
                + " score " + " INTEGER,"
                + " create_date " + " TEXT"
               // + " FOREIGN KEY (player_id) REFERENCES Player (player_id), "
               // + " FOREIGN KEY (game_id) REFERENCES Game (game_id) "
                + " ); "
        );

        //db.setForeignKeyConstraintsEnabled(true);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Player");
        db.execSQL("DROP TABLE IF EXISTS Game");
        db.execSQL("DROP TABLE IF EXISTS Log");
        onCreate(db);


    }

   @Override
    public void onConfigure(SQLiteDatabase db) {

        db.setForeignKeyConstraintsEnabled(true);

       super.onConfigure(db);
    }

    /*@Override
    public void onOpen(SQLiteDatabase db) {

        db.setForeignKeyConstraintsEnabled(true);

        super.onOpen(db);
    }*/
}
