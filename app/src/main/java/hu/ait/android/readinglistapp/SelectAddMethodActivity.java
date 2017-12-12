package hu.ait.android.readinglistapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SelectAddMethodActivity extends AppCompatActivity {
    private EditText etTitle;
    private Button btnAddMan;
    private Button btnSearchGB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_method);

        etTitle = findViewById(R.id.etTitle);
        btnSearchGB = findViewById(R.id.btnSearchGB);
        btnAddMan = findViewById(R.id.btnAddMan);

        btnSearchGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectAddMethodActivity.this,
                        "Searching on Google Books...", Toast.LENGTH_LONG).show();

            }
        });

        btnAddMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SelectAddMethodActivity.this,
                        "Adding manually...", Toast.LENGTH_LONG).show();
            }
        });
    }
}
