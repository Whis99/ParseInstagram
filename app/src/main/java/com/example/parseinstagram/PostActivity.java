package com.example.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}