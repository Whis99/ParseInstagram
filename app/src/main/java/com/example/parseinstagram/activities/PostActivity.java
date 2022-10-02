package com.example.parseinstagram.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.parseinstagram.R;
import com.example.parseinstagram.helpers.TimeFormatter;
import com.example.parseinstagram.models.Comment;
import com.example.parseinstagram.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "PostActivity";

    private TextView userName2, description2, postCreation2, likeCounter, postComment;
    private int postLikeCounter;
    private ImageView detailProfile, picture2, postLike;
    protected List<String> commentsParse;
    protected List<Comment> comments;
//    protected CommentAdapter adapter;
    Context context;
    public static int like;
    public static ArrayList<String> userLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ParseUser currentUser = ParseUser.getCurrentUser();

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));
        String timeFormat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
        String profile = post.getUser().getParseFile("Profile").getUrl();
        String username = post.getUser().getUsername();



        userName2 = findViewById(R.id.userName2);
        description2 = findViewById(R.id.postDescription2);
        picture2 = findViewById(R.id.postImg2);
        postCreation2 = findViewById(R.id.postTime2);
        detailProfile = findViewById(R.id.userProfile2);
        postLike = findViewById(R.id.postLike2);
        likeCounter = findViewById(R.id.postLikeCounter);
        postComment = findViewById(R.id.postComment2);

        userName2.setText(post.getUser().getUsername());
        description2.setText(username + ": " +post.getDescription());
        postCreation2.setText(timeFormat + " ago");

        post.getLike();
        commentsParse = Comment.fromJsonArray(post.getLike());

        // get like's user
        try {
            userLike = Post.fromJsonArray(post.getLike());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // if heart is enable
        try{
            if (userLike.contains(currentUser.getObjectId())) {
                Drawable drawable = ContextCompat.getDrawable(PostActivity.this, R.drawable.ic_heart_);
                postLike.setImageDrawable(drawable);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        comments = new ArrayList<>();

        // Show post
        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this)
                    .load(image.getUrl())
                    .into(picture2);
        }

        // Show profile
        if(profile != null){
            Glide.with(this)
                    .load(profile)
                    .centerCrop()
                    .transform(new CircleCrop())
                    .into(detailProfile);
        }

        // Comment button is clicked
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this, CommentActivity.class);
                i.putExtra("post", Parcels.wrap(post));
                startActivity(i);
            }
        });

        // Like button is clicked
        postLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postLikeCounter = post.getLikeCount();
                        int index;

                        if (!userLike.contains(currentUser.getObjectId())){
                            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart_);
                            postLike.setImageDrawable(drawable);
                            postLikeCounter++;
                            index = -1;

                        }else {
                            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_heart_curvy_outline);
                            postLike.setImageDrawable(drawable);
                            postLikeCounter--;
                            index = userLike.indexOf(currentUser.getObjectId());
                        }

                        likeCounter.setText(String.valueOf(postLikeCounter));
                        saveLike(post, postLikeCounter, index, currentUser);
                    }

            private void saveLike(Post post, int postLikeCounter, int index, ParseUser currentUser) {
                post.setLikeCount(like);

                if (index == -1){
                    post.setLike(currentUser);
                    userLike.add(currentUser.getObjectId());
                }else {
                    userLike.remove(index);
                    post.removeLike(userLike);
                }

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Log.e(TAG, "Error while saving", e);
                            Toast.makeText(context, "Error while saving", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.i(TAG, userLike.toString());
                    }
                });
            }
        });
    }
}