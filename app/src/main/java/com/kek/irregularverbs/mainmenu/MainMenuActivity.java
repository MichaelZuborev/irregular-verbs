package com.kek.irregularverbs.mainmenu;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.GroupListActivity;
import com.kek.irregularverbs.practice.PracticeActivity;
import com.kek.irregularverbs.verblsit.VerbListActivity;

public class MainMenuActivity extends AppCompatActivity {

    private static final String MAINMENUACTIVITY_LOG = "MainMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onBtnClick(View view) {
        Intent intent;

        Log.d(MAINMENUACTIVITY_LOG, "onBtnClick: id is " + view.toString());

        switch (view.getId()) {
            case R.id.quiz_btn:
                intent = new Intent(this, PracticeActivity.class);
                Log.d(MAINMENUACTIVITY_LOG, "Starting " + view.toString());
                startActivity(intent);
                break;
            case R.id.groups_btn:
                intent = new Intent(this, GroupListActivity.class);
                Log.d(MAINMENUACTIVITY_LOG, "Starting " + view.toString());
                startActivity(intent);
                break;
            case R.id.verbs_btn:
                intent = new Intent(this, VerbListActivity.class);
                Log.d(MAINMENUACTIVITY_LOG, "Starting " + view.toString());
                startActivity(intent);
                break;
            case R.id.floatingActionButtonMainMenu:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"michaelzuborev@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Irregular verbs app");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException e) {
                    Log.e(MAINMENUACTIVITY_LOG, "Client not found " + e);
                }
                break;
        }
    }
}
