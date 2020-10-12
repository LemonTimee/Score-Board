package com.lemontimee.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.CardDesignHolder>{

    private Context mContext;
    private List<Players> playersList;
    private int id = View.generateViewId();
    private Database database;
    private Animation winnerAnim;
    private ArrayList<Players> playersArrayList;

    public GamesAdapter(Context mContext, List<Players> playersList) {
        this.mContext = mContext;
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_games,parent,false);

        return new CardDesignHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardDesignHolder holder, final int position) {

        final Players players = playersList.get(position);

        holder.textViewPlayerName.setText(players.getPlayer_name());
        holder.textViewTotalScore.setText("Total: 0" );
        final ArrayList<Integer> totalScoreArray = new ArrayList<>();  //arraylist for total score
        database  = new Database(mContext);

        final int finish_score = new GamesDao().returnFinishScore(database);
        final int finish_round = new GamesDao().returnFinishRound(database);

        holder.buttonPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Players> playersArrayList = new ArrayList<>();
                playersArrayList = new PlayersDao().returnPlayersForGames(database);
                final int ID = playersArrayList.get(position).getPlayer_id();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

                View view = LayoutInflater.from(mContext).inflate(
                        R.layout.layout_alert_view_score, null);

                builder.setView(view);

                final EditText editTextScore = view.findViewById(R.id.editTextScore);
                final TextView textView = view.findViewById(R.id.textView);
                final Button buttonYes = view.findViewById(R.id.buttonYes);

                final AlertDialog alertDialog = builder.create();

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String score;
                        int scoreTotal = 0;
                        int roundTotal = 0;

                        score = editTextScore.getText().toString().trim();

                        if(score.length() == 0){

                            Toast.makeText(mContext, "Insert Score Please!", Toast.LENGTH_SHORT).show();

                        }
                        else {

                            int score1 = Integer.parseInt(score);
                            holder.gridLayout.addView(textViewCreater(score));
                            totalScoreArray.add(score1);
                            //new LogDao().addLog(database,x,y,0,"null");

                            for (int i = 0; i < totalScoreArray.size(); i++){

                                scoreTotal = scoreTotal + totalScoreArray.get(i);
                                roundTotal++;

                            }

                            /*int gameID = new LogDao().returnLastGameId(database);
                            int logID = new LogDao().returnLogId(database,players.getPlayer_name(),gameID);
                            Log.e("oyuncuadı",""+players.getPlayer_name());
                            Log.e("logID",""+logID);*/
                            new LogDao().addScore(database,scoreTotal,ID);

                            ArrayList<LogPlayer> LogPlayersArrayList = new ArrayList<>();
                            LogPlayersArrayList = new GamesDao().returnScoreForRound(database);

                           //LogPlayer logPlayer = LogPlayersArrayList.get(0);
                          //  int a = logPlayer.getScore();
                          //  int a = LogPlayersArrayList.size();
                           // Log.e("birincininSkoru",""+a);

                            if (finish_score != 0 && scoreTotal - finish_score >= 0){

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

                                View view = LayoutInflater.from(mContext).inflate(
                                        R.layout.layout_alert_view_winner, null);

                                builder.setView(view);

                                winnerAnim = AnimationUtils.loadAnimation(mContext,R.anim.winner_blink);

                                final Button buttonYes = view.findViewById(R.id.buttonYes);

                                final TextView textViewChmp = view.findViewById(R.id.textViewChmp);

                                textViewChmp.setText(players.getPlayer_name());

                                textViewChmp.startAnimation(winnerAnim);

                                final AlertDialog alertDialog = builder.create();

                                view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        alertDialog.dismiss();
                                    }
                                });

                                if(alertDialog.getWindow() != null){

                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }

                                alertDialog.show();
                                //Toast.makeText(mContext, "Oyun bitti", Toast.LENGTH_SHORT).show();

                            }

                            if (finish_round != 0 && roundTotal == finish_round){

                                int g = LogPlayersArrayList.get(0).getScore();
                                Log.e("birincininSkoru",""+g);

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

                                View view = LayoutInflater.from(mContext).inflate(
                                        R.layout.layout_alert_view_winner, null);

                                builder.setView(view);

                                winnerAnim = AnimationUtils.loadAnimation(mContext,R.anim.winner_blink);

                                final Button buttonYes = view.findViewById(R.id.buttonYes);

                                final TextView textViewChmp = view.findViewById(R.id.textViewChmp);

                                textViewChmp.setText(LogPlayersArrayList.get(0).getPlayer_name());

                                textViewChmp.startAnimation(winnerAnim);

                                final AlertDialog alertDialog = builder.create();

                                view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        alertDialog.dismiss();
                                    }
                                });

                                if(alertDialog.getWindow() != null){

                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                                }

                                alertDialog.show();
                                //Toast.makeText(mContext, "Oyun bitti", Toast.LENGTH_SHORT).show();

                            }

                        }

                        alertDialog.dismiss();  // close alertview
                        holder.textViewTotalScore.setText("Total: " + scoreTotal);
                    }

                });


                if(alertDialog.getWindow() != null){

                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return playersList.size();
    }

    public class CardDesignHolder extends RecyclerView.ViewHolder{

        private Button buttonPlusOne;
        private TextView textViewPlayerName,textViewTotalScore;
        private GridLayout gridLayout;
        private CardView cardViewGame;

        public CardDesignHolder(@NonNull View itemView) {
            super(itemView);
            buttonPlusOne = itemView.findViewById(R.id.buttonPlusOne);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            gridLayout = itemView.findViewById(R.id.gridLayout);
            cardViewGame = itemView.findViewById(R.id.cardViewGame);
            textViewTotalScore = itemView.findViewById(R.id.textViewTotalScore);

        }
    }

    public void alertShowerGamesAdapter(){  //bu metodu çağırmadık. metot içindeki kodu direk onBindViewHolder içine ekledik.

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.layout_alert_view_score, null);

        builder.setView(view);

        final EditText editTextScore = view.findViewById(R.id.editTextScore);
        final TextView textView = view.findViewById(R.id.textView);
        final Button buttonYes = view.findViewById(R.id.buttonYes);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        if(alertDialog.getWindow() != null){

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();


    }

    public TextView textViewCreater(String scoreTextView){

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(mContext);

        textView.setId(id);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(14);
        textView.setMinEms(4);
        textView.setText(scoreTextView);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }

    public void alertShowerFinishGame(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.layout_alert_view_winner, null);

        builder.setView(view);

        winnerAnim = AnimationUtils.loadAnimation(mContext,R.anim.winner_blink);

        final Button buttonYes = view.findViewById(R.id.buttonYes);

        final TextView textViewChmp = view.findViewById(R.id.textViewChmp);

        textViewChmp.startAnimation(winnerAnim);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

}