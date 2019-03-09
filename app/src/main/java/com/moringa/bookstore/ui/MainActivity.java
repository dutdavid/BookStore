package com.moringa.bookstore.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.moringa.bookstore.models.Book;
import com.moringa.bookstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.searchBooksButton) Button mFindBooksButton;
    @BindView(R.id.locationEditText) EditText mLocationEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindBooksButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindBooksButton) {
            String location = mLocationEditText.getText().toString();
            Intent intent = new Intent(MainActivity.this, Book.class);
            intent.putExtra("location", location);
            startActivity(intent);
        }
    }
}