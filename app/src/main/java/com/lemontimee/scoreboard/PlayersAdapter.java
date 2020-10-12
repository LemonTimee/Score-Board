package com.lemontimee.scoreboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.CardDesignHolder>{

    private Context mContext;
    private List<Players> playersList;
    private Database database;

    public PlayersAdapter(Context mContext, List<Players> playersList) {
        this.mContext = mContext;
        this.playersList = playersList;
    }

    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_players,parent,false);

        return new CardDesignHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardDesignHolder holder, final int position) {

        final Players players = playersList.get(position);
        database  = new Database(mContext);

        holder.textViewPlayerName.setText(players.getPlayer_name());
        holder.imageDelete.setImageResource(R.drawable.icon_delete);

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // holder.imageDelete.setSelected(!holder.imageDelete.isPressed());

                String s = players.getPlayer_name();

                Log.e("isim","" + s);
                Log.e("position","" + position);

                int a = new PlayersDao().returnPlayerId(database,players.getPlayer_name());  //get player_id on database

               // int a = new PlayersDao().returnPlayerIdWithName(database,s);
                Log.e("returnId","" + a);

                new PlayersDao().deletePlayer(database,a);

                if (holder.imageDelete.isPressed()){

                    holder.imageDelete.setImageResource(R.drawable.icon_delete_red);

                }
                else{

                    holder.imageDelete.setImageResource(R.drawable.icon_delete);
                }

                int bd;
                bd = playersList.size();
                Log.e("ListSize",""+bd);
                Log.e("liste",""+playersList);

                playersList.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position,playersList.size());
                notifyDataSetChanged();


                //silme işlemini yapacak
                //PlayersDao playersDao = new PlayersDao();
                //playersDao.deletePlayer(database,s);

               // Intent intent = new Intent(mContext,ActivityNewGame.class);
                //mContext.startActivity(intent);


              //  Toast.makeText(mContext, "sile basıldu", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {

        return playersList.size();
    }

    public class CardDesignHolder extends RecyclerView.ViewHolder{

        private TextView textViewPlayerName;
        private ImageView imageDelete;

        public CardDesignHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            imageDelete = itemView.findViewById(R.id.imageDelete);

        }
    }

    public void alertShower(){  //burada işi yok fakat dursun diye bıraktım proje bitince sil.

        //LayoutInflater layoutInflater = LayoutInflater.from(mContext);
       // View view = layoutInflater.inflate(R.layout.layout_alert_view,null);

        //final EditText editTextName = view.findViewById(R.id.editTextName);

        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext,R.style.AlertDialogTheme);

        View view = LayoutInflater.from(mContext).inflate(
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



            }
        });

        view.findViewById(R.id.buttonNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        if(alertDialog.getWindow() != null){

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();


    }

}
