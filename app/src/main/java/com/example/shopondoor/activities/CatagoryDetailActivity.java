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

        // rolls
        if(type!= null && type.equalsIgnoreCase("rolls")){
            db.collection("CatagoryDeatils").whereEqualTo("type","rolls").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // fryrice
        if(type!= null && type.equalsIgnoreCase("fryrice")){
            db.collection("CatagoryDeatils").whereEqualTo("type","fryrice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // momos
        if(type!= null && type.equalsIgnoreCase("momos")){
            db.collection("CatagoryDeatils").whereEqualTo("type","momos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // chilly
        if(type!= null && type.equalsIgnoreCase("chilly")){
            db.collection("CatagoryDeatils").whereEqualTo("type","chilly").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // maggi
        if(type!= null && type.equalsIgnoreCase("maggi")){
            db.collection("CatagoryDeatils").whereEqualTo("type","maggi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // soup
        if(type!= null && type.equalsIgnoreCase("soup")){
            db.collection("CatagoryDeatils").whereEqualTo("type","soup").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // vegmain
        if(type!= null && type.equalsIgnoreCase("vegmain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","vegmain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // paneermain
        if(type!= null && type.equalsIgnoreCase("paneermain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","paneermain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // dalmain
        if(type!= null && type.equalsIgnoreCase("dalmain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","dalmain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // mushroommain
        if(type!= null && type.equalsIgnoreCase("mushroommain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","mushroommain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // eggmain
        if(type!= null && type.equalsIgnoreCase("eggmain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","eggmain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // chickenmain
        if(type!= null && type.equalsIgnoreCase("chickenmain")){
            db.collection("CatagoryDeatils").whereEqualTo("type","chickenmain").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // breads
        if(type!= null && type.equalsIgnoreCase("breads")){
            db.collection("CatagoryDeatils").whereEqualTo("type","breads").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // rice
        if(type!= null && type.equalsIgnoreCase("rice")){
            db.collection("CatagoryDeatils").whereEqualTo("type","rice").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // salad
        if(type!= null && type.equalsIgnoreCase("salad")){
            db.collection("CatagoryDeatils").whereEqualTo("type","salad").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // combos
        if(type!= null && type.equalsIgnoreCase("combos")){
            db.collection("CatagoryDeatils").whereEqualTo("type","combos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // extravalue
        if(type!= null && type.equalsIgnoreCase("extravalue")){
            db.collection("CatagoryDeatils").whereEqualTo("type","extravalue").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // mess
        if(type!= null && type.equalsIgnoreCase("mess")){
            db.collection("CatagoryDeatils").whereEqualTo("type","mess").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // namkeen
        if(type!= null && type.equalsIgnoreCase("namkeen")){
            db.collection("CatagoryDeatils").whereEqualTo("type","namkeen").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        // softdrink
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

        // chocolate
        if(type!= null && type.equalsIgnoreCase("chocolate")){
            db.collection("CatagoryDeatils").whereEqualTo("type","chocolate").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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