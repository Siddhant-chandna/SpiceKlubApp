package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shopondoor.R;
import com.example.shopondoor.models.MyCartModel;
import com.example.shopondoor.models.ViewOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OrderPlacedActivity extends AppCompatActivity {

    private static final String TAG = "Tag";
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseDatabase database;
    Toolbar toolbar;
    String orderStatus="Ordered";
    String address;
    String locality;
    String city;
    String phone;
    String name;
    String documentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

            auth=FirebaseAuth.getInstance();
            firestore=FirebaseFirestore.getInstance();
            database = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        address = snapshot.child("address").getValue().toString();
                        city = snapshot.child("city").getValue().toString();
                        locality = snapshot.child("locality").getValue().toString();
                        name = snapshot.child("name").getValue().toString();
                        phone = snapshot.child("phone").getValue().toString();
                        AddOrder();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        }



        public void AddOrder(){
            List<MyCartModel> myCartModelList=(ArrayList<MyCartModel>)getIntent().getSerializableExtra("itemList");
            if(myCartModelList==null){
                Toast.makeText(OrderPlacedActivity.this, "NULLLLLLLLLLLLLLLLLLLLLLLLLL", Toast.LENGTH_SHORT).show();
            }
            if (myCartModelList != null && myCartModelList.size() > 0) {
                String saveCureentDate,saveCurrentTime;
                Calendar calForDate= Calendar.getInstance();

                SimpleDateFormat currentDate=new SimpleDateFormat("yyyy/MM/dd kk:mm:ss a");
                saveCureentDate=currentDate.format(calForDate.getTime());

//                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
//                saveCurrentTime=currentTime.format(calForDate.getTime());


                for(MyCartModel model:myCartModelList){
                    final HashMap<String, Object> cartMap = new HashMap<>();

                    cartMap.put("productName", model.getProductName());
                    cartMap.put("productImage", model.getProductImage());
                    cartMap.put("productPriceint", model.getProductPriceint());
                    cartMap.put("productPrice", model.getProductPrice());
                    cartMap.put("totalQuantity", model.getTotalQuantity());
                    cartMap.put("totalPrice", model.getTotalPrice());
                    cartMap.put("totaldiscountPrice",model.getTotaldiscountPrice());
                    cartMap.put("currentDate", saveCureentDate);
//                    cartMap.put("currentTime", saveCurrentTime);
                    cartMap.put("orderStatus", orderStatus);
                    cartMap.put("address", address);
                    cartMap.put("city",city);
                    cartMap.put("locality", locality);
                    cartMap.put("phone",phone);
                    cartMap.put("name",name);
                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("MyOrder").add(cartMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("orderId").setValue(documentReference.getId()+"(ord)");
                            database.getReference().child("Admin").child("AiS7EsAzP2dgMUp8yjtNowBr6yn1").child("orderId").setValue(documentReference.getId()+"(ord)");
                            Toast.makeText(OrderPlacedActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                    }
                myCartModelList.clear();
            }
        }
    }


