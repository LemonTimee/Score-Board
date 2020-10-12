package com.lemontimee.scoreboard;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button buttonNewGame,buttonSavedGames,buttonRateMe,buttonAboutApp;
    private ImageView imageView;
    private Animation imageAnimation, button1Animation, button2Animation, button3Animation, button4Animation;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(MainActivity.this);
        int a = new GamesDao().returnGameId(database);

        if (a == 0){

            new PlayersDao().addNewPlayer(database,"Me",null);
            new PlayersDao().addNewPlayer(database,"You",null);
            new GamesDao().addNewGame(database,"action_new_game",null,null,null);
            Log.e("oyunSayisi",""+a);

        }

       // database = new Database(MainActivity.this);
       // new PlayersDao().addNewPlayer(database,"Me",null);
      //  new PlayersDao().addNewPlayer(database,"You",null);
     //   new GamesDao().addNewGame(database,"action_new_game",null,null,null);

        buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonSavedGames = findViewById(R.id.buttonSavedGames);
        buttonRateMe = findViewById(R.id.buttonRateMe);
        buttonAboutApp = findViewById(R.id.buttonAboutApp);


        imageView = findViewById(R.id.imageView);

        imageAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.trophy_anim);
        button1Animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_down_to_top_1);
        button2Animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_down_to_top_2);
        button3Animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_down_to_top_3);
        button4Animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_down_to_top_4);

        imageView.setAnimation(imageAnimation);
        buttonNewGame.setAnimation(button1Animation);
        buttonSavedGames.setAnimation(button2Animation);
        buttonRateMe.setAnimation(button3Animation);
        buttonAboutApp.setAnimation(button4Animation);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ActivityNewGame.class);
                startActivity(intent);

            }
        });



    }
}