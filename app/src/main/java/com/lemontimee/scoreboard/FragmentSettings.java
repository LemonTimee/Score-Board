package com.lemontimee.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class FragmentSettings extends Fragment {

    private TextInputEditText editTextGameName;
    private EditText editTextScore,editTextRound;
    private RadioButton radioButtonRound, radioButtonScore;
    private RadioGroup radioGroup;
    private Button buttonSave,buttonClear;
    private String round,gameName = null;
    private String score = null;
    private Database database;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Long now;
    private String nowLog;
    private int x;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_settings,container,false);

        editTextGameName = rootview.findViewById(R.id.editTextGameName);
        editTextRound = rootview.findViewById(R.id.editTextRound);
        editTextScore = rootview.findViewById(R.id.editTextScore);
        radioButtonRound = rootview.findViewById(R.id.radioButtonRound);
        radioButtonScore = rootview.findViewById(R.id.radioButtonScore);
        radioGroup = rootview.findViewById(R.id.radioGroup);
        buttonClear = rootview.findViewById(R.id.buttonClear);
        buttonSave = rootview.findViewById(R.id.buttonSave);

        editTextRound.setEnabled(false);
        editTextScore.setEnabled(false);


        editTextGameName.setText(sharedOutGame());
        editTextScore.setText(sharedOutScore());
        editTextRound.setText(sharedOutRound());

        Log.e("oyun","" + gameName);


        //editTextGameName.setText("Sefa");
        //editTextRound.setText("1");

        /*editTextGameName.setText(gameName1);
        editTextRound.setText(round1);
        editTextScore.setText(score1);*/

        database = new Database(getContext());
        now = returnMiliSecond();
        nowLog = getDate(now,"dd/MM/yyyy hh:mm:ss.SSS");

        Log.e("tarih",""+nowLog);


        radioButtonRound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    editTextRound.setEnabled(true);
                    editTextRound.requestFocus();
                    editTextScore.setEnabled(false);
                    editTextScore.getText().clear();
                }
            }
        });

        radioButtonScore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    editTextScore.setEnabled(true);
                    editTextScore.requestFocus();
                    editTextRound.setEnabled(false);
                    editTextRound.getText().clear();

                }
            }
        });

        editTextScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){

                    editTextScore.setHint("");

                }
                else{

                    editTextScore.setHint("0");
                }
            }
        });

        editTextRound.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){

                    editTextRound.setHint("");

                }
                else{

                    editTextRound.setHint("0");
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextScore.getText().clear();
                editTextRound.getText().clear();
                editTextGameName.getText().clear();
                radioGroup.clearCheck();
                editTextGameName.clearFocus();
                editTextRound.clearFocus();
                editTextScore.clearFocus();
                new GamesDao().addNewGame(database,"buttonClear","null","null",nowLog);
                sharedIn("","","");
                Toast.makeText(getContext(), "CLEARED", Toast.LENGTH_SHORT).show();

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                round = editTextRound.getText().toString().trim();
                score = editTextScore.getText().toString().trim();
                gameName = editTextGameName.getText().toString().trim();

                sharedIn(gameName,score,round);


                Log.e("oyun","" + gameName);

                if(gameName.length() == 0){

                    editTextGameName.setError("Please Insert Game Name!");
                    //Toast.makeText(getContext(), "Please Insert Game Name!", Toast.LENGTH_SHORT).show();

                }
                if (round.length() > 0){

                    int round1 = Integer.parseInt(round);

                    if (round1 == 0){

                        editTextRound.getText().clear();
                        editTextRound.setError("Round cannot be 0!");
                        //Toast.makeText(getContext(), "Round cannot be 0!", Toast.LENGTH_SHORT).show();

                    }
                }

                if (score.length() > 0){

                    int score1 = Integer.parseInt(score);

                    if (score1 == 0){

                        editTextScore.getText().clear();
                        editTextScore.setError("Score cannot be 0!");
                        //Toast.makeText(getContext(), "Score cannot be 0!", Toast.LENGTH_SHORT).show();

                    }

                }

                editTextGameName.setText(gameName);
                editTextRound.setText(round);
                editTextScore.setText(score);

                new GamesDao().addNewGame(database,gameName,score,round,nowLog);
                //new LogDao().returnGameIdInsertLogTable(database);
                //int z = new GamesDao().returnGameId(database);

                x = new GamesDao().returnGameId(database);
                Log.e("Game id settings fra",""+x);

               // Log.e("zzz",""+z);

                Toast.makeText(getContext(), "SAVED", Toast.LENGTH_SHORT).show();


               // Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                //ActivityNewGame activityNewGame = (ActivityNewGame) getActivity();
               // activityNewGame.getStrings(gameName,round,score);

            }
        });

        editTextGameName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    //Toast.makeText(getContext(), "Entere basıldı", Toast.LENGTH_SHORT).show();
                    gameName = editTextGameName.getText().toString().trim();
                    editTextGameName.setText(gameName);
                    return true;

                }

                return false;
            }
        });

        editTextScore.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    //Toast.makeText(getContext(), "Entere basıldı", Toast.LENGTH_SHORT).show();
                    score = editTextScore.getText().toString().trim();
                    editTextScore.setText(score);
                    return true;

                }

                return false;
            }
        });

        editTextRound.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){

                    //Toast.makeText(getContext(), "Entere basıldı", Toast.LENGTH_SHORT).show();
                    round = editTextRound.getText().toString().trim();
                    editTextRound.setText(round);
                    return true;

                }

                return false;
            }
        });

        rootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                View view = getActivity().getCurrentFocus();

                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(view != null && inputMethodManager != null) {

                    //inputMethodManager.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);  //duruma göre bunu kullan. null hatası geçmez ise.

                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                }

                return false;
            }
        });


       /* rootview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(editTextRound.getText().toString().trim().length() == 0){

                    radioGroup.clearCheck();
                   // editTextRound.setEnabled(false);
                   // editTextRound.setText("");

                }

                if(editTextScore.getText().toString().trim().length() == 0){

                    radioGroup.clearCheck();
                  //  editTextScore.setEnabled(false);
                  //  editTextScore.setText("");


                }

                if (editTextGameName.getText().toString().trim().length() == 0){

                    editTextGameName.clearFocus();

                }

                return true;
            }
        });*/

        return rootview;

    }


    public void openKeyboard(){ //open keyboard

        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

    }

    public long returnMiliSecond(){

        long currentTimeMillis = System.currentTimeMillis();
        Log.e("sure",""+currentTimeMillis);

        return currentTimeMillis;
    }

    public void sharedIn(String name,String score,String round){

        sharedPreferences = getActivity().getSharedPreferences("isim",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("name",name);
        editor.putString("score",score);
        editor.putString("round",round);

        Log.e("sharedIn", ""+name);

        editor.commit();

    }

    public String sharedOutGame(){

        sharedPreferences = getActivity().getSharedPreferences("isim",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String a = sharedPreferences.getString("name","");

        editor.commit();

        Log.e("sharedOut", ""+a);

        return a;

    }

    public String sharedOutScore(){

        sharedPreferences = getActivity().getSharedPreferences("isim",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String b = sharedPreferences.getString("score","");

        editor.commit();

        Log.e("sharedOut", ""+b);

        return b;

    }

    public String sharedOutRound(){

        sharedPreferences = getActivity().getSharedPreferences("isim",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String c = sharedPreferences.getString("round","");

        editor.commit();

        Log.e("sharedOut", ""+c);

        return c;

    }

    public String getDate(long milliSeconds, String dateFormat){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return simpleDateFormat.format(calendar.getTime());

    }

}
