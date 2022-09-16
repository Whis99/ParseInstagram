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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;


    public UserAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user_all_post, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userPostDescription;
        private TextView userPostCreation;
        private ImageView userPostContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPostDescription = itemView.findViewById(R.id.userPostDescription);
            userPostContent = itemView.findViewById(R.id.userPostContent);
            userPostCreation = itemView.findViewById(R.id.userPostTime);
        }

        public void bind(Post post) {
            String timeFormat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());

            // Bind the post data to the view elements
            userPostDescription.setText(post.getDescription());
            userPostCreation.setText(timeFormat + " ago");

            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(userPostContent);
            }
        }

    }

}
