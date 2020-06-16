package com.example.easy_voting;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class Main2Activity extends AppCompatActivity {

     Button Sign_Up;
     EditText firstname;
     EditText lastname;

     EditText id;
     EditText password,pAgain;
     EditText phone_no;
     EditText mail;
      Databasehelper myDb1;
    private static String cryptoPass = "sup3rupar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Sign_Up=(Button)(findViewById(R.id.button));
        id=(EditText)(findViewById(R.id.textView3)) ;
        firstname=(EditText)(findViewById(R.id.textView4)) ;
        lastname=(EditText)(findViewById(R.id.textView6)) ;
        phone_no=(EditText)(findViewById(R.id.textView7)) ;
        mail=(EditText)(findViewById(R.id.textView8)) ;

        password=(EditText)(findViewById(R.id.textView10)) ;
        pAgain = (EditText)(findViewById(R.id.textView11));
        myDb1=new Databasehelper(this);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //     boolean isInserted=true;
                try {
                    String id1 = id.getText().toString(),fname =firstname.getText().toString() ,lname = lastname.getText().toString(),
                         phone = (phone_no.getText().toString()), email =  mail.getText().toString(),  pass =  encryptIt(password.getText().toString()),pp =encryptIt(password.getText().toString()) ;
                    if(id1.equals("")||fname.equals("")||lname.equals("")||phone.equals("")||email.equals("")||pass.equals(encryptIt(""))||pp.equals(encryptIt("")))
                    {
                        Toast.makeText(Main2Activity.this,"Please Enter All the details",Toast.LENGTH_LONG).show();
                    }
                    else if(pp.equals(pass)==false)
                    {
                        Toast.makeText(Main2Activity.this,"Passwords do not match",Toast.LENGTH_LONG).show();
                        pAgain.setText("");
                    }
                    else {
                        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                        {
                        boolean x = myDb1.insertData(id1, fname, lname, phone, email, pass);
                        if(x) {

                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Main2Activity.this,"User Already exists!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                            startActivity(intent);
                        }}
                        else
                        {
                            Toast.makeText(Main2Activity.this,"Enter Valid Email Address!",Toast.LENGTH_LONG).show();
                        }
                    }
                 //  Toast.makeText(Main2Activity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }
                catch (android.database.SQLException e)
                {
                    Toast.makeText(Main2Activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                //if(isInserted == true)
                   // Toast.makeText(Main2Activity.this, "Data Inserted", Toast.LENGTH_LONG).show();
               // else
                 //   Toast.makeText(Main2Activity.this , "Data not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }


    public String encryptIt(String value) {
        try {
            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            String encrypedValue = Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
            //TOAST.makeText(MainActivity.this, "Encrypted: " + value + " -> " + encrypedValue, Toast.LENGTH_LONG);
            //Log.d(TAG, "Encrypted: " + value + " -> " + encrypedValue);
            return encrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return value;
    };

}
