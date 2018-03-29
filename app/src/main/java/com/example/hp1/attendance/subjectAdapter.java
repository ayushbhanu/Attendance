package com.example.hp1.attendance;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Ayush on 15-Mar-18.
 */

public class subjectAdapter  extends ArrayAdapter<String> {

    String currentword;
    int attend;
    TextView loc,date,attendance,name;
    int click;
ArrayList<String> words;
    public subjectAdapter(Activity context, ArrayList<String> words) throws FileNotFoundException {

        super(context, 0, words);
this.words=words;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        attend=0;
        currentword = getItem(position);
      //  View current=parent.getChildAt(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customsubject, parent, false);
        }



        name=(TextView)convertView.findViewById(R.id.tname);
        name.setText(words.get(position));


        return convertView;

    }







}

