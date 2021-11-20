package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout mEmail;
    private TextInputLayout mPass;

    private Button mButtonLogin;
    private Button mButtonRegistration;

    //Firebase
    private FirebaseAuth mAuth;


    // Progress Dialog
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //progress bar
        mProgressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        RegistrationFunction();


    }

    private void RegistrationFunction() {

        mEmail = (TextInputLayout) findViewById(R.id.xEmailRegTextLayout);
        mPass = (TextInputLayout) findViewById(R.id.xPasswordRegTextLayout);

        mButtonLogin = (Button) findViewById(R.id.xLoginButtonRA);
        mButtonRegistration = (Button) findViewById(R.id.xRegitrationButtonRA);

        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pEmail = mEmail.getEditText().getText().toString().trim();
                String pPass = mPass.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(pEmail)) {
                    mEmail.setError("field can't be empty");
                    return;
                }
                mEmail.setError("");

                if (!Patterns.EMAIL_ADDRESS.matcher(pEmail).matches()) {
                    mEmail.setError("please enter valid email address");
                    return;
                }

                if (TextUtils.isEmpty(pPass)) {

                    mPass.setError("field can't be empty");
                    return;
                }
                mPass.setError("");

                mProgressDialog.setMessage("Processing..");
                mProgressDialog.show();

                mAuth.createUserWithEmailAndPassword(pEmail, pPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Registration Failed ", Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }

                    }
                });


            }
        });

        //go to the back Login Activity
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }
}