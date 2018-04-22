package com.kek.irregularverbs.verblsit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.kek.irregularverbs.R;

import java.lang.ref.WeakReference;

public class VerbListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final String VERB_LIST_ACTIVITY = "VerbListActivity";

    private VerbListAdapter mListAdapter;
    private WeakReference mWeakContext;
    private ListView mListView;
    private AsyncTaskLoader<String> mAsyncTaskLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verb_list);
        mWeakContext = new WeakReference(getApplicationContext());

        mListView = findViewById(R.id.list_view_verb_list);

        LoaderManager mLoaderManager = getSupportLoaderManager();
        Loader mLoader = mLoaderManager.getLoader(1);

        if (mLoader == null) {
            mLoaderManager.initLoader(1, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(1, null, this).forceLoad();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        mAsyncTaskLoader = new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                if (mWeakContext != null) {
                    mListAdapter = new VerbListAdapter((Context) mWeakContext.get());
                } else {
                    Log.e(VERB_LIST_ACTIVITY, "Failed to load adapter");
                    onBackPressed();
                }
                return null;
            }
        };
        return mAsyncTaskLoader;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAsyncTaskLoader.cancelLoad();
    }
}
