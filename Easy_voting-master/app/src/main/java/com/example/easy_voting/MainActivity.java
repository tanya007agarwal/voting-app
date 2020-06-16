package com.example.easy_voting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class MainActivity extends AppCompatActivity {

    public static final String MY_FILENAME="package com.example.easy_voting.ID";
    Databasehelper myDb;
    EditText ED_enroll;
    EditText ED_password, password;
    Button Sign_up;
    Button login,AdminLogin;
    private static String cryptoPass = "sup3rupar";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences.Editor editor;
        myDb= new Databasehelper(this);
        ED_enroll=(EditText)(findViewById(R.id.ETid1));
        Sign_up=(Button)(findViewById(R.id.button2));
        login = (Button) (findViewById(R.id.BTid1));
        password = (EditText) (findViewById(R.id.EDid2));
        AdminLogin = (Button)(findViewById(R.id.button3));
      SharedPreferences editor1=getSharedPreferences(MY_FILENAME,MODE_PRIVATE);
        //editor1.putString("COLLEGE_ID",null);
        //editor1.putString("PASS",null);
        //editor1.commit();
        String pass;
        String enrol=editor1.getString("enrol","");
        String pass1=editor1.getString("pass1","");
        //if(pass1.compareTo("")==0)
        //{
          //  pass = (pass1);

//        }
  //      else
      //  {
           //  pass = encryptIt(pass1);

     //   }
        //if(pass.compareTo("")!=0)
        {
           // pass = encryptIt(pass1);
        }
        ED_enroll.setText(enrol);
        password.setText(pass1);
        if(enrol.equals("")||pass1.equals(""))
        {

          //  Toast.makeText(MainActivity.this,"Please Enter the Details",Toast.LENGTH_LONG).show();
        }
        else if(myDb.check(enrol, encryptIt(pass1))==true)
        {
       //     Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
            ED_enroll.setText("");
            password.setText("");
            Intent intent = new Intent(MainActivity.this,UserPolls.class);
            intent.putExtra("userActive",enrol);
            startActivity(intent);
        }
        else
        {
          //  Toast.makeText(MainActivity.this, "Please check the details", Toast.LENGTH_LONG).show();
            ED_enroll.setText("");
            password.setText("");
        }

        Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Signing Up", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(MainActivity.this,com.example.easy_voting.Main2Activity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enrol = ED_enroll.getText().toString();
                String pass = encryptIt(password.getText().toString());
                SharedPreferences.Editor editor=getSharedPreferences(MY_FILENAME,MODE_PRIVATE).edit();
               editor.putString("enrol",enrol);
               editor.putString("pass1",password.getText().toString());
               editor.commit();

                if(enrol.equals("")||pass.equals(""))
                {
                    Toast.makeText(MainActivity.this,"Please Enter the Details",Toast.LENGTH_LONG).show();
                }
                else if(myDb.check(enrol, pass)==true)
                {
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    ED_enroll.setText("");
                    password.setText("");
                    Intent intent = new Intent(MainActivity.this,UserPolls.class);
                    intent.putExtra("userActive",enrol);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please check the details", Toast.LENGTH_LONG).show();
                    ED_enroll.setText("");
                    password.setText("");
                }
            }
        });
        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AdminLogin.class);
                startActivity(intent);
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
