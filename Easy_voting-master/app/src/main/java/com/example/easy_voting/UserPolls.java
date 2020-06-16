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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserPolls extends AppCompatActivity {


    Databasehelper Db;
    static SimpleAdapter adapter1;
    public static ListView lv1;
    public static ArrayList<HashMap<String, String>> hello1;
    public static HashMap<String,String> al1 = new HashMap<>();
    String h;

    public static Context getContext() {
        return getContext();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_polls);
        //    btn = (Button) findViewById(R.id.btn);

        hello1 = new ArrayList<>();
        Db = new Databasehelper(UserPolls.this);
         h = getIntent().getStringExtra("userActive");
        al1 = Db.pollDisplayUser(h);
        lv1 = (ListView) findViewById(R.id.listView1);
        adapter1 = new SimpleAdapter(UserPolls.this, hello1, R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = al1.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            String Eid = pair.getKey().toString();
            String desc = Db.getDesc(Eid);
            hm.put("First Line", pair.getValue().toString());
            hm.put("Second Line", Eid+"-"+desc);
            hello1.add(hm);
        }
        lv1.setAdapter(adapter1);
        registerForContextMenu(lv1);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(UserPolls.this, ElectionCandidate.class);
                HashMap<String,String> temp = new HashMap<>();
                temp = hello1.get(position);
                Bundle extra=new Bundle();
                String getId = temp.get("Second Line");
                int index = getId.indexOf('-');
                extra.putString("EID",getId.substring(0,index));
                extra.putString("userActive2",h);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.viewResult:
                Intent intent = new Intent(UserPolls.this,viewResultUser.class);
                startActivity(intent);
                return true;
            case R.id.showPolls:
                Intent intent1 = new Intent(UserPolls.this,viewVotedPolls.class);
                intent1.putExtra("userOnline",h);
                startActivity(intent1);
                return true;
            case R.id.logOut:
                SharedPreferences pref = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                pref.edit().remove("enrol").commit();
                pref.edit().remove("pass1").commit();
                Intent intent3 = new Intent(UserPolls.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(UserPolls.this,edit_profile.class);
                Bundle bundle = new Bundle();
                SharedPreferences pref1 = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                h = pref1.getString("enrol",null);
                bundle.putString("ActiveUser",h);
                bundle.putString("activity","UserPolls");
                intent4.putExtras(bundle);
                Toast.makeText(UserPolls.this,"hello User!",Toast.LENGTH_LONG).show();
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(UserPolls.this,UserPolls.class);
        startActivity(intent);
    }
}