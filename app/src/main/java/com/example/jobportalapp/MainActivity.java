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

public class MainActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_main);

        //Firebaes connection
        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        // Progress Dialog Connection
        mProgressDialog = new ProgressDialog(this);

        LoginFunction();

    }

    private void LoginFunction() {

        mEmail = (TextInputLayout) findViewById(R.id.xEmailLoginTextLayout);
        mPass = (TextInputLayout) findViewById(R.id.xPasswordLoginTextLayout);

        mButtonLogin = (Button) findViewById(R.id.xLoginButtonLA);
        mButtonRegistration = (Button) findViewById(R.id.xRegitrationButtonLA);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pEmail = mEmail.getEditText().getText().toString().trim();
                String pPass = mPass.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(pEmail)){
                    mEmail.setError("field can't be empty");
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(pEmail).matches()){
                    mEmail.setError("please enter valid email address");
                    return;
                }
                mEmail.setError("");

                if(TextUtils.isEmpty(pPass)){
                    mPass.setError("field can't be empty");
                    return;
                }
                mPass.setError("");

                mProgressDialog.setMessage("Processing..");
                mProgressDialog.show();


                mAuth.signInWithEmailAndPassword(pEmail,pPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            finish();
                            mProgressDialog.dismiss();

                        }else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }

                    }
                });



            }
        });


        //go to the back Registration Activity
        mButtonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                finish();
            }
        });

    }
}