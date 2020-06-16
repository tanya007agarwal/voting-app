package com.example.easy_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UpdateCandidate extends AppCompatActivity {

    Button updatebtn;
    EditText fname;
    EditText sname;
    EditText mail;
    EditText number;
    TextView rollno;
    Databasehelper Db;
    String enrol,eId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_candidate);
        Db = new Databasehelper(UpdateCandidate.this);
        fname=(EditText)findViewById(R.id.editText);
        sname=(EditText)findViewById(R.id.editTexts);
        mail=(EditText)findViewById(R.id.editText8);
        number=(EditText)findViewById(R.id.editText7);
        rollno=(TextView)findViewById(R.id.editText2);
        Bundle extras = getIntent().getExtras();
        enrol = extras.getString("enrol");
        eId = extras.getString("electionId");
        number.setText(enrol);
        fname.setText(Db.getFirstName(enrol,eId));
        sname.setText(Db.getSecondName(enrol,eId));
        mail.setText(Db.getCandidateMail(enrol,eId));
        number.setText(Db.getCandidateNumber(enrol,eId));
        updatebtn = (Button)findViewById(R.id.button5);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Db = new Databasehelper(UpdateCandidate.this);
                String fnames = fname.getText().toString().trim();
                String snames = sname.getText().toString().trim();
                String mails = mail.getText().toString();
                String phone = number.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(mails).matches())
               {
                   Toast.makeText(UpdateCandidate.this,"Enter Valid Email Address!",Toast.LENGTH_LONG).show();

               }
               else {
                    Db.updateCandidateDetails(enrol, eId, fnames, snames, mails, phone);
                    Intent intent = new Intent(UpdateCandidate.this, CandidateElectionDetails.class);
                    //  intent.putExtra("activity","UpdateCandidate");
                    Bundle extra = new Bundle();
                    //String getId = temp.get("Second Line");
                    extra.putString("activity", "UpdateCandidate");
                    extra.putString("EIDContinues", eId);
                    intent.putExtras(extra);
               /* CandidateElectionDetails.al5 = new HashMap<>();
                CandidateElectionDetails.hello5 = new ArrayList<>();
                CandidateElectionDetails.al5 = Db.candidateFinalVotes(eId);
                CandidateElectionDetails.adapter5 = new SimpleAdapter(UpdateCandidate.this, CandidateElectionDetails.hello5, R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
                Iterator it;
                it = CandidateElectionDetails.al5.entrySet().iterator();
                while (it.hasNext()) {
                    HashMap<String, String> hm = new HashMap<>();
                    Map.Entry pair = (Map.Entry) it.next();
                    hm.put("First Line",pair.getKey().toString() );
                    hm.put("Second Line", pair.getValue().toString());
                    CandidateElectionDetails.hello5.add(hm);
                }
                ListView lv2;
                lv2 = (ListView) findViewById(R.id.listView5);

                lv2.setAdapter(CandidateElectionDetails.adapter5);
                registerForContextMenu(lv2);*/
                    startActivity(intent);
                }
            }
        });
    }

}
