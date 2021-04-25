package com.example.e_commerce;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class forget_password extends AppCompatActivity {

    int Q_number=0 ,correct=0;
    Cursor cursor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Button NextBtn=findViewById(R.id.next);
        final EditText Answer=findViewById(R.id.AnswerTxt);
        final TextView Question=findViewById(R.id.QuestionTxt);
        final String[] questions=getResources().getStringArray(R.array.Questions);

        Question.setText(questions[Q_number]);

        final DB_Helper db=new DB_Helper(this);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Q_number == 0) {
                        cursor = db.getCustomerInfo(Answer.getText().toString());
                        if (cursor != null) {
                            Q_number++;
                            correct++;
                            Question.setText(questions[Q_number]);
                            Answer.getText().clear();
                        } else
                            Alert();
                    } else if (correct == 1 && Q_number == 1) {
                        if (Answer.getText().toString().equals(cursor.getString(5))) {
                            Q_number++;
                            correct++;
                            Question.setText(questions[Q_number]);
                            Answer.getText().clear();
                        } else
                            Alert();
                    } else if (correct == 2 && Q_number == 2) {
                        if (Answer.getText().toString().equals(cursor.getString(6))) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
                            builder.setTitle("Return password");
                            builder.setMessage("Your password is :" + cursor.getString(3));
                            builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.custName = cursor.getString(2);
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else
                            Alert();
                    }
                }catch (Exception ex){Alert();}
            }
        });
    }
    private void Alert()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(forget_password.this);
        builder.setTitle("Error while Retrieving the password");
        builder.setMessage("we are sorry but it seems that you aren't the fragmentUser");
        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("Create new account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
