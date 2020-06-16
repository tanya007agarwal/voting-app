package com.example.easy_voting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class showResultElection extends AppCompatActivity {


    String eId;
    TextView tv3;
    Databasehelper Db;
    SimpleAdapter adapter;
    ListView lv4;
    ArrayList<HashMap<String, String>> hello4;
    HashMap<String,String> al4,temp1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result_election);
        eId = getIntent().getStringExtra("myElectionId");
        tv3= (TextView)findViewById(R.id.winner);

        al4 = new HashMap<>();
        hello4 = new ArrayList<>();
        Db = new Databasehelper(showResultElection.this);
        al4 = Db.candidateFinalVotes(eId);
        temp1 = new HashMap<>();

        lv4 = (ListView) findViewById(R.id.listView4);
        adapter = new SimpleAdapter(showResultElection.this, hello4,R.layout.adminlistviewcp, new String[]{"First Line", "Second Line"}, new int[]{R.id.t1, R.id.t2});
        Iterator it;
        it = al4.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> hm = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            hm.put("First Line",pair.getKey().toString() );
            hm.put("Second Line", "No of Votes :- " + pair.getValue().toString());

            hello4.add(hm);
        }
        temp1 = hello4.get(0);
        String s1=temp1.get("First Line");
        String id1 = s1.substring(s1.indexOf('(')+1,s1.lastIndexOf(')'));
        String temp = temp1.get("Second Line");
        String temp2 = temp.substring(temp.indexOf('-')+2);
        int votes = Integer.parseInt(temp2);
        String print = "Winners :- ";
        if(votes==0)
            tv3.setText("No one has voted for any candidate!");
        else {
            print+="   ";
            print += temp1.get("First Line");
        }
        it = al4.entrySet().iterator();
        it.next();
        while (it.hasNext()&&votes!=0) {

            Map.Entry pair = (Map.Entry) it.next();

             if(Integer.parseInt(pair.getValue().toString())==votes)
             {
                 print += "    "+pair.getKey().toString();
             }
             else
                 break;

        }


        tv3.setText(print);
        lv4.setAdapter(adapter);
        registerForContextMenu(lv4);

    }
    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(showResultElection.this,viewResultUser.class);
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
                Intent intent3 = new Intent(showResultElection.this,MainActivity.class);
                startActivity(intent3);
                return true;
            case R.id.viewProfile:
                Intent intent4 = new Intent(showResultElection.this,edit_profile.class);
                Bundle bundle = new Bundle();
                SharedPreferences pref1 = getSharedPreferences(MainActivity.MY_FILENAME,MODE_PRIVATE);
                String hehe = pref1.getString("enrol",null);
                bundle.putString("ActiveUser",hehe);
                bundle.putString("activity","showResultElection");
                intent4.putExtras(bundle);
                //  Toast.makeText(UserPolls.this,"hello User!",Toast.LENGTH_LONG).show();
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
