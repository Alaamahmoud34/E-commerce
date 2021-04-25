package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    EditText UsernameTxt,PasswordTxt,FullNameTxt,phoneTxt,birthDate,confirmTxt,jobTxt;
    RadioGroup Gender;
    RadioButton checkedGender;
    DB_Helper db;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("SignUp Form");
        db=new DB_Helper(getApplicationContext());

        UsernameTxt=findViewById(R.id.userNameTxt);
        PasswordTxt=findViewById(R.id.PasswordTxt);
        FullNameTxt=findViewById(R.id.nameTxt);
        phoneTxt=findViewById(R.id.phoneTxt);
        confirmTxt=findViewById(R.id.confirmTxt);
        birthDate=findViewById(R.id.et_Birthdate);
        jobTxt=findViewById(R.id.jobTxt);
        Gender=findViewById(R.id.Gender);

        Calendar calendar=Calendar.getInstance();
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        final int month=calendar.get(Calendar.MONTH);
        final int year=calendar.get(Calendar.YEAR);

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(SignUp.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month=month+1;
                String date=day+"/"+month+"/"+year;
                birthDate.setText(date);
            }
        };
    }

    public void register_btn(View view) {
        boolean found = db.IsCustomer(UsernameTxt.getText().toString());
        if(found) {
            Toast.makeText(this,"Used E-mail",Toast.LENGTH_LONG).show();
            UsernameTxt.setText(null);
        }
        else {
            if(!PasswordTxt.getText().toString().equals(confirmTxt.getText().toString())) {
                Toast.makeText(this, "Password is not the same", Toast.LENGTH_LONG).show();
                confirmTxt.setText(null);
            }
            else {
                checkedGender=findViewById(Gender.getCheckedRadioButtonId());
                db.add_customer(FullNameTxt.getText().toString(), UsernameTxt.getText().toString(), PasswordTxt.getText().toString(),
                        String.valueOf(checkedGender.getText()), birthDate.getText().toString(), jobTxt.getText().toString());
                Toast.makeText(this, "You are a fragmentUser now ^_^", Toast.LENGTH_LONG).show();
                MainActivity.custName=UsernameTxt.getText().toString();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }
}