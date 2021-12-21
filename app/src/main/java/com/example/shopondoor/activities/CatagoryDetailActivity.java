package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.shopondoor.R;
import com.example.shopondoor.adapters.CatDetailAdapter;
import com.example.shopondoor.adapters.CatagoryAdapter;
import com.example.shopondoor.models.CatDetailModel;
import com.example.shopondoor.models.CatagoryModel;
import com.example.shopondoor.models.PopularModel;
import com.example.shopondoor.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CatagoryDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CatDetailModel> catDetailModelList;
    CatDetailAdapter catDetailAdapter;
    FirebaseFirestore db;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagory_detail);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db=FirebaseFirestore.getInstance();
        String type=getIntent().getStringExtra("type");
        recyclerView=findViewById(R.id.cat_detail_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        catDetailModelList=new ArrayList<>();
        catDetailAdapter=new CatDetailAdapter(this,catDetailModelList);
        recyclerView.setAdapter(catDetailAdapter);

        // SoftDrink
        if(type!= null && type.equalsIgnoreCase("softdrink")){
            db.collection("CatagoryDeatils").whereEqualTo("type","softdrink").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        CatDetailModel catDetailModel=documentSnapshot.toObject(CatDetailModel.class);
                        catDetailModelList.add(catDetailModel);
                        catDetailAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // meat
        if(type!= null && type.equalsIgnoreCase("meat")){
            db.collection("CatagoryDeatils").whereEqualTo("type","meat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        CatDetailModel catDetailModel=documentSnapshot.toObject(CatDetailModel.class);
                        catDetailModelList.add(catDetailModel);
                        catDetailAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        //breakfast
        if(type!= null && type.equalsIgnoreCase("breakfast")){
            db.collection("CatagoryDeatils").whereEqualTo("type","breakfast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        CatDetailModel catDetailModel=documentSnapshot.toObject(CatDetailModel.class);
                        catDetailModelList.add(catDetailModel);
                        catDetailAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        // icecream
        if(type!= null && type.equalsIgnoreCase("icecream")){
            db.collection("CatagoryDeatils").whereEqualTo("type","icecream").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        CatDetailModel catDetailModel=documentSnapshot.toObject(CatDetailModel.class);
                        catDetailModelList.add(catDetailModel);
                        catDetailAdapter.notifyDataSetChanged();
                    }
                }
            });
        }


    }
}