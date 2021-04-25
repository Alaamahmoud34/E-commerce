package com.example.e_commerce;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class fragmentUser extends Fragment {
    DB_Helper db;
    Cursor cursor;
    LineGraphSeries<DataPoint> series;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_user,container,false);
        EditText name=view.findViewById(R.id.UserName);
        EditText Email=view.findViewById(R.id.UserEmail);
        EditText Gender=view.findViewById(R.id.UserGender);
        EditText Birthdate=view.findViewById(R.id.UserBirthdate);
        EditText job=view.findViewById(R.id.UserJob);
        GraphView orders=view.findViewById(R.id.graphView);

        int id=0;
        db=new DB_Helper(this.getContext());
        cursor = db.getCustomerInfo(MainActivity.custName);
        if (cursor != null) {
            id=cursor.getInt(0);
            name.setText(cursor.getString(1));
            Email.setText(cursor.getString(2));
            Gender.setText(cursor.getString(4));
            Birthdate.setText(cursor.getString(5));
            job.setText(cursor.getString(6));
        }

        cursor=db.get_orders(id);
        series=new LineGraphSeries<>();
        int[]arr=new int[12];
        while (!cursor.isAfterLast()) {
            int x;
            if(cursor.getString(1).charAt(5)=='0')
                x=Integer.parseInt(cursor.getString(1).charAt(6)+"");
            else
                x=Integer.parseInt(cursor.getString(1).substring(5, 6));

            arr[x]+=1;
            cursor.moveToNext();
        }
        for(int i=0 ; i<12; i++)
        {
            series.appendData(new DataPoint(i,arr[i]), true, 500);
        }
        orders.addSeries(series);
        return view;
    }
}
