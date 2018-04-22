package com.kek.irregularverbs.practice;

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

//It uses Group class from grouplist.data pocket
public class PracticeGroupListAdapter extends BaseAdapter {
    private static final String PRACTICE_GROUP_LIST_ADAPTER = "practGroupListAdapter";
    private LayoutInflater layInflater;
    private int resource;

    private List<Group> groupList;

    public PracticeGroupListAdapter(Context context, @LayoutRes int resource) {
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

        View resultView;
        if (convertView != null) {
            resultView = convertView;
        } else {
            resultView = layInflater.inflate(resource, parent, false);
        }

        Group currentGroup = groupList.get(position);

        TextView groupName = resultView.findViewById(R.id.group_name);
        TextView groupSize = resultView.findViewById(R.id.group_size);
        Log.d(PRACTICE_GROUP_LIST_ADAPTER, "views have been found");

        String mGroupSize;
        if (currentGroup.getGroupSize() == 1) {
            mGroupSize = currentGroup.getGroupSize() + " verb";
        } else {
            mGroupSize = currentGroup.getGroupSize() + " verbs";
        }

        groupName.setText(currentGroup.getGroupName());
        groupSize.setText(mGroupSize);
        groupName.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));
        groupSize.setTextColor(resultView.getResources().getColor(R.color.BLACK_SOFT));

        Log.d(PRACTICE_GROUP_LIST_ADAPTER, "text has been set");

        return resultView;
    }

    public List<Group> getGroups() {
        return groupList;
    }

    public boolean isEmpty(int position){
        if(groupList.get(position).getGroupSize() == 0){
            return true;
        }else {
            return false;
        }
    }
}
