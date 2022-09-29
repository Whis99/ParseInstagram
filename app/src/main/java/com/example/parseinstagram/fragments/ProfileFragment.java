//package com.example.parseinstagram.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.parseinstagram.R;
//import com.example.parseinstagram.adapters.PostGVAdapter;
//import com.example.parseinstagram.adapters.PostsAdapter;
//import com.example.parseinstagram.fragments.HomeFragment;
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
//public class ProfileFragment extends Fragment {
//
//    public static final String TAG = "UserAllPostActivity";
//    private TextView user_UserName;
//    private ImageView user_Profile;
//    private PostGVAdapter user_adapter;
//    private ArrayList<Post> user_allPosts;
//    private GridView gvPost;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_account, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        gvPost = view.findViewById(R.id.gvPost);
//
//        user_allPosts = new ArrayList<>();
//        user_adapter = new PostGVAdapter(getContext(), user_allPosts);
//
//        // Setting adapter on the recycler view
//        gvPost.setAdapter(user_adapter);
//
//        queryPost();
//    }
//
//    private void queryPost() {
//        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
//        query.include(Post.KEY_USER);
//        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
