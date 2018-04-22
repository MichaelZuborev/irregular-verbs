package com.kek.irregularverbs.grouplist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.data.Group;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

//this class is supposed to be responsible for saving and loading data
public class GroupListDataManager {
    private Context mContext;
    private GroupListAdapter mAdapter;

    private final static String GROUP_LIST_MANAGER = "groupListManager";

    public GroupListDataManager(Context context) {
        this.mContext = context;
        mAdapter = new GroupListAdapter(context, R.layout.group);

        try {
            Log.d(GROUP_LIST_MANAGER, "constructor: loading data");

            loadData();

            Log.d(GROUP_LIST_MANAGER, "constructor: data has been successfully loaded");
        } catch (IOException e) {
            Log.d(GROUP_LIST_MANAGER, "constructor: data hasn't been loaded. Exception at constructor: " + e);
            e.printStackTrace();
        }
    }

    private void saveData() throws IOException {
        try {
            FileOutputStream fileOutStream = mContext.openFileOutput("groups.list", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutStream);
            Log.d(GROUP_LIST_MANAGER, "output stream has been created");

            writeList(outputWriter);
            Log.d(GROUP_LIST_MANAGER, "writeSet method has been successfully called");

            outputWriter.close();
            fileOutStream.close();
            Log.d(GROUP_LIST_MANAGER, "output stream has been closed");

        } catch (IOException e) {
            Log.e(GROUP_LIST_MANAGER, "Exception in saveData method: " + e);
            throw e;
        }
    }

    //save groups by their names
    private void writeList(OutputStreamWriter writer) throws IOException {
        Log.d(GROUP_LIST_MANAGER, "writeList method begins");

        for (Group group : mAdapter.getGroups()) {
            Log.d(GROUP_LIST_MANAGER,"Writing group " + group.getGroupName());
            writer.write(group.getGroupName() + "&");
        }

        Log.d(GROUP_LIST_MANAGER, "writeList method complete");
    }

    public void loadData() throws IOException {
        try {
            FileInputStream fileInStream = mContext.openFileInput("groups.list");
            InputStreamReader inputRead = new InputStreamReader(fileInStream);
            Log.d(GROUP_LIST_MANAGER, "input stream has been successfully created & initialised");

            char[] inputBuffer = new char[100];
            int charRead;
            List<Character> cashListOfChar = new ArrayList<>();

            while ((charRead = inputRead.read(inputBuffer)) > 0) {
                for (char ch : String.copyValueOf(inputBuffer, 0, charRead).toCharArray()) {
                    cashListOfChar.add(ch);
                }
            }
            Log.d(GROUP_LIST_MANAGER, "content has been loaded");

            StringBuilder cash = new StringBuilder();
            for (char ch : cashListOfChar) {
                if (ch != '&') {
                    cash.append(ch);
                } else {
                    Log.d(GROUP_LIST_MANAGER, "Creating group " + cash.toString());
                    mAdapter.getGroups().add(new Group(mContext, cash.toString()));
                    cash = new StringBuilder();
                }
            }
            Log.d(GROUP_LIST_MANAGER, "content has been converted");

            inputRead.close();
            fileInStream.close();
            Log.d(GROUP_LIST_MANAGER, "stream has been closed");


        } catch (IOException e) {
            Log.e(GROUP_LIST_MANAGER, "Exception in loadData method: " + e);
            throw e;
        }
    }

    public GroupListAdapter getAdapter() {
        return mAdapter;
    }

    public void addNewGroup(String name) {
        try {
            Log.d(GROUP_LIST_MANAGER, "addNewGroup: adding group");

            //Check of existence of the group
            boolean mIsExist = false;
            for (int i = 0; i < mAdapter.getGroups().size(); i++) {
                if(mAdapter.getGroups().get(i).getGroupName().equals(name)){
                    mIsExist = true;
                }
            }

            if(!mIsExist) {
                mAdapter.getGroups().add(new Group(mContext, name));
            }else {
                Log.d(GROUP_LIST_MANAGER, "Group with such name already exists");
                Toast.makeText(mContext, "Group with such name already exists", Toast.LENGTH_SHORT).show();
            }


            Log.d(GROUP_LIST_MANAGER, "addNewGroup: group has been added. Saving data");

            saveData();

            Log.d(GROUP_LIST_MANAGER, "addNewGroup: data has been successfully saved");
        } catch (IOException e) {
            Log.d(GROUP_LIST_MANAGER, "addNewGroup: data hasn't been saved. Exception: " + e);

            e.printStackTrace();

            Toast.makeText(mContext, "Exceptional situation at saving changes.", Toast.LENGTH_SHORT).show();
            Toast.makeText(mContext, "Data might become corrupted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteGroup(int index) {
        try {
            Log.d(GROUP_LIST_MANAGER, "deleteGroup: deleting group");

            mAdapter.getGroups().remove(index);

            Log.d(GROUP_LIST_MANAGER, "deleteGroup: group has been deleted. Saving data");

            saveData();

            Log.d(GROUP_LIST_MANAGER, "deleteGroup: data has been successfully saved");
        } catch (IOException e) {
            Log.d(GROUP_LIST_MANAGER, "deleteGroup: data hasn't been saved. Exception: " + e);
            e.printStackTrace();
        }
    }

}

