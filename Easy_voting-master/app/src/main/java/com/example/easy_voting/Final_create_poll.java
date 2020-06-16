package com.example.easy_voting;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.easy_voting.Databasehelper.ACC_COLLEGE_ID;
import static com.example.easy_voting.Databasehelper.ACC_PASSWORD;

public class Final_create_poll extends AppCompatActivity {


     Databasehelper db2;
    Button back;
     Button submit;
     EditText fname;
     EditText sname;
     EditText mail;
     EditText number;
     EditText rollno;
     Button finish;
    /*Intent intent = getIntent();
    Bundle extra = intent.getExtras();*/
    private static int counter =  2;//Integer.parseInt(extra.getString("max"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_create_poll);
        counter = 0;
        back=(Button)(findViewById(R.id.button6));
        submit=(Button)(findViewById(R.id.button5));
        fname=(EditText)(findViewById(R.id.editText));
        sname=(EditText)(findViewById(R.id.editTexts));
        mail=(EditText)(findViewById(R.id.editText8));
        number=(EditText)(findViewById(R.id.editText7));
        rollno=(EditText)(findViewById(R.id.editText2));
        finish=(Button)(findViewById(R.id.button7));
        db2= new Databasehelper(this);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Final_create_poll.this,"Election successfully created",Toast.LENGTH_SHORT);
                Intent intent_new1 = new Intent(Final_create_poll.this, AdminFeatures.class);
                startActivity(intent_new1);

            }
        });

     /*   Intent intent = getIntent();
        Bundle extra=intent.getExtras();
        String  election_name= extra.getString("election_name");
      String batch=extra.getString("batch");
       String max=extra.getString("max");
        String desc=extra.getString("desc");
        String date=extra.getString("date");
//
        String fnames=fname.getText().toString().trim();
        String snames=sname.getText().toString().trim();
        String mails=mail.getText().toString();
        String enrol_no=rollno.getText().toString();
        String phone=number.getText().toString();
        db2= new Databasehelper(this);*/
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              /* Intent intent = getIntent();
               Bundle extra = intent.getExtras();
               String election_name = extra.getString("electionName");
               String batch = extra.getString("batch");
               String max = extra.getString("max");

               String desc = extra.getString("desc");
               String date = extra.getString("date");
               String election_id = extra.getString("election_id");*/

               Intent intent1=new Intent(Final_create_poll.this,com.example.easy_voting.CreatePollActivity.class);

                   String k="back";

             /*  Bundle extra1=new Bundle();
               extra1.putString("electionName",election_name);
               extra1.putString("batch",batch);
               extra1.putString("max",max);
               extra1.putString("date",date);
               extra1.putString("desc",desc);
               extra1.putString("election_id",election_id);
               extra1.putString("k",k);
               intent1.putExtras(extra1);*/
               startActivity(intent1);
           }
       });
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = getIntent();
                 Bundle extra = intent.getExtras();
                 String election_name = extra.getString("electionName");
                 String batch = extra.getString("batch");
                 String max = extra.getString("max");

                 String desc = extra.getString("desc");
                 String date = extra.getString("date");
                 String election_id = extra.getString("election_id");
                 String sTime = extra.getString("STARTTIME");
                 String eTime = extra.getString("ENDTIME");

                 int noOfCand = Integer.parseInt(max);
                 String fnames = fname.getText().toString().trim();
                 String snames = sname.getText().toString().trim();
                 String mails = mail.getText().toString();
                 String enrol_no = rollno.getText().toString();
                 String phone = number.getText().toString();
                 //  db2= new Databasehelper(this);
                 Toast.makeText(Final_create_poll.this, "Data Inserted", Toast.LENGTH_LONG).show();

                 db2.Insertpoll(election_id, election_name, desc, date,sTime,eTime, max, batch, enrol_no, mails, phone, fnames, snames);
                 fname.setText("");
                 sname.setText("");
                 mail.setText("");
                 rollno.setText("");
                 number.setText("");
                 if(db2.newActivity(election_id,Integer.parseInt(max))) {
                     Toast.makeText(Final_create_poll.this,"Election successfully created",Toast.LENGTH_LONG);
                     Intent intent_new = new Intent(Final_create_poll.this, AdminFeatures.class);
                     startActivity(intent_new);
                 }
             }
         });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(Final_create_poll.this,Final_create_poll.class);
        startActivity(intent);
    }
}
