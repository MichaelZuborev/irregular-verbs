package com.kek.irregularverbs.practice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.data.Group;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


//this class is supposed to be responsible for loading data
public class PracticeGroupListDataManager {
    private Context mContext;
    private PracticeGroupListAdapter mAdapter;

    private final static String PRACTICE_GROUP_LIST_MANAGER = "practGroupListManager";

    public PracticeGroupListDataManager(Context context) {
        this.mContext = context;
        mAdapter = new PracticeGroupListAdapter(context, R.layout.group);

        try {
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "constructor: loading data");

            loadData();

            Log.d(PRACTICE_GROUP_LIST_MANAGER, "constructor: data has been successfully loaded");
        } catch (IOException e) {
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "constructor: data hasn't been loaded. Exception at constructor: " + e);
            e.printStackTrace();
            Toast.makeText(context, "No groups found", Toast.LENGTH_SHORT).show();

        }
    }


    public void loadData() throws IOException {
        try {
            FileInputStream fileInStream = mContext.openFileInput("groups.list");
            InputStreamReader inputRead = new InputStreamReader(fileInStream);
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "input stream has been successfully created & initialised");

            char[] inputBuffer = new char[100];
            int charRead;
            List<Character> cashListOfChar = new ArrayList<>();

            while ((charRead = inputRead.read(inputBuffer)) > 0) {
                for (char ch : String.copyValueOf(inputBuffer, 0, charRead).toCharArray()) {
                    cashListOfChar.add(ch);
                }
            }
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "content has been loaded");

            StringBuilder cash = new StringBuilder();
            for (char ch : cashListOfChar) {
                if (ch != '&') {
                    cash.append(ch);
                } else {
                    Log.d(PRACTICE_GROUP_LIST_MANAGER, "Creating group " + cash.toString());
                    mAdapter.getGroups().add(new Group(mContext, cash.toString()));
                    cash = new StringBuilder();
                }
            }
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "content has been converted");

            inputRead.close();
            fileInStream.close();
            Log.d(PRACTICE_GROUP_LIST_MANAGER, "stream has been closed");


        } catch (IOException e) {
            Log.e(PRACTICE_GROUP_LIST_MANAGER, "Exception in loadData method: " + e);
            throw e;
        }
    }

    public PracticeGroupListAdapter getAdapter() {
        return mAdapter;
    }

}