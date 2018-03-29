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
 * Created by hp 1 on 23-Feb-17.
 */
public class customadapter extends ArrayAdapter<Student_info> {
    private ArrayList<Student_info> words;

    Student_info currentword;
    int attend;
    TextView loc,date,attendance,num;
    int click;

    public customadapter(Activity context, ArrayList<Student_info> words) throws FileNotFoundException {

        super(context, 0, words);
        this.words=words;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
attend=0;
         currentword = getItem(position);
View current=parent.getChildAt(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customlayout, parent, false);
        }


         loc = (TextView) convertView.findViewById(R.id.name);
        loc.setText(currentword.getName());

         date = (TextView) convertView.findViewById(R.id.faculty_no);
        date.setText(currentword.getFaculty_number());
        final ImageView im =(ImageView) convertView.findViewById(R.id.present);

        num=(TextView)convertView.findViewById(R.id.no);
        num.setText(""+(position+1));


        return convertView;

    }







}
