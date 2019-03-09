package com.moringa.david.bookstore.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moringa.david.bookstore.AuthorViewActivity;
import com.moringa.david.bookstore.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AuthorRecyclerViewAdapter extends RecyclerView.Adapter<AuthorRecyclerViewAdapter.AuthorViewHolder> {
    private static final String TAG = AuthorRecyclerViewAdapter.class.getName();

    private final List<com.moringa.david.bookstore.model.Author> authorList;

    public AuthorRecyclerViewAdapter(List<com.moringa.david.bookstore.model.Author> authorList) {
        this.authorList = authorList;
    }

    @Override
    public void onBindViewHolder(AuthorViewHolder holder, int position) {

        final com.moringa.david.bookstore.model.Author a = authorList.get(position);
        holder.name.setText(a.getName());
        Picasso.get()
                .load(a.getImg())
                .transform(new com.moringa.david.bookstore.util.CircleTransform())
                .into(holder.image);

        holder.row.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), AuthorViewActivity.class);
            Log.d(TAG, "onBindViewHolder: onClick" + a.getImg());
            i.putExtra("author", a);
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return authorList.size();
    }

    @Override
    public AuthorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author_row_layout, parent, false);
        return new AuthorViewHolder(v);
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageView image;
        final LinearLayout row;

        AuthorViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.authorName);
            image = view.findViewById(R.id.authorImage);
            row = view.findViewById(R.id.authorRowContainer);
        }
    }
}
