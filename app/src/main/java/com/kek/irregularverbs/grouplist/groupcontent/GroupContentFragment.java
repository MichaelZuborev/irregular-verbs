package com.kek.irregularverbs.grouplist.groupcontent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.GroupListActivity;
import com.kek.irregularverbs.grouplist.data.Group;
import com.kek.irregularverbs.grouplist.groupcontent.data.GroupVerbsDialogItem;

import static android.app.Activity.RESULT_OK;


public class GroupContentFragment extends Fragment {

    private String mName;
    private GroupContentDataManager mManager;
    private long mCashID;

    private final static String GROUPCONTENTFRAGMENT_LOG = "GroupContentFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(GROUPCONTENTFRAGMENT_LOG, "Getting name");

        mName = GroupListActivity.mGroupName;

        Log.d(GROUPCONTENTFRAGMENT_LOG, "Got name " + mName);

        getActivity().setTitle(mName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDataManager();

        Log.d(GROUPCONTENTFRAGMENT_LOG, "manager loaded");

        FloatingActionButton fab = view.findViewById(R.id.add_verbs_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GroupContentDialogCreating.class);

                StringBuilder cash = new StringBuilder();
                SparseArray<GroupVerbsDialogItem> items = mManager.getAdapter().getItemArray();

                Log.d(GROUPCONTENTFRAGMENT_LOG, "fab: list has been initialized");
                for (int i = 0; i < items.size(); i++) {
                    cash.append(items.valueAt(i).getId());
                    cash.append("&");
                }

                Log.d(GROUPCONTENTFRAGMENT_LOG, "fab: get data " + cash.toString());

                intent.putExtra("Ticked", cash.toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    //load old or updated data
    private void loadDataManager() {
        Log.d(GROUPCONTENTFRAGMENT_LOG, "loadManager start");

        mManager = new GroupContentDataManager(getContext(), new Group(getContext(), mName));
        ListView view = getView().findViewById(R.id.group_content_container);
        view.setAdapter(mManager.getAdapter());
        registerForContextMenu(view);

        Log.d(GROUPCONTENTFRAGMENT_LOG, "loadManager completed");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Delete verb");

        Log.d(GROUPCONTENTFRAGMENT_LOG, "onCreateContext menu method is called");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = new Intent(getContext(), GroupContentDialogDeleting.class);

        AdapterView.AdapterContextMenuInfo adaptConMenInf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        mCashID = adaptConMenInf.id;

        Log.d(GROUPCONTENTFRAGMENT_LOG, "intent has been created");

        startActivityForResult(intent, 2);

        Log.d(GROUPCONTENTFRAGMENT_LOG, "activity started");

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (data != null) {
                    String result = data.getStringExtra("result");
                    Log.d(GROUPCONTENTFRAGMENT_LOG, "data is " + result);

                    mManager.setResource(result);
                    loadDataManager();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK && mCashID != -1) {
                    Log.d(GROUPCONTENTFRAGMENT_LOG, "deleting group with id " + mCashID);

                    mManager.deleteVerb((int) mCashID);
                    loadDataManager();

                    //-1 value of mCashID is used to be a token of default & not used id
                    mCashID = -1;

                    Log.d(GROUPCONTENTFRAGMENT_LOG, "group has been deleted, mCashID set to " + mCashID);

                } else {
                    Log.d(GROUPCONTENTFRAGMENT_LOG, "Failed to delete. resultCode: " + resultCode + ", mCashID:" + mCashID);
                }
                break;
        }
    }

}
