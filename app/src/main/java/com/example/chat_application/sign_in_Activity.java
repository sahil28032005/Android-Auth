package com.example.chat_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.chat_application.databinding.ActivitySignInBinding;
import com.example.chat_application.helper.params;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class sign_in_Activity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    ProgressDialog pgd;
    FirebaseDatabase db;
    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        pgd = new ProgressDialog(sign_in_Activity.this);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        pgd.setTitle("Validating Resources....");
        pgd.setMessage("Signing You in.....");
        setContentView(binding.getRoot());

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button3:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.editTextTextEmailAddress2.getText().toString().isEmpty() && !binding.editTextTextPassword2.getText().toString().isEmpty()) {
                    pgd.show();
                    mAuth.signInWithEmailAndPassword(binding.editTextTextEmailAddress2.getText().toString(), binding.editTextTextPassword2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pgd.dismiss();
                                Toast.makeText(sign_in_Activity.this, "Welcome " + binding.editTextTextEmailAddress2.getText().toString(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(sign_in_Activity.this, MainActivity.class));
                            } else {
                                Toast.makeText(sign_in_Activity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(sign_in_Activity.this, "Please fill Whole information..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sign_in_Activity.this, sign_up_Activity.class));
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 65);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 65) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "" + account.getIdToken(), Toast.LENGTH_SHORT).show();
            authWithGoogle(account.getIdToken());

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            e.printStackTrace();
        }

    }

    private void authWithGoogle(String idToken) {

        AuthCredential credeantial= GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credeantial).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(sign_in_Activity.this, "staged", Toast.LENGTH_SHORT).show();
                    FirebaseUser fbu=mAuth.getCurrentUser();//method doesn't conclude any sign-in function on retrieves current user
                    params p=new params();
                    p.setUserId(fbu.getUid());
                    p.setUserName(fbu.getDisplayName());
                    p.setProfilePic(fbu.getPhotoUrl().toString());
                    db.getReference().child("Users").child(fbu.getUid()).setValue(p);
                    startActivity(new Intent(sign_in_Activity.this,MainActivity.class));
                }
                else{

                }
            }
        });
    }
}
