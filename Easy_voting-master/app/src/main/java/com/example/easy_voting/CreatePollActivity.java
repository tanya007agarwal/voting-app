package com.example.easy_voting;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

public class CreatePollActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button next;
    EditText election_name,startTime,endTime;

    EditText maxNo;
    EditText date;
    EditText description;
    EditText election_id;
   Calendar c;
   Databasehelper Db;
   int hrs,min;
    @Override
  //  Button next;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);
        election_id=(EditText)(findViewById(R.id.editText6));
        next=(Button)(findViewById(R.id.button4));
        election_name=(EditText)(findViewById(R.id.textView15));
     //   batch = (EditText)(findViewById(R.id.editText3));
        maxNo=(EditText)(findViewById(R.id.editText4));
        date=(EditText)(findViewById(R.id.editText5));
        description=(EditText)(findViewById(R.id.editdesc)) ;
        startTime = (EditText) findViewById(R.id.txtStartTime);
        endTime = (EditText)findViewById(R.id.txtEndTime);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker=new Datepicker();
                datepicker.show(getSupportFragmentManager(),"date picker");
            }
        });


        startTime.setOnClickListener(new View.OnClickListener() {
                                  @Override
         public void onClick(View view) {
                 pickTime1();
            }
          });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickTime2();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String electionName=election_name.getText().toString();
             //   int Batch =Integer.parseInt(batch.getText().toString());
           //     String Batch=batch.getText().toString();
               String max=(maxNo.getText().toString());
                String date1=date.getText().toString().trim();
                String desc=description.getText().toString().trim();
                String electionID=election_id.getText().toString();
                String sTime = startTime.getText().toString();
                String eTime = endTime.getText().toString();
                Db = new Databasehelper(CreatePollActivity.this);
              if(electionID.equals("")||electionName.equals("")||max.equals("")||
                      date1.equals("")||
                      sTime.equals("")||
                      eTime.equals(""))
              {
                  Toast.makeText(CreatePollActivity.this,"Please Enter all details!",Toast.LENGTH_LONG).show();
                  startActivity(new Intent(CreatePollActivity.this,CreatePollActivity.class));
              }
              if(sTime.compareTo(eTime)>0)
              {
                  Toast.makeText(CreatePollActivity.this,"Start Time is greater than End Time!",Toast.LENGTH_LONG).show();
                  startActivity(new Intent(CreatePollActivity.this,CreatePollActivity.class));

              }
                if(Db.checkPrimaryElection(electionID))
                {
                    Toast.makeText(CreatePollActivity.this,"Election Already Exists!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreatePollActivity.this,CreatePollActivity.class));
                }

                Intent intent1=new Intent(CreatePollActivity.this,com.example.easy_voting.Final_create_poll.class);
                 Bundle extra=new Bundle();
                 extra.putString("electionName",electionName);
              //   extra.putString("batch",Batch);
                extra.putString("max",max);
                extra.putString("date",date1);
                extra.putString("desc",desc);
                extra.putString("election_id",electionID);
                extra.putString("STARTTIME",sTime);
                extra.putString("ENDTIME",eTime);




                intent1.putExtras(extra);
                Toast.makeText(CreatePollActivity.this, "Moving to Next", Toast.LENGTH_LONG).show();

                  startActivity(intent1);


            }
        });


    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void pickTime1()
    {
        final Calendar c = Calendar.getInstance();
        hrs = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String ans;
                ans = Integer.toString(i);
                if(ans.length()==1)
                {
                    ans = "0"+ans;
                }
                ans+=" : ";
                if(i1/10==0)
                {
                    ans+=("0"+Integer.toString(i1) );
                }
                else
                {
                    ans+=(Integer.toString(i1));
                }
                startTime.setText(ans);
            }
        },hrs,min,false);
        timePickerDialog.show();
    }

    public void pickTime2()
    {
        final Calendar c = Calendar.getInstance();
        hrs = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String ans;
                ans = Integer.toString(i);
                if(ans.length()==1)
                {
                    ans = "0"+ans;
                }
                ans+=" : ";
                if(i1/10==0)
                {
                    ans+=("0"+Integer.toString(i1) );
                }
                else
                {
                    ans+=(Integer.toString(i1));
                }
                endTime.setText(ans);
            }
        },hrs,min,false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        c=Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);
       // String req_Date= DateFormat.getDateInstance(Date).format(c.getTime());
        String mm=Integer.toString(month),dd=Integer.toString(day);
        if(month<10)
            mm = "0"+mm;
        if(day<10)
            dd = "0"+dd;
        String tDate = year + "-" + mm + "-" + dd;


        date.setText(tDate);
        //Date date= get Date();
        //DateFormat.getDateTimeInstance(). format(date);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //String strDate = sdf.format(new Date());

    }
}
