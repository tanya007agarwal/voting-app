package com.example.easy_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class edit_profile extends AppCompatActivity {

    TextView college_id ;
    EditText first_name;
    EditText second_name;
    EditText phone_no;
    EditText mail;
    Button done;
    String activUser,actReturn,first,second,phone,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        college_id=findViewById(R.id.textView9);
        first_name=findViewById(R.id.editText9);
        second_name=findViewById(R.id.editText10);
        phone_no=findViewById(R.id.editText11);
        mail=findViewById(R.id.editText12);
        done=findViewById(R.id.button8);
        Intent intent1 = getIntent();
        Bundle extra = intent1.getExtras();
       // String election_name = extra.getString(
        /* Intent intent = getIntent();
                 Bundle extra = intent.getExtras();
                 String election_name = extra.getString("electionName");
*/

         activUser = extra.getString("ActiveUser");
        actReturn = extra.getString("activity");
        Databasehelper db=new Databasehelper(edit_profile.this);

         first=db.getFirst(activUser);
         second=db.getSecond(activUser);
         phone=  db.getPhone(activUser);
        email = db.getMail(activUser);
        college_id.setText(activUser);
        mail.setText(email);
        first_name.setText(first);
        second_name.setText(second);
        phone_no.setText(phone);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first=first_name.getText().toString();
                second=second_name.getText().toString();
                email = mail.getText().toString();
                phone = phone_no.getText().toString();

                Databasehelper db1=new Databasehelper(edit_profile.this);
                if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()))
                {
                    Toast.makeText(edit_profile.this,"Enter Valid Email Address!",Toast.LENGTH_LONG).show();

                }
                else {
                    db1.setProfile(activUser, first, second, phone, email);

                    Intent intent6;

                    if (actReturn.equals("UserPolls")) {
                        intent6 = new Intent(edit_profile.this, UserPolls.class);
                    } else if (actReturn.equals("viewVotedPolls")) {
                        intent6 = new Intent(edit_profile.this, viewVotedPolls.class);
                    } else if (actReturn.equals("viewResultUser")) {
                        intent6 = new Intent(edit_profile.this, viewResultUser.class);
                    } else if (actReturn.equals("showVotedElectionCandidates")) {
                        intent6 = new Intent(edit_profile.this, showVotedElectionCandidates.class);
                    } else {
                        intent6 = new Intent(edit_profile.this, showResultElection.class);
                    }

                    startActivity(intent6);
                }
            }
        });


    }
}
