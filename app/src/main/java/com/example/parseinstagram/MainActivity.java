package com.example.parseinstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ImageView postImg;
    private EditText descriptionEditText;
    private Button pictureBtn;
    private Button submitBtn;
    private Button logOutbtn;
    private ProgressBar progressBar;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 25;
    private File photoFile;
    public String photoFileName = "photo.jpg";
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseObject firstObject = new  ParseObject("FirstClass");
        firstObject.put("message","Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null){
                Log.e(TAG, e.getLocalizedMessage());
            }else{
                Log.d(TAG,"Object saved.");
            }
        });

        postImg = findViewById(R.id.imageInput);
        descriptionEditText = findViewById(R.id.descriptionImg);
        pictureBtn = findViewById(R.id.picBtn);
        submitBtn = findViewById(R.id.submitBtn);
        logOutbtn = findViewById(R.id.logOutBtn);
        progressBar = findViewById(R.id.pbLoading);

        queryPost();

        // Camera button click
        pictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Launching camera", Toast.LENGTH_SHORT).show();
                launchCamera();
                postImg.setVisibility(View.VISIBLE);
            }
        });

        // Submit button click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Description = descriptionEditText.getText().toString();
                if(Description.isEmpty()){
                    Toast.makeText(MainActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(photoFile == null || postImg.getDrawable() == null){
                    Toast.makeText(MainActivity.this, "There is no image", Toast.LENGTH_SHORT).show();
                    return;

                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(Description, currentUser);
            }
        });

        // Log out button
        logOutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.i(TAG, "Logout");
            }
        });

    }

    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode==RESULT_OK){

                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                postImg.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Error taking picture", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);


    }

    private void savePost(String description, ParseUser currentUser) {
        // Show progress bar
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(MainActivity.this, "Error while saving", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post was saved succesfully");
                descriptionEditText.setText("");
                postImg.setImageResource(0);
                // Hide progress bar
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

    private void queryPost() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post" + post.getDescription() + ",  username: " + post.getUser().getUsername());

                }
            }
        });
    }
}