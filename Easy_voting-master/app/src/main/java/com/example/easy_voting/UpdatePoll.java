package com.example.easy_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdatePoll extends AppCompatActivity {

    Databasehelper Db;
    EditText election_name,startTime,endTime;
    EditText batch;
    EditText maxNo;
    EditText date;
    EditText description;
    TextView election_id;
    Button updatebtn;
    String id;
    String electionId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_poll);
        Db = new Databasehelper(UpdatePoll.this);
        election_id=(TextView)(findViewById(R.id.textView6));
        election_name=(EditText)(findViewById(R.id.textView15));
        batch = (EditText)(findViewById(R.id.editText3));
        maxNo=(EditText)(findViewById(R.id.editText4));
        date=(EditText)(findViewById(R.id.editText5));
        description=(EditText)(findViewById(R.id.editdesc)) ;
        startTime = (EditText) findViewById(R.id.txtStartTime);
        endTime = (EditText)findViewById(R.id.txtEndTime);
        updatebtn = (Button) findViewById(R.id.button4);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("election_id");
        election_id.setText(id);
        election_name.setText(Db.getElectionName(id));
        int k=Db.getMaxNo(id);
        String K=String.valueOf(k);
        maxNo.setText(K);
        date.setText(Db.getElectionDate(id));
        batch.setText(Db.getBatch(id));
        description.setText(Db.getDescription(id));
        startTime.setText(Db.getStartTime(id));
        endTime.setText(Db.getEndTime(id));

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String electionId = election_id.getText().toString();
               // String electionName = election_name.getText().toString();

                String electionName=election_name.getText().toString();
                String Batch=batch.getText().toString();
                String max=(maxNo.getText().toString());
                String date1 = date.getText().toString().trim();
                String desc=description.getText().toString().trim();
                String sTime = startTime.getText().toString();
                String eTime = endTime.getText().toString();

                Db = new Databasehelper(UpdatePoll.this);
                Db.updatePoll(id,electionName, Batch, date1, desc, sTime, eTime, max);
                Intent intent = new Intent(UpdatePoll.this, AdminFeatures.class);
                startActivity(intent);
            }
        });
    }



}
