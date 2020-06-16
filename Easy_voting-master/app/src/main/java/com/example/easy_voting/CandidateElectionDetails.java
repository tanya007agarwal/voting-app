package com.example.easy_voting;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Admin side Class shown when a particular election is clicked

public class CandidateElectionDetails extends AppCompatActivity {

    Databasehelper Db;
     SimpleAdapter adapter5;
    ListView lv2;
    ArrayList<HashMap<String, String>> hello5;
    String value; //value stores election id
    private int selectpos;
    private HashMap<String,String> tempMa = new HashMap<>();
    TextView tv5;
   public
   HashMap<String,String> al5,temp1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_election_details);
        al5 = new HashMap<>();
        hello5 = new ArrayList<>();
        tv5 = (TextView)findViewById(R.id.currStatus);
        Db = new Databasehelper(CandidateElectionDetails.this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

//        String actName = extra.getString("activity");
       value = extra.getString("EIDContinues");

        al5 = Db.candidateFinalVotes(value);

        lv2 = (ListView) findViewById(R.id.listView5);
        adapter5 = new SimpleAdapter(CandidateElectionDetails.this, hello5, R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = al5.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            hm.put("First Line",pair.getKey().toString() );
            hm.put("Second Line", pair.getValue().toString());
            hello5.add(hm);
        }

        int check = Db.checkElectionTime(value);
        if(check==1)
        {
            temp1 = hello5.get(0);
            tv5.setText(temp1.get("First Line")+" is the winner");
        }
        else if(check==0)
        {
            tv5.setText("Election in progress");
        }
        else
        {
            tv5.setText("Election is yet to start!");
        }
        lv2.setAdapter(adapter5);
        registerForContextMenu(lv2);

    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView5) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.mennu, menu);
        }
    }


    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.upd1:
                selectpos = info.position; //position in the adapter
                tempMa = new HashMap<>();
                tempMa = hello5.get(selectpos);
                String s=tempMa.get("First Line");
                String id = s.substring(s.indexOf('(')+1,s.lastIndexOf(')'));

                Db =  new Databasehelper(CandidateElectionDetails.this);
                Intent intent = new Intent(CandidateElectionDetails.this, UpdateCandidate.class);
                Intent intent1 = getIntent();
                Bundle extra = intent1.getExtras();
                String eId = extra.getString("EIDContinues");
                Bundle b = new Bundle();
                b.putString("electionId",eId);
                b.putString("enrol", id);
                intent.putExtras(b);
                startActivity(intent);

                return true;
         /*   case R.id.del:
                selectpos = info.position; //position in the adapter
                tempMa = new HashMap<>();
                tempMa = hello5.get(selectpos);
                String s1=tempMa.get("First Line");
               String id1 = s1.substring(s1.indexOf('(')+1,s1.lastIndexOf('('));
                Db =  new Databasehelper(CandidateElectionDetails.this);
                 Intent intent4 = getIntent();
                Bundle extra1 = intent4.getExtras();
                String eId1 = extra1.getString("EIDContinues");
                SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
           //     sqLiteDatabase.delete(Db.TABLE_DETAILS, "Election_id="+"'"+s+"';",null);
                return true;
*/
            default:
                return super.onContextItemSelected(item);
        }
    }
}
