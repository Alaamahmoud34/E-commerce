package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    DB_Helper db;
    EditText UsernameTxt, PasswordTxt;
    TextView forgetTxt;
    CheckBox Remember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Form");
        UsernameTxt=findViewById(R.id.EmailTxt);
        PasswordTxt=findViewById(R.id.passwordTxt);
        forgetTxt=findViewById(R.id.forgetTxt);
        Remember=findViewById(R.id.remember);
        db=new DB_Helper(getApplicationContext());

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("Remember","");
        if(checkbox.equals("true"))
        {
            MainActivity.custName=preferences.getString("Username","");
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        else if(checkbox.equals("false"))
            Toast.makeText(Login.this,"please sign in",Toast.LENGTH_SHORT).show();

        Remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("Remember","true");
                    editor.putString("Username",UsernameTxt.getText().toString());
                    editor.apply();
                    Toast.makeText(Login.this,"checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("Remember","false");
                    editor.apply();
                    Toast.makeText(Login.this,"unchecked",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void register_btn(View view) {
        startActivity(new Intent(getApplicationContext(),SignUp.class));
    }

    public void login_btn(View view) {
        boolean found = db.IsValidLogin(UsernameTxt.getText().toString(),PasswordTxt.getText().toString());
        if(found) {
            Toast.makeText(this,"Login successfully",Toast.LENGTH_LONG).show();
            MainActivity.custName=UsernameTxt.getText().toString();
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else
            Toast.makeText(this,"Invalid Username or Password",Toast.LENGTH_LONG).show();
    }

    public void forgetClick(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(),forget_password.class));
    }
}
