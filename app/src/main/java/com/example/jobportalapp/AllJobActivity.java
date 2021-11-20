package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jobportalapp.model.DataModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AllJobActivity extends AppCompatActivity {


    //Tool bar
    private Toolbar allPost;


    // Recyler View
    private RecyclerView mAllpostjobrecylerview;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);



        allPost = findViewById(R.id.allposttoolbar);
        setSupportActionBar(allPost);
        getSupportActionBar().setTitle("All Job Post");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String userId = mUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Public database");
        databaseReference.keepSynced(true);


        mAllpostjobrecylerview = findViewById(R.id.xAllpostjobrecylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        mAllpostjobrecylerview.setHasFixedSize(true);
        mAllpostjobrecylerview.setLayoutManager(linearLayoutManager);




    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<DataModel, AllJobViewHolder> adp = new FirebaseRecyclerAdapter<DataModel, AllJobViewHolder>(

                DataModel.class,
                R.layout.alljobpost,
                AllJobViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(AllJobViewHolder allJobViewHolder, DataModel dataModel, int i) {



                allJobViewHolder.setAllJobTitle(dataModel.getDtitle());
                allJobViewHolder.setAllJobDescription(dataModel.getDdescription());
                allJobViewHolder.setAllJobDate(dataModel.getDdate());
                allJobViewHolder.setAllJobSalary(dataModel.getDsalary());
                allJobViewHolder.setAllJobSkill(dataModel.getDskill());



                allJobViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                        intent.putExtra("title", dataModel.getDtitle());
                        intent.putExtra("date", dataModel.getDdate());
                        intent.putExtra("description", dataModel.getDdescription());
                        intent.putExtra("skill", dataModel.getDskill());
                        intent.putExtra("salary", dataModel.getDsalary());
                        startActivity(intent);

                    }
                });

            }
        };




        mAllpostjobrecylerview.setAdapter(adp);


    }


    public static class AllJobViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public AllJobViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setAllJobTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.xAllJobTitle);
            mTitle.setText(title);
        }

        public void setAllJobDescription(String desc) {
            TextView mDesc = mView.findViewById(R.id.xAllJobDescription);
            mDesc.setText(desc);
        }


        public void setAllJobSkill(String skill) {
            TextView mSkill = mView.findViewById(R.id.xAllJobSkill);
            mSkill.setText(skill);
        }

        public void setAllJobDate(String date) {
            TextView mDate = mView.findViewById(R.id.xAllJobDate);
            mDate.setText(date);
        }

        public void setAllJobSalary(double salary) {
            TextView mSalary = mView.findViewById(R.id.xAllJobsalary);
            int intSalary = Integer.parseInt(String.valueOf(Math.round(salary)));
            String doubleSalary = String.valueOf(intSalary);
            mSalary.setText(doubleSalary);
        }


    }
}