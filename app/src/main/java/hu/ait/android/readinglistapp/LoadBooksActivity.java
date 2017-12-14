package hu.ait.android.readinglistapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.readinglistapp.APIdata.BooksResult;
import hu.ait.android.readinglistapp.ListsPackage.adapter.LoadBooksAdapter;
import hu.ait.android.readinglistapp.data.Book;
import hu.ait.android.readinglistapp.network.BooksAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadBooksActivity extends AppCompatActivity {

    private RecyclerView recyclerContent;
    private Button btnRequest;
    private TextView errorMsg;
    private LoadBooksAdapter adapter;
    private String title;

    private static String currUserId;
    private static String currListId;

    private static final String API_KEY = "AIzaSyDuac4XWRM_oQN7TUEbt6yxPTatCQhhl_U";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("EXTRA", "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_books);

        errorMsg = findViewById(R.id.tvTester);

        if (getIntent().hasExtra(SelectAddMethodActivity.BOOK_TITLE)) {
            title = getIntent().getStringExtra(SelectAddMethodActivity.BOOK_TITLE);
        } else {
            errorMsg.setError(getString(R.string.no_title));
        }
        if (getIntent().hasExtra(EditBooklistActivity.CURR_USER_ID)) {
            currUserId = getIntent().getStringExtra(EditBooklistActivity.CURR_USER_ID);
        } else {
            errorMsg.setError(getString(R.string.no_currUserId));
        }
        if (getIntent().hasExtra(EditBooklistActivity.CURR_LIST_ID)) {
            currListId = getIntent().getStringExtra(EditBooklistActivity.CURR_LIST_ID);
        } else {
            errorMsg.setError(getString(R.string.no_currListId));
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final BooksAPI booksAPI = retrofit.create(BooksAPI.class);

        Log.d("EXTRA", "created booksAPI");

        loadBooks(title, booksAPI);
    }

    private void loadBooks(String title, BooksAPI booksAPI) {
        Log.d("EXTRA", "loading title " + title);
        Call<BooksResult> call = booksAPI.getBookByName(title, API_KEY);

        call.enqueue(new Callback<BooksResult>() {
            @Override
            public void onResponse(Call<BooksResult> call, Response<BooksResult> response) {
                if (response.body() != null){
                    List<Book> bookList = createQueryResult(response);

                    adapter = new LoadBooksAdapter(LoadBooksActivity.this, bookList);
                    recyclerContent = (RecyclerView) findViewById(R.id.recyclerContent);
                    recyclerContent.setLayoutManager(new LinearLayoutManager(LoadBooksActivity.this));
                    recyclerContent.setAdapter(adapter);

                } else {
                    errorMsg.setVisibility(View.VISIBLE);
                    errorMsg.setText(R.string.error_msg);
                }
            }
            @Override
            public void onFailure(Call<BooksResult> call, Throwable t) {
                errorMsg.setText(t.getMessage());
            }
        });

    }

    private List<Book> createQueryResult(Response<BooksResult> response) {
        List<Book> bookList = new ArrayList<Book>();
        int i = 0;
        while (i < 10 && i < response.body().getItems().size()) {
            Book book = new Book(response.body().getItems().get(i).getVolumeInfo().getTitle(),
                    response.body().getItems().get(i).getVolumeInfo().getAuthors().get(0),
                    response.body().getItems().get(i).getVolumeInfo().getDescription(),
                    response.body().getItems().get(i).getVolumeInfo().getImageLinks().getSmallThumbnail());

            bookList.add(book);
            i++;
        }
        return bookList;
    }


    public void addBookToFirebase(Book book) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users")
                .child(currUserId).child("booklists").child(currListId).child("books");
        String key = databaseRef.push().getKey();

        databaseRef
                .child(key)
                .setValue(book)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Snackbar.make(context, "Book added successfully!", Snackbar.LENGTH_SHORT);
                        finish();
                    }

                });
    }


}
