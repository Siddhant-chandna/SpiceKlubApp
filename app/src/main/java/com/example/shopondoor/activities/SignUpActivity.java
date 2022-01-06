package com.example.shopondoor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopondoor.R;
import com.example.shopondoor.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseFirestore db;
    ProgressBar  progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        signIn = findViewById(R.id.sign_in);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        database=FirebaseDatabase.getInstance();
        progressBar=findViewById(R.id.regProgress);
        progressBar.setVisibility(View.GONE);

        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createUser();
                progressBar.setVisibility(View.VISIBLE);
            }

        });
    }

        private void createUser(){
            String userName=name.getText().toString();
            String userEmail=email.getText().toString();
            String userPassword=password.getText().toString();

            if(TextUtils.isEmpty(userName)){
                Toast.makeText(this,"Name is Empty!",Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(this,"Name is Empty!",Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(userPassword)){
                Toast.makeText(this,"Password is Empty!",Toast.LENGTH_SHORT).show();
            }
            if(userPassword.length()<6){
                Toast.makeText(this,"Password is too Short,It must be greater than 6 Characters!",Toast.LENGTH_SHORT).show();
              return;
            }

//             Create User
            auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserModel userModel=new UserModel(userName,userEmail,userPassword);
                                String id=auth.getCurrentUser().getUid();
                                progressBar.setVisibility(View.GONE);
                                database.getReference().child("Users").child(id).setValue(userModel);
                                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));

                                final HashMap<String, Object> cartMap = new HashMap<>();

                                cartMap.put("Uid",auth.getCurrentUser().getUid());

                                db.collection("UserId").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<DocumentReference> task) {
                                        Toast.makeText(SignUpActivity.this, "Added", Toast.LENGTH_SHORT).show();
                                    }

                                });

                                Toast.makeText(SignUpActivity.this,"Sign Up Successful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this,"Error in Sign Up!"+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

}