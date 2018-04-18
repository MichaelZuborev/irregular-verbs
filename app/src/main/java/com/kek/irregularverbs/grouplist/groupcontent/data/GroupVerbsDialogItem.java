package com.kek.irregularverbs.grouplist.groupcontent.data;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kek.irregularverbs.database.DbHelper;

import java.io.IOException;

public class GroupVerbsDialogItem {

    private static final String GROUP_ITEM_LOG = "groupItem";

    private int mId;

    private String mMainVerb;
    private String mFirstForm;
    private String mSecondForm;
    private String mThirdForm;

    private boolean mIsIntentToDelete;


    public GroupVerbsDialogItem(DbHelper dbHelper, int id) {
        this.mId = id;

        loadData(dbHelper);
    }

    private void loadData(DbHelper dbHelper) {
        Log.d(GROUP_ITEM_LOG, "creating DB");

        try {
            dbHelper.createDataBase();

            Log.d(GROUP_ITEM_LOG, "operation succeed");
        } catch (IOException e) {
            Log.e(GROUP_ITEM_LOG, "operation failed. Exception  is" + e);
            e.printStackTrace();
        }

        Log.d(GROUP_ITEM_LOG, "creating cursor and DB");

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.getCursor(mId);

        Log.d(GROUP_ITEM_LOG, "creation of cursor and DB succeed. Initialisation of verbs");

        mMainVerb = cursor.getString(1);
        mFirstForm = cursor.getString(2);
        mSecondForm = cursor.getString(3);
        mThirdForm = cursor.getString(4);

        Log.d(GROUP_ITEM_LOG, "verbs have been initialised. Closing resources");

        cursor.close();
        database.close();

        Log.d(GROUP_ITEM_LOG, "closing succeed");
    }

    public String getMainVerb() {
        return mMainVerb;
    }

    public String getFirstForm() {
        return mFirstForm;
    }

    public String getSecondForm() {
        return mSecondForm;
    }

    public String getThirdForm() {
        return mThirdForm;
    }

    public int getId() {
        return mId;
    }

    public boolean isIntentToDelete() {
        return mIsIntentToDelete;
    }

    public void setIntentToDelete(boolean intentToDelete) {
        mIsIntentToDelete = intentToDelete;
    }
}
