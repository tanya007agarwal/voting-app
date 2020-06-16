package com.example.easy_voting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.example.easy_voting.viewVotedPolls.hello4;

public class showVotedElectionCandidates extends AppCompatActivity {

    TextView tv1;
    TextView textview;
    Databasehelper Db;
    SimpleAdapter adapter;
    ListView lv5;
    ArrayList<HashMap<String, String>> hello5;
    HashMap<String,String> al5,temp1;
    String electionSelected,userActive;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_voted_election_candidates);
        tv1 = (TextView)findViewById(R.id.votedFor);
        al5 = new HashMap<>();
        hello5 = new ArrayList<>();
        Db = new Databasehelper(showVotedElectionCandidates.this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        userActive = extra.getString("userOnlineNow");
        electionSelected = extra.getString("myElectionId");
        al5 = Db.pollCandidate(electionSelected);
        temp1 = new HashMap<>();

        lv5 = (ListView) findViewById(R.id.listView5);
        adapter = new SimpleAdapter(showVotedElectionCandidates.this, hello5,R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = al5.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            hm.put("First Line",pair.getKey().toString() );
            hm.put("Second Line",  pair.getValue().toString());

            hello5.add(hm);
        }
        temp1 = hello5.get(0);
        tv1.setText("You voted for "+Db.getMyVoteDetails(electionSelected,userActive));
        lv5.setAdapter(adapter);
        registerForContextMenu(lv5);

    }

   /* public void onBackPressed()
    {

       /* hello4 = new ArrayList<>();
        textview = (TextView)findViewById(R.id.votedFor);
        Db =  new Databasehelper(showVotedElectionCandidates.this);
        viewVotedPolls.al4 = Db.viewPollsAlreadyVoted(getIntent().getStringExtra("userOnline"));
        viewVotedPolls.lv4 = (ListView)findViewById(R.id.listView4);
        adapter = new SimpleAdapter(showVotedElectionCandidates.this,hello4, R.layout.adminlistviewcp,new String[]{"First Line","Second Line"},new int[]{R.id.t1,R.id.t2});
        Iterator it;
        it = viewVotedPolls.al4.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap<String,String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            String Eid = pair.getKey().toString();
            String desc = Db.getDesc(Eid);
            hm.put("First Line", pair.getValue().toString());
            hm.put("Second Line", Eid+"-"+desc);
            hello4.add(hm);
        }
        viewVotedPolls.lv4.setAdapter(adapter);
        registerForContextMenu(viewVotedPolls.lv4);
        Intent intent  = new Intent(showVotedElectionCandidates.this,viewVotedPolls.class);
        startActivity(intent);
    }
*/
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logOut:
                SharedPreferences pref = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                pref.edit().remove("enrol").commit();
                pref.edit().remove("pass1").commit();
                Intent intent3 = new Intent(showVotedElectionCandidates.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(showVotedElectionCandidates.this,edit_profile.class);
                Bundle bundle = new Bundle();
                SharedPreferences pref1 = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                String hehe = pref1.getString("enrol",null);
                bundle.putString("ActiveUser",hehe);
                bundle.putString("activity","showVotedElectionCandidates");
                intent4.putExtras(bundle);
                //  Toast.makeText(UserPolls.this,"hello User!",Toast.LENGTH_LONG).show();
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}
