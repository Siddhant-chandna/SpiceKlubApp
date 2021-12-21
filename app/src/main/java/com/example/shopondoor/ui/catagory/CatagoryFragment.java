package com.example.shopondoor.ui.catagory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopondoor.R;
import com.example.shopondoor.adapters.CatagoryAdapter;
import com.example.shopondoor.adapters.RecomendedAdapter;
import com.example.shopondoor.models.CatagoryModel;
import com.example.shopondoor.models.RecomendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CatagoryFragment extends Fragment {

    RecyclerView catagoryRec;
    List<CatagoryModel> catagoryModelList;
    CatagoryAdapter catagoryAdapter;
    FirebaseFirestore db;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_catagory, container, false);

        db= FirebaseFirestore.getInstance();
        catagoryRec=root.findViewById(R.id.cat_rec);
        catagoryRec.setVisibility(View.GONE);

        progressBar=root.findViewById(R.id.progressbar_category);
        progressBar.setVisibility(View.VISIBLE);

        //CategoryItems
        catagoryRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        catagoryModelList=new ArrayList<>();
        catagoryAdapter=new CatagoryAdapter(getActivity(),catagoryModelList);
        catagoryRec.setAdapter(catagoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CatagoryModel catagoryModel=document.toObject(CatagoryModel.class);
                                catagoryModelList.add(catagoryModel);
                                catagoryAdapter.notifyDataSetChanged();
                                catagoryRec.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}