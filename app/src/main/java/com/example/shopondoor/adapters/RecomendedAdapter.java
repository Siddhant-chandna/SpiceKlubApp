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
import com.example.shopondoor.activities.DetailActivity;
import com.example.shopondoor.models.RecomendedModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecomendedAdapter extends RecyclerView.Adapter<RecomendedAdapter.ViewHolder> {

    Context context;
    List<RecomendedModel> recomendedModelList;

    public RecomendedAdapter(Context context, List<RecomendedModel> recomendedModelList) {
        this.context = context;
        this.recomendedModelList = recomendedModelList;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RecomendedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recomended_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Glide.with(context).load(recomendedModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(recomendedModelList.get(position).getName());
        holder.rating.setText(recomendedModelList.get(position).getRating());
        holder.description.setText(recomendedModelList.get(position).getDescription());
        holder.price.setText(recomendedModelList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("d",recomendedModelList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recomendedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price,description,rating;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.rec_img);
            name=itemView.findViewById(R.id.rec_name);
            description=itemView.findViewById(R.id.rec_desc);
            rating=itemView.findViewById(R.id.rec_rating);
            price=itemView.findViewById(R.id.rec_price);
        }
    }
}
