package com.kek.irregularverbs.verblsit;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.database.DbHelper;
import com.kek.irregularverbs.verblsit.data.VerbListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerbListAdapter extends BaseAdapter {
    private static final String VERBLIST_ADAPTER = "verbListAdapter";
    private static final int DB_SIZE = 100;

    private List<VerbListItem> mItemList;
    private LayoutInflater mLayoutInflater;

    public VerbListAdapter(Context context) {
        mItemList = new ArrayList<>();

        Log.d(VERBLIST_ADAPTER, "Initialising inflater");
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d(VERBLIST_ADAPTER, "LayoutInflater has been created");

        readData(context);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mResultView;

        if (convertView != null) {
            mResultView = convertView;
        } else {
            mResultView = mLayoutInflater.inflate(R.layout.verb_list_item, parent, false);
        }

        VerbListItem mItem = mItemList.get(position);

        Log.d(VERBLIST_ADAPTER, "creation of text views with verbs");
        TextView mMainVerb = mResultView.findViewById(R.id.verbListMainVerb);
        TextView mFirstForm = mResultView.findViewById(R.id.verbListFirstForm);
        TextView mSecondForm = mResultView.findViewById(R.id.verbListSecondForm);
        TextView mThirdForm = mResultView.findViewById(R.id.verbListThirdForm);

        Log.d(VERBLIST_ADAPTER, "text views have been created. Starting setting text");

        mMainVerb.setText(mItem.getMainVerb());
        mFirstForm.setText(mItem.getFirstForm());
        mSecondForm.setText(mItem.getSecondForm());
        mThirdForm.setText(mItem.getThirdForm());

        Log.d(VERBLIST_ADAPTER, "text has been set");

        return mResultView;
    }

    //This method provides access to db
    private void readData(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        Log.d(VERBLIST_ADAPTER, "DBHelper has been created and inflater has been initialised. Inflating mItemList");
        try {
            for (int i = 1; i <= DB_SIZE; i++) {

                try {
                    dbHelper.createDataBase();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                SQLiteDatabase database = dbHelper.getReadableDatabase();
                Cursor cursor = dbHelper.getCursor(i);

                String mainVerb = cursor.getString(1);
                String firstFormVerb = cursor.getString(2);
                String secondFormVerb = cursor.getString(3);
                String thirdFormVerb = cursor.getString(4);

                cursor.close();
                database.close();

                mItemList.add(new VerbListItem(mainVerb, firstFormVerb, secondFormVerb, thirdFormVerb, i));

            }

            Log.d(VERBLIST_ADAPTER, "mItemList has been successfully inflated");
        } catch (Exception e) {
            Log.e(VERBLIST_ADAPTER, "exception at mItemList inflating process. Exception is " + e.toString());
            e.printStackTrace();
        }


    }
}
