package com.example.hp1.attendance;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.app.AlertDialog;
import java.util.Date;

import static android.R.attr.id;
import static android.util.Log.v;
import static com.example.hp1.attendance.R.id.read;


public class MainActivity extends AppCompatActivity {
    ArrayList<Student_info> student;
TextView tv;
    ListView lv;
    String id;
    File extFile;
    ImageView im;
    customadapter ca = null;
    String cdate;
    private String sname, sfaculty_no;
    int totalClick[],totalPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

tv=(TextView)findViewById(R.id.welcome);

         id = intent.getStringExtra("extra");


        tv.setText("Welcome " + id);



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        cdate = dateFormat.format(date);
        v("Date  ", cdate + "  " + date.toString());
        //et =(EditText)findViewById(R.id.etext);
        lv = (ListView) findViewById(R.id.list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();


            }
        });




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





                totalClick[position]=totalClick[position]+1;
                int click=totalClick[position];
                v("click",click+"");
                try {
                    update(position,click);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                View v = lv.getChildAt(position -
                        lv.getFirstVisiblePosition());




                if (v == null)
                    return;

                im = (ImageView) v.findViewById(R.id.present);

                if(click%2==0){
im.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Absent", Toast.LENGTH_SHORT).show();

                }
                else {
                    im.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Present", Toast.LENGTH_SHORT).show();
                    // ca.notifyDataSetChanged();

                }
            }


        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }


    void fileWrite() throws FileNotFoundException {


        String filename = cdate.replaceAll("/", ".") + "student" + ".txt";
        v("file", filename);
        String filepath = "MyFileStorage";
        extFile = new File(getExternalFilesDir(filepath), filename);
        BufferedWriter writer = null;
        // FileOutputStream fos=openFileOutput("new.txt", Context.MODE_APPEND);
        // Log.i(TAG, "Path: " + file.getCanonicalPath()); // -> "Path: /userAccounts.txt"

        if (extFile.exists() == false) {

            try {
                extFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        FileOutputStream fos = new FileOutputStream(extFile, true);
        DataOutputStream dos = new DataOutputStream(fos);

        writer = new BufferedWriter(new OutputStreamWriter(dos));
        try {
            writer.write(sname + " " + sfaculty_no + " " + "0");
            v("hello1", sname + " " + sfaculty_no);
            writer.newLine();
            writer.flush();
            Toast.makeText(this, "Succes", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos.close();
            fos.close();
writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void fileRead() {
        String mess="";
        String[] newwords ;
        student = new ArrayList<>();
        String filename = cdate.replaceAll("/", ".") + "student" + ".txt";
        String filepath = "MyFileStorage";
        extFile = new File(getExternalFilesDir(filepath), filename);


        FileInputStream fi = null;
        try {
            fi = new FileInputStream(extFile);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No file exist", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
            return;
        }

        DataInputStream dis = new DataInputStream(fi);
        InputStreamReader reader=new InputStreamReader(dis);
        BufferedReader br = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        try {
            while ((mess = br.readLine()) != null) {
                newwords = mess.split(" ");
//if(newwords.length==1){
//continue;}
Log.v("mess",mess +newwords[0]+" " +newwords[1]+" "+ newwords[2]+" "+newwords.length);

                student.add(new Student_info(newwords[0],newwords[1],newwords[2]));


                sb.append(mess + "\n");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    //    Toast.makeText(this, "File Read", Toast.LENGTH_SHORT).show();
        try {
            ca = new customadapter(this, student);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lv.setAdapter(ca);
        ca.notifyDataSetChanged();
totalPosition=ca.getCount();
        v("total",""+totalPosition);
        View parentLayout = findViewById(android.R.id.content);
        totalClick=new int[totalPosition];

        Snackbar.make(parentLayout, "Tap on names to mark attendance", Snackbar.LENGTH_LONG).show();
        try {
            if(reader != null)
                reader.close();
            if(fi != null)
                fi.close();

            dis.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.edit_name);
        final EditText faculty = (EditText) dialogView.findViewById(R.id.edit_faculty);


        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                try {
                    sname = name.getText().toString();
                    sfaculty_no = faculty.getText().toString();
                    v("hello", sname + " " + sfaculty_no);
                    fileWrite();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    String upRead(int position,int click) {
        int count = 0;
        String updated = "";
        String mess;
        String[] words = null;
        student = new ArrayList<>();
        String filename = cdate.replaceAll("/", ".") + "student" + ".txt";
        String filepath = "MyFileStorage";
        extFile = new File(getExternalFilesDir(filepath), filename);
if(extFile.exists()==false){

    try {
        extFile.createNewFile();
    } catch (IOException e) {
        e.printStackTrace();
    }


}

        FileInputStream fi = null;
        try {
            fi = new FileInputStream(extFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        DataInputStream dis = new DataInputStream(fi);
        InputStreamReader reader=new InputStreamReader(dis);
        BufferedReader br = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        try {
            while ((mess = br.readLine()) != null) {

                if (count == position) {

                    words = mess.split(" ");
//                    if(words.length==1){
//                        continue;}

                    student.add(new Student_info(words[0], words[1], words[2]));
                    int att=Integer.parseInt(words[2]);

                    if(click%2==0){
    att--;
                    updated = updated + words[0] + " " + words[1] + " " + ""+att + "\n";}
else{
    att++;
    updated = updated + words[0] + " " + words[1] + " " + ""+att + "\n";



}

                } else {

                    updated = updated + mess + "\n";

                }

                count++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
            fi.close();
            dis.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
       // Toast.makeText(this, "File Read", Toast.LENGTH_SHORT).show();


        v("hell09", updated);

        return updated;
    }

    void upWrite(String updated) throws FileNotFoundException {

        String filename = cdate.replaceAll("/", ".") + "student" + ".txt";
        String filepath = "MyFileStorage";
        extFile = new File(getExternalFilesDir(filepath), filename);
        BufferedWriter writer = null;

        FileOutputStream fos = new FileOutputStream(extFile, false);
        DataOutputStream dos = new DataOutputStream(fos);

        writer = new BufferedWriter(new OutputStreamWriter(dos));
        try {
            writer.write(updated);
            v("hello1", sname + " " + sfaculty_no);
          //  writer.newLine();
            writer.flush();
         //   Toast.makeText(this, "Student marked", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
            dos.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void update(int position,int click) throws FileNotFoundException {

        String updated = upRead(position,click);

        upWrite(updated);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.read:
                fileRead();
                //item.setVisible(false);


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}