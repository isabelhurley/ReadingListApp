package hu.ait.android.readinglistapp;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView tester;
    private LoadBooksAdapter adapter;

    private static final String API_KEY = "AIzaSyDuac4XWRM_oQN7TUEbt6yxPTatCQhhl_U";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_books);

        btnRequest = (Button) findViewById(R.id.btnReqest);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final BooksAPI booksAPI = retrofit.create(BooksAPI.class);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<BooksResult> call = booksAPI.getBookByName("hamlet", API_KEY);

                call.enqueue(new Callback<BooksResult>() {
                    @Override
                    public void onResponse(Call<BooksResult> call, Response<BooksResult> response) {
                        if (response.body() != null){
                            List<Book> bookList = createQueryResult(response);

                            adapter = new LoadBooksAdapter(LoadBooksActivity.this, bookList);
                            recyclerContent = (RecyclerView) findViewById(R.id.recyclerContent);
                            recyclerContent.setLayoutManager(new LinearLayoutManager(LoadBooksActivity.this));
                            recyclerContent.setAdapter(adapter);

                            //tester.setText(response.body().getItems().get(0).getVolumeInfo().getInfoLink());
                        } else {
                            tester.setText("ERROR");
                        }
                    }
                    @Override
                    public void onFailure(Call<BooksResult> call, Throwable t) {
                        tester.setText(t.getMessage());
                    }
                });
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
}
