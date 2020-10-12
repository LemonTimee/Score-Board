package com.lemontimee.scoreboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentSavedPlayers extends Fragment {

    private ListView listView;
    private ArrayList<String> playersArrayList = new ArrayList<>();  //player olmalı
    private ArrayAdapter<String> playersArrayAdapter; //player olmalı


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View rootview = inflater.inflate(R.layout.fragment_saved_players,container,false);

       listView = rootview.findViewById(R.id.listView);

       playersArrayList.add("Kazım");
       playersArrayList.add("Sefa");
       playersArrayList.add("Yıldırım");
       playersArrayList.add("Gökçe");

       playersArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,playersArrayList);

       listView.setAdapter(playersArrayAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           }
       });


        return rootview;
    }

}
