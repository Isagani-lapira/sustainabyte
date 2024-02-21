package com.example.sustainabyte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final List<Post> postList;
    private Context context;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Bind data to views
//        holder.imageUserIcon.setImageResource(post.getUserIcon());
        holder.textUsername.setText(post.getUsername());
        holder.textTitle.setText(post.getTitle());
        holder.textContent.setText(post.getContent());
        holder.textUpvotes.setText(String.valueOf(post.getLike()));
        holder.textComments.setText(String.valueOf(post.getComment()));

        // Implement click listeners for action buttons if needed
        // holder.btnUpvote.setOnClickListener(...)
        // holder.btnComment.setOnClickListener(...)
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView imageUserIcon;
        TextView textUsername, textTitle, textContent, textUpvotes, textComments;
        ImageButton btnUpvote, btnComment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            imageUserIcon = itemView.findViewById(R.id.image_user_icon);
            textUsername = itemView.findViewById(R.id.text_username);
            textTitle = itemView.findViewById(R.id.text_title);
            textContent = itemView.findViewById(R.id.text_content);
            textUpvotes = itemView.findViewById(R.id.text_upvotes);
            textComments = itemView.findViewById(R.id.text_comments);
            btnUpvote = itemView.findViewById(R.id.btn_upvote);
            btnComment = itemView.findViewById(R.id.btn_comment);
        }
    }
}
