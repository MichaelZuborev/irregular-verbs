package com.kek.irregularverbs.verblsit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.kek.irregularverbs.R;

public class VerbListActivity extends AppCompatActivity {



    private static final String VERB_LIST_ACTIVITY = "VerbListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verb_list);

        ListView listView = findViewById(R.id.list_view_verb_list);

        VerbListAdapter listAdapter = new VerbListAdapter(this);
        listView.setAdapter(listAdapter);
    }
}
