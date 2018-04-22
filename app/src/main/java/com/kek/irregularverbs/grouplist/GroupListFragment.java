package com.kek.irregularverbs.grouplist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.groupcontent.GroupContentFragment;

import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_OK;


public class GroupListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks{

    private static final String GROUPLISTFRAGMENT_LOG = "groupListFragment";

    private long mCashID;

    private GroupListDataManager mManager;
    private AsyncTaskLoader mAsyncTaskLoader;
    private LoaderManager mLoaderManager;
    private WeakReference mWeakContext;

    //Cash view
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mView = view;
        mWeakContext = new WeakReference(getContext());

        //loading data in loader manager
        mLoaderManager = getLoaderManager();
        Loader mLoader = mLoaderManager.getLoader(0);

        if (mLoader == null) {
            mLoaderManager.initLoader(0, null, this);
        } else {
            mLoaderManager.restartLoader(0, null, this);
        }

        mLoaderManager.getLoader(0).forceLoad();

        FloatingActionButton mAddBtn = view.findViewById(R.id.add_group_btn);
        mAddBtn.setOnClickListener(this);

        GroupListActivity.mGroupName = null;

        getActivity().setTitle("Your groups");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_group_btn:
                Intent intent = new Intent(getActivity(), GroupListCreatingDialog.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        mAsyncTaskLoader = new AsyncTaskLoader<String>(getContext()) {
            @Override
            public String loadInBackground() {
                Log.d(GROUPLISTFRAGMENT_LOG, "Loading the data manager");
                if(mWeakContext != null) {
                    mManager = new GroupListDataManager((Context) mWeakContext.get());
                }else {
                    Log.e(GROUPLISTFRAGMENT_LOG, "Failed to load weak reference");
                }
                return null;
            }
        };
        return mAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(GROUPLISTFRAGMENT_LOG, "Setting adapter to container");
        ListView mContainer = mView.findViewById(R.id.group_list_container);
        mContainer.setAdapter(mManager.getAdapter());
        Log.d(GROUPLISTFRAGMENT_LOG, "Adapter has been set");
        registerForContextMenu(mContainer);

        mContainer.setOnItemClickListener(this);

        Log.d(GROUPLISTFRAGMENT_LOG, "data has been loaded");
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    //load old or updated data
    private void loadDataManager() {
        Log.d(GROUPLISTFRAGMENT_LOG, "Loading the data manager");
        mManager = new GroupListDataManager(getActivity());
        Log.d(GROUPLISTFRAGMENT_LOG, "Manager has been created. Setting adapter to container");
        ListView mContainer = mView.findViewById(R.id.group_list_container);
        mContainer.setAdapter(mManager.getAdapter());
        Log.d(GROUPLISTFRAGMENT_LOG, "Adapter has been set");
        registerForContextMenu(mContainer);

        mContainer.setOnItemClickListener(this);

        Log.d(GROUPLISTFRAGMENT_LOG, "data has been loaded");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data != null) {
                    String mInputName = data.getStringExtra("name");

                    Log.d(GROUPLISTFRAGMENT_LOG, "name obtained: " + mInputName);

                    mManager.addNewGroup(mInputName);
                    loadDataManager();

                    Log.d(GROUPLISTFRAGMENT_LOG, "group has been added");
                } else {
                    Log.e(GROUPLISTFRAGMENT_LOG, "got null data");
                }
                break;
            case 1:
                if (resultCode == RESULT_OK && mCashID != -1) {
                    Log.d(GROUPLISTFRAGMENT_LOG, "deleting group with id " + mCashID);

                    mManager.deleteGroup((int) mCashID);
                    loadDataManager();

                    //-1 value of mCashID is used to be a token of default & not used id
                    mCashID = -1;

                    Log.d(GROUPLISTFRAGMENT_LOG, "group has been deleted, mCashID set to " + mCashID);

                } else {
                    Log.e(GROUPLISTFRAGMENT_LOG, "Failed to delete. resultCode: " + resultCode + ", mCashID:" + mCashID);
                }
                break;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Delete group");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), GroupListDeletingDialog.class);

        AdapterView.AdapterContextMenuInfo adaptConMenInf = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        mCashID = adaptConMenInf.id;

        Log.d(GROUPLISTFRAGMENT_LOG, "intent has been created");

        startActivityForResult(intent, 1);

        Log.d(GROUPLISTFRAGMENT_LOG, "activity started");

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.group_list_placeholder, new GroupContentFragment());
        GroupListActivity.mGroupName = mManager.getAdapter().getItem(position).toString();
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAsyncTaskLoader.cancelLoad();
    }
}
