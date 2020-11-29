package com.example.portfolio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ProfileActivity extends AppCompatActivity  {
    Toolbar toolbarAll;
    TextView textViewProfileName, textViewProfileDescription;
    Button contactBtn;
    ImageView imageViewProfilePicture;
    RecyclerView recyclerViewProfile;
    ProfileAdapter profileAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String userID;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbarAll = (Toolbar) findViewById(R.id.toolbarAll);
        toolbarAll.setTitle("Profile");
        setSupportActionBar(toolbarAll);
        toolbarAll.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        recyclerViewProfile = (RecyclerView) findViewById(R.id.recyclerViewProfile);
        recyclerViewProfile.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        profileAdapter = new ProfileAdapter();
        recyclerViewProfile.setAdapter(profileAdapter);

        textViewProfileName = (TextView) findViewById(R.id.textViewProfileName);
        textViewProfileDescription = (TextView) findViewById(R.id.textViewProfileDescription);
        contactBtn = (Button) findViewById(R.id.contactBtn);
        imageViewProfilePicture = (ImageView) findViewById(R.id.imageViewProfilePicture);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                textViewProfileName.setText(value.getString("Full Name"));
            }
        });


    }
}