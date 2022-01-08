package com.example.shopondoor;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.media.MediaBrowserServiceCompat.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopondoor.activities.OrderPlacedActivity;
import com.example.shopondoor.adapters.MyCartAdapter;
import com.example.shopondoor.models.MyCartModel;
import com.example.shopondoor.models.RecomendedModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MyCartFragment extends Fragment {


    private static final String TAG = "Tag";
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> myCartModelList;
    TextView totalAmount, storeClosed;
    TextView totalDisAmount;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    Button buyNow;
    String openStatus;
    String discountedPrice;
    Double discount = 0.0;
    List<Address> addresses;
    FusedLocationProviderClient fusedLocationProviderClient;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storeClosed = root.findViewById(R.id.storeClosed);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        database.getReference().child("Admin").child("AiS7EsAzP2dgMUp8yjtNowBr6yn1")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        openStatus = snapshot.child("openStatus").getValue().toString();
                        if (openStatus.equals("Open")) {
                            buyNow.setVisibility(View.VISIBLE);
                            storeClosed.setVisibility(View.GONE);
                        } else {
                            buyNow.setVisibility(View.GONE);
                            storeClosed.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        progressBar = root.findViewById(R.id.progressbar_cart);
        progressBar.setVisibility(View.GONE);

        constraintLayout = root.findViewById(R.id.constraint1);
        constraintLayout.setVisibility(View.VISIBLE);

        buyNow = root.findViewById(R.id.buyNow);

        recyclerView = root.findViewById(R.id.cart_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Total Amount
        totalAmount = root.findViewById(R.id.product_total_price_fragment);
        totalDisAmount = root.findViewById(R.id.product_total_dis_price_fragment);

        myCartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(), myCartModelList);
        recyclerView.setAdapter(myCartAdapter);

        db.collection("Discount").document("J3YBD9f1L0nBeI9Fr0s1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    discount = documentSnapshot.getDouble("discountInt");
                }
            }
        });

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentId = documentSnapshot.getId();

                        MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                        myCartModelList.add(myCartModel);
                        myCartAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        constraintLayout.setVisibility(View.GONE);
                    }

                    Collections.sort(myCartModelList, new Comparator<MyCartModel>() {
                        @Override
                        public int compare(MyCartModel o1, MyCartModel o2) {
                            return o1.getProductName().compareTo(o2.getProductName());
                        }
                    });

                    calculateTotalAmount(myCartModelList);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(myCartModelList.size()>0){
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        getLocation();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Cart is Empty", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return root;
    }



    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){

                    try {
                        Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault()
                        );
                        addresses=geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("address").setValue(addresses.get(0).getAddressLine(0));
                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("city").setValue(addresses.get(0).getSubAdminArea());
                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("lat").setValue(addresses.get(0).getLatitude());
                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("lon").setValue(addresses.get(0).getLongitude());

                        final String[] status={addresses.get(0).getAddressLine(0)+" "+addresses.get(0).getSubAdminArea(),"Other Address"};
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setTitle("Confirm Address");


                        EditText AddLocal=new EditText(getActivity());
                        AddLocal.setInputType(InputType.TYPE_CLASS_TEXT);
                        builder.setView(AddLocal);
                        AddLocal.setVisibility(View.VISIBLE);
                        AddLocal.setHint("Enter Locality");

                        builder.setSingleChoiceItems(status, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    AddLocal.setHint("Enter Locality");
                                }
                                else if(which==1){
                                    AddLocal.setHint("Enter Complete Address");
                                }

                            }
                        });
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String myText = AddLocal.getText().toString();

                                    database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("locality").setValue(myText);

                                    Intent intent=new Intent(getContext(), OrderPlacedActivity.class);
                                    intent.putExtra("itemList",(Serializable) myCartModelList);
                                    startActivity(intent);

                                    dialog.dismiss();

                                    db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                            .collection("AddToCart").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                                                        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                                                .collection("AddToCart").document(snapshot.getId()).delete();
                                                    }
                                                }
                                            });
                                    database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("discountedPrice").setValue("0");
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {

        double totalAmountCart=0.0;
        for(MyCartModel myCartModel : myCartModelList){
            totalAmountCart += myCartModel.getTotalPrice();
        }
        totalAmount.setText(""+totalAmountCart);
        totalAmount.setPaintFlags(totalAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        database.getReference().child("Users").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        discountedPrice = snapshot.child("discountedPrice").getValue().toString();
                        totalDisAmount.setText(" "+ discountedPrice);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}