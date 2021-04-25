package com.example.e_commerce;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_Cart_Adapter extends RecyclerView.Adapter<recycler_Cart_Adapter.Recycler_ViewHolder> {
    Context context;
    ArrayList<String> ProdNames ;
    ArrayList<Float> ProdPrices ;
    ArrayList<Integer> ProdQuantities , totalQuantity;
    int Quantity; String price;
    public recycler_Cart_Adapter(Context context,ArrayList<String> ProdNames ,ArrayList<Float> ProductPrices,ArrayList<Integer>ProdQuantities, ArrayList<Integer> totalQuantity)
    {
        this.context=context;
        this.ProdNames = ProdNames;
        this.ProdPrices=ProductPrices;
        this.ProdQuantities=ProdQuantities;
        this.totalQuantity=totalQuantity;
    }

    @NonNull
    @Override
    public Recycler_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_cart_row,parent,false);
        return new Recycler_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Recycler_ViewHolder holder, final int position) {
        holder.NameTxt.setText(ProdNames.get(position));
        holder.PriceTxt.setText(String.valueOf(ProdPrices.get(position)));
        holder.QuantityTxt.setText(String.valueOf(ProdQuantities.get(position)));
        Quantity=Integer.parseInt(holder.QuantityTxt.getText().toString());
        holder.TotalPriceTxt.setText(String.valueOf(Float.parseFloat(holder.PriceTxt.getText().toString())*
                Integer.parseInt(holder.QuantityTxt.getText().toString())));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Quantity < totalQuantity.get(position)) {
                    Quantity += 1;
                    holder.QuantityTxt.setText(String.valueOf(Quantity));
                    holder.TotalPriceTxt.setText(String.valueOf(Float.parseFloat(holder.PriceTxt.getText().toString())*
                            Integer.parseInt(holder.QuantityTxt.getText().toString())));
                    MainActivity.cartBundle.get(position).putInt("ProdQuantity",Quantity);
                    MainActivity.cartBundle.get(position).putInt("TotalQuantity",totalQuantity.get(position)-Quantity);
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quantity > 1) {
                    Quantity -= 1;
                    holder.QuantityTxt.setText(String.valueOf(Quantity));
                    holder.TotalPriceTxt.setText(String.valueOf(Float.parseFloat(holder.PriceTxt.getText().toString())*
                            Integer.parseInt(holder.QuantityTxt.getText().toString())));
                    MainActivity.cartBundle.get(position).putInt("ProdQuantity",Quantity);
                    MainActivity.cartBundle.get(position).putInt("TotalQuantity",totalQuantity.get(position)-Quantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return MainActivity.cartBundle.size();
    }

    public class Recycler_ViewHolder extends RecyclerView.ViewHolder{
        TextView NameTxt , PriceTxt , QuantityTxt , TotalPriceTxt;
        ConstraintLayout mainLayout;
        ImageButton add,remove;
        public Recycler_ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTxt=itemView.findViewById(R.id.Cart_prodNameTxt);
            PriceTxt=itemView.findViewById(R.id.Cart_prodPriceTxt);
            add=itemView.findViewById(R.id.Cart_addBtn);
            remove=itemView.findViewById(R.id.Cart_removeBtn);
            TotalPriceTxt=itemView.findViewById(R.id.totalPrice);
            QuantityTxt=itemView.findViewById(R.id.Cart_prodQuantityTxt);
            mainLayout=itemView.findViewById(R.id.cartCellLayout);
        }
    }
}
