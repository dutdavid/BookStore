package com.moringa.david.bookstore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringa.david.bookstore.adapters.BookRecyclerViewAdapter;
import com.moringa.david.bookstore.model.Book;
import com.moringa.david.bookstore.model.BookBuilder;
import com.moringa.david.bookstore.network.GoodreadRequest;
import com.moringa.david.bookstore.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    private ImageView image;
    private Button btn;
    private static final int RC_Request_code = 10;

    private final int INTERNET_PERMISSION = 1;
    private GoodreadRequest mGoodreadRequest;
    private com.moringa.david.bookstore.InternalStorage cache;
    private List<Book> books = new ArrayList<>();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    private BookRecyclerViewAdapter bookRecyclerViewAdapter;

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(LandingPage.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // User is logged in
        }

        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(LandingPage.this, LoginActivity.class));
                    finish();
                }
            }
        };

        cache = new com.moringa.david.bookstore.InternalStorage(this);

        RecyclerView bookRecyclerView = findViewById(R.id.recyclerViewLandingPage);

        // for smooth scrolling in recycler view
        bookRecyclerView.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        bookRecyclerView.setLayoutManager(layoutManager);


        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(books);
        bookRecyclerView.setAdapter(bookRecyclerViewAdapter);


        requestInternetPermission();


        //Api request
        mGoodreadRequest = new GoodreadRequest(getString(R.string.GR_API_Key), this);


        loadFavBooks();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SearchActivity.class)));
        image = (android.widget.ImageView) findViewById(R.id.imageview6);
        btn = (Button)findViewById(R.id.btn_cap);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

    }


    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, RC_Request_code);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==RC_Request_code){
            if (resultCode==RESULT_OK){
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                image.setScaleType (ImageView.ScaleType.FIT_CENTER);
                image.setImageBitmap(bp);
            }
            else if (resultCode==RESULT_CANCELED){
                android.widget.Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();


            }
        }
    }




    private void loadFavBooks() {
        final List<Integer> RANDOM_BOOK_ID = cache.getFavListCache();
        for (int i = 0; i < RANDOM_BOOK_ID.size(); i++) {

            if (cache.getCachedBookById(RANDOM_BOOK_ID.get(i)) == null) {

                mGoodreadRequest.getBook(RANDOM_BOOK_ID.get(i), new com.moringa.david.bookstore.network.SuccessFailCallback() {
                    @Override
                    public void success(String response) {

                        Book book = BookBuilder.getBookFromXML(response);
                        cache.cacheBook(book);
                        bookRecyclerViewAdapter.add(book);

                    }

                    @Override
                    public void failed() {
                        Toast.makeText(
                                getApplicationContext(),
                                "some error occurred",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                bookRecyclerViewAdapter.add(cache.getCachedBookById(RANDOM_BOOK_ID.get(i)));
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void requestInternetPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        INTERNET_PERMISSION);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case INTERNET_PERMISSION: {
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Internet Access denied", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


}



