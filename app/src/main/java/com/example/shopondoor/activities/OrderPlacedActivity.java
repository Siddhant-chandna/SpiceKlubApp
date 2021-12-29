package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopondoor.R;
import com.example.shopondoor.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class OrderPlacedActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

            auth=FirebaseAuth.getInstance();
            firestore=FirebaseFirestore.getInstance();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

            List<MyCartModel> myCartModelList=(ArrayList<MyCartModel>)getIntent().getSerializableExtra("itemList");
            if (myCartModelList != null && myCartModelList.size() > 0) {
                String saveCureentDate,saveCurrentTime;
                Calendar calForDate= Calendar.getInstance();

                SimpleDateFormat currentDate=new SimpleDateFormat("yyyy,MM,dd");
                saveCureentDate=currentDate.format(calForDate.getTime());

                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                saveCurrentTime=currentTime.format(calForDate.getTime());
                for (MyCartModel model : myCartModelList) {
                    final HashMap<String, Object> cartMap = new HashMap<>();

                    cartMap.put("productName", model.getProductName());
                    cartMap.put("productImage", model.getProductImage());
                    cartMap.put("productPriceint", model.getProductPriceint());
                    cartMap.put("productPrice", model.getProductPrice());
                    cartMap.put("totalQuantity", model.getTotalQuantity());
                    cartMap.put("totalPrice", model.getTotalPrice());
                    cartMap.put("currentDate", saveCureentDate);
                    cartMap.put("currentTime", saveCurrentTime);

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


