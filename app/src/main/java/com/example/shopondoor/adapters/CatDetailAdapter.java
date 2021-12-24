package com.example.shopondoor.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.activities.DetailActivity;
import com.example.shopondoor.models.CatDetailModel;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class CatDetailAdapter extends RecyclerView.Adapter<CatDetailAdapter.ViewHolder> {

    Context context;
    List<CatDetailModel> catDetailModelList;

    public CatDetailAdapter(Context context, List<CatDetailModel> catDetailModelList) {
        this.context = context;
        this.catDetailModelList = catDetailModelList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_detail_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Glide.with(context).load(catDetailModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(catDetailModelList.get(position).getName());
        holder.price.setText(catDetailModelList.get(position).getPrice());
        holder.description.setText(catDetailModelList.get(position).getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("det",catDetailModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catDetailModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price,description;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.cat_detail_img);
            name=itemView.findViewById(R.id.cat_detail_name);
            price=itemView.findViewById(R.id.cat_detail_price);
            description=itemView.findViewById(R.id.cat_detail_des);
        }
    }
}
