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

public class fragmentProducts extends Fragment {

    ArrayList<String> prodNames=new ArrayList<>();
    ArrayList<Float> prodPrices=new ArrayList<>();
    ArrayList<Integer> prodQuantities=new ArrayList<>();
    DB_Helper db;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_products,container,false);

        recyclerView=view.findViewById(R.id.ProductRecyclerView);
        db=new DB_Helper(this.getContext());
        Bundle bundle=this.getArguments();
        Cursor cursor;
        try {
            cursor = db.retProducts(bundle.getString("CatName"));
            if (cursor != null)
                while (!cursor.isAfterLast()) {
                    prodNames.add(cursor.getString(1));
                    prodPrices.add(cursor.getFloat(2));
                    prodQuantities.add(cursor.getInt(3));
                    cursor.moveToNext();
                }
        }catch(Exception ex){}
        try {
            cursor = db.product_Details(bundle.getString("ProdName"));
            if (cursor != null) {
                prodNames.add(cursor.getString(1));
                prodPrices.add(cursor.getFloat(2));
                prodQuantities.add(cursor.getInt(3));
            }
        }catch (Exception ex){}
        recycler_Products_Adapter adapter = new recycler_Products_Adapter(this.getContext(), prodNames, prodPrices, prodQuantities);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }
}