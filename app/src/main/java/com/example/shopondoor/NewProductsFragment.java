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
import android.widget.Toast;

import com.example.shopondoor.adapters.NewProductAdapter;
import com.example.shopondoor.models.NewProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewProductsFragment extends Fragment {
    RecyclerView newproductRec;
    List<NewProductModel> newProductModelList;
    NewProductAdapter newProductAdapter;
    FirebaseFirestore db;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_new_products, container, false);

        db= FirebaseFirestore.getInstance();
        newproductRec=root.findViewById(R.id.new_product_rec);
        newproductRec.setVisibility(View.GONE);

        progressBar=root.findViewById(R.id.progressbar_new);
        progressBar.setVisibility(View.VISIBLE);

        constraintLayout=root.findViewById(R.id.new_constraint1);
        constraintLayout.setVisibility(View.VISIBLE);

        //NewProductItems
        newproductRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        newProductModelList=new ArrayList<>();
        newProductAdapter=new NewProductAdapter(getActivity(),newProductModelList);
        newproductRec.setAdapter(newProductAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductModel newProductModel=document.toObject(NewProductModel.class);
                                newProductModelList.add(newProductModel);
                                newProductAdapter.notifyDataSetChanged();
                                newproductRec.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                constraintLayout.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}