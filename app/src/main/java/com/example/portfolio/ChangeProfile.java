package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ChangeProfile extends AppCompatActivity {
    Toolbar toolbarAll;
    EditText editTextEditName, editTextEditPassword, editTextEditDescription, editTextEditPhone;
    ImageView imageViewEditProfilePicture;
    Button saveEditBtn, cancelEditBtn;
    ProgressBar progressBarEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        toolbarAll = (Toolbar) findViewById(R.id.toolbarAll);
        toolbarAll.setTitle("Edit Profile");
        setSupportActionBar(toolbarAll);
        toolbarAll.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        editTextEditName = (EditText) findViewById(R.id.editTextEditUserName);
        editTextEditDescription = (EditText) findViewById(R.id.editTextEditDescription);
        editTextEditPassword = (EditText) findViewById(R.id.editTextEditPassword);
        editTextEditPhone = (EditText) findViewById(R.id.editTextEditPhone);
        imageViewEditProfilePicture = (ImageView) findViewById(R.id.imageViewEditProfilePicture);
        saveEditBtn = (Button) findViewById(R.id.saveEditBtn);
        cancelEditBtn = (Button) findViewById(R.id.cancelEditBtn);
        progressBarEditProfile = (ProgressBar) findViewById(R.id.progressBarEditProfile);
    }
}