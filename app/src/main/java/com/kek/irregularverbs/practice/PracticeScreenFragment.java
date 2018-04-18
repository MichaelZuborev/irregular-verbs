package com.kek.irregularverbs.practice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.kek.irregularverbs.R;

public class PracticeScreenFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String PRACTICESCRRENFRAGMENT_LOG = "practiceScreenFragment";

    private TextInputEditText editText;
    private PracticeActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.practice_screen_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mActivity = (PracticeActivity) getActivity();
        mActivity.setTitle(mActivity.getGroupName());
        mActivity.getPracticeController().initialiseController(mActivity.getGroupName());

        Log.d(PRACTICESCRRENFRAGMENT_LOG, "Controller has been initialised");

        Button btnNext = mActivity.findViewById(R.id.buttonNext);
        btnNext.setOnClickListener(this);

        editText = mActivity.findViewById(R.id.textInputEditText);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.requestFocus();
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        try {
            ((InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(editText, 0);
        } catch (NullPointerException e) {
            Log.d(PRACTICESCRRENFRAGMENT_LOG, "Keyboard is broken " + e);
        }

        editText.setOnEditorActionListener(this);

        mActivity.getPracticeController().updateBar();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == KeyEvent.KEYCODE_ENDCALL) {
            mActivity.getPracticeController().onNextBtnClicked();

            Log.d(PRACTICESCRRENFRAGMENT_LOG, "Enter btn has been clicked");
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        mActivity.getPracticeController().onNextBtnClicked();
        Log.d(PRACTICESCRRENFRAGMENT_LOG, "Next btn has been clicked");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editText.setInputType(InputType.TYPE_NULL);
    }
}
