package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_Products_Adapter extends RecyclerView.Adapter<recycler_Products_Adapter.Recycler_ViewHolder> {
    Context context;
    ArrayList<String> ProdNames ;
    ArrayList<Float> ProdPrices ;
    ArrayList<Integer> ProdQuantities;
    int Quantity; String price;
    public recycler_Products_Adapter(Context context, ArrayList<String> ProdNames ,ArrayList<Float> ProductPrices,ArrayList<Integer>ProdQuantities)
    {
        this.context=context;
        this.ProdNames = ProdNames;
        this.ProdPrices=ProductPrices;
        this.ProdQuantities=ProdQuantities;
    }
    @NonNull
    @Override
    public Recycler_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_products_row,parent,false);
        return new Recycler_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Recycler_ViewHolder holder, final int position) {
        holder.NameTxt.setText(ProdNames.get(position));
        holder.PriceTxt.setText(String.valueOf(ProdPrices.get(position)));
        Quantity=Integer.parseInt(holder.QuantityTxt.getText().toString());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Quantity < ProdQuantities.get(position)) {
                    Quantity += 1;
                    holder.QuantityTxt.setText(String.valueOf(Quantity));
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quantity > 1) {
                    Quantity -= 1;
                    holder.QuantityTxt.setText(String.valueOf(Quantity));
                }
            }
        });

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("ProdName",holder.NameTxt.getText().toString());
                bundle.putFloat("ProdPrice",Float.parseFloat(holder.PriceTxt.getText().toString()));
                bundle.putInt("ProdQuantity",Integer.parseInt(holder.QuantityTxt.getText().toString()));
                bundle.putInt("TotalQuantity",ProdQuantities.get(position)- Integer.parseInt(holder.QuantityTxt.getText().toString()));
                MainActivity.cartBundle.add(bundle);

                ProdNames.clear();
                ProdQuantities.clear();
                ProdPrices.clear();

                Toast.makeText(context,"added",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ProdNames.size();
    }

    public class Recycler_ViewHolder extends RecyclerView.ViewHolder{

        TextView NameTxt , PriceTxt , QuantityTxt;
        ConstraintLayout mainLayout;
        ImageButton add,remove , addToCart;
        public Recycler_ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTxt=itemView.findViewById(R.id.prodNameTxt);
            PriceTxt=itemView.findViewById(R.id.prodPriceTxt);
            add=itemView.findViewById(R.id.addBtn);
            remove=itemView.findViewById(R.id.removeBtn);
            addToCart=itemView.findViewById(R.id.addToCartBtn);
            QuantityTxt=itemView.findViewById(R.id.prodQuantityTxt);
            mainLayout=itemView.findViewById(R.id.productCellLayout);
        }
    }
}
