package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class fragmentCart extends Fragment {
    RecyclerView cart;
    ArrayList<String> ProdNames=new ArrayList<>();
    ArrayList<Float> ProdPrices=new ArrayList<>();
    ArrayList<Integer> ProdQuantities=new ArrayList<>();
    ArrayList<Integer> totalQuantitiy=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart,container,false);
        cart=view.findViewById(R.id.cartRecycleView);
        Button submitBtn=view.findViewById(R.id.submitBtn);
        for(int i=0 ; i<MainActivity.cartBundle.size();i++)
        {
            ProdNames.add(MainActivity.cartBundle.get(i).getString("ProdName"));
            ProdPrices.add(MainActivity.cartBundle.get(i).getFloat("ProdPrice"));
            ProdQuantities.add(MainActivity.cartBundle.get(i).getInt("ProdQuantity"));
            totalQuantitiy.add(MainActivity.cartBundle.get(i).getInt("TotalQuantity"));
        }

        recycler_Cart_Adapter adapter = new recycler_Cart_Adapter(this.getContext(), ProdNames, ProdPrices, ProdQuantities ,totalQuantitiy);
        cart.setAdapter(adapter);
        cart.setLayoutManager(new LinearLayoutManager(this.getContext()));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float totalPrice=0;
                for(int i=0 ; i<MainActivity.cartBundle.size();i++)
                {
                    totalPrice+=MainActivity.cartBundle.get(i).getInt("ProdQuantity")*MainActivity.cartBundle.get(i).getFloat("ProdPrice");
                }
                Bundle bundle=new Bundle();
                bundle.putFloat("TotalPrice",totalPrice);
                Intent intent=new Intent(getContext(),GoogleMapsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return view;
    }
}
