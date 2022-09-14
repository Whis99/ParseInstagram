package com.example.parseinstagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parseinstagram.R;
import com.example.parseinstagram.helpers.TimeFormatter;
import com.example.parseinstagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public AccountAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_post_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView accountPostDescription;
        private TextView accountPostCreation;
        private ImageView accountPostContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            accountPostDescription = itemView.findViewById(R.id.accountPostDescription);
            accountPostContent = itemView.findViewById(R.id.accountPostContent);
            accountPostCreation = itemView.findViewById(R.id.accountPostTime);
        }

        public void bind(Post post) {
            String timeFormat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            // Bind the post data to the view elements
            accountPostDescription.setText(post.getDescription());
            accountPostCreation.setText(timeFormat + " ago");

            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(accountPostContent);
            }
        }

    }
}
