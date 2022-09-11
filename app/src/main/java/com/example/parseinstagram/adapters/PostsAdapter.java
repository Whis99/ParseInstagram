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
import com.example.parseinstagram.helpers.TimeFormatter;
import com.example.parseinstagram.models.Post;
import com.example.parseinstagram.R;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_post, parent, false);
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

    // Method to clean all elements of the recycler
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    // Method to add a list of Posts -- change to type used
    public void addAll(List<Post> postList){
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView description;
        private TextView postCreation;
        private ImageView picture;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            description = itemView.findViewById(R.id.postDescription);
            picture = itemView.findViewById(R.id.postImg);
            postCreation = itemView.findViewById(R.id.postTime);



        }

        public void bind(Post post) {
            String timeFormat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
            // Bind the post data to the view elements
            userName.setText(post.getUser().getUsername());
            description.setText(post.getDescription());
            postCreation.setText(timeFormat + " ago");

            ParseFile image = post.getImage();
            if(image != null){
                Glide.with(context)
                        .load(image.getUrl())
                        .into(picture);
            }
        }
    }


}
