package com.kek.irregularverbs.practice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.GroupListDataManager;

import java.lang.ref.WeakReference;

public class PracticeGroupListFragment extends Fragment implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks {

    private static final String PRACTICEGROUPLISTFRAGMENT = "PracticeGroupListFrag";

    private PracticeGroupListDataManager mManager;
    private AsyncTaskLoader mAsyncTaskLoader;
    private LoaderManager mLoaderManager;
    private WeakReference mWeakContext;

    //Cash view
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice_group_list, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        getActivity().setTitle("Choose a group");
    }

    //load data
    private void loadDataManager() {
        Log.d(PRACTICEGROUPLISTFRAGMENT, "Loading the data manager");
        mManager = new PracticeGroupListDataManager(getActivity());
        Log.d(PRACTICEGROUPLISTFRAGMENT, "Adapter has been created. Setting adapter to container");
        ListView mContainer = mView.findViewById(R.id.practice_group_list_container);
        mContainer.setAdapter(mManager.getAdapter());
        Log.d(PRACTICEGROUPLISTFRAGMENT, "Adapter has been set");

        mContainer.setOnItemClickListener(this);

        Log.d(PRACTICEGROUPLISTFRAGMENT, "data has been loaded");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!mManager.getAdapter().isEmpty(position)) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.practice_group_list_placeholder, new PracticeScreenFragment());
            ((PracticeActivity) getActivity()).setGroupName(mManager.getAdapter().getItem(position).toString());
            ft.commit();

            Log.d(PRACTICEGROUPLISTFRAGMENT, "Fragment has been launched");
        } else {
            try {
                Snackbar.make(getView(), "Group is empty", Snackbar.LENGTH_SHORT).show();
            } catch (RuntimeException e){
                Toast.makeText(getContext(), "Group is empty", Toast.LENGTH_SHORT).show();
                Log.d(PRACTICEGROUPLISTFRAGMENT, "SnackBar exception " + e);
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        mAsyncTaskLoader = new AsyncTaskLoader<String>(getContext()) {
            @Override
            public String loadInBackground() {
                if(mWeakContext != null) {
                    Log.d(PRACTICEGROUPLISTFRAGMENT, "Loading the data manager");
                    mManager = new PracticeGroupListDataManager(getActivity());
                }else {
                    Log.e(PRACTICEGROUPLISTFRAGMENT, "Failed to load weak reference");
                }
                return null;
            }
        };
        return mAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(PRACTICEGROUPLISTFRAGMENT, "Adapter has been created. Setting adapter to container");
        ListView mContainer = mView.findViewById(R.id.practice_group_list_container);
        mContainer.setAdapter(mManager.getAdapter());
        Log.d(PRACTICEGROUPLISTFRAGMENT, "Adapter has been set");

        mContainer.setOnItemClickListener(this);

        Log.d(PRACTICEGROUPLISTFRAGMENT, "data has been loaded");
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mAsyncTaskLoader.cancelLoad();
    }
}
