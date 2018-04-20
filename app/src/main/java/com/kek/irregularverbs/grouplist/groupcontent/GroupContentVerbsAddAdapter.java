package com.kek.irregularverbs.grouplist.groupcontent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.database.DbHelper;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import java.util.ArrayList;
import java.util.List;

public class GroupContentVerbsAddAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    private static final String GROUP_CONTENT_VERBS_ADAPTER = "groupContVerbsAdapter";
    private static final int DB_SIZE = 263;

    private List<GroupVerbsDialogItem> mItemList;
    private LayoutInflater mLayoutInflater;

    public GroupContentVerbsAddAdapter(Context context) {
        mItemList = new ArrayList<>();

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "creating DBHelper and initialising inflater");
        DbHelper dbHelper = new DbHelper(context);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "DBHelper has been created and inflater has been initialised. Inflating mItemList");
        try {
            for (int i = 1; i <= DB_SIZE; i++) {
                mItemList.add(new GroupVerbsDialogItem(dbHelper, i));
            }

            Log.d(GROUP_CONTENT_VERBS_ADAPTER, "mItemList has been successfully inflated");
        } catch (Exception e) {
            Log.d(GROUP_CONTENT_VERBS_ADAPTER, "exception at mItemList inflating process. Exception is " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public GroupVerbsDialogItem getItem(int position) {
        return mItemList.get(position);
    }

    //get id of the item (SQL ID)
    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View resultView;

        if (convertView != null) {
            resultView = convertView;
        } else {
            resultView = mLayoutInflater.inflate(R.layout.group_content_creating_dialog_item, parent, false);
        }

        GroupVerbsDialogItem mItem = mItemList.get(position);

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "creation of text views with verbs");
        TextView mainVerb = resultView.findViewById(R.id.groupContentCreatingMainVerb);
        TextView firstForm = resultView.findViewById(R.id.groupContentCreatingFirstForm);
        TextView secondForm = resultView.findViewById(R.id.groupContentCreatingSecondForm);
        TextView thirdForm = resultView.findViewById(R.id.groupContentCreatingThirdForm);

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "text views have been created. Starting setting text");

        mainVerb.setText(mItem.getMainVerb());
        firstForm.setText(mItem.getFirstForm());
        secondForm.setText(mItem.getSecondForm());
        thirdForm.setText(mItem.getThirdForm());

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "text has been set");

        CheckBox checkBox = resultView.findViewById(R.id.groupCreatingCheckBox);

        boolean isChecked = false;
        if(mItemList.get(position).isIntentToDelete()) {
            checkBox.setChecked(true);
            isChecked = true;
        }else {
            checkBox.setChecked(false);
        }

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "checkbox loaded to " + isChecked + ". Position " + position);
        return resultView;
    }

    public List<GroupVerbsDialogItem> getTicked() {
        List<GroupVerbsDialogItem> mTickedList = new ArrayList<>();

        for (GroupVerbsDialogItem item : mItemList) {

            if (item.isIntentToDelete()) {
                mTickedList.add(item);
            }

        }

        Log.d(GROUP_CONTENT_VERBS_ADAPTER, "returning tickets");

        return mTickedList;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        getItem((Integer) buttonView.getTag()).setIntentToDelete(isChecked);
    }
}
