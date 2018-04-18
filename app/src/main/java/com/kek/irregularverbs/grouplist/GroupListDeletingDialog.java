package com.kek.irregularverbs.grouplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kek.irregularverbs.R;

public class GroupListDeletingDialog extends AppCompatActivity {

    //private String id;

    private static final String GROUPLIST_DELETING_DIALOG_LOG = "GroupListDeletingDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_list_deleting_dialog);

        setTitle("Delete group");
    }

    public void onButtonClickListener(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.group_list_no_btn:
                setResult(RESULT_CANCELED, intent);
                Log.d(GROUPLIST_DELETING_DIALOG_LOG, "Dialog canceled");
                this.finish();
                break;
            case R.id.group_list_delete_btn:
                setResult(RESULT_OK, intent);
                Log.d(GROUPLIST_DELETING_DIALOG_LOG, "Deleting group confirmed");
                this.finish();
                break;
        }
    }

}
