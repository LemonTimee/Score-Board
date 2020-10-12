package com.lemontimee.scoreboard;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyTextview {  //kullanılmıyor

    private Context mContext;

    public MyTextview(Context mContext) {
        this.mContext = mContext;
    }

    public TextView textViewCreater(Context context, String score){

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);

        int id = View.generateViewId();
        textView.setId(id);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(14);
        textView.setMinEms(4);
        textView.setText(score);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        //textView.setPadding(1,1,1,1);

        return textView;

    }
}
