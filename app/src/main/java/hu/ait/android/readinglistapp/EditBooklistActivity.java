package hu.ait.android.readinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.android.readinglistapp.ListsPackage.adapter.BooksAdapter;
import hu.ait.android.readinglistapp.data.Book;
import hu.ait.android.readinglistapp.data.Booklist;

public class EditBooklistActivity extends AppCompatActivity {

    public static final String CURR_USER_ID = "currUserId";
    public static final String CURR_LIST_ID = "currListId";
    public static final String BOOKS = "books";
    public static final String BOOKLISTS = "booklists";
    public static final String USERS = "users";
    public static final String LIST_TO_DELETE = "LIST_TO_DELETE";
    public static final int EDIT_LIST_CODE = 1;

    private BooksAdapter adapter;
    private String currUserId;
    private String currListId;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add:
                    mTextMessage.setText("Add book");
                    startSelectAddMethodActivity();
                    return true;
                case R.id.navigation_delete:
                    mTextMessage.setText("Delete list");
                    deleteCurrentList();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booklist);

        if (getIntent().hasExtra(CURR_USER_ID)) {
            currUserId = getIntent().getStringExtra(CURR_USER_ID);
        } else {
            Toast.makeText(EditBooklistActivity.this, R.string.no_currUserId, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().hasExtra(CURR_LIST_ID)) {
            currListId = getIntent().getStringExtra(CURR_LIST_ID);
        } else {
            Toast.makeText(EditBooklistActivity.this, R.string.no_currListId, Toast.LENGTH_SHORT).show();
        }
        if (getIntent().hasExtra(MenuActivity.LIST_NAME)) {
            setTitle(getIntent().getStringExtra(MenuActivity.LIST_NAME));
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBookList);
        adapter = new BooksAdapter(this, currUserId, currListId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initBookListener();
    }

    private void initBookListener() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS)
                .child(currUserId).child(BOOKLISTS).child(currListId).child(BOOKS);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Book book = dataSnapshot.getValue(Book.class);
                adapter.addBook(book, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.removeBookByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void startSelectAddMethodActivity() {
        Intent intentSelectAddMethod = new Intent(EditBooklistActivity.this, SelectAddMethodActivity.class);
        intentSelectAddMethod.putExtra(CURR_USER_ID, currUserId);
        intentSelectAddMethod.putExtra(CURR_LIST_ID, currListId);
        startActivity(intentSelectAddMethod);

    }

    public void deleteCurrentList() {
        Intent intentDelete = new Intent();
        intentDelete.putExtra(LIST_TO_DELETE, currListId);
        Log.d("EXTRA", "put in list ID " + currListId);
        setResult(RESULT_OK, intentDelete);
        finish();
    }

}
