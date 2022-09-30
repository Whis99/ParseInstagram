package com.example.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.parseinstagram.models.Post;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class CommentActivity extends AppCompatActivity {
    private ImageView cProfile;
    private TextView cUserName;
    private TextInputLayout TextField;
    private Button button;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        String profile = ParseUser.getCurrentUser().getParseFile("Profile").getUrl();

        cProfile = findViewById(R.id.commentProfile);
        cUserName = findViewById(R.id.commentUsername);
        TextField = findViewById(R.id.commentTxt);
        button = findViewById(R.id.commentBtn);

        cUserName.setText(ParseUser.getCurrentUser().getUsername());

        if(profile != null){
            Glide.with(this)
                    .load(profile)
                    .centerCrop()
                    .transform(new RoundedCorners(20))
                    .into(cProfile);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo
            }
        });
    }
}