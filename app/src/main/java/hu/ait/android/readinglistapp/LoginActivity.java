package hu.ait.android.readinglistapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.readinglistapp.data.User;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    private static String currUserId;
    public static final String CURR_USER_ID = "currUserId";
    public static final String USERS = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @OnClick(R.id.btnRegister)
    void registerClick() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();

        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),
                etPassword.getText().toString()).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = task.getResult().getUser();

                            fbUser.updateProfile(
                                    new UserProfileChangeRequest.Builder()
                                            .setDisplayName(usernameFromEmail(fbUser.getEmail())).build()
                            );
                            User newUser = new User(fbUser.getUid(), fbUser.getDisplayName(), fbUser.getEmail());
                            databaseReference.child(USERS).child(fbUser.getUid()).setValue(newUser);

                            Toast.makeText(LoginActivity.this,
                                    "Registration ok", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @OnClick(R.id.btnLogin)
    void loginClick() {
        if (!isFormValid()) {
            return;
        }

        showProgressDialog();

        firebaseAuth.signInWithEmailAndPassword(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()) {
                    // open messages Activity
                    currUserId = task.getResult().getUser().getUid();
                    Intent startMenu = new Intent(LoginActivity.this, MenuActivity.class);
                    startMenu.putExtra(CURR_USER_ID, currUserId);
                    startActivity(startMenu);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Error: "+task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this,
                        "Error: "+e.getMessage(),
                        Toast.LENGTH_SHORT).show();

                e.printStackTrace();
            }
        });

    }


    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Wait for it...");
        }

        progressDialog.show();
    }


    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText())) {
            etEmail.setError("The email cannot be empty");
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("The password cannot be empty");
            return false;
        }

        return true;
    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public static String getCurrUserId() {
        return currUserId;
    }

}