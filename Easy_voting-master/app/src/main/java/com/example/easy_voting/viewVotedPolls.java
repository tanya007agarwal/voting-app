package com.example.easy_voting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
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

public class viewVotedPolls extends AppCompatActivity {

    TextView textview;
    static ListView lv4;
    static HashMap<String,String> al4;
    Context context;
    Databasehelper Db;
    SimpleAdapter adapter;
    static ArrayList<HashMap<String,String>> hello4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_voted_polls);
        al4 = new HashMap<>();
        hello4 = new ArrayList<>();
        textview = (TextView)findViewById(R.id.votedFor);
        Db =  new Databasehelper(viewVotedPolls.this);
        al4 = Db.viewPollsAlreadyVoted(getIntent().getStringExtra("userOnline"));
        lv4 = (ListView)findViewById(R.id.listView4);
        adapter = new SimpleAdapter(viewVotedPolls.this,hello4, R.layout.adminlistviewcp,new String[]{"First Line","Second Line"},new int[]{R.id.t1,R.id.t2});
        Iterator it;
        it = al4.entrySet().iterator();
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
        lv4.setAdapter(adapter);
        registerForContextMenu(lv4);
        lv4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(viewVotedPolls.this,showVotedElectionCandidates.class);
                HashMap<String,String> temp = new HashMap<>();
                temp = hello4.get(position);
                String getId = temp.get("Second Line");
                int index = getId.indexOf('-');
                Bundle bundle = new Bundle();
                bundle.putString("userOnlineNow",getIntent().getStringExtra("userOnline"));
                bundle.putString("myElectionId",getId.substring(0,index));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }
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
                Intent intent3 = new Intent(viewVotedPolls.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(viewVotedPolls.this,edit_profile.class);
                Bundle bundle = new Bundle();
                SharedPreferences pref1 = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                String hehe = pref1.getString("enrol",null);
                bundle.putString("ActiveUser",hehe);
                bundle.putString("activity","viewVotedPolls");
                intent4.putExtras(bundle);
              //  Toast.makeText(UserPolls.this,"hello User!",Toast.LENGTH_LONG).show();
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onBackPressed()
    {
        UserPolls.al1 = Db.pollDisplayUser(getIntent().getStringExtra("userOnline"));
        ListView lv1 = (ListView) findViewById(R.id.listView1);
        UserPolls.adapter1 = new SimpleAdapter(UserPolls.getContext(), UserPolls.hello1, R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = UserPolls.al1.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            String Eid = pair.getKey().toString();
            String desc = Db.getDesc(Eid);
            hm.put("First Line", pair.getValue().toString());
            hm.put("Second Line", Eid + "-" + desc);
            UserPolls.hello1.add(hm);
        }
        lv1.setAdapter(UserPolls.adapter1);
        registerForContextMenu(lv1);
        startActivity(new Intent(viewVotedPolls.this,UserPolls.class));
    }
}
