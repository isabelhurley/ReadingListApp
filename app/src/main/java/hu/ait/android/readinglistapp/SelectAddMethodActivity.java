package hu.ait.android.readinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SelectAddMethodActivity extends AppCompatActivity {
    public static final String TITLE = "TITLE";


    public static final String BOOK_TITLE = "BOOK_TITLE";
    public static final String CURR_USER_ID = "currUserId";
    public static final String CURR_LIST_ID = "currListId";
    private EditText etTitle;
    private Button btnAddMan;
    private Button btnSearchGB;
    private String currUserId;
    private String currListId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_method);

        if (getIntent().hasExtra(CURR_USER_ID)) {
            currUserId = getIntent().getStringExtra(CURR_USER_ID);
        } else {
            Toast.makeText(SelectAddMethodActivity.this, R.string.no_currUserId, Toast.LENGTH_SHORT).show();
        }

        if (getIntent().hasExtra(CURR_LIST_ID)) {
            currListId = getIntent().getStringExtra(CURR_LIST_ID);
        } else {
            Toast.makeText(SelectAddMethodActivity.this, R.string.no_currListId, Toast.LENGTH_SHORT).show();
        }

        etTitle = findViewById(R.id.etTitle);
        btnSearchGB = findViewById(R.id.btnSearchGB);
        btnAddMan = findViewById(R.id.btnAddMan);

        btnSearchGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etTitle.getText().toString().equals("")) {
                    etTitle.setError(getString(R.string.blank_title_error));
                } else {
                    Intent intent = new Intent(SelectAddMethodActivity.this, LoadBooksActivity.class);
                    intent.putExtra(BOOK_TITLE, etTitle.getText().toString());
                    intent.putExtra(CURR_USER_ID, currUserId);
                    intent.putExtra(CURR_LIST_ID, currListId);
                    Log.d("EXTRA", "put in title " + etTitle.getText().toString());
                    Log.d("EXTRA", "put in current User ID " + currUserId);
                    Log.d("EXTRA", "put in current List ID " + currListId);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnAddMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startManualAddBookActivity();
                finish();
            }
        });
    }

    public void startManualAddBookActivity() {
        Intent intentManualAddBook = new Intent(SelectAddMethodActivity.this,
                ManualAddBookActivity.class);
        intentManualAddBook.putExtra(CURR_USER_ID, currUserId);
        intentManualAddBook.putExtra(CURR_LIST_ID, currListId);
        intentManualAddBook.putExtra(TITLE, etTitle.getText().toString());
        startActivity(intentManualAddBook);

    }
}
