package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.jobportalapp.model.DataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class InsertJobPostActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";


    //Toolbar
    private Toolbar toolbar;

    //Edit text
    private EditText mJob_title;
    private EditText mJobDescription;
    private EditText mJob_skill;
    private EditText mJob_salary;

    //BUtton
    private Button mJob_Post_Button;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabasePost,mPublicDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_job_post);



        toolbar = findViewById(R.id.xInsertJobPostToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Firebase connections
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();// i get current user id

        String userid = mUser.getUid();

        mDatabasePost = FirebaseDatabase.getInstance().getReference().child("Job Post").child(userid);

        mPublicDatabase = FirebaseDatabase.getInstance().getReference().child("Public database");

        InsertJob();
    }

    private void InsertJob() {


        //Creating refference
        mJob_title = findViewById(R.id.xJobTitle_Post);
        mJobDescription = findViewById(R.id.xJobDesc_Post);
        mJob_skill = findViewById(R.id.xJobSkill_Post);
        mJob_salary = findViewById(R.id.xJobSalary_Post);


        mJob_Post_Button = findViewById(R.id.xJob_post_button);

        mJob_Post_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = mJob_title.getText().toString().trim();
                String description = mJobDescription.getText().toString().trim();
                String skill = mJob_skill.getText().toString().trim();
                String salary = mJob_salary.getText().toString().trim();


                // check all the  filed is empty ?  then return false
                if (TextUtils.isEmpty(title)) {
                    mJob_title.setError("Required field..");
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    mJobDescription.setError("Required field..");
                    return;
                }
                if (TextUtils.isEmpty(skill)) {
                    mJob_skill.setError("Required field..");
                    return;
                }
                if (TextUtils.isEmpty(salary)) {
                    mJob_salary.setError("Required field..");
                    return;
                } else {
                    // check user enter string or integer
                    try {

                        double dSalary = Double.parseDouble(salary);
                        Log.d(TAG, String.valueOf(dSalary));

                    } catch (Exception e) {
                        mJob_salary.setError("Invalid Values..");
                        return;
                    }

                }

                double doubleSalary = Double.parseDouble(salary);
                String id = mDatabasePost.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());

//                 public DataModel(String dtitle, String ddescription, String dskill, double dsalary, String did, String ddate)

                DataModel data = new DataModel(title, description, skill, doubleSalary, id, date);

                mPublicDatabase.child(id).setValue(data);

                mDatabasePost.child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();
                        startActivity( new Intent(getApplicationContext(),PostJobActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

}