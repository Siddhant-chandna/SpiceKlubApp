package com.example.shopondoor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.models.ViewOrderModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewHolder> {

    double discount;
    Context context;
    List<ViewOrderModel> viewOrderModellist;
    public ViewOrderAdapter(Context context,List<ViewOrderModel> viewOrderModellist) {
        this.context = context;
        this.viewOrderModellist = viewOrderModellist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewOrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_placed,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(viewOrderModellist.get(position).getProductImage()).into(holder.imageView);
        holder.name.setText(viewOrderModellist.get(position).getProductName());
        holder.price.setText(viewOrderModellist.get(position).getProductPrice());
        holder.quantity.setText(viewOrderModellist.get(position).getTotalQuantity());
        holder.time.setText(viewOrderModellist.get(position).getCurrentTime());
        holder.date.setText(viewOrderModellist.get(position).getCurrentDate());
        holder.totalPrice.setText(String.valueOf(viewOrderModellist.get(position).getTotaldiscountPrice()));
        holder.orderStatus.setText(viewOrderModellist.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return viewOrderModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price,quantity,totalPrice,time,date,orderStatus;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.order_img);
            name=itemView.findViewById(R.id.order_name);
            price=itemView.findViewById(R.id.order_price);
            quantity=itemView.findViewById(R.id.order_quantity);
            totalPrice=itemView.findViewById(R.id.order_total_price);
            time=itemView.findViewById(R.id.order_time);
            date=itemView.findViewById(R.id.order_date);
            orderStatus=itemView.findViewById(R.id.orderStatus);
        }
    }
}
