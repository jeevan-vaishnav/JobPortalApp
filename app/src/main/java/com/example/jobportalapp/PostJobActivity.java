package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.jobportalapp.model.DataModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PostJobActivity extends AppCompatActivity {


    //    floating_button_add_post
    private FloatingActionButton floatingActionButton;

    //Tool bar
    private Toolbar toolbar;

    //Recyclerview
    private RecyclerView mRecycleView_post_job;

    //Firebase..
    private FirebaseAuth mAuth;
    private DatabaseReference mJobPostDataDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);



        //tool bar set
        toolbar = findViewById(R.id.toolbar_post_job);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Job");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Firebase connection;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mJobPostDataDatabase = FirebaseDatabase.getInstance().getReference().child("Job Post").child(uid);


        //Reycler View Refference with conntection
        mRecycleView_post_job = (RecyclerView) findViewById(R.id.xRecycleView_post_job);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecycleView_post_job.setHasFixedSize(true);
        mRecycleView_post_job.setLayoutManager(linearLayoutManager);


        //Refference floating_button_add_post
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_button_add_post);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InsertJobPostActivity.class));

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DataModel, PostJobViewHolder> adp = new FirebaseRecyclerAdapter<DataModel, PostJobViewHolder>(

                DataModel.class,
                R.layout.job_post_item_data,
                PostJobViewHolder.class,
                mJobPostDataDatabase

        ) {
            @Override
            protected void populateViewHolder(PostJobViewHolder postJobViewHolder, DataModel dataModel, int i) {
                postJobViewHolder.setJobTitle(dataModel.getDtitle());
                postJobViewHolder.setJobDescription(dataModel.getDdescription());
                postJobViewHolder.setJobDate(dataModel.getDdate());
                postJobViewHolder.setJobSalary(dataModel.getDsalary());
                postJobViewHolder.setJobSkill(dataModel.getDskill());


            }
        };

        mRecycleView_post_job.setAdapter(adp);

    }


    //View Holder Class
    public static class PostJobViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PostJobViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setJobTitle(String title) {
            TextView mTitle = mView.findViewById(R.id.xJob_Title_Text_View);
            mTitle.setText(title);
        }

        public void setJobDescription(String desc) {
            TextView mDesc = mView.findViewById(R.id.xJob_Post_Description_Text_View);
            mDesc.setText(desc);
        }


        public void setJobSkill(String skill) {
            TextView mSkill = mView.findViewById(R.id.xJobSkill_Text_View);
            mSkill.setText(skill);
        }

        public void setJobDate(String date) {
            TextView mDate = mView.findViewById(R.id.xJobDate_Text_View);
            mDate.setText(date);
        }

        public void setJobSalary(double salary) {
            TextView mSalary = mView.findViewById(R.id.xSalary_Text_View);
            int intSalary = Integer.parseInt(String.valueOf(Math.round(salary)));
            String doubleSalary = String.valueOf(intSalary);
            mSalary.setText(doubleSalary);
        }


    }

}