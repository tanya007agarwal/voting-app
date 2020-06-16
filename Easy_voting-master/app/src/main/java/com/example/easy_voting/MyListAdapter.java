package com.example.easy_voting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by divija on 10/4/18.
 */

public  class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] progNames;
    private final Integer[] progImages;


    public MyListAdapter( Activity context, String[] progNames,Integer[] progImages) {
        super(context, R.layout.activity_list_view_purpose ,progNames);
        this.context = context;
        this.progImages = progImages;
        this.progNames = progNames;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView =  inflater.inflate(R.layout.activity_list_view_purpose,null,true);
        TextView txtTitle = (TextView)rowView.findViewById(R.id.haha);
        ImageView imgView = (ImageView)rowView.findViewById(R.id.Iv1);
        txtTitle.setText(progNames[position]);
        imgView.setImageResource(progImages[position]);
        return rowView;
    }
}
