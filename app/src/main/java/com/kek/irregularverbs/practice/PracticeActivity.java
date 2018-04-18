package com.kek.irregularverbs.practice;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.GroupListFragment;

public class PracticeActivity extends AppCompatActivity {

    private static final String PRACTICEGROUPLISTACTIVITY_LOG = "pracGroupListActivity";

    private String mGroupName;

    private PracticeController mPracticeController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        Toolbar toolbar = findViewById(R.id.toolbarPracticeGroupList);
        setSupportActionBar(toolbar);

        showActionBar();

        mPracticeController = new PracticeController(this);

        Log.d(PRACTICEGROUPLISTACTIVITY_LOG, "Creating fragment manager");
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d(PRACTICEGROUPLISTACTIVITY_LOG, "Fragment manager has been created. Starting replace fragment transaction");
        mFragmentTransaction.replace(R.id.practice_group_list_placeholder, new PracticeGroupListFragment());
        Log.d(PRACTICEGROUPLISTACTIVITY_LOG, "Committing");
        mFragmentTransaction.commit();
        Log.d(PRACTICEGROUPLISTACTIVITY_LOG, "Committed");
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //No check because there is only one menu button
        onBackPressed();

        Log.d(PRACTICEGROUPLISTACTIVITY_LOG, "Back button has been pressed");
        return super.onOptionsItemSelected(item);
    }



    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public PracticeController getPracticeController() {
        return mPracticeController;
    }
}
