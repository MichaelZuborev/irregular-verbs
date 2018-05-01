package com.kek.irregularverbs.grouplist;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;


import static android.content.Context.MODE_PRIVATE;

//this class init default groups list
public class DefaultGroupsInitializer {

    private static final String DEFAULT_GROUP_INITIALISER = "GroupsInitializer";
    private static final ArrayList<String> DEFAULT_GROUP_ARRAY = new ArrayList<String>() {
        {
            add("-ed -en&");
            add("No form change&");
            add("-t -ght&");
            add("-en&");
        }
    };
    private static final HashMap<String, int[]> ID_MAP = new HashMap<String, int[]>() {{
        put("-ed -en&", new int[]{42, 84, 156, 184, 230});
        put("-en&", new int[]{2, 3, 4, 6, 8, 10, 11, 19, 20, 22, 23, 25, 29, 33, 42, 43, 57, 59
                , 60, 70, 72, 74, 75, 76, 78, 82, 84, 91, 98, 99, 100, 121, 126, 139, 145, 147, 150
                , 152, 156, 169, 171, 173, 177, 183, 184, 193, 205, 207, 219, 224, 227, 230, 233
                , 238, 241, 246, 247, 251, 254, 263});
        put("-t -ght&", new int[]{13, 15, 16, 34, 35, 38, 41, 47, 50, 55, 58, 62, 63, 67, 81
                , 101, 102, 107, 108, 110, 111, 114, 115, 118, 120, 125, 131, 149, 159, 163
                , 178, 180, 199, 204, 209, 210, 214, 215, 229, 234, 237, 242, 255});
        put("No form change&", new int[]{0, 18, 21, 32, 36, 37, 40, 46, 49
                , 71, 92, 94, 96, 97, 103, 112, 124, 129, 133, 157, 158, 160, 167, 170, 174
                , 181, 186, 191, 194, 203, 213, 216, 243, 250, 256});
    }};

    private Context mContext;

    public DefaultGroupsInitializer(Context context) {
        mContext = context;
        initialiseGroupsList();
    }

    private void initialiseGroupsList() {
        try {
            File file = new File(mContext.getFilesDir(), "groups.list");

            if (file.exists()) {
                Log.d(DEFAULT_GROUP_INITIALISER, "File already exists");
                return;
            }

            FileOutputStream fileOutStream = mContext.openFileOutput(file.getName(), MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutStream);

            Log.d(DEFAULT_GROUP_INITIALISER, "output stream has been successfully created & initialised");

            Log.d(DEFAULT_GROUP_INITIALISER, "Writing groups");
            for (String group : DEFAULT_GROUP_ARRAY) {
                outputWriter.write(group);
                Log.d(DEFAULT_GROUP_INITIALISER, "Saved group " + group);
            }

            outputWriter.close();
            fileOutStream.close();

            Log.d(DEFAULT_GROUP_INITIALISER, "output stream has been successfully closed");

            for (String group : DEFAULT_GROUP_ARRAY) {
                initialiseGroupsContent(group);
                Log.d(DEFAULT_GROUP_INITIALISER, group + " content has been loaded");
            }

        } catch (Exception e) {
            Log.e(DEFAULT_GROUP_INITIALISER, "Exception is " + e);
        }
    }

    private void initialiseGroupsContent(String groupName) {
        try {
            FileOutputStream fileOutStream = mContext.openFileOutput(groupName.substring(0, groupName.length() - 1) + ".group", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutStream);
            Log.d(DEFAULT_GROUP_INITIALISER, "output stream has been created, writing data");

            int[] idArray = ID_MAP.get(groupName);
            for (int id : idArray) {
                outputWriter.write(id + "&");
            }

            Log.d(DEFAULT_GROUP_INITIALISER, "data has been successfully written");

            outputWriter.close();
            fileOutStream.close();
            Log.d(DEFAULT_GROUP_INITIALISER, "output stream has benn closed");

        } catch (IOException e) {
            Log.d(DEFAULT_GROUP_INITIALISER, "Exception in initialiseGroupsContent method: " + e);
        }
    }
}
