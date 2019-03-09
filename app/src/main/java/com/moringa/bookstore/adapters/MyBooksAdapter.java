package com.moringa.bookstore.adapters;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.moringa.bookstore.R;
import com.moringa.bookstore.models.Work;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBooksAdapter
        extends RecyclerView.Adapter<MyBooksAdapter.BookViewHolder> {
    private List<Work> mWork;
    private Context mContext;

    public MyBooksAdapter(Context context, List<Work> myBooks) {
        mContext = context;
        mWork = myBooks;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bindBooks(mWork.get(position));
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_book_item, parent, false);
        BookViewHolder viewHolder = new BookViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mWork.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.bookImageView) ImageView mBookImageView;
        @BindView(R.id.bookNameTextView) TextView mBookNameTextView;
        @BindView(R.id.authorTextView) TextView mAuthorTextView;
        @BindView(R.id.ratingTextView) TextView mRatingTextView;
        @BindView(R.id.ratingsTextView) TextView mRatingsTextView;
        @BindView(R.id.ratingBar2) RatingBar mBookRatingBar;

        private Context mContext;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            Intent intent = new Intent(mContext, BookDetailsActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("work", Parcels.wrap(mWork));
            mContext.startActivity(intent);
        }


        public void bindBooks(Work work) {
            NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
            double bookRatings = Double.parseDouble(work.getRatingsCount().getText());
            mBookNameTextView.setText(work.getBestBook().getTitle());
            mAuthorTextView.setText("By " + work.getBestBook().getAuthor().getName());
            mRatingTextView.setText(work.getAverageRating() + "/5");
            mRatingsTextView.setText(formatter.format(bookRatings) + " Ratings");
            mBookRatingBar.setRating(Float.valueOf(work.getAverageRating()));


            if (!(work.getBestBook().getSmallImageUrl().isEmpty())) {
                Picasso.with(mContext).load(work.getBestBook().getSmallImageUrl())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.goodreads)
                        .into(mBookImageView);
            }
        }
    }
}

