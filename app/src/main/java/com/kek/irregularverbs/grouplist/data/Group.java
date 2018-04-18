package com.kek.irregularverbs.grouplist.data;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.kek.irregularverbs.database.DbHelper;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class Group {
    private static final String GROUP_LOG = "Group";

    //it is marked as final to use it as a file name
    private final String mGroupName;

    private final Context context;

    private DbHelper dbHelper;

    private SparseArray<GroupVerbsDialogItem> mGroupItems;

    public Group(Context context, String groupName) {
        mGroupName = groupName;
        mGroupItems = new SparseArray<>();
        this.context = context;
        dbHelper = new DbHelper(context);

        //loading saved data
        try {
            Log.d(GROUP_LOG, "trying to load saved data");

            loadData();

            Log.d(GROUP_LOG, "loading complete");
        } catch (FileNotFoundException e) {
            //it is supposed to be a new group
            Log.e(GROUP_LOG, "File not found");
        } catch (IOException e) {
            Log.e(GROUP_LOG, "Data hasn't been loaded. Exception at constructor: " + e);
            e.printStackTrace();


            Toast.makeText(context, "Exceptional situation. Data has been corrupted.", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Please don't panic and delete this group!", Toast.LENGTH_SHORT).show();
        }
    }

    public Integer getGroupSize() {
        return mGroupItems.size();
    }

    private void saveData() throws IOException {
        try {
            FileOutputStream fileOutStream = context.openFileOutput(mGroupName + ".group", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutStream);
            Log.d(GROUP_LOG, "output stream has been created");

            writeSet(outputWriter);
            Log.d(GROUP_LOG, "writeSet method has been successfully called");

            outputWriter.close();
            fileOutStream.close();
            Log.d(GROUP_LOG, "output stream has benn closed");

        } catch (IOException e) {
            Log.e(GROUP_LOG, "Exception in saveData method: " + e);
            throw e;
        }
    }

    private void writeSet(OutputStreamWriter writer) throws IOException {
        Log.d(GROUP_LOG, "writeSet method begins");

        //saving data
        for (int i = 0; i < mGroupItems.size(); i++) {
            mGroupItems.valueAt(i);
            writer.write(mGroupItems.valueAt(i).getId() + "&");
        }

        Log.d(GROUP_LOG, "writeSet method complete");
    }

    private void loadData() throws IOException {
        try {
            FileInputStream fileInStream = context.openFileInput(mGroupName + ".group");
            InputStreamReader inputRead = new InputStreamReader(fileInStream);
            Log.d(GROUP_LOG, "input stream has been successfully created & initialised. File name is: " + mGroupName);

            char[] inputResult = new char[500];
            inputRead.read(inputResult);
            Log.d(GROUP_LOG, "content has been loaded.");

            StringBuilder cash = new StringBuilder();
            int key = 0;
            for (char ch : inputResult) {
                if (ch != '&') {
                    cash.append(ch);
                } else {
                    Log.d(GROUP_LOG, "Creating item with id: " + Integer.parseInt(cash.toString()));
                    mGroupItems.put(key, new GroupVerbsDialogItem(dbHelper, Integer.parseInt(cash.toString())));
                    key++;
                    cash = new StringBuilder();
                }
            }
            Log.d(GROUP_LOG, "content has been converted to objects");


            inputRead.close();
            fileInStream.close();
            Log.d(GROUP_LOG, "stream has been closed");


        } catch (IOException e) {
            Log.e(GROUP_LOG, "Exception in loadData method: " + e);
            throw e;
        }
    }

    //clean group
    public void deleteData() throws IOException {
        mGroupItems = new SparseArray<>();

        FileOutputStream fileOutStream = context.openFileOutput(mGroupName + ".txt", MODE_PRIVATE);
        fileOutStream.write(new byte[1]);
        fileOutStream.close();

        saveData();

        Log.d(GROUP_LOG, "data has been deleted");
    }

    @Override
    public String toString() {
        return  mGroupName;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public SparseArray<GroupVerbsDialogItem> getGroupItems() {
        return mGroupItems;
    }
}
