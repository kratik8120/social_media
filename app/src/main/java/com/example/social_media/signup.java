package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.social_media.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    // by this we dont need to write the find view by id part
ActivitySignupBinding binding;

// this is for the authentication
FirebaseAuth auth;

// this is used to store the data in the database
FirebaseDatabase database;

// this is used to add the progress dialog
    // this means we are showing the progress bar in the progress dialog
   ProgressDialog progressDialog;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressDialog=new ProgressDialog(signup.this);
        progressDialog.setTitle("creating account");
        progressDialog.setMessage("we are creating your account");

        // in this first the signup button will get clicked
        // after that it will check for the email and pass
        // ie if their is any exception or not ie error
        // whether the exception is present or not , it is checked by the Authresult
        // which is present in the parameter of the oncomplete fun

        binding.signupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we want the progress when we click on the signup btn
                progressDialog.show();
                auth.createUserWithEmailAndPassword(binding.emailtext.getText().toString(),binding.passtext.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // close the progress when the user acc is created
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            // now as the user will enter the name,mail,pass we will store it in the realtime database
                            Users users=new Users(binding.nametext.getText().toString(),binding.emailtext.getText().toString(),binding.passtext.getText().toString());

                            // now we will get the id
                            // by this we can uniquely identified the users
                            String id=task.getResult().getUser().getUid();

                            // this basically means that we have created a child name Users under which their is a subchild
                            //name id and this id is unique for ever users as users enter their detail new id will be generetaed
                            // and under that unique id we have store the value of the obj users and it include
                            // username,mail,pass
                            database.getReference().child("Users").child(id).setValue(users);
                            Toast.makeText(signup.this, "user created successfully", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(signup.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        // this is done for the already have an acc
    binding.alreadytext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(signup.this, signin.class);
            startActivity(i);
            finish();
        }
    });
    }
}