package com.example.squadzframes.ui.intro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.squadzframes.ui.home.MainActivity;
import com.example.squadzframes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailEdit, passwordEdit;
    private Switch switch1;

    private String textUser, textPass;
    private Button sign_up_button;
    private Button login_button;
    private ProgressDialog loginProgress;

    private boolean switchOnOff;
    public static final String SHARED_PREFs = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String TEXTPASS = "textPass";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__page);

        mAuth = FirebaseAuth.getInstance();

        login_button = findViewById(R.id.login_button);
        sign_up_button = findViewById(R.id.sign_up_button);
        loginProgress = new ProgressDialog(this);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "true");
        editor.apply();

        //initialize the views that we want to keep
        emailEdit = (EditText) findViewById(R.id.editLoginEmail);
        passwordEdit = (EditText) findViewById(R.id.loginPassword);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEdit.getEditableText().toString();
                String pass = passwordEdit.getEditableText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

                    loginProgress.setTitle("Logging in");
                    loginProgress.setMessage("Please wait while we check your credentials.");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();

                    loginUser(email,pass);
                }
                // loadData();
                //updateViews();
            }
        });


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpIntent = new Intent(Login_Page.this, SignUpPage.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginProgress.dismiss();
                            Intent send = new Intent(Login_Page.this, MainActivity.class);
                            send.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(send);
                            //updateUI(user);
                        } else {
                            loginProgress.hide();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_Page.this, "Cannot Login in. Please check the form and try again.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}