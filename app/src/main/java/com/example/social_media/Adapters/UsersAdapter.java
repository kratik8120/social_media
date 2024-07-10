package com.example.social_media.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media.ChatDetailActivity;
import com.example.social_media.R;
import com.example.social_media.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

// this is basically the recycler view adapter only
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    // this arraylist is used to store the all the items present in the user model class
    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_sample_show_result,parent,false);

        return new ViewHolder(v);
    }

    // basically this is used to bind the thing on their place
    // means image ki jagha image and msg ki jagha msg
    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
     // we use the user class that we have made and we will get the position becz acc to that
        // only the things will be at their proper place
        // here we have use thelist becz list contain all the things like image msg etc;
        Users users=list.get(position);
       // we want to first place the image
        // first we will get the image then load it to the circular part
        // for this we want the users their circular image is present
        // palceholder means if no img is there show the default one
        // after it place it into the correct pos ie holder.image
        //and this imae is of the viewholder
        Picasso.get().load(users.getProfileimg()).placeholder(R.drawable.baseline_person_24).into(holder.image);
        holder.userName.setText(users.getUsername());

        // now on clicking the item we want to go to the chatdetailactivity for chat
        // now all this go to the correct position
        // this is becz there is a position on onbindviewholder as a parameter this will ensure that it will go to the
        // correct position
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, ChatDetailActivity.class);
                i.putExtra("userId",users.getUserid());
                i.putExtra("profilePic",users.getProfileimg());
                i.putExtra("userName",users.getUsername());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView userName,lastmessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.circlepic);
            userName=itemView.findViewById(R.id.picname);
            lastmessage=itemView.findViewById(R.id.piclastmessage);
        }
    }
}
