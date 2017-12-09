package hu.ait.android.readinglistapp;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.readinglistapp.data.Booklist;

public class CreateBooklistActivity extends AppCompatActivity {

    @BindView(R.id.etNewList)
    EditText etNewList;
    @BindView(R.id.ivAddNewList)
    ImageView ivAddNewList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_booklist);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivAddNewList)
    void addListClick() {
        String key = FirebaseDatabase.getInstance().getReference().child("booklists").push().getKey();
        String text = etNewList.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(CreateBooklistActivity.this, "No Booklist added", Toast.LENGTH_SHORT).show();
            finish();
        }
        Booklist newBooklist = new Booklist(text);

        FirebaseDatabase.getInstance()
                .getReference()
                .child("booklists")
                .child(key)
                .setValue(newBooklist)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(CreateBooklistActivity.this, "Booklist created", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
