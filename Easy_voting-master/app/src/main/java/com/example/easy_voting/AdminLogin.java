package com.example.easy_voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    Databasehelper myDb;
    Button bucky;
    EditText ED_enroll1;
    EditText ED_password1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        bucky = (Button)(findViewById(R.id.login_admin));
        ED_enroll1=(EditText)(findViewById(R.id.admin_username));
        ED_password1=(EditText)(findViewById(R.id.admin_pass));
        myDb= new Databasehelper(this);
        bucky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enrol = ED_enroll1.getText().toString();
                String pass = ED_password1.getText().toString();
                boolean checking = pass.equals("admin");
                //Toast.makeText(AdminLogin.this,"Before Database",Toast.LENGTH_LONG).show();
                if(myDb.checkAdminCredentials(enrol, pass)==true||(enrol.equals("admin")&&pass.equals("admin")))
                {
                   // Toast.makeText(AdminLogin.this,enrol+checking, Toast.LENGTH_LONG).show();
                    ED_enroll1.setText("");
                    ED_password1.setText("");
                    Intent intent=new Intent(AdminLogin.this,AdminFeatures.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(AdminLogin.this, "Please check the details", Toast.LENGTH_LONG).show();
                    ED_enroll1.setText("");
                    ED_password1.setText("");
                }

            }
        });
    }

}
