package com.example.social_media;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.social_media.Adapters.ChatAdapter;
import com.example.social_media.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    // this help us to bring the user data at the chat activity

    FirebaseDatabase database;
    FirebaseAuth auth;
    ActivityChatDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        // now we will receive the user data
        // we will get the id using auth
        final String senderId=auth.getUid();
        String receiverId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");

        // now put them on exact place
        // here username is the id of the name
        // and the circlepic is the id of the circular image
        binding.username.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.baseline_person_24).into(binding.circlepic);

        //this is for back arrow
        binding.backarow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        final ArrayList<MessageModel>messageModels=new ArrayList<>();

        final ChatAdapter chatAdapter=new ChatAdapter(messageModels,this);
        binding.chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom=senderId+receiverId;
        final String receiverRoom=receiverId+senderId;

        // this is done to show the msg in the recycler view part

        database.getReference().child("chats")
                        .child(senderRoom)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       messageModels.clear();
                                       for (DataSnapshot snapshot1:snapshot.getChildren())
                                       {
                                           MessageModel model=snapshot1.getValue(MessageModel.class);
                                           messageModels.add(model);
                                       }
                                       chatAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });





        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // first we will convert the msg to the tostring then only we will save the msg to the firebase
                String message=binding.etMessage.getText().toString();
                final  MessageModel model=new MessageModel(senderId,message);
                model.setTimestamp(new Date().getTime());

                // now after sending the msg the edit text should get empty automatically
                binding.etMessage.setText(" ");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                              // here we do the receiver part
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model);
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void unused) {
//
//                                            }
//                                        });

                            }
                        });
            }
        });
    }
}