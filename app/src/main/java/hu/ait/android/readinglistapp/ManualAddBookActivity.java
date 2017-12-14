package hu.ait.android.readinglistapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.android.readinglistapp.data.Book;
import hu.ait.android.readinglistapp.data.Booklist;

public class ManualAddBookActivity extends AppCompatActivity {

    public static final String CURR_USER_ID = "currUserId";
    public static final String CURR_LIST_ID = "currListId";
    public static final String TITLE = "TITLE";

    public static final String BOOKS = "books";
    public static final String BOOKLISTS = "booklists";
    public static final String USERS = "users";

    private String currUserId;
    private String currListId;
    private DatabaseReference databaseRef;

    private EditText etManTitle;
    private EditText etAuthor;
    private EditText etDesc;
    private Button btnAddBookMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_book);

        etManTitle = findViewById(R.id.etManTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etDesc = findViewById(R.id.etDesc);
        btnAddBookMan = findViewById(R.id.btnAddBookMan);

        btnAddBookMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (getIntent().hasExtra(CURR_USER_ID)) {
            currUserId = getIntent().getStringExtra(CURR_USER_ID);
        } else {
            Toast.makeText(ManualAddBookActivity.this, R.string.no_currUserId, Toast.LENGTH_SHORT).show();
        }

        if (getIntent().hasExtra(CURR_LIST_ID)) {
            currListId = getIntent().getStringExtra(CURR_LIST_ID);
        } else {
            Toast.makeText(ManualAddBookActivity.this, R.string.no_currListId, Toast.LENGTH_SHORT).show();
        }

        if (getIntent().hasExtra(TITLE)) {
            etManTitle.setText(getIntent().getStringExtra(TITLE));
        }

        databaseRef = FirebaseDatabase.getInstance().getReference(USERS)
                .child(currUserId).child(BOOKLISTS).child(currListId).child(BOOKS);

        btnAddBookMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etManTitle.getText())) {
                    String key = databaseRef.push().getKey();
                    String title = etManTitle.getText().toString();
                    String author = etAuthor.getText().toString();
                    String desc = etDesc.getText().toString();

                    Book newBook = new Book(title, author, desc);

                    databaseRef
                            .child(key)
                            .setValue(newBook)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    finish();
                                }
                            });
                } else {
                    etManTitle.setError(getString(R.string.empty_title_error));
                }
            }
        });
    }
}
