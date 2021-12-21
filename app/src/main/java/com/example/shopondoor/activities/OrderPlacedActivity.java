package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.shopondoor.R;
import com.example.shopondoor.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderPlacedActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        List<MyCartModel> myCartModelList=(ArrayList<MyCartModel>)getIntent().getSerializableExtra("itemList");

        if(myCartModelList != null  && myCartModelList.size()>0){
            for(MyCartModel model:myCartModelList){
                final HashMap<String,Object> cartMap=new HashMap<>();

                cartMap.put("productName",model.getProductName());
                cartMap.put("productImage",model.getProductImage());
                cartMap.put("productPriceint",model.getProductPriceint());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("totalQuantity",model.getTotalQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                        Toast.makeText(OrderPlacedActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}