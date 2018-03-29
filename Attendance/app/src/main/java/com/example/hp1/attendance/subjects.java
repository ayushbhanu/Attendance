package com.example.hp1.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class subjects extends AppCompatActivity {
ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

       final  ArrayList<String> al=new ArrayList<>();
        al.add("Dr.Saiful Islam");
        al.add("Dr.Warsi");
        al.add("Dr.Rashid Ali");


        lv=(ListView)findViewById(R.id.teacherid);
        try {
            subjectAdapter sa=new subjectAdapter(this,al);
            lv.setAdapter(sa);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i=new Intent(subjects.this,MainActivity.class);
                i.putExtra("extra",al.get(position));
                startActivity(i);



            }
        });




    }
}
