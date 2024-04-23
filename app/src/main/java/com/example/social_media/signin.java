package com.example.social_media;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.social_media.databinding.ActivitySigninBinding;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class signin extends AppCompatActivity {

    // note : we want that when we open the app signin act will come first
    // so for that we have change the manifest file ie we have place
    // the intent act part in the signin act from the main act
    // we have done same as we do in the splash activity part
    ActivitySigninBinding binding;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    public BeginSignInRequest signInRequest;
    public  SignInClient oneTapClient;
    GoogleSignInClient mGoogleSignInClient;

    FirebaseDatabase database;

    FirebaseAuth auth;
    // this is used to add the progress dialog
    // this means we are showing the progress bar in the progress dialog
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(signin.this);
        progressDialog.setTitle("login");
        progressDialog.setMessage("login your account");


        // THIS IS FOR THE GOOGLE AUTHENTICATION
        // earlier their is an error in default_web_client_id
        // so to resolve this we have put the web client id ie taken from the firebase project setting
        // ie goto the signin method then go to the drop down arrow of the sdk their it is
        // and put it in the string.xml file
        // see their
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);


        binding.ggsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleclick();
            }
        });

        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                auth.signInWithEmailAndPassword(binding.emailsign.getText().toString(),binding.passsignin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   progressDialog.dismiss();
                   if (task.isSuccessful())
                   {

                       Intent i=new Intent(signin.this, MainActivity.class);
                       startActivity(i);
                       finish();
                   }
                   else {
                       Toast.makeText(signin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                    }
                });

            }
        });

        // this is done for the text that is click to signup
        binding.clicksignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(signin.this, signup.class);
                startActivity(i);
                finish();
            }
        });
        // now we want that if the user is already login then no need to show them the
        // signin page directy redirect them to the mainact
        if(auth.getCurrentUser()!=null)
        {
            Intent i=new Intent(signin.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void googleclick() {

        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1000);


    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==1000)
            {
                Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                try
                {
                    GoogleSignInAccount account =task.getResult(ApiException.class);
                    Log.w(TAG, "Google sign in failed"+account.getId());
                    firebaseauthwithGoogle(account.getIdToken());
                } catch (ApiException e)
                {
                    Log.w(TAG, "Google sign in failed",e);
//                    throw new RuntimeException(e);
//                    Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                }
            }
        }

    private void firebaseauthwithGoogle(String idToken) {
        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseuser =auth.getCurrentUser();

                            // now we will take the user mail,username and profile pic
                            Users users=new Users();
                            users.setUserid(firebaseuser.getUid());
                            users.setUsername(firebaseuser.getDisplayName());
                            users.getProfileimg(firebaseuser.getPhotoUrl().toString());
                            database.getReference().child("Users").child(firebaseuser.getUid()).setValue(users);

                            Intent i=new Intent(signin.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            Toast.makeText(signin.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
