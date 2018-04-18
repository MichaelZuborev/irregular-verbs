package com.kek.irregularverbs.grouplist.groupcontent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.kek.irregularverbs.R;

public class GroupContentDialogDeleting extends AppCompatActivity {
    private static final String GROUP_CONTENT_DIALOG_DELETING = "groupContDialDel";

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_content_deleting_dialog);

        setTitle("Delete verb");
    }

    public void onButtonClickListener(View v) {
        Log.d(GROUP_CONTENT_DIALOG_DELETING,"groupContDialDel: button has been clicked");

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.group_list_no_btn:
                setResult(RESULT_CANCELED, intent);

                Log.d(GROUP_CONTENT_DIALOG_DELETING,"cancel btn");

                this.finish();
                break;
            case R.id.group_list_delete_btn:
                setResult(RESULT_OK, intent);

                Log.d(GROUP_CONTENT_DIALOG_DELETING,"delete btn");
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            id = data.getStringExtra("id");

            Log.d(GROUP_CONTENT_DIALOG_DELETING,"grConDialDel: id is " + id);
        }
    }
}