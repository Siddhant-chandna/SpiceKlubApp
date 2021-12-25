package com.example.shopondoor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopondoor.activities.OrderPlacedActivity;
import com.example.shopondoor.adapters.MyCartAdapter;
import com.example.shopondoor.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> myCartModelList;
    TextView totalAmount;
    TextView totalDisAmount;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    Button buyNow;
    Double discount = 0.0;


    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        progressBar= root.findViewById(R.id.progressbar_cart);
        progressBar.setVisibility(View.GONE);

        constraintLayout=root.findViewById(R.id.constraint1);
        constraintLayout.setVisibility(View.VISIBLE);

        buyNow=root.findViewById(R.id.buyNow);

        recyclerView=root.findViewById(R.id.cart_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Total Amount
        totalAmount=root.findViewById(R.id.product_total_price_fragment);
        totalDisAmount=root.findViewById(R.id.product_total_dis_price_fragment);

        myCartModelList=new ArrayList<>();
        myCartAdapter=new MyCartAdapter(getActivity(),myCartModelList);
        recyclerView.setAdapter(myCartAdapter);

        db.collection("Discount").document("J3YBD9f1L0nBeI9Fr0s1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    discount=documentSnapshot.getDouble("discountInt");
                }
            }
        });

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){

                        String documentId=documentSnapshot.getId();

                        MyCartModel myCartModel=documentSnapshot.toObject(MyCartModel.class);
                            myCartModel.setDocumentId(documentId);
                            myCartModelList.add(myCartModel);
                            myCartAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            constraintLayout.setVisibility(View.GONE);
                    }

                    calculateTotalAmount(myCartModelList);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });



        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), OrderPlacedActivity.class);
                intent.putExtra("itemList",(Serializable) myCartModelList);
                startActivity(intent );
            }
        });

        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {



        double totalAmountCart=0.0,totaldiscountAmount=0.0;
        for(MyCartModel myCartModel : myCartModelList){
            totalAmountCart += myCartModel.getTotalPrice();
        }
        totalAmount.setText(""+totalAmountCart);
        totaldiscountAmount=totalAmountCart-(totalAmountCart*discount);
        totalDisAmount.setText(" "+ totaldiscountAmount);
    }

}