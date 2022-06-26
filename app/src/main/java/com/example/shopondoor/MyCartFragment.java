package com.example.shopondoor;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopondoor.activities.LogInActivity;
import com.example.shopondoor.activities.OrderPlacedActivity;
import com.example.shopondoor.adapters.MyCartAdapter;
import com.example.shopondoor.models.MyCartModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyCartFragment extends Fragment  {


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
    double distRad=0;


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
        Checkout.preload(getActivity());

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
                getFragmentManager().beginTransaction().detach(MyCartFragment.this).attach(MyCartFragment.this).commit();
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

                        LinearLayout layout = new LinearLayout(getContext());
                        layout.setOrientation(LinearLayout.VERTICAL);

                        final EditText AddLocation=new EditText(getActivity());
                        AddLocation.setInputType(InputType.TYPE_CLASS_TEXT);
                        layout.addView(AddLocation);
                        AddLocation.setVisibility(View.VISIBLE);
                        AddLocation.setHint("Add Locality");
                        final EditText AddLocal2=new EditText(getActivity());
                        AddLocal2.setInputType(InputType.TYPE_CLASS_TEXT);
                        layout.addView(AddLocal2);
                        AddLocal2.setVisibility(View.GONE);

                        builder.setView(layout);

                        builder.setSingleChoiceItems(status, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    AddLocation.setHint("Enter Locality");
                                    AddLocal2.setVisibility(View.GONE);
                                }
                                else if(which==1){
                                    AddLocal2.setVisibility(View.VISIBLE);
                                    AddLocation.setHint("Enter Complete Address");
                                    AddLocal2.setHint("Enter City");
                                }

                            }
                        });
                        builder.setPositiveButton("Confirm to Payment", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String myText = AddLocation.getText().toString();
                                String myCity = AddLocal2.getText().toString();

                                    database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("locality").setValue(myText);
                                    double lat2=(addresses.get(0).getLatitude());
                                    double lon2=(addresses.get(0).getLongitude());

//                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                                List<Address> addresses = null;
//                                try {
//                                    addresses = geocoder.getFromLocationName(myCity, 1);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                if(addresses.size() > 0) {
//                                    double latitude= addresses.get(0).getLatitude();
//                                    double longitude= addresses.get(0).getLongitude();
//                                }

//                                23.374039,85.331263
                                //29.969513,76.878281
                                    distRad=getDistance(23.809839,86.355209,lat2,lon2);
                                    if(distRad<=5){

                                        database.getReference().child("Users").child(auth.getCurrentUser().getUid())
                                                .addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String amount = snapshot.child("discountedPrice").getValue().toString();
                                                        String name = snapshot.child("name").getValue().toString();
                                                        String email = snapshot.child("email").getValue().toString();
                                                        String phone=snapshot.child("phone").getValue().toString();
                                                        String city=snapshot.child("city").getValue().toString();
                                                        PaymentMethod(amount,name,email,city,phone);


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Sorry! We deliver only within 5Km radius of Our Store", Toast.LENGTH_SHORT).show();
                                    }

                                    dialog.dismiss();

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

    private void PaymentMethod(String amount,String name,String email,String city,String phone) {
        final Activity activity=getActivity();
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_CB5l3lYsWbEQzk");
        // rzp_test_BRyGdm2LMgSoUi
        checkout.setImage(R.drawable.ic_baseline_person_24);


        double finalAmount = Float.parseFloat(amount)*100;

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAmount+"");//300 X 100
            options.put("prefill.contact", phone);
            options.put("prefill.email", email);
            options.put("city",city);


            checkout.open(getActivity(), options);
            getFragmentManager().beginTransaction().detach(MyCartFragment.this).attach(MyCartFragment.this).commit();

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }


    private void calculateTotalAmount(List<MyCartModel> myCartModelList) {
        getFragmentManager().beginTransaction().detach(MyCartFragment.this).attach(MyCartFragment.this).commit();
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


    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}