package com.example.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmailLogin, editTextPasswordLogin;
    ImageView imageViewLogoLogin;
    Button LogInBtnLogin;
    TextView textViewRegisterBtn;
    ProgressBar progressBarLogin;
    FirebaseAuth firebaseAuth;
    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        editTextEmailLogin = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = (EditText) findViewById(R.id.editTextPasswordLogin);
        imageViewLogoLogin = (ImageView) findViewById(R.id.imageViewLogoLogin);
        LogInBtnLogin = (Button) findViewById(R.id.LogInBtnLogin);
        textViewRegisterBtn = (TextView) findViewById(R.id.textViewRegisterBtn);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        LogInBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailId = editTextEmailLogin.getText().toString().trim();
                String pass = editTextPasswordLogin.getText().toString().trim();

                if(TextUtils.isEmpty(emailId))
                {
                    editTextEmailLogin.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(pass))
                {
                    editTextPasswordLogin.setError("Password is required");
                    return;
                }
                inputMethodManager.hideSoftInputFromWindow(LogInBtnLogin.getWindowToken(),0);
                progressBarLogin.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(emailId, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this,"Welcome Back", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else
                        {
                            progressBarLogin.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this,"Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        textViewRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });
    }
}