package com.example.jobportalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class JobDetailsActivity extends AppCompatActivity {


    //Toolbar
    private Toolbar toolbar;

    private TextView mJobTitle;
    private TextView mJobDescription;
    private TextView mJobDate;
    private TextView mJobSkill;
    private TextView mJobSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);




        toolbar = findViewById(R.id.xJobdetailstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Job Details");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mJobTitle = findViewById(R.id.xJobTitleDetails);
        mJobDate = findViewById(R.id.xJobDateDetails);
        mJobDescription = findViewById(R.id.xJobDescriptionDetails);
        mJobSkill = findViewById(R.id.xJobSkillDetails);
        mJobSalary = findViewById(R.id.xJobSalaryDetails);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");
        String skill = intent.getStringExtra("skill");
        double salary = intent.getDoubleExtra("salary",0);

        String stSalary = String.valueOf(Math.round(salary));



        mJobTitle.setText(title);
        mJobDate.setText(date);
        mJobDescription.setText(description);
        mJobSkill.setText(skill);
        mJobSalary.setText(stSalary);


    }
}