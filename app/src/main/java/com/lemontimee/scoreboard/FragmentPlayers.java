package com.lemontimee.scoreboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FragmentPlayers extends Fragment {

    private RecyclerView rv;
    private ArrayList<Players> playersArrayList = new ArrayList<>();
    private PlayersAdapter playersAdapter;
    private Button buttonAddPlayer,buttonSavedPlayer;
    private Database database;
    private Long now;
    private String nowLog;
    private int x,y;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private InterstitialAd interstitialAd;

    //private Set<String> playerSharedList;
   // private Set<String> playerSharedListComin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_players,container,false);

        setHasOptionsMenu(true); //options menu work on the fragment

        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() { //reklam gösterimi için ilk adımın son işlemini yapmış olduk.
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        interstitialAd = new InterstitialAd(getContext());  //objeyi oluşturduk
        interstitialAd.setAdUnitId("ca-app-pub-1850125260929155/8341404772");  // test id'sini ekledik.
        interstitialAd.loadAd(new AdRequest.Builder().build());  //reklamı gösterim öncesinde hazırladık ve yükledik. Uygulama çalıştığı anda reklam yüklenmiş olacak.

        rv = rootview.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        now = returnMiliSecond();
        nowLog = getDate(now,"dd/MM/yyyy hh:mm:ss.SSS");

       // a = sharedOut();
      //  playersArrayListView.add(a);

        database = new Database(getContext());
       // new PlayersDao().addNewPlayer(database,"You");

        //playersArrayList = new PlayersDao().returnPlayersForGames(database);

       // int ss = playersArrayList.size();
        //Log.e("size",""+ss);

        playersArrayList = new PlayersDao().returnPlayersForGames(database);

      //  playersArrayList = returnList(database);

        //playersArrayList = new GamesDao().

        buttonAddPlayer = rootview.findViewById(R.id.buttonAddPlayer);
        buttonSavedPlayer = rootview.findViewById(R.id.buttonSavedPlayer);

       // playersArrayList = new ArrayList<>();

        //Players players1 = new Players(0,"You",0);
        //Players players2 = new Players(0,"Me",0);

        //playersArrayList.add(players1);
       // playersArrayList.add(players2);

    //    adapterCall();
        playersAdapter = new PlayersAdapter(getContext(),playersArrayList);
        //refreshList();

        rv.setAdapter(playersAdapter);


        rv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));

        interstitialAd.setAdListener(new AdListener(){  //reklamın listeneri

            @Override
            public void onAdLoaded() {  //bu metot reklam yüklenince çalışıyor.

                Log.e("Interstitial", "onAdLoaded çalıştı");

            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {  //yükleme sırasında hata alınırsa burası çalışır.

                Log.e("Interstitial", "onAdFailedToLoad çalıştı");

            }

            @Override
            public void onAdOpened() { //reklam ekranı kapladığı zaman bu metot çalışır.

                Log.e("Interstitial", "onAdOpened çalıştı");

            }

            @Override
            public void onAdLeftApplication() { //reklam çalıştığında uygulamadan ayrılınırsa bu metot çalışır.

                Log.e("Interstitial", "onAdLeftApplication çalıştı");

            }

            @Override
            public void onAdClosed() { //reklam çarpı işareti yada geri tuşu ile kapatılırsa bu metot çalışır

                interstitialAd.loadAd(new AdRequest.Builder().build()); //reklam kapatılırsa tekrar yüklenecek ki bir dahaki gösterime hazır olsun.
                Log.e("Interstitial", "onAdClosed çalıştı");

            }
        });


/*

        Players players1 = new Players(0,"Kazım",0);
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
        Players players14 = new Players(0,"Sefa",0);


        playersArrayList.add(players1);
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



        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertShower();


            }
        });

        buttonSavedPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertShowerListView();

            }
        });



        return rootview;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) { //inflate options menu

        inflater.inflate(R.menu.options_menu_new_game,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //options menu items

        switch (item.getItemId()){

            case R.id.action_next:

                interstitialAd.show(); //reklamı göster dedik

                String a = new GamesDao().returnGameName(database);

                if (a.length() == 0 || a.equals(null) || a.equals("action_finish_game") || a.equals("buttonClear") || a.equals("action_new_game") ){

                    Toast.makeText(getContext(), "Please Insert Game Name", Toast.LENGTH_SHORT).show();

                }

                else {

                    Intent intent = new Intent(getContext(),ActivityGamePage.class);
                    startActivity(intent);
                    getActivity().finish();
                }


            default:

                return super.onOptionsItemSelected(item);
        }

    }



    public void alertShower(){

        //LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        // View view = layoutInflater.inflate(R.layout.layout_alert_view,null);

        //final EditText editTextName = view.findViewById(R.id.editTextName);

        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_alert_view, null);

        builder.setView(view);

        final EditText editTextName = view.findViewById(R.id.editTextScore);
        final TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        final Button buttonNo = view.findViewById(R.id.buttonNo);
        final Button buttonYes = view.findViewById(R.id.buttonYes);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                x = new GamesDao().returnGameId(database);
                 Log.e("Game id player 2",""+x);

                y = new PlayersDao().returnLastPlayerId(database) +1;
                //Log.e("Player id",""+y);

                String name = editTextName.getText().toString().trim();

                new PlayersDao().addNewPlayer(database,name,nowLog);
                new LogDao().addLog(database,x,y,0,nowLog);

                Players players = new Players(0,name,nowLog);
                playersArrayList.add(players);

                playersAdapter.notifyDataSetChanged(); //reload adapter

                alertDialog.dismiss();
            }
        });


        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
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

    public void alertShowerListView(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);

        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_alert_view_listview, null);

        builder.setView(view);

        final ListView listView = view.findViewById(R.id.listView);
        LinkedHashSet<String> uniqueStrings;

        final ArrayAdapter<String> playersArrayAdapterView;

        final TextView textViewTitle = view.findViewById(R.id.textViewTitle);
        final Button buttonYes = view.findViewById(R.id.buttonYes);

        final AlertDialog alertDialog = builder.create();

        uniqueStrings = new PlayersDao().returnLastPlayers(database); // for unique data

        final ArrayList<String> playersArrayListView = new ArrayList<>(uniqueStrings);

        playersArrayAdapterView = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_multiple_choice,playersArrayListView);
        listView.setAdapter(playersArrayAdapterView);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final ArrayList<String> checkedList = new ArrayList<>(); //yeni

       /* listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                SparseBooleanArray ch

                if (checked){

                    String a = playersArrayListView.get(position);

                    checkedList.add(a);

                }
                else{

                    checkedList.remove(position);

                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Object items = parent.getItemAtPosition(position);
                    String mItem = items.toString();

                     x = new GamesDao().returnGameId(database);
                    // Log.e("Game id player 2",""+x);

                    y = new PlayersDao().returnLastPlayerId(database) +1;
                    //Log.e("Player id",""+y);

                    new PlayersDao().addNewPlayer(database,mItem,nowLog);
                    new LogDao().addLog(database,x,y,0,nowLog);

                    Players players = new Players(0,mItem,nowLog);
                    playersArrayList.add(players);

                    //playersAdapter.notifyItemRemoved(position);

                    Log.e("seçili isim","" + mItem);

            }
        });

        view.findViewById(R.id.buttonYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playersAdapter.notifyDataSetChanged(); //reload adapter

                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow() != null){

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();

    }

   /* public void content(){

        refresh(200);
    }

    private void refresh(int milliseconds){

        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };

        handler.postDelayed(runnable,milliseconds);

    }*/

    public String getDate(long milliSeconds, String dateFormat){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        return simpleDateFormat.format(calendar.getTime());

    }

    public long returnMiliSecond(){

        long currentTimeMillis = System.currentTimeMillis();
        Log.e("sure",""+currentTimeMillis);

        return currentTimeMillis;
    }

    public ArrayList<Players> returnList(Database database){

        ArrayList<Players> players = new PlayersDao().returnPlayersForGames(database);

        int xx;
        xx = new GamesDao().returnGameId(database);
        Log.e("Game id player 1",""+xx);

        return players;

    }

    public void refreshList(){

        playersAdapter.notifyDataSetChanged();

    }

    public void adapterCall(){

        playersAdapter = new PlayersAdapter(getContext(),playersArrayList);

    }

    public void sharedIn(String name){

        sharedPreferences = getActivity().getSharedPreferences("oyuncular",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("name",name);

        Log.e("sharedIn", ""+name);

        editor.commit();

    }

    public String sharedOut(){

        sharedPreferences = getActivity().getSharedPreferences("oyuncular",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String a = sharedPreferences.getString("name","");

        editor.commit();

        Log.e("sharedOut", ""+a);

        return a;

    }

}
