package com.lemontimee.scoreboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Random;

public class ActivityGamePage extends AppCompatActivity {

    private ImageView imageViewLittle;
    private RecyclerView rv;
    private ArrayList<Players> playersArrayList;
    private GamesAdapter adapter;
    private Toolbar toolbarGamePage;
    private Chronometer chronometer;
    private ToggleButton toggleButton;
    private Button buttonReset;
    private long pauseOffSet = 0; //for chronometer
    private boolean isPlaying = false; //for chronometer
    private Animation refreshButtonAnim;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Database database;
    private Animation winnerAnim;
    private AdView banner;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        imageViewLittle = findViewById(R.id.imageViewLittle);
        rv = findViewById(R.id.rv);
        toolbarGamePage = findViewById(R.id.toolbarGamePage);
        chronometer = findViewById(R.id.chronometer);
        toggleButton = findViewById(R.id.toggleButton);
        buttonReset = findViewById(R.id.buttonReset);

       // title = new GamesDao().returnGameName(database);
        toolbarGamePage.setTitle("");
        setSupportActionBar(toolbarGamePage);
        //getSupportActionBar().setTitle("title");


        MobileAds.initialize(ActivityGamePage.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        banner = findViewById(R.id.banner);

        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        // Bu bloğu sonra db'den playerları okuyacak şekilde değiştir. Bu bloğu sil

        playersArrayList = new ArrayList<>();
        database = new Database(getApplicationContext());

        playersArrayList = new PlayersDao().returnPlayersForGames(database);

       /* Players players1 = new Players(0,"Kazım",0);
        Players players2 = new Players(0,"Sefa",0);
        Players players3 = new Players(0,"Yıldırım",0);
        Players players4 = new Players(0,"Gökçe",0);
        Players players5 = new Players(0,"Güllü",0);
        Players players6 = new Players(0,"123456",0);
        Players players7 = new Players(0,"Güllü",0);
        Players players8 = new Players(0,"12345678",0);
        Players players9 = new Players(0,"Güllü",0);
        Players players10 = new Players(0,"123456789",0);
        Players players11 = new Players(0,"Güllü",0);
        Players players12 = new Players(0,"12345678912",0);
        Players players13 = new Players(0,"Güllü",0);
        Players players14 = new Players(0,"Sefa",0);*/


        /* playersArrayList.add(players1);
        playersArrayList.add(players2);
        playersArrayList.add(players3);
        playersArrayList.add(players4);
       playersArrayList.add(players5);
        playersArrayList.add(players6);
        playersArrayList.add(players7);
        playersArrayList.add(players8);
        playersArrayList.add(players9);
        playersArrayList.add(players10);
        playersArrayList.add(players11);
        playersArrayList.add(players12);
        playersArrayList.add(players13);
        playersArrayList.add(players14);*/

        //
        adapter = new GamesAdapter(this,playersArrayList);

        rv.setAdapter(adapter);

        refreshButtonAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.refresh_button_rotate); //refresh buton animation

        toggleButton.setText(null);
        toggleButton.setTextOff(null);
        toggleButton.setTextOn(null);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
                    chronometer.start();
                    isPlaying = true;
                    buttonReset.setVisibility(View.INVISIBLE);

                }
                else {

                    chronometer.stop();
                    pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isPlaying = false;
                    buttonReset.setVisibility(View.VISIBLE);

                }
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonReset.startAnimation(refreshButtonAnim);
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffSet = 0;

            }
        });

        imageViewLittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                int randomNumber = random.nextInt(6) + 1;

                if (randomNumber == 1){

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_one
                            , null));
                    settingsDialog.show();
                }
                else if (randomNumber == 2){

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_two
                            , null));
                    settingsDialog.show();
                }
                else if (randomNumber == 3){

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_three
                            , null));
                    settingsDialog.show();
                }
                else if (randomNumber == 4){

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_four
                            , null));
                    settingsDialog.show();
                }
                else if (randomNumber == 5){

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_five
                            , null));
                    settingsDialog.show();
                }
                else {

                    Dialog settingsDialog = new Dialog(ActivityGamePage.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.layout_dice_six
                            , null));
                    settingsDialog.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu_game_page,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_new_game:

                Snackbar.make(findViewById(R.id.action_new_game),"Are you sure?",Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new GamesDao().addNewGame(database,"action_new_game","null","null","null");
                                Intent intent = new Intent(ActivityGamePage.this,ActivityNewGame.class);
                                sharedIn("","","");
                                startActivity(intent);
                                finish();

                            }
                        }).show();
                return true;

            case R.id.action_finish_game:

                Snackbar.make(findViewById(R.id.action_finish_game),"Are you sure?",Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ArrayList<LogPlayer> LogPlayersArrayList = new ArrayList<>();
                                LogPlayersArrayList = new GamesDao().returnScoreForRound(database);

                                int g = LogPlayersArrayList.get(0).getScore();
                                Log.e("birincininSkoru",""+g);

                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityGamePage.this,R.style.AlertDialogTheme);

                                View view = LayoutInflater.from(getApplicationContext()).inflate(
                                        R.layout.layout_alert_view_winner, null);

                                builder.setView(view);

                                winnerAnim = AnimationUtils.loadAnimation(ActivityGamePage.this,R.anim.winner_blink);

                                final Button buttonYes = view.findViewById(R.id.buttonYes);

                                final TextView textViewChmp = view.findViewById(R.id.textViewChmp);

                                textViewChmp.setText(LogPlayersArrayList.get(0).getPlayer_name());

                                textViewChmp.startAnimation(winnerAnim);

                                final AlertDialog alertDialog = builder.create();

                                view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent = new Intent(ActivityGamePage.this,ActivityNewGame.class);
                                        sharedIn("","","");
                                        startActivity(intent);
                                        finish();

                                        alertDialog.dismiss();
                                    }
                                });

                                if(alertDialog.getWindow() != null){

                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }

                                alertDialog.show();

                                new GamesDao().addNewGame(database,"action_finish_game","null","null","null");
                                //alertShower();
                                sharedIn("","","");

                            }
                        }).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void alertShower(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogTheme);

        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_alert_view_winner, null);

        builder.setView(view);

        final Button buttonYes = view.findViewById(R.id.buttonYes);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityGamePage.this,MainActivity.class);
                startActivity(intent);
                finish();

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    public void sharedIn(String name,String score,String round){

        sharedPreferences = getSharedPreferences("isim",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("name",name);
        editor.putString("score",score);
        editor.putString("round",round);

        Log.e("sharedIn", ""+name);

        editor.commit();

    }


}