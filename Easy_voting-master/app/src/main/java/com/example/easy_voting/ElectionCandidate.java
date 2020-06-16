package com.example.easy_voting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ElectionCandidate extends AppCompatActivity {


    Databasehelper Db;
    SimpleAdapter adapter;
    ListView lv2;
    ArrayList<HashMap<String, String>> hello2;
    String value;
    HashMap<String,String> al2,temp1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_election_candidate);
        al2 = new HashMap<>();
        hello2 = new ArrayList<>();
        Db = new Databasehelper(ElectionCandidate.this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        value = extra.getString("EID");

        al2 = Db.pollCandidate(value);

        lv2 = (ListView) findViewById(R.id.listView2);
        adapter = new SimpleAdapter(ElectionCandidate.this, hello2, R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = al2.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            hm.put("First Line",pair.getValue().toString() );
            hm.put("Second Line", pair.getKey().toString());
            hello2.add(hm);
        }
        lv2.setAdapter(adapter);
        registerForContextMenu(lv2);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                temp1 = new HashMap<>();
                temp1 = hello2.get(position);

                AlertDialog.Builder builder= new AlertDialog.Builder(ElectionCandidate.this);
                builder.setMessage("Are you sure you want to vote for " + temp1.get("Second Line") + "(" + temp1.get("First Line") + ")");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //query to delete
                        Toast.makeText(ElectionCandidate.this, "Thank You!", Toast.LENGTH_SHORT).show();
                        String myTemp = temp1.get("Second Line");
                        Db.increaseVote(myTemp, value);
                        Intent intent = getIntent();
                        Bundle extra = intent.getExtras();

                        String user = extra.getString("userActive2");

                        Db.userVoteSet(value, user, myTemp);
                        // UserPolls.adapter.notifyDataSetChanged();
                        UserPolls.al1 = Db.pollDisplayUser(user);
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
                        // UserPolls.adapter1.notifyDataSetChanged();
                        Intent intent2 = new Intent(ElectionCandidate.this, UserPolls.class);
                        //   UserPolls.adapter1.notifyDataSetChanged();
                        startActivity(intent2);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
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
                Intent intent3 = new Intent(ElectionCandidate.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(ElectionCandidate.this,edit_profile.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(ElectionCandidate.this,UserPolls.class);
        startActivity(intent);
    }
}


