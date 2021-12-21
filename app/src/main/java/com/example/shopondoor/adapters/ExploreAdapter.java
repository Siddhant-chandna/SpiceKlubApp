package com.example.shopondoor.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.activities.ViewAllActivity;
import com.example.shopondoor.models.ExploreModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    Context context;
    List<ExploreModel> catagoryList;

    public ExploreAdapter(Context context, List<ExploreModel> catagoryList) {
        this.context = context;
        this.catagoryList = catagoryList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cat_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Glide.with(context).load(catagoryList.get(position).getImg_url()).into(holder.catImg);
        holder.name.setText(catagoryList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ViewAllActivity.class);
                intent.putExtra("type",catagoryList.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catagoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImg;
        TextView name;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            catImg=itemView.findViewById(R.id.cat_home_img);
            name=itemView.findViewById(R.id.cat_home_name);
        }
    }
}
