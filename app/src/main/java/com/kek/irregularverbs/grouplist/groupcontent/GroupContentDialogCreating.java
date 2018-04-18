package com.kek.irregularverbs.grouplist.groupcontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import java.util.List;

public class GroupContentDialogCreating extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final static String GROUP_CONTENT_DIALOG_CREATING = "GroupContDialCreating";

    private GroupContentVerbsAddAdapter mAdapter;

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_content_creating_screen);

        mAdapter = new GroupContentVerbsAddAdapter(getApplicationContext());
        ListView view = findViewById(R.id.group_content_crating_screen_listview);
        view.setAdapter(mAdapter);
        view.setOnItemClickListener(this);

        Log.d(GROUP_CONTENT_DIALOG_CREATING, "mAdapter loaded");

        initializeTickedVerbs();

        Log.d(GROUP_CONTENT_DIALOG_CREATING, "ticked verbs has been initialized");

        mFab = findViewById(R.id.group_content_creating_dialog_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<GroupVerbsDialogItem> items = mAdapter.getTicked();
                StringBuilder ids = new StringBuilder();

                for (GroupVerbsDialogItem item : items) {
                    ids.append(item.getId());
                    ids.append("&");
                }

                Log.d(GROUP_CONTENT_DIALOG_CREATING, ids.toString());

                Intent intent = new Intent();
                intent.putExtra("result", ids.toString());

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        setTitle("Choose verbs");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(GROUP_CONTENT_DIALOG_CREATING, "onItemClick: position " + position);

        boolean isChecked = mAdapter.getItem(position).isIntentToDelete();

        Log.d(GROUP_CONTENT_DIALOG_CREATING, "onItemClick: isCheck get " + isChecked);

        CheckBox checkBox = view.findViewById(R.id.groupCreatingCheckBox);


        if (mFab.getVisibility() == View.INVISIBLE) {
            mFab.setVisibility(View.VISIBLE);

            Log.d(GROUP_CONTENT_DIALOG_CREATING, "mFab visibility set to VISIBLE");
        }else {
            Log.d(GROUP_CONTENT_DIALOG_CREATING, "mFab is already visible");
        }


        if (isChecked) {
            mAdapter.getItem(position).setIntentToDelete(false);
            checkBox.setChecked(false);

            Log.d(GROUP_CONTENT_DIALOG_CREATING, "onItemClick: isCheck became " + !isChecked);

        } else {
            mAdapter.getItem(position).setIntentToDelete(true);
            checkBox.setChecked(true);

            Log.d(GROUP_CONTENT_DIALOG_CREATING, "onItemClick: isCheck became " + !isChecked);
        }
    }

    private void initializeTickedVerbs() {
        char[] data = getIntent().getStringExtra("Ticked").toCharArray();

        Log.d(GROUP_CONTENT_DIALOG_CREATING, "initializeTickedVerbs: data is " + data);

        StringBuilder cash = new StringBuilder();
        for (char ch : data) {
            if (ch != '&') {
                cash.append(ch);
            } else {
                mAdapter.getItem((Integer.parseInt(cash.toString())) - 1).setIntentToDelete(true);
                cash = new StringBuilder();
            }
        }
    }

}