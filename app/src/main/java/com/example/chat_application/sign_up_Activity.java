package com.example.chat_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chat_application.databinding.ActivitySignUpBinding;
import com.example.chat_application.helper.params;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up_Activity extends AppCompatActivity {

ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    ProgressDialog pgd;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot());
        // Set the dimensions of the sign-in button.






        mAuth = FirebaseAuth.getInstance();//inatialized an firebase instance
        db=FirebaseDatabase.getInstance();
        pgd=new ProgressDialog(sign_up_Activity.this);
        pgd.setTitle("Creating Account..");
        pgd.setMessage("Your account is being created..");
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(sign_up_Activity.this,MainActivity.class));
        }

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!binding.editTextTextPersonName2.getText().toString().isEmpty() && !binding.editTextTextPassword2.getText().toString().isEmpty() && !binding.editTextTextEmailAddress2.getText().toString().isEmpty()){
                    pgd.show();
                    mAuth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress2.getText().toString(),binding.editTextTextPassword2.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pgd.dismiss();
                                    if(task.isSuccessful()){

//                                        params p=new params(binding.editTextTextPersonName2.getText().toString(),binding.editTextTextEmailAddress2.getText().toString(),binding.editTextTextPassword2.getText().toString());
//                                        String uId=task.getResult().getUser().getUid();
//                                        db.getReference().child("params").child(uId).setValue(p);
                                         params userInf=new params(binding.editTextTextPersonName2.getText().toString(),binding.editTextTextEmailAddress2.getText().toString(),binding.editTextTextPassword2.getText().toString());
                                         String uId=task.getResult().getUser().getUid();
                                        Toast.makeText(sign_up_Activity.this, ""+uId, Toast.LENGTH_SHORT).show();
                                        db.getReference().child("Users").child(uId).setValue(userInf);
                                        Toast.makeText(sign_up_Activity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(sign_up_Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(sign_up_Activity.this, "Please fill all informaion!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_up_Activity.this,sign_in_Activity.class));
            }
        });
    }




    }
