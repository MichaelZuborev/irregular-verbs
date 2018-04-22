package com.kek.irregularverbs.grouplist;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.data.Group;

import java.util.ArrayList;
import java.util.List;


public class GroupListAdapter extends BaseAdapter {
    private static final String GROUP_LIST_ADAPTER = "groupListAdapter";
    private LayoutInflater layInflater;
    private int resource;

    private List<Group> groupList;

    public GroupListAdapter(Context context, @LayoutRes int resource) {
        this.resource = resource;
        layInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        groupList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
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
            mResultView = layInflater.inflate(resource, parent, false);
        }
        Group currentGroup = groupList.get(position);

        TextView groupName = mResultView.findViewById(R.id.group_name);
        TextView groupSize = mResultView.findViewById(R.id.group_size);
        Log.d(GROUP_LIST_ADAPTER, "views have been found");

        String mGroupSize;
        if (currentGroup.getGroupSize() == 1) {
            mGroupSize = currentGroup.getGroupSize() + " verb";
        } else {
            mGroupSize = currentGroup.getGroupSize() + " verbs";
        }

        groupName.setText(currentGroup.getGroupName());
        groupSize.setText(mGroupSize);
        groupName.setTextColor(mResultView.getResources().getColor(R.color.BLACK_SOFT));
        groupSize.setTextColor(mResultView.getResources().getColor(R.color.BLACK_SOFT));
        Log.d(GROUP_LIST_ADAPTER, "text has been set");

        return mResultView;
    }

    public List<Group> getGroups() {
        return groupList;
    }
}
