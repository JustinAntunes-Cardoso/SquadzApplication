package com.example.squadzframes.ui.intro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.squadzframes.ui.home.MainActivity;
import com.example.squadzframes.R;
import com.example.squadzframes.model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

//import com.example.firebaseapp.model.UserInformation;
//import com.example.squadzframes.firebase.realTimeDatabase.RealtimeDatabaseManager;


public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText username, fullName, email, password, date;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    private ProgressDialog signUpProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = database.getReference("Users");

        signUpProgress = new ProgressDialog(this);

        username = findViewById(R.id.editText_firstname);
        fullName = findViewById(R.id.editText_lastname);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        date = findViewById(R.id.editText_dob);


        Button btn = (Button) findViewById(R.id.sign_Up_Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    signUpProgress.setTitle("Registering User");
                    signUpProgress.setMessage("Please wait while we create your account !");
                    signUpProgress.setCanceledOnTouchOutside(false);
                    signUpProgress.show();

                    //registerUser();
                    register_user();


               // sendData();
            }
        });
    }

    private void register_user() {

        String sUsername, sFullname, sEmail, sPassword, sDate;
        sUsername = username.getText().toString().trim();
        sFullname = fullName.getText().toString().trim();
        sEmail = email.getText().toString().trim();
        sPassword = password.getText().toString().trim();
        sDate = date.getText().toString().trim();

        if(sEmail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }

        if(sPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(sPassword.length()< 6){
            password.setError("minumum pass length should be 6 characters");
            password.requestFocus();
        }

        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            database = FirebaseDatabase.getInstance();
                            DatabaseReference dataRef = database.getReference().child("Users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("name", sUsername + " " +sFullname);
                            userMap.put("dob", sDate);
                            userMap.put("email", sEmail);
                            userMap.put("password", sPassword);
                            userMap.put("image","default");
                            userMap.put("background","default");
                            userMap.put("status","Hello, I'm using Squadz. Let's play ball!");

                            dataRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        signUpProgress.dismiss();

                                        Intent mainIntent = new Intent(SignUpPage.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                }
                            });

                            //updateUI(user);
                        } else {
                            signUpProgress.hide();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpPage.this, "Cannot Sign in. Please check the form and that password is 6 or more char. Try again.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

    private void sendData() {
        String sUsername, sFullname, sEmail, sPassword, sDate;

        sUsername = username.getText().toString();
        sFullname = fullName.getText().toString();
        sEmail = email.getText().toString();
        sPassword = password.getText().toString();
        sDate = date.getText().toString();

        String id = databaseReference.push().getKey();

        UserInformation personInfo = new UserInformation(sUsername, sFullname, sEmail, sPassword, sDate);

        databaseReference.child(id).setValue(personInfo);

    }
}