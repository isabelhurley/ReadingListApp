package hu.ait.android.readinglistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.android.readinglistapp.ListsPackage.adapter.ListsAdapter;
import hu.ait.android.readinglistapp.data.Booklist;

public class MenuActivity extends AppCompatActivity {

    public static final String CURR_USER_ID = "currUserId";
    public static final String CURR_LIST_ID = "currListId";
    public static final String BOOKLISTS = "booklists";
    public static final String USERS = "users";
    public static final String LIST_NAME = "LIST_NAME";
    public static final int REQUEST_CODE = 1;

    private ListsAdapter adapter;
    private String currUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if (getIntent().hasExtra(CURR_USER_ID)) {
            currUserId = getIntent().getStringExtra(CURR_USER_ID);
        } else {
            Toast.makeText(MenuActivity.this, R.string.no_currUserId, Toast.LENGTH_SHORT).show();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.Menu_activity_title);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MenuActivity.this, CreateBooklistActivity.class));
                Snackbar.make(view, "Add a new booklist", Snackbar.LENGTH_LONG);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLists);
        adapter = new ListsAdapter(this, currUserId);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        initBooklistListener();

    }

    private void initBooklistListener() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(USERS)
                .child(currUserId).child(BOOKLISTS);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(MenuActivity.this, "made child", Toast.LENGTH_LONG).show();
                Booklist booklist = dataSnapshot.getValue(Booklist.class);
                adapter.addBooklist(booklist, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.removeBooklistByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startEditBooklistActivity(String booklistKey, String booklistName) {
        Intent intentEditBooklist = new Intent(MenuActivity.this, EditBooklistActivity.class);
        intentEditBooklist.putExtra(CURR_USER_ID, currUserId);
        intentEditBooklist.putExtra(CURR_LIST_ID, booklistKey);
        intentEditBooklist.putExtra(LIST_NAME, booklistName);
        startActivityForResult(intentEditBooklist, REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String keyToDelete = data.getStringExtra(EditBooklistActivity.LIST_TO_DELETE);
                Log.d("EXTRA", "received ID " + keyToDelete);
                adapter.removeBooklistByKey(keyToDelete);
            }
        }
    }
}
