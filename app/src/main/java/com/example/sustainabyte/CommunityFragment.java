package com.example.sustainabyte;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private MaterialButton createPostBtn; // Declaration of MaterialButton

    private DatabaseReference databaseReference;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // Initialize the createPostBtn after inflating the layout
        createPostBtn = view.findViewById(R.id.createPostBtn);

        // Set OnClickListener for createPostBtn
        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CreatePostActivity
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = view.findViewById(R.id.recycler_view_posts);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("posts");

        // Retrieve posts from the database
        // Retrieve posts from the database
        // Retrieve posts from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear(); // Clear existing posts before adding new ones
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Extract post data from the snapshot
                    String title = snapshot.child("title").getValue(String.class);
                    String content = snapshot.child("content").getValue(String.class);
                    String authorId = snapshot.child("author").getValue(String.class);
                    long timestamp = snapshot.child("timestamp").getValue(Long.class);
                    int like = snapshot.child("like").getValue(Integer.class);
                    String comment = "0";

                    // Fetch username and icon URL based on authorId
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(authorId);
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String username = dataSnapshot.child("username").getValue(String.class);
                                String iconUrl = dataSnapshot.child("icon").getValue(String.class);

                                // Create a Post object and add it to the postList
                                Post post = new Post(iconUrl, username, title, content, (int) like, timestamp, comment);
                                postList.add(post);

                                // Notify the adapter that the dataset has changed
                                postAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle database error
                            Toast.makeText(getContext(), "Failed to retrieve user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(getContext(), "Failed to retrieve posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
