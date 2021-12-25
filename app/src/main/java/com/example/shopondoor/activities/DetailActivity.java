package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.shopondoor.R;
import com.example.shopondoor.models.CatDetailModel;
import com.example.shopondoor.models.NewProductModel;
import com.example.shopondoor.models.RecomendedModel;
import com.example.shopondoor.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

public class DetailActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView price,name,description,discount;
    Button addtoCart;
    ImageView addItem,removerItem;
    Toolbar toolbar;

    ViewAllModel viewAllModel = null;
    CatDetailModel catDetailModel=null;
    RecomendedModel recomendedModel=null;
    NewProductModel newProductModel=null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    TextView quantity;
    int priceint;
    int totalQuantity=0;
    int totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        final Object object1=getIntent().getSerializableExtra("detail");
        final Object object2=getIntent().getSerializableExtra("det");
        final Object object3=getIntent().getSerializableExtra("d");
        final Object object4=getIntent().getSerializableExtra("newProductdetail");
        if(object1 instanceof ViewAllModel){
            viewAllModel=(ViewAllModel) object1;
        }
        if(object2 instanceof CatDetailModel){
            catDetailModel=(CatDetailModel) object2;
        }
        if(object3 instanceof RecomendedModel){
            recomendedModel=(RecomendedModel) object3;
        }
        if(object4 instanceof NewProductModel){
            newProductModel=(NewProductModel) object4;
        }

        quantity=findViewById(R.id.deatails_quantity);

        detailedImg=findViewById(R.id.details_img);
        addItem=findViewById(R.id.details_additem);
        removerItem=findViewById(R.id.details_removeitem);
        price=findViewById(R.id.details_price);
        name=findViewById(R.id.details_name);
        description=findViewById(R.id.details_des);
        discount=findViewById(R.id.details_discount);

        if(viewAllModel!=null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            name.setText(viewAllModel.getName());
            description.setText(viewAllModel.getDescription());
            price.setText(viewAllModel.getPrice());
            priceint=viewAllModel.getPriceint();

        }
        else if(catDetailModel!=null){
            Glide.with(getApplicationContext()).load(catDetailModel.getImg_url()).into(detailedImg);
            name.setText(catDetailModel.getName());
            description.setText(catDetailModel.getDescription());
            price.setText(catDetailModel.getPrice());
            priceint=catDetailModel.getPriceint();

        }
        else if(recomendedModel!=null){
            Glide.with(getApplicationContext()).load(recomendedModel.getImg_url()).into(detailedImg);
            name.setText(recomendedModel.getName());
            description.setText(recomendedModel.getDescription());
            price.setText(recomendedModel.getPrice());
            priceint=recomendedModel.getPriceint();

        }
        else if(newProductModel!=null){
            Glide.with(getApplicationContext()).load(newProductModel.getImage_url()).into(detailedImg);
            name.setText(newProductModel.getName());
            description.setText(newProductModel.getDescription());
            price.setText(newProductModel.getPrice());
            priceint=newProductModel.getPriceint();

        }

        firestore.collection("Discount").document("J3YBD9f1L0nBeI9Fr0s1")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    discount.setText(documentSnapshot.getString("discount"));
                }
            }
        });


        addtoCart=findViewById(R.id.add_to_cart);
        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity>0) {
                    addedtoCart();
                }
                else
                    Toast.makeText(DetailActivity.this, "Select Some Quantity", Toast.LENGTH_SHORT).show();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity<10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=priceint*totalQuantity;
                }
            }
        });

        removerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity>0){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice=priceint*totalQuantity;
                }
            }
        });
    }

    private void addedtoCart() {
        String saveCureentDate,saveCurrentTime;
        Calendar calForDate= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MM dd,yyyy");
        saveCureentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap=new HashMap<>();

        if(viewAllModel!=null) {
            cartMap.put("productName", viewAllModel.getName());
            cartMap.put("productImage", viewAllModel.getImg_url());
            cartMap.put("productPriceint", priceint);
            cartMap.put("productPrice", viewAllModel.getPrice());
            cartMap.put("currentDate", saveCureentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);
        }
        else if(catDetailModel!=null){
            cartMap.put("productName", catDetailModel.getName());
            cartMap.put("productImage", catDetailModel.getImg_url());
            cartMap.put("productPriceint", priceint);
            cartMap.put("productPrice", catDetailModel.getPrice());
            cartMap.put("currentDate", saveCureentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);
        }
        else if(recomendedModel!=null){
            cartMap.put("productName", recomendedModel.getName());
            cartMap.put("productImage", recomendedModel.getImg_url());
            cartMap.put("productPriceint", priceint);
            cartMap.put("productPrice", recomendedModel.getPrice());
            cartMap.put("currentDate", saveCureentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);
        }
        else if(newProductModel!=null){
            cartMap.put("productName", newProductModel.getName());
            cartMap.put("productImage", newProductModel.getImage_url());
            cartMap.put("productPriceint", priceint);
            cartMap.put("productPrice", newProductModel.getPrice());
            cartMap.put("currentDate", saveCureentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);
        }

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                Toast.makeText(DetailActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}