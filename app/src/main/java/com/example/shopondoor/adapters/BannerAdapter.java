package com.example.shopondoor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.models.BannerModel;
import com.example.shopondoor.models.PopularModel;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private Context context;
    private List<BannerModel> bannerModelList;
    FirebaseFirestore db;

    public BannerAdapter(Context context, List<BannerModel> bannerModelList) {
        this.context = context;
        this.bannerModelList = bannerModelList;
    }

    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new BannerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        db= FirebaseFirestore.getInstance();

        Glide.with(context).load(bannerModelList.get(position).getImg_url()).into(holder.popImg);
    }

    @Override
    public int getItemCount()  {
        return bannerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView popImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg=itemView.findViewById(R.id.banner_img);
        }
    }
}
