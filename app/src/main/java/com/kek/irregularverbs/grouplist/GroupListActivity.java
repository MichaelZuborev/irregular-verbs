package com.kek.irregularverbs.grouplist;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.kek.irregularverbs.R;

public class GroupListActivity extends AppCompatActivity {

    private static final String GROUPLISTACTIVITY_LOG = "GroupListActivity";

    public static String mGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        Toolbar toolbar = findViewById(R.id.toolbarGroupList);
        setSupportActionBar(toolbar);

        showActionBar();

        Log.d(GROUPLISTACTIVITY_LOG, "Creating fragment manager");
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d(GROUPLISTACTIVITY_LOG,"Fragment manager has been created. Starting replace fragment transaction");
        mFragmentTransaction.replace(R.id.group_list_placeholder, new GroupListFragment());
        Log.d(GROUPLISTACTIVITY_LOG,"Committing");
        mFragmentTransaction.commit();
        Log.d(GROUPLISTACTIVITY_LOG,"Committed");
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

        Log.d(GROUPLISTACTIVITY_LOG, "Back button has been pressed");
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }
}
