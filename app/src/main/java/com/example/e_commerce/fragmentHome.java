package com.example.e_commerce;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragmentHome extends Fragment {
    ArrayList<String> catNames=new ArrayList<>();

    int[] images={R.drawable.bags,R.drawable.shoes,R.drawable.supermarket,R.drawable.sports,
    R.drawable.kitchen_basics,R.drawable.toys,R.drawable.men_clothes,R.drawable.women_clothes,
    R.drawable.electronics,R.drawable.health_and_beauty,R.drawable.home_furniture};

    DB_Helper db;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView=view.findViewById(R.id.recycleView);
        db=new DB_Helper(this.getContext());
        Cursor cursor=db.AllCategories();
        while (!cursor.isAfterLast()) {
            catNames.add(cursor.getString(0));
            cursor.moveToNext();
        }

        recycler_categories_Adapter adapter=new recycler_categories_Adapter(this.getContext(),catNames,images);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }
}
