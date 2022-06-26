package com.example.shopondoor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.activities.CatagoryDetailActivity;
import com.example.shopondoor.models.CatagoryModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.ViewHolder> {

    Context context;
    List<CatagoryModel> catagoryModellist;
    FirebaseFirestore db;

    public CatagoryAdapter(Context context, List<CatagoryModel> catagoryModellist) {
        this.context = context;
        this.catagoryModellist = catagoryModellist;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_cat_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        db=FirebaseFirestore.getInstance();

        Glide.with(context).load(catagoryModellist.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(catagoryModellist.get(position).getName());
        holder.description.setText(catagoryModellist.get(position).getDescription());


        db.collection("Discount").document("J3YBD9f1L0nBeI9Fr0s1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    holder.discount.setText(documentSnapshot.getString("discount"));
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CatagoryDetailActivity.class);
                intent.putExtra("type",catagoryModellist.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catagoryModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,discount;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cat_nav_img);
            name=itemView.findViewById(R.id.cat_nav_name);
            description=itemView.findViewById(R.id.cat_nav_des);
            discount=itemView.findViewById(R.id.cat_nav_discount);
        }
    }

    public static class MyCartAdapter {
    }
}
