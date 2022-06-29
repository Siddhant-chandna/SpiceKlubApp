package com.example.shopondoor;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopondoor.activities.LogInActivity;
import com.example.shopondoor.activities.OrderPlacedActivity;
import com.example.shopondoor.adapters.MyCartAdapter;
import com.example.shopondoor.databinding.FragmentCatagoryBinding;
import com.example.shopondoor.models.MyCartModel;
import com.example.shopondoor.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.gms.wallet.callback.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopondoor.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    List<MyCartModel> myCartModelList;
    String Food="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myCartModelList=new ArrayList<>();

//        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
//                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//
//                        String documentId = documentSnapshot.getId();
//
//                        MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
//                        myCartModel.setDocumentId(documentId);
//                        myCartModelList.add(myCartModel);
//                    }
//                }
//            }
//        });

        String Uid=auth.getCurrentUser().getUid().toString();
        Uid.trim();
        reference=database.getInstance().getReference().child("Users").child(Uid);
        reference.addValueEventListener(new ValueEventListener() {
            private String TAG;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Oid=snapshot.child("orderId").getValue().toString();
                String Name=snapshot.child("name").getValue().toString();
//                String Oid="SSS";
                Oid.trim();
                String A1="(ord)";
                String A2="(coo)";
                String A3="(rea)";
                String A4="(del)";
                if(Oid.length()>5){
                   String Id=Oid.substring(Oid.length() - 5);
                   String iid=Oid.substring(0,Oid.length()-5);

                   FirebaseFirestore.getInstance().collection("CurrentUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .collection("MyOrder").document(iid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           DocumentSnapshot documentSnapshot=task.getResult();

                           if(documentSnapshot.exists()){
                               Food=documentSnapshot.getString("productName");
                               if(Id.equals(A1)){
                                   notification1(Name,Food);
                                   database.getInstance().getReference().child("Users").child(Uid).child("orderId").setValue("Done");
                               }
                               else if(Id.equals(A2)){
                                   notification2(Name);
                                   database.getInstance().getReference().child("Users").child(Uid).child("orderId").setValue("Done");
                               }
                               else if(Id.equals(A3)){
                                   notification3(Name);
                                   database.getInstance().getReference().child("Users").child(Uid).child("orderId").setValue("Done");
                               }
                               else if(Id.equals(A4)){
                                   notification4(Name);
                                   database.getInstance().getReference().child("Users").child(Uid).child("orderId").setValue("Done");
                               }
                           }
                       }
                   });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_catagory, R.id.nav_profile,R.id.nav_myCart,R.id.nav_newProducts,R.id.nav_myOrders)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseDatabase database=FirebaseDatabase.getInstance();

        View headerView=navigationView.getHeaderView(0);
        TextView headerName=headerView.findViewById(R.id.nav_name);
        TextView headerEmail=headerView.findViewById(R.id.nav_email);
        CircleImageView headerImage=headerView.findViewById(R.id.nav_profile_img);


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        UserModel userModel=snapshot.getValue(UserModel.class);
                        headerName.setText(userModel.getName());
                        headerEmail.setText(userModel.getEmail());
                        Glide.with(MainActivity.this).load(userModel.getProfileImg()).into(headerImage);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });


    }

    private void notification1(String Name,String Food){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentText("Your Order for "+Food+" is Confirmed")
                .setSmallIcon(R.drawable.profile)
                .setAutoCancel(true)
                .setContentTitle("Hey, "+Name);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
    private void notification2(String Name){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentText("Your Order for "+Food+" is being Prepared")
                .setSmallIcon(R.drawable.profile)
                .setAutoCancel(true)
                .setContentTitle("Hey,"+Name);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
    private void notification3(String Name){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentText("Your Order for "+Food+" is Reaching you soon")
                .setSmallIcon(R.drawable.profile)
                .setAutoCancel(true)
                .setContentTitle("Hey,"+Name);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
    private void notification4(String Name){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                .setContentText("Your Order for "+Food+" has been Delivered")
                .setSmallIcon(R.drawable.profile)
                .setAutoCancel(true)
                .setContentTitle("Hey,"+Name);

        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout: FirebaseAuth.getInstance().signOut();
                                     startActivity(new Intent(MainActivity.this, LogInActivity.class));
                                     finish();
                                     break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

        @Override
        public void onPaymentSuccess (String s){

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
                        }
                        Intent intent = new Intent(MainActivity.this,OrderPlacedActivity.class);
                        intent.putExtra("itemList", (Serializable) myCartModelList);
                        startActivity(intent);
                        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                .collection("AddToCart").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                                                    .collection("AddToCart").document(snapshot.getId()).delete();
                                        }
                                    }
                                });

                        myCartModelList.clear();
                        database.getReference().child("Users").child(auth.getCurrentUser().getUid()).child("discountedPrice").setValue("0");
                    }
                }
            });

            }

        @Override
        public void onPaymentError ( int i, String s){

        }
}