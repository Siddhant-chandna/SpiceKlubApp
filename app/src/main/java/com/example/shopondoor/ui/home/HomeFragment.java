package com.example.shopondoor.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopondoor.R;
import com.example.shopondoor.adapters.BannerAdapter;
import com.example.shopondoor.adapters.CatDetailAdapter;
import com.example.shopondoor.adapters.ExploreAdapter;
import com.example.shopondoor.adapters.PopularAdapter;
import com.example.shopondoor.adapters.RecomendedAdapter;
import com.example.shopondoor.adapters.ViewAllAdapter;
import com.example.shopondoor.models.BannerModel;
import com.example.shopondoor.models.CatDetailModel;
import com.example.shopondoor.models.ExploreModel;
import com.example.shopondoor.models.PopularModel;
import com.example.shopondoor.models.RecomendedModel;
import com.example.shopondoor.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView bannerRec,exploreRec,recomendedRec,popularRec;
    FirebaseFirestore db;

    // Search View
    EditText search_box;
    private List<CatDetailModel> catDetailModelList;
    private RecyclerView recyclerViewSearch;
    private CatDetailAdapter catDetailAdapter;

    List<BannerModel> bannerModelList;
    com.example.shopondoor.adapters.BannerAdapter BannerAdapter;

    //Popular Items
    List<PopularModel> popularModelList;
    PopularAdapter popularAdapter;

    //Explore Items
    List<ExploreModel> exploreModelList;
    ExploreAdapter exploreAdapter;

    //Recomended Items
    List<RecomendedModel> recomendedModelList;
    RecomendedAdapter recomendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_home,container,false);
        db=FirebaseFirestore.getInstance();

        bannerRec=root.findViewById(R.id.banner_rec);
        popularRec=root.findViewById(R.id.popular_rec);
        exploreRec=root.findViewById(R.id.explore_rec);
        recomendedRec=root.findViewById(R.id.recomended_rec);
        scrollView=root.findViewById(R.id.scrollView);
        progressBar=root.findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        // Popular Items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList=new ArrayList<>();
        popularAdapter=new PopularAdapter(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapter);

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel=document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Banner Items
        bannerRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        bannerModelList=new ArrayList<>();
        BannerAdapter=new BannerAdapter(getActivity(),bannerModelList);
        bannerRec.setAdapter(BannerAdapter);

        db.collection("Banner")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               BannerModel bannerModel=document.toObject(BannerModel.class);
                               bannerModelList.add(bannerModel);
                               BannerAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Explore Items
        exploreRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        exploreModelList=new ArrayList<>();
        exploreAdapter=new ExploreAdapter(getActivity(),exploreModelList);
        exploreRec.setAdapter(exploreAdapter);

        db.collection("ExploreProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ExploreModel exploreModel=document.toObject(ExploreModel.class);
                                exploreModelList.add(exploreModel);
                                exploreAdapter.notifyDataSetChanged();
                            }
                            Collections.sort(exploreModelList, new Comparator<ExploreModel>() {
                                @Override
                                public int compare(ExploreModel o1, ExploreModel o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Recomended Items
        recomendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recomendedModelList=new ArrayList<>();
        recomendedAdapter=new RecomendedAdapter(getActivity(),recomendedModelList);
        recomendedRec.setAdapter(recomendedAdapter);

        db.collection("RecomendedProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecomendedModel recomendedModel=document.toObject(RecomendedModel.class);
                                recomendedModelList.add(recomendedModel);
                                recomendedAdapter.notifyDataSetChanged();
                            }
                            Collections.sort(recomendedModelList, new Comparator<RecomendedModel>() {
                                @Override
                                public int compare(RecomendedModel o1, RecomendedModel o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Error:"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // Search Items
        recyclerViewSearch=root.findViewById(R.id.search_rec);
        search_box=root.findViewById(R.id.search_box);
        catDetailModelList=new ArrayList<>();
        catDetailAdapter=new CatDetailAdapter(getContext(),catDetailModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(catDetailAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    catDetailModelList.clear();
                    catDetailAdapter.notifyDataSetChanged();
                }
                else{
                    searchProduct(s.toString());
                }
            }
        });

        return root;
    }



    private void searchProduct(String typedetail) {
        if(!typedetail.isEmpty() && typedetail.equalsIgnoreCase(typedetail)){
            db.collection("CatagoryDeatils").whereEqualTo("typedetail",typedetail).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful() && task.getResult() != null){
                                catDetailModelList.clear();
                                catDetailAdapter.notifyDataSetChanged();
                                for(DocumentSnapshot doc : task.getResult().getDocuments()){
                                    CatDetailModel catDetailModel=doc.toObject(CatDetailModel.class);
                                    catDetailModelList.add(catDetailModel);
                                    catDetailAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}