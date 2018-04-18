package com.kek.irregularverbs.grouplist.groupcontent;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.kek.irregularverbs.database.DbHelper;
import com.kek.irregularverbs.grouplist.data.Group;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GroupContentDataManager {

    GroupContentAdapter adapter;
    Context context;

    private final static String GROUP_CONTENT_MANAGER = "groupContentManager";

    public GroupContentDataManager(Context context, Group group) {
        adapter = new GroupContentAdapter(context, group);
        this.context = context;

        try {
            Log.d(GROUP_CONTENT_MANAGER, "constructor: loading data");

            loadData();

            Log.d(GROUP_CONTENT_MANAGER, "constructor: data has been successfully loaded");
        } catch (IOException e) {
            Log.d(GROUP_CONTENT_MANAGER, "constructor: data hasn't been loaded. Exception at constructor: " + e);
            e.printStackTrace();
            Toast.makeText(context, "No words found", Toast.LENGTH_SHORT).show();

        }
    }

    public void setResource(String resource) {
        adapter.cleanItemsArray();
        SparseArray<GroupVerbsDialogItem> items = adapter.getItemArray();

        Log.d(GROUP_CONTENT_MANAGER, "sparseArray has been loaded");

        char[] chars = resource.toCharArray();
        StringBuilder cash = new StringBuilder();
        int key = 0;
        for (char ch : chars) {
            if (ch != '&') {
                cash.append(ch);
            } else {
                int id = Integer.parseInt(cash.toString());

                boolean isContains = false;
                for (int i = 0; i < items.size(); i++) {
                    if (items.valueAt(i).getId() == id) {
                        isContains = true;
                    }
                }

                if (!isContains) {
                    items.put(key, new GroupVerbsDialogItem(new DbHelper(context), id));
                    key++;
                }

                cash = new StringBuilder();
            }
        }

        Log.d(GROUP_CONTENT_MANAGER, "resources has been set");


        try {
            saveData();

            Log.d(GROUP_CONTENT_MANAGER, "data has been saved");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(GROUP_CONTENT_MANAGER, "data hasn't been saved. Exception " + e);
        }
    }

    public GroupContentAdapter getAdapter() {
        return adapter;
    }

    public void loadData() throws IOException {
        try {
            FileInputStream fileInStream = context.openFileInput(adapter.getGroup().getGroupName() + ".group");
            InputStreamReader inputRead = new InputStreamReader(fileInStream);
            Log.d(GROUP_CONTENT_MANAGER, "input stream has been successfully created & initialised");

            char[] inputBuffer = new char[100];
            int charRead;
            List<Character> cashListOfChar = new ArrayList<>();

            while ((charRead = inputRead.read(inputBuffer)) > 0) {
                for (char ch : String.copyValueOf(inputBuffer, 0, charRead).toCharArray()) {
                    cashListOfChar.add(ch);
                }
            }
            Log.d(GROUP_CONTENT_MANAGER, "content has been loaded");

            StringBuilder cash = new StringBuilder();
            int key = 0;
            for (char ch : cashListOfChar) {
                if (ch != '&') {
                    cash.append(ch);
                } else {
                    adapter.getItemArray().put(key, new GroupVerbsDialogItem(new DbHelper(context), Integer.parseInt(cash.toString())));
                    key++;
                    cash = new StringBuilder();
                }
            }
            Log.d(GROUP_CONTENT_MANAGER, "content has been converted");

            inputRead.close();
            fileInStream.close();
            Log.d(GROUP_CONTENT_MANAGER, "stream has been closed");


        } catch (IOException e) {
            Log.d(GROUP_CONTENT_MANAGER, "Exception in loadData method: " + e);
            throw e;
        }
    }

    public void saveData() throws IOException {
        try {
            FileOutputStream fileOutStream = context.openFileOutput(adapter.getGroup().getGroupName() + ".group", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutStream);
            Log.d(GROUP_CONTENT_MANAGER, "output stream has been created, writing data");

            SparseArray<GroupVerbsDialogItem> items = adapter.getItemArray();
            for (int i = 0; i < items.size(); i++) {
                outputWriter.write(items.valueAt(i).getId() + "&");
            }

            Log.d(GROUP_CONTENT_MANAGER, "data has been successfully written");

            outputWriter.close();
            fileOutStream.close();
            Log.d(GROUP_CONTENT_MANAGER, "output stream has benn closed");

        } catch (IOException e) {
            Log.d(GROUP_CONTENT_MANAGER, "Exception in saveData method: " + e);
            throw e;
        }
    }

    public void deleteVerb(int index) {
        try {
            Log.d(GROUP_CONTENT_MANAGER, "deleteGroup: deleting group");

            adapter.getItemArray().remove(index);

            Log.d(GROUP_CONTENT_MANAGER, "deleteGroup: group has been deleted. Saving data");

            saveData();

            Log.d(GROUP_CONTENT_MANAGER, "deleteGroup: data has been successfully saved");
        } catch (IOException e) {
            Log.d(GROUP_CONTENT_MANAGER, "deleteGroup: data hasn't been saved. Exception: " + e);
            e.printStackTrace();
        }
    }
}
