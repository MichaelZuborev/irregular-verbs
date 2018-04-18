package com.kek.irregularverbs.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kek.irregularverbs.R;

public class PracticeFinishFragment extends Fragment implements View.OnClickListener {

    private static final String PRACTICEFINISHFRAGMENT_LOG = "practiceFinishFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practice_finish, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((PracticeActivity) getActivity()).getPracticeController().onFinishFragment();
        Button btnFinish = getActivity().findViewById(R.id.buttonFinish);
        btnFinish.setOnClickListener(this);
        ((PracticeActivity) getActivity()).getPracticeController().setShuffled(false);

        Log.d(PRACTICEFINISHFRAGMENT_LOG, "IsShuffled has been set to default");
    }

    @Override
    public void onClick(View v) {
        Log.d(PRACTICEFINISHFRAGMENT_LOG, "Creating fragment manager");
        FragmentTransaction mFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Log.d(PRACTICEFINISHFRAGMENT_LOG, "Fragment manager has been created. Starting replace fragment transaction");
        mFragmentTransaction.replace(R.id.practice_group_list_placeholder, new PracticeGroupListFragment());
        Log.d(PRACTICEFINISHFRAGMENT_LOG, "Committing");
        mFragmentTransaction.commit();
        Log.d(PRACTICEFINISHFRAGMENT_LOG, "Committed");
    }
}
