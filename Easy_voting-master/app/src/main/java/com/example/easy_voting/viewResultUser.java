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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class viewResultUser extends AppCompatActivity {

    ListView lv3;
    Context context;
    Databasehelper Db;
    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> hello3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_user);
        HashMap<String,String> al3 = new HashMap<>();
        hello3 = new ArrayList<>();
        Db =  new Databasehelper(viewResultUser.this);
        al3 = Db.viewResultPollUser();
        lv3 = (ListView)findViewById(R.id.listView3);
        adapter = new SimpleAdapter(viewResultUser.this,hello3, R.layout.adminlistviewcp,new String[]{"First Line","Second Line"},new int[]{R.id.t1,R.id.t2});
        Iterator it;
        it = al3.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap<String,String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            String Eid = pair.getKey().toString();
            String desc = Db.getDesc(Eid);
            hm.put("First Line", pair.getValue().toString());
            hm.put("Second Line", Eid+"-"+desc);
            hello3.add(hm);
        }
        lv3.setAdapter(adapter);
        registerForContextMenu(lv3);
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(viewResultUser.this,showResultElection.class);
                HashMap<String,String> temp = new HashMap<>();
                temp = hello3.get(position);
                String getId = temp.get("Second Line");
                int index = getId.indexOf('-');
                intent.putExtra("myElectionId",getId.substring(0,index));
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(viewResultUser.this,UserPolls.class);
        startActivity(intent);
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
                Intent intent3 = new Intent(viewResultUser.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(viewResultUser.this,edit_profile.class);
                Bundle bundle = new Bundle();
                SharedPreferences pref1 = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                String hehe = pref1.getString("enrol",null);
                bundle.putString("ActiveUser",hehe);
                bundle.putString("activity","viewResultUser");
                intent4.putExtras(bundle);
                //  Toast.makeText(UserPolls.this,"hello User!",Toast.LENGTH_LONG).show();
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

