package com.example.chat_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chat_application.FragmentsAdapter.FargmentsGrasper;
import com.example.chat_application.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth=FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FargmentsGrasper(getSupportFragmentManager()));
       binding.tabbedLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf=getMenuInflater();
        inf.inflate(R.menu.menubar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.cretae_grp:
                Toast.makeText(this, "Your group is being created..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Linking_device:
                Toast.makeText(this, "Link your device...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "inside settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.log_out:
                mAuth.signOut();
                Toast.makeText(this, "You are no longer use this account......", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,sign_up_Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}