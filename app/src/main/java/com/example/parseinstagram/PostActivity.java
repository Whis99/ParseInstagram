package com.example.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.parseinstagram.helpers.TimeFormatter;
import com.example.parseinstagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostActivity extends AppCompatActivity {
    private TextView userName2;
    private TextView description2;
    private TextView postCreation2;
    private ImageView picture2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        userName2 = findViewById(R.id.userName2);
        description2 = findViewById(R.id.postDescription2);
        picture2 = findViewById(R.id.postImg2);
        postCreation2 = findViewById(R.id.postTime2);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("Post"));

        String timeFormat = TimeFormatter.getTimeDifference(post.getCreatedAt().toString());
        String username = post.getUser().getUsername();

        userName2.setText(post.getUser().getUsername());
        description2.setText(username + ": " +post.getDescription());
        postCreation2.setText(timeFormat + " ago");

        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(this)
                    .load(image.getUrl())
                    .into(picture2);
        }
    }
}