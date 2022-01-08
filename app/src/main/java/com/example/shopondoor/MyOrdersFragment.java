package com.example.shopondoor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopondoor.adapters.MyCartAdapter;
import com.example.shopondoor.adapters.ViewOrderAdapter;
import com.example.shopondoor.models.MyCartModel;
import com.example.shopondoor.models.RecomendedModel;
import com.example.shopondoor.models.ViewOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyOrdersFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView order_recyclerView;
    ViewOrderAdapter viewOrderAdapter;
    List<ViewOrderModel> viewOrderModelList;
    ConstraintLayout constraintLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        constraintLayout=root.findViewById(R.id.my_order_constraint);
        constraintLayout.setVisibility(View.VISIBLE);
        order_recyclerView=root.findViewById(R.id.order_rec);
        order_recyclerView.setVisibility(View.GONE);
        order_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        viewOrderModelList=new ArrayList<>();
        viewOrderAdapter=new ViewOrderAdapter(getActivity(),viewOrderModelList);
        order_recyclerView.setAdapter(viewOrderAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentId = documentSnapshot.getId();

                        ViewOrderModel viewOrderModel = documentSnapshot.toObject(ViewOrderModel.class);
                        viewOrderModel.setDocumentId(documentId);
                        viewOrderModelList.add(viewOrderModel);
                        viewOrderAdapter.notifyDataSetChanged();
                        order_recyclerView.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.GONE);
                    }

                    Collections.sort(viewOrderModelList);

                } else {
                    Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}