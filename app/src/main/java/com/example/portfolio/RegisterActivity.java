package com.example.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    ImageView ImageViewLogo;
    TextView LogInBtn, privacyPolicy;
    EditText editTextName, editTextPassword,editTextEmail;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ProgressBar progressBar;
    Button RegisterBtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    InputMethodManager inputMethodManager;
    Spinner states,city;
    CheckBox checkBox;
    String userID;

    ArrayAdapter<String> adapterStates;
    ArrayAdapter<String> adapterCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String[] statesNames = {"Gujarat", "Maharastra", "Delhi", "Rajasthan", "Punjab", "UP", "MP", "Tamilnadu", "Goa", "Bihar", "Jammu-Kashmir", "Himachal"};
        String[] cityNames = {"Rajkot", "Ahmedabad", "Junagadh", "Bhavnagar", "Gandhinagar", "Jamnagar", "Kutch", "Surendranagar", "Morbi", "Surat", "Vadodara"};

        states = (Spinner) findViewById(R.id.spinnerStates);
        city = (Spinner) findViewById(R.id.spinnerCity);

        adapterStates = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, statesNames);
        adapterStates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        states.setAdapter(adapterStates);

        adapterCity = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cityNames);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapterCity);

        final String selectedState  = states.getSelectedItem().toString();
        final String selectedCity  = city.getSelectedItem().toString();

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        ImageViewLogo = (ImageView) findViewById(R.id.ImageViewLogo);
        LogInBtn = (TextView) findViewById(R.id.LogInBtn);
        privacyPolicy = (TextView) findViewById(R.id.PrivacyPolicy);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RegisterBtn = (Button) findViewById(R.id.RegisterBtn);
        checkBox = (CheckBox) findViewById(R.id.checkBoxPrivacyPolicy);
        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);

                final String email = editTextEmail.getText().toString().trim();
                final String name = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                final String gender = radioButton.getText().toString();


                if(TextUtils.isEmpty(email))
                {
                    editTextEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(name))
                {
                    editTextName.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    editTextPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(gender))
                {
                    radioButton.setError("Gender is required");
                    return;
                }
                if(!checkBox.isChecked())
                {
                    checkBox.setError("Please accept the privacy policy");
                    return;
                }
                inputMethodManager.hideSoftInputFromWindow(RegisterBtn.getWindowToken(), 0);
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Full Name", name);
                            user.put("Email", email);
                            user.put("Gender", gender);
                            user.put("Selected State", selectedState);
                            user.put("Selected City", selectedCity);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(RegisterActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PrivacyPolicy.class));
            }
        });
    }
}