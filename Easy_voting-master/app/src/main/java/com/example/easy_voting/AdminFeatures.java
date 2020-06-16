package com.example.easy_voting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AdminFeatures extends AppCompatActivity {

    ListView lv;
    Context context;
    Databasehelper Db;
    Button btn;
    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> hello;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_features);
        btn = (Button) findViewById(R.id.btn);
        HashMap<String,String> al = new HashMap<>();
        hello = new ArrayList<>();
        Db =  new Databasehelper(AdminFeatures.this);
        al = Db.pollDisplay();
        lv = (ListView)findViewById(R.id.listView);
        adapter = new SimpleAdapter(AdminFeatures.this,hello, R.layout.adminlistviewcp,new String[]{"First Line","Second Line"},new int[]{R.id.t1,R.id.t2});
        Iterator it;
        it = al.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap<String,String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            hm.put("First Line",pair.getValue().toString());
            hm.put("Second Line",pair.getKey().toString());
            hello.add(hm);
        }
        lv.setAdapter(adapter);
        registerForContextMenu(lv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminFeatures.this, CreatePollActivity.class);
                startActivity(intent);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                Intent intent = new Intent(AdminFeatures.this, CandidateElectionDetails.class);
                HashMap<String,String> temp = new HashMap<>();
                temp = hello.get(position);
                Bundle extra=new Bundle();
                String getId = temp.get("Second Line");
                extra.putString("activity","AdminFeatures");
                extra.putString("EIDContinues",getId);
                //extra.putString("userActive2",h);
                intent.putExtras(extra);
                startActivity(intent);
            }
        });

    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.adminoptions, menu);
        }
    }


    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.del:
                 //_id from database in this case
                int selectpos = info.position; //position in the adapter
                HashMap<String,String> tempMa = new HashMap<>();
                tempMa = hello.get(selectpos);

                String s=tempMa.get("Second Line");


                Db =  new Databasehelper(AdminFeatures.this);
              //  Toast.makeText(AdminFeatures.this,"database ::  " +Long.toString(selectid) + " Table :: " + Integer.toString(selectpos),Toast.LENGTH_LONG ).show();
                SQLiteDatabase sqLiteDatabase = Db.getWritableDatabase();
                String arr[]={s};
           //     Toast.makeText(AdminFeatures.this,s,Toast.LENGTH_LONG).show();
               int  x = sqLiteDatabase.delete(Db.TABLE_DETAILS, "Election_id="+"'"+s+"';",null);
               String X= String.valueOf(x);
          //      Toast.makeText(AdminFeatures.this,X,Toast.LENGTH_LONG).show();
                hello.remove(selectpos);
               x = sqLiteDatabase.delete(Db.TABLE_CANDIDATE, "Election_id="+"'"+s+"';",null);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.upd:
                selectpos = info.position; //position in the adapter
                tempMa = new HashMap<>();
                tempMa = hello.get(selectpos);

                s=tempMa.get("Second Line");


                Db =  new Databasehelper(AdminFeatures.this);
                //  Toast.makeText(AdminFeatures.this,"database ::  " +Long.toString(selectid) + " Table :: " + Integer.toString(selectpos),Toast.LENGTH_LONG ).show();
                //sqLiteDatabase = Db.getWritableDatabase();
                //arr[]={s};
                Intent intent = new Intent(AdminFeatures.this, UpdatePoll.class);
                intent.putExtra("election_id", s);
                startActivity(intent);

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(AdminFeatures.this,MainActivity.class));
    }

}