//package com.example.parseinstagram;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.parseinstagram.adapters.PostsAdapter;
//import com.example.parseinstagram.models.Post;
//import com.parse.FindCallback;
//import com.parse.ParseException;
//import com.parse.ParseQuery;
//import com.parse.ParseUser;
//
//import org.parceler.Parcels;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserAllPostActivity extends AppCompatActivity {
//    public static final String TAG = "UserAllPostActivity";
//    private TextView user_UserName;
//    private ImageView user_Profile;
//    private PostsAdapter user_adapter;
//    private List<Post> user_allPosts;
//    private RecyclerView user_rvPosts;
//    Post post = Parcels.unwrap(getIntent().getParcelableExtra("UserPost"));
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_all_post);
//
//        user_Profile = findViewById(R.id.user_UserProfile);
//        user_UserName = findViewById(R.id.userUsername);
//
//
//
//        user_rvPosts = findViewById(R.id.user_rvPosts);
//
//        user_allPosts = new ArrayList<>();
//        user_adapter = new PostsAdapter(this, user_allPosts);
//
//        user_rvPosts.setAdapter(user_adapter);
//
//        user_rvPosts.setLayoutManager(new LinearLayoutManager(this));
//        queryPost();
//
//        user_UserName.setText(post.getUser().getUsername());
//    }
//
//    private void queryPost() {
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        query.include(Post.KEY_USER);
//        query.whereEqualTo(Post.KEY_USER, post.getUser());
//        query.setLimit(20);
//        query.addDescendingOrder(Post.KEY_CREATED_KEY);
//        query.findInBackground(new FindCallback<Post>() {
//            @Override
//            public void done(List<Post> posts, ParseException e) {
//                if(e != null){
//                    Log.e(TAG, "Issue with getting posts", e);
//                    return;
//                }
//                for(Post post: posts){
//                    Log.i(TAG, "Post" + post.getDescription() + ",  username: " + post.getUser().getUsername());
//
//                }
//                user_allPosts.addAll(posts);
//                user_adapter.notifyDataSetChanged();
//            }
//        });
//    }
//}