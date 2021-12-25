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
import com.example.shopondoor.activities.CatagoryDetailActivity;
import com.example.shopondoor.activities.DetailActivity;
import com.example.shopondoor.models.CatagoryModel;
import com.example.shopondoor.models.NewProductModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    Context context;
    List<NewProductModel> newProductModellist;
    FirebaseFirestore db;

    public NewProductAdapter(Context context, List<NewProductModel> newProductModellist) {
        this.context = context;
        this.newProductModellist = newProductModellist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_roduct_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        db=FirebaseFirestore.getInstance();

        Glide.with(context).load(newProductModellist.get(position).getImage_url()).into(holder.imageView);
        holder.name.setText(newProductModellist.get(position).getName());
        holder.description.setText(newProductModellist.get(position).getDescription());
        holder.price.setText(newProductModellist.get(position).getPrice());

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
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("newProductdetail",newProductModellist.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newProductModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,description,discount,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.new_prod_img);
            name=itemView.findViewById(R.id.new_prod_name);
            description=itemView.findViewById(R.id.new_prod_des);
            discount=itemView.findViewById(R.id.new_prod_discount);
            price=itemView.findViewById(R.id.new_prod_price);
        }
    }
}
