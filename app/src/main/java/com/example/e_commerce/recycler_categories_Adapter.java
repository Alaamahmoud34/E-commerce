package com.example.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_categories_Adapter extends RecyclerView.Adapter<recycler_categories_Adapter.Recycler_ViewHolder> {
    Context context;
    ArrayList<String>CatNames;
    int[]Images;
    public recycler_categories_Adapter(Context context, ArrayList<String> CatNames, int[]Images)
    {
        this.context=context;
        this.CatNames=CatNames;
        this.Images=Images;
    }
    @NonNull
    @Override
    public Recycler_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_categories_row,parent,false);
        return new Recycler_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Recycler_ViewHolder holder, int position) {
        holder.NameTxt.setText(CatNames.get(position));
        holder.imageView.setImageResource(Images[position]);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                fragmentProducts products=new fragmentProducts();
                Bundle bundle=new Bundle();
                bundle.putString("CatName",holder.NameTxt.getText().toString());
                products.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,products).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Images.length;
    }

    public class Recycler_ViewHolder extends RecyclerView.ViewHolder{

        TextView NameTxt;
        ImageView imageView;
        ConstraintLayout mainLayout;
        public Recycler_ViewHolder(@NonNull View itemView) {
            super(itemView);
            NameTxt=itemView.findViewById(R.id.CatNameTxt);
            imageView=itemView.findViewById(R.id.catImage);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
