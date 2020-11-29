package com.example.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrivacyPolicy extends AppCompatActivity {
    Toolbar toolbarAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        toolbarAll = (Toolbar) findViewById(R.id.toolbarAll);
        toolbarAll.setTitle("Privacy Policy");
        setSupportActionBar(toolbarAll);
        toolbarAll.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });
    }
}