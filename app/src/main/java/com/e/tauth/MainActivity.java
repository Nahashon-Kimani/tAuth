package com.e.tauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.tauth.activity.EventActivity;
import com.e.tauth.activity.LoginActivity;
import com.e.tauth.model.UserRegistration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userNameEdt, emailEdt, passwordEdt, phoneNoEdt;
    TextView registerTvt;
    Button signInBtn;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameEdt = findViewById(R.id.user_name);
        emailEdt = findViewById(R.id.email_edt);
        passwordEdt = findViewById(R.id.pass_edt);
        phoneNoEdt = findViewById(R.id.phone_no);
        signInBtn = findViewById(R.id.register_btn);
        registerTvt = findViewById(R.id.register_tvt);
        progressBar = findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();

        signInBtn.setOnClickListener(this);
        registerTvt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                registerUser();
                break;
            case R.id.register_tvt:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        //We need to get the current user ID so that using it we can get name of the user to the next Activities;
        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("currentUser", user.getEmail());
            startActivity(intent);
            finish();
        }
    }

    public void registerUser() {
        final String name = userNameEdt.getText().toString().trim();
        final String email = emailEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        final String phone = phoneNoEdt.getText().toString().trim();


        if (name.isEmpty()) {
            userNameEdt.setError("Name should not be empty");
            userNameEdt.requestFocus();
        }
        if (email.isEmpty()) {
            emailEdt.setError("Email Address should not be empty");
            userNameEdt.requestFocus();
        }
        if (password.isEmpty()) {
            passwordEdt.setError("Password should not be empty");
            userNameEdt.requestFocus();
        }
        if (phone.isEmpty()) {
            phoneNoEdt.setError("Phone number should not be empty");
            userNameEdt.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEdt.setError("Please enter a valid email");
            emailEdt.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    //We will authenticate the userRegistration and store additional field at the real-time database.
                    UserRegistration userRegistration = new UserRegistration(name, email, phone);
                    FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userRegistration).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            //
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "UserRegistration registered Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
