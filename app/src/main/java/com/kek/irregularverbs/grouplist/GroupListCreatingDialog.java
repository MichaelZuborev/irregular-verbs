package com.kek.irregularverbs.grouplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kek.irregularverbs.R;


public class GroupListCreatingDialog extends AppCompatActivity {
    private static final String GROUP_CREATING_DIALOG = "GroupCreatingDialog";

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_create_dialog);
        editText = findViewById(R.id.group_list_dialog_input);

        setTitle("Create group");
    }

    public void onButtonClickListener(View v) {
        switch (v.getId()) {
            case R.id.group_list_cancel_btn:

                Log.d(GROUP_CREATING_DIALOG, "\"Cancel\" btn has been pushed");

                this.finish();
                break;
            case R.id.group_list_create_btn:
                String name = editText.getText().toString();

                Log.d(GROUP_CREATING_DIALOG, "\"Create\" btn has been pushed");

                //check name for mistakes
                if (name.isEmpty()) {
                    editText.setHint("Input a name");
                } else if (name.length() > 20) {
                    editText.setHint("Name length > 20 symbols");
                } else {

                    Log.d(GROUP_CREATING_DIALOG, "Got name " + name);

                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    setResult(RESULT_OK, intent);

                    this.finish();
                }
                break;
        }
    }
}