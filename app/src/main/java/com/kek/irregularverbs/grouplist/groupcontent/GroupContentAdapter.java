package com.kek.irregularverbs.grouplist.groupcontent;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.data.Group;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import java.io.IOException;

public class GroupContentAdapter extends BaseAdapter {
    private static final String GROUP_CONTENT_ADAPTER = "groupContentAdapter";

    private LayoutInflater mLayoutInflater;
    private Group mGroup;
    private int mItem = R.layout.group_content_item;

    public GroupContentAdapter(Context context, Group group) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mGroup = group;

        Log.d(GROUP_CONTENT_ADAPTER, "groupContentAdapter: constructor has been called");
    }

    @Override
    public int getCount() {
        return mGroup.getGroupSize();
    }

    @Override
    public Object getItem(int position) {
        return mGroup.getGroupItems().get(position);
    }

    //returning id of element(NOT id of verb)
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View resultView;
        if (convertView != null) {
            resultView = convertView;
        } else {
            resultView = mLayoutInflater.inflate(mItem, parent, false);
        }

        Log.d(GROUP_CONTENT_ADAPTER, "getting textViews");

        TextView mainVerb = resultView.findViewById(R.id.groupContentMainVerb);
        TextView firstFormVerb = resultView.findViewById(R.id.groupContentFirstForm);
        TextView secondFormVerb = resultView.findViewById(R.id.groupContentSecondForm);
        TextView thirdFormVerb = resultView.findViewById(R.id.groupContentThirdForm);

        Log.d(GROUP_CONTENT_ADAPTER, "textViews has been gotten. Setting text");

        mainVerb.setText(getItemArray().get(position).getMainVerb());
        firstFormVerb.setText(getItemArray().get(position).getFirstForm());
        secondFormVerb.setText(getItemArray().get(position).getSecondForm());
        thirdFormVerb.setText(getItemArray().get(position).getThirdForm());

        mainVerb.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));
        firstFormVerb.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));
        secondFormVerb.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));
        thirdFormVerb.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));

        Log.d(GROUP_CONTENT_ADAPTER, "text has been set");

        return resultView;
    }

    public SparseArray<GroupVerbsDialogItem> getItemArray() {
        return mGroup.getGroupItems();
    }

    public void cleanItemsArray() {
        try {
            mGroup.deleteData();

            Log.d(GROUP_CONTENT_ADAPTER, "data has been cleaned");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(GROUP_CONTENT_ADAPTER, "data hasn't been cleaned in cause of exception");
        }
    }

    public Group getGroup() {
        return mGroup;
    }
}
