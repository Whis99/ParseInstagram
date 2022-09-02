package com.example.parseinstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText userLog;
    private EditText passLog;
    private Button logBtn;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLog = findViewById(R.id.usernameLog);
        passLog = findViewById(R.id.passwrdLog);
        logBtn = findViewById(R.id.buttonLog);
        img = findViewById(R.id.logoImage);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick button");
                String username = userLog.getText().toString();
                String passsword = passLog.getText().toString();
                loginUser(username, passsword);
            }
        });

    }

    private void loginUser(String username, String password){
        Log.i(TAG, "Attempting to login user " + username);
        // Todo

    }
}