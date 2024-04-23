package com.example.social_media;

import static com.example.social_media.R.id.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.social_media.Adapters.FragmentAdapter;
import com.example.social_media.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();


        //viewpager me fragments ko slides krwane ke liye we make adapter
        binding.viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        // set tablayout with viewpager
        binding.tablayout.setupWithViewPager(binding.viewpager);

    }

    // this is basically for the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        // menu basically menu folder ke ander hai with the name of menu
        // and isme jo 2nd wala menu hai vo basically parameter me jo menu hai vo hai
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }
    // now we have made the menu now we will perform the operation on it
    //inside menu their are two option ie logout and setting so in order to
    //perform operation on it we use the optionitemselected
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       int id=item.getItemId();
       if(id==R.id.settings)
       {
           Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
       }
       if(id==R.id.logout)
       {
           auth.signOut();
           Intent i=new Intent(MainActivity.this, signin.class);
           startActivity(i);
           finish();
       }
        return true;
    }
}