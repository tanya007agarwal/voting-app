package com.example.easy_voting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;

public class Databasehelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="easy_voting_database.db";

    /* ADMIN TABLE */
    public static final String TABLE_ADMIN= "Admin";
    public static final String ADMIN_ID= "Admin_id";
    public static final String ADMIN_PASSWORD= "Admin_password";

     /* ACCOUNT TABLE */

    public static final String TABLE_ACCOUNT= "Account";
    public static final String ACC_COLLEGE_ID= "college_id";
    public static final String ACC_PASSWORD= "Password";

    /* CANDIDATE TABLE */


    public static final String TABLE_CANDIDATE= "Candidate";
    public static final String  CANDI_CODE= "Authorisation_code";
    public static final String CANDI_VOTES= "No_of_votes";
    public static final String CANDI_ID= "college_id";
    public static final String CANDI_PHONE= "phone_no";
    public static final String CANDI_MAIL= "mail";
    public static final String CANDI_ELECTION_ID= "Election_id";
    public static final String CANDI_FNAME= "FName";
    public static final String CANDI_SNAME= "SName";


    /* CHECK TABLE */

    public static final String TABLE_CHECK= "checker";
    public static final String CHECK_COLLEGE_ID= "college_id";
    public static final String CHECK_ELECTION_ID= "Election_id";
    public static final String CANDIDATE_COLLEGE_ID= "candiCollegeId";
    /* USER TABLE */

    public static final String TABLE_USER= "User";
    public static final String USER_COLLEGE_ID= "college_id";
    public static final String USER_NAME1= "First_name";
    public static final String USER_NAME2= "Second_name";
    public static final String USER_ADD= "Address";
    public static final String USER_PHONE_NO= "Ph_no";
    public static final String USER_EMAIL= "Email";
    public static final String USER_AGE= "Age";

    /*OFFICER TABLE*/

    public static final String TABLE_OFFICER= "Officer";
    public static final String OFFICER_ID= "R_O_id";
    public static final String OFFICER_NAME= "Name";
    public static final String OFFICER_PHONE_NO= "Ph_no";
    public static final String OFFICER_ADD= "Address";
    public static final String OFFICER_EMAIL= "Email";
    public static final String OFFICER_AGE= "Age";


    /* Details */

    public static final String TABLE_DETAILS= "Details";
    public static final String DETAILS_ELECTION_NAME= "Election_name";
    public static final String DETAILS_ELECTION_ID= "Election_id";
    public static final String DETAILS_OFFICER_ID= "R_O_id";
    public static final String DETAILS_DATE= "Date";
    public static final String START_TIME = "Start_time";
    public static final String END_TIME = "End_time";
  //  public static final String DETAILS_VOTER_MIN= "Voter_min";
    //public static final String DETAILS_VOTER_MAX= "Voter_max";
    public static final String DETAILS_BATCH= "Batch";
    public static final String DETAILS_MAX= "Max_no";
    public static final String DETAILS_DESC= "desc1";




    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null ,  6);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /* TABLE ADMIN */
     //   sqLiteDatabase.execSQL("create table " + TABLE_DETAILS + "(Election_name TEXT ,Election_id PRIMARY KEY NOT NULL UNIQUE AUTOINCREMENT,Admin_password TEXT );");
        sqLiteDatabase.execSQL("create table " + TABLE_ADMIN + "(Admin_id TEXT PRIMARY KEY NOT NULL UNIQUE,Admin_password TEXT );");
        sqLiteDatabase.execSQL("create table " + TABLE_ACCOUNT + "(college_id TEXT PRIMARY KEY NOT NULL UNIQUE,Password TEXT );");
        sqLiteDatabase.execSQL("create table " + TABLE_USER + "(college_id TEXT PRIMARY KEY NOT NULL UNIQUE,First_name TEXT,Second_name TEXT,Ph_no TEXT,Email TEXT);");
       String table_details="create table Details (Election_id TEXT PRIMARY KEY  NOT NULL UNIQUE ,Election_name TEXT,Start_time TEXT, End_time TEXT,desc1 TEXT,BATCH TEXT,Max_no INTEGER,Date TEXT);";
       sqLiteDatabase.execSQL(table_details);
       String Candidate="create table Candidate ( Authorisation_code INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL UNIQUE, FName TEXT,SName TEXT,No_of_votes INTEGER,phone_no TEXT,mail TEXT,college_id TEXT,Election_id TEXT);";
        sqLiteDatabase.execSQL(Candidate);
        sqLiteDatabase.execSQL("create table checker(college_id TEXT,Election_id TEXT,candiCollegeId TEXT);" );

        // AddMasterAdmin();
    }

    public void AddMasterAdmin()
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(ADMIN_ID,"admin");
        contentValues.put(ADMIN_PASSWORD,"admin");
        long t = sqLiteDatabase.insert(TABLE_ADMIN,null,contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT+ ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN+ ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TABLE_DETAILS + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATE + ";");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS checker;");
        onCreate(sqLiteDatabase);

    }
    public boolean insertData(String id,String firstname,String lastname,String phone_no,String  mail,String password )throws android.database.SQLException
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor c1 = sqLiteDatabase.rawQuery("select college_id from User where college_id = '"+id+"';",null);

            if(c1 != null)

            {
                if (c1.getCount() > 0) {
                    return false;
                } else {

                }
            }


      //  Toast.makeText(Databasehelper.this, "writable database", Toast.LENGTH_LONG).show();
        ContentValues contentValues=new ContentValues();
        ContentValues contentValues1=new ContentValues();
        contentValues.put(USER_COLLEGE_ID,id);
        contentValues.put(USER_NAME1,firstname);
        contentValues.put(USER_NAME2,lastname);

        contentValues.put(USER_PHONE_NO,phone_no);
         contentValues.put(USER_EMAIL,mail);
        contentValues1.put(ACC_COLLEGE_ID,id);
        contentValues1.put(ACC_PASSWORD,password);
     long r=   sqLiteDatabase.insert(TABLE_USER,null,contentValues);
        long r1=   sqLiteDatabase.insert(TABLE_ACCOUNT,null,contentValues1);
   return  true;
    }

    HashMap<String,String> pollDisplay()
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select Election_id,Election_name from Details;",null);
        if(cursor!=null)
        {


            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    al.put(cursor.getString(0),cursor.getString(1));
                }while(cursor.moveToNext());
            }

        }
        return al;
    }

    boolean checkPrimaryElection(String eid)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Election_id from Details where Election_id = '"+eid+"';",null);
        if(cursor!=null)
        {
            if (cursor.getCount() > 0) {
                return true;
            } else {
            }

        }
        return false;
    }

    int checkElectionTime(String eId)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String mm=Integer.toString(month),dd=Integer.toString(day);
        if(month<10)
            mm = "0"+mm;
        if(day<10)
            dd = "0"+dd;
        String presentdate = year + "-" + mm + "-" + dd;

        String ans = Integer.toString(hour);
        if(ans.length()==1)
        {
            ans = "0"+ans;
        }
        ans+=" : ";
        if(minute/10==0)
        {
            ans+=("0"+Integer.toString(minute) );
        }
        else
        {
            ans+=(Integer.toString(minute));
        }
        String inputDate="",inputSTime="",inputETime="";
        Cursor cursor = sqLiteDatabase.rawQuery("select Start_time,End_time,Date from Details where Election_id='"+eId+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    inputSTime = cursor.getString(0);
                    inputETime = cursor.getString(1);
                    inputDate = cursor.getString(2);
                    if(presentdate.compareTo(inputDate)>0||(presentdate.equals(inputDate)&&inputETime.compareTo(ans)<=0))
                    {
                        return 1;  //Election is already over
                    }
                    else if(presentdate.compareTo(inputDate)==0&&(ans.compareTo(inputSTime)>=0&&inputETime.compareTo(ans)>=0))
                    {
                        return 0; //election is going on
                    }
                    else
                    {
                        return -1; // Election is yet to start
                    }
                }while(cursor.moveToNext());
            }

        }
        return 0;
    }

    public String getDesc(String Eid)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select desc1 from Details where Election_id = '"+Eid+"';",null);
      String res = "";
        if(cursor!=null)
        {


            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    res = (cursor.getString(0));
                }while(cursor.moveToNext());
            }

        }
        return res;
    }

    HashMap<String,String> pollDisplayUser(String uId)
    {
        Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String mm=Integer.toString(month),dd=Integer.toString(day);
        if(month<10)
            mm = "0"+mm;
        if(day<10)
            dd = "0"+dd;
        String presentdate = year + "-" + mm + "-" + dd;

        String ans = Integer.toString(hour);
        if(ans.length()==1)
        {
            ans = "0"+ans;
        }
        ans+=" : ";
        if(minute/10==0)
        {
            ans+=("0"+Integer.toString(minute) );
        }
        else
        {
            ans+=(Integer.toString(minute));
        }
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor;
        String inputDate="",inputSTime="",inputETime="";
        cursor = sqLiteDatabase.rawQuery("select Start_time,End_time,Date,Election_id,Election_name from Details;",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    inputSTime = cursor.getString(0);
                    inputETime = cursor.getString(1);
                    inputDate = cursor.getString(2);
                    String pk = cursor.getString(3);
                    Cursor c1 = sqLiteDatabase.rawQuery("select Election_id from checker where Election_id = '"+pk+"' and college_id='"+uId+"';",null);
                    boolean check = false;
                    if(c1 != null)

                    {
                        if (c1.getCount() > 0) {
                            ;
                        } else {
                            check = true;
                        }
                    }
                    else
                        check = true;
                    c1.close();
                    if(check&&inputDate.equals(presentdate)&&(inputSTime.compareTo(ans)<=0)&&inputETime.compareTo(ans)>=0)
                    {
                        al.put(pk,cursor.getString(4));
                    }
                }while(cursor.moveToNext());
            }

        }
        cursor.close();
       /* Log.d("My_Tag",todayDate);
        cursor = sqLiteDatabase.rawQuery("select Election_name,desc  from Details  ;",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    al.put(cursor.getString(0),cursor.getString(1));
                }while(cursor.moveToNext());
            }

        }*/
        return al;
    }

    HashMap<String,String> pollCandidate(String EId)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select FName,SName,college_id from Candidate where Election_id = '"+EId+"';",null);
      //  Toast.makeText(Databasehelper.this,EId,Toast.LENGTH_LONG).show();
        if(cursor!=null)
        {
        cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    al.put(cursor.getString(2),cursor.getString(0)+" "+cursor.getString(1));
                }while(cursor.moveToNext());
            }

        }
        return al;
    }

    public String getMyVoteDetails(String eId,String uId)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select candiCollegeId from checker where Election_id = '"+eId+"' and college_id = '"+uId+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }


    HashMap<String,String> viewResultPollUser()
    {
         Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
         String mm=Integer.toString(month),dd=Integer.toString(day);
        if(month<10)
            mm = "0"+mm;
        if(day<10)
            dd = "0"+dd;
        String presentdate = year + "-" + mm + "-" + dd;

        String ans = Integer.toString(hour);
        if(ans.length()==1)
        {
            ans = "0"+ans;
        }
        ans+=" : ";
        if(minute/10==0)
        {
            ans+=("0"+Integer.toString(minute) );
        }
        else
        {
            ans+=(Integer.toString(minute));
        }
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor;
        String inputDate="",inputSTime="",inputETime="";
        cursor = sqLiteDatabase.rawQuery("select Start_time,End_time,Date,Election_id,Election_name from Details order by Date;",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    inputSTime = cursor.getString(0);
                    inputETime = cursor.getString(1);
                    inputDate = cursor.getString(2);
                    String pk = cursor.getString(3);

                    if(inputDate.compareTo(presentdate)<0||(inputDate.compareTo(presentdate)==0&&inputETime.compareTo(ans)<=0))
                    {
                        al.put(pk,cursor.getString(4));
                    }
                }while(cursor.moveToNext());
            }

        }
        cursor.close();
        return al;
    }

    HashMap<String,String> candidateFinalVotes(String EId)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select FName,SName,college_id,No_of_votes from Candidate where Election_id = '"+EId+"' order by No_of_votes;",null);
        //  Toast.makeText(Databasehelper.this,EId,Toast.LENGTH_LONG).show();
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{

                    al.put(cursor.getString(0)+" "+cursor.getString(1)+"("+cursor.getString(2)+")",(cursor.getString(3)));
                }while(cursor.moveToNext());
            }

        }
        return al;
    }



    HashMap<String,String> viewPollsAlreadyVoted(String uId)
    {
        Calendar c= Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        String mm=Integer.toString(month),dd=Integer.toString(day);
        if(month<10)
            mm = "0"+mm;
        if(day<10)
            dd = "0"+dd;
        String presentdate = year + "-" + mm + "-" + dd;

        String ans = Integer.toString(hour);
        if(ans.length()==1)
        {
            ans = "0"+ans;
        }
        ans+=" : ";
        if(minute/10==0)
        {
            ans+=("0"+Integer.toString(minute) );
        }
        else
        {
            ans+=(Integer.toString(minute));
        }
        SQLiteDatabase sqLiteDatabase= this.getReadableDatabase();
        HashMap<String,String> al = new HashMap<>();
        Cursor cursor;
        String inputDate="",inputSTime="",inputETime="";
        cursor = sqLiteDatabase.rawQuery("select Start_time,End_time,Date,Election_id,Election_name from Details;",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    inputSTime = cursor.getString(0);
                    inputETime = cursor.getString(1);
                    inputDate = cursor.getString(2);
                    String pk = cursor.getString(3);
                    Cursor c1 = sqLiteDatabase.rawQuery("select Election_id from checker where Election_id = '"+pk+"' and college_id='"+uId+"';",null);
                    boolean check = false;
                    if(c1 != null)

                    {
                        if (c1.getCount() > 0) {
                            ;
                        } else {
                            check = true;
                        }
                    }
                    else
                        check = true;
                    c1.close();
                    if(!check&&inputDate.equals(presentdate)&&(inputSTime.compareTo(ans)<=0)&&inputETime.compareTo(ans)>=0)
                    {
                        al.put(pk,cursor.getString(4));
                    }
                }while(cursor.moveToNext());
            }

        }
        cursor.close();
        return al;

    }




    public void increaseVote(String cId,String eId)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.execSQL("update Candidate set No_of_votes = No_of_votes+1 "+"where Election_id = '"+eId+"' and college_id = '"+cId+"';");
    }

    public void userVoteSet(String eId,String uId,String cId)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        sqLiteDatabase.execSQL("insert into checker values('"+uId+"','"+eId+"','"+cId+"');");
    }


    public void Insertpoll(String election_id,String election_name,String desc, String date,String startTime, String endTime,String max,String batch,String college_id,String mail,String phone,String fname,String sname)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues2=new ContentValues();
        ContentValues contentValues3=new ContentValues();
        int maxs=Integer.parseInt(max);
       // int batchs=Integer.parseInt(batch);
     //   int phones=Integer.parseInt(phone);
        contentValues2.put(DETAILS_ELECTION_ID,election_id);
        contentValues2.put(DETAILS_ELECTION_NAME,election_name);
        contentValues2.put(DETAILS_DESC,desc);
        contentValues2.put(DETAILS_DATE,date);
        contentValues2.put(START_TIME, startTime);
        contentValues2.put(END_TIME, endTime);
        contentValues2.put(DETAILS_MAX,maxs);
        contentValues2.put(DETAILS_BATCH,batch);

        String get="SELECT Election_id FROM Details WHERE Election_id ='"+ election_id+"';";

        final Cursor cursor1 = sqLiteDatabase.rawQuery(get, null);

      // Log.d();
        if(cursor1 != null)

        {
            if(cursor1.getCount()>0)
            {
                ;
            }
            else {
                long a = sqLiteDatabase.insert(TABLE_DETAILS, null, contentValues2);
            }
        }

       // String get="SELECT Election_id FROM Details WHERE Election_id = (SELECT MAX(Election_id) FROM Details);";

        //SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
      /*  final Cursor cursor1 = sqLiteDatabase.rawQuery(get, null);
       int election_id=0;
      // Log.d();
       if (cursor1 != null) {
            try {
                if (cursor1.moveToFirst()) {
                    election_id = cursor1.getInt(0);
                }
            } finally {
                cursor1.close();
            }
        }
        int no_votes=0;*/


        contentValues3.put(CANDI_ID,college_id);
        contentValues3.put(CANDI_MAIL,mail);
        contentValues3.put(CANDI_ELECTION_ID,election_id);
        contentValues3.put(CANDI_PHONE,phone);
     //   contentValues3.put(CANDI_VOTES,no_votes);
        contentValues3.put(CANDI_ID,college_id);
        contentValues3.put(CANDI_FNAME,fname);
        contentValues3.put(CANDI_SNAME,sname);
        contentValues3.put("No_of_votes","0");
        long b = sqLiteDatabase.insert(TABLE_CANDIDATE,null,contentValues3);


    }

/*
    public void InsertpollUpdated(String college_id,String mail,String phone,String fname,String sname)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        ContentValues contentValues3=new ContentValues();
        String get="SELECT Election_id FROM Details WHERE Election_id = (SELECT MAX(Election_id) FROM Details);";

        //SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        final Cursor cursor1 = sqLiteDatabase.rawQuery(get, null);
        int election_id=0;
        if (cursor1 != null) {
            try {
                if (cursor1.moveToFirst()) {
                    election_id = cursor1.getInt(0);
                }
            } finally {
                cursor1.close();
            }
        }
        int no_votes=0;
        contentValues3.put(CANDI_ID,college_id);
        contentValues3.put(CANDI_MAIL,mail);
        contentValues3.put(CANDI_ELECTION_ID,election_id);
        contentValues3.put(CANDI_PHONE,phone);
        contentValues3.put(CANDI_VOTES,no_votes);
        contentValues3.put(CANDI_ID,college_id);
        contentValues3.put(CANDI_FNAME,fname);
        contentValues3.put(CANDI_SNAME,sname);
        long b = sqLiteDatabase.insert(TABLE_CANDIDATE,null,contentValues3);
    }
*/
    public Boolean check(String enrol, String password)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
       // boolean c= true;
      //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE "+ACC_COLLEGE_ID+"=? AND "+ACC_PASSWORD+"=?", new String[] {enrol, password});
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE "+ACC_COLLEGE_ID+"='"+enrol+"' AND "+ACC_PASSWORD+"='"+password+"';",null);// Toast.makeText("Data Inserted", Databasehelper.this, Toast.LENGTH_LONG).show();

        if(cursor != null)
        {
            if(cursor.getCount()>0)
            {
                return true;
            }
            else
                return false;
        }
        return false;

    }

    void deletePoll(String s)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        String arr[]={s};
        sqLiteDatabase.delete(TABLE_DETAILS, "Election_id=?",arr);

        //sqLiteDatabase.execSQL("delete from Details where ELection_id = '"+s+"';");
    }
    public  void setProfile(String id, String first,String second,String phone, String email){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();

        sqLiteDatabase.execSQL("update User set First_name = '"+first+"' , Second_name = '"+second+"' , Ph_no = '"+phone+"', Email = '"+email+"' " +"where college_id = '"+id+"' ;");

    }

    public boolean newActivity(String electionId,int max)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Candidate WHERE Election_id = '"+electionId + "';",null);// Toast.makeText("Data Inserted", Databasehelper.this, Toast.LENGTH_LONG).show();

        if(cursor != null)
        {

            if(cursor.getCount()==max)
            {
                return true;
            }
            else
                return false;
        }
        return false;
    }



    public Boolean checkAdminCredentials(String enrol, String password)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        // boolean c= true;
        //  Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Account WHERE "+ACC_COLLEGE_ID+"=? AND "+ACC_PASSWORD+"=?", new String[] {enrol, password});
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " +TABLE_ADMIN+ " WHERE "+ADMIN_ID+"='"+enrol+"' AND "+ADMIN_PASSWORD+"='"+password+"';",null);// Toast.makeText("Data Inserted", Databasehelper.this, Toast.LENGTH_LONG).show();

        if(cursor != null)

        {
            if(cursor.getCount()>0)
            {
                return true;
            }
            else
                return false;
        }
        return false;

    }

    public String getElectionName(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Election_name from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public int getMaxNo(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Max_no from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                   // int k=cursor.getInt(0);
                   // String max=k.to
                    return cursor.getInt(0);
                }while(cursor.moveToNext());
            }

        }
        return 0;
    }

    public String getElectionDate(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Date from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getDescription(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select desc1 from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getBatch(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Batch from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }


    public String getStartTime(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Start_time from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getEndTime(String id)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select End_time from Details where Election_id = '"+id+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public void updatePoll(String id,String electionName, String Batch, String date1, String desc, String sTime, String eTime, String max)
    {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        int max1 = Integer.parseInt(max);
        sqLiteDatabase.execSQL("update Details set Election_name = '"+electionName+"' , Batch = '"+Batch+"' , Date = '"+date1+"', Start_time = '"+sTime+"', End_time = '"+eTime+"', desc1 = '"+desc+"' , Max_no = "+max1+" where Election_id = '"+id+"' ;");

    }
    public  void close(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.close();
    }


    public String getFirst(String activUser) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select First_name from User where college_id = '"+activUser+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getSecond(String activUser) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Second_name from User where college_id = '"+activUser+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getMail(String activUser) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Email from User where college_id = '"+activUser+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }


    public String getPhone(String activUser) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select Ph_no from User where college_id = '"+activUser+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getFirstName(String enrol, String eId) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select FName from Candidate where college_id = '"+enrol+"' and Election_id = '"+eId+"' ;",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getSecondName(String enrol, String eId) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select SName from Candidate where college_id = '"+enrol+"' and Election_id = '"+eId+"' ;",null);
       // Cursor cursor = sqLiteDatabase.rawQuery("select SName from Candidate where college_id = '"+enrol+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getCandidateMail(String enrol, String eId) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select mail from Candidate where college_id = '"+enrol+"' and Election_id = '"+eId+"' ;",null);
        //Cursor cursor = sqLiteDatabase.rawQuery("select mail from Candidate where college_id = '"+enrol+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public String getCandidateNumber(String enrol, String eId){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select phone_no from Candidate where college_id = '"+enrol+"' and Election_id = '"+eId+"' ;",null);
        //Cursor cursor = sqLiteDatabase.rawQuery("select phone_no from Candidate where college_id = '"+enrol+"';",null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast())
            {
                do{
                    return cursor.getString(0);
                }while(cursor.moveToNext());
            }

        }
        return "";
    }

    public void updateCandidateDetails(String enrol, String eId, String fnames, String snames, String mails, String phone) {
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        sqLiteDatabase.execSQL("update Candidate set FName = '"+fnames+"' , SName = '"+snames+"' , mail = '"+mails+"', phone_no = '"+phone+"' where college_id = '"+enrol+"' and Election_id = '"+eId+"' ;");
    }
}