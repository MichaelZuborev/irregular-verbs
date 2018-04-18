package com.kek.irregularverbs.practice.view;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kek.irregularverbs.R;
import com.kek.irregularverbs.practice.PracticeActivity;
import com.kek.irregularverbs.practice.PracticeController;

public class PracticeViewController {
    private static final String PRACTICEVIEWCONTROLLER_LOG = "practiceViewController";

    private ProgressBar mBar;
    private PracticeController mPracticeController;
    private TextInputLayout mTextInput;

    private PracticeActivity mActivity;

    private TextView mFirstFormHint;
    private TextView mSecondFormHint;
    private TextView mThirdFormHint;

    public PracticeViewController(PracticeController controller) {
        mPracticeController = controller;
        mActivity =  (PracticeActivity) mPracticeController.getContext();
        mBar = mActivity.findViewById(R.id.practiceProgressBar);
        mBar.setMax(mPracticeController.getGroupSize());

        String mMainVerb = mPracticeController.getGroup().getGroupItems().get(mPracticeController
                .getShuffledArray().get(mPracticeController.getId())).getMainVerb();
        TextView mainVerbView = mActivity.findViewById(R.id.mainVerb);
        mainVerbView.setText(mMainVerb);

        mFirstFormHint = mActivity.findViewById(R.id.hiddenFormOne);
        mSecondFormHint = mActivity.findViewById(R.id.hiddenFormTwo);
        mThirdFormHint = mActivity.findViewById(R.id.hiddenFormThree);

        mTextInput = mActivity.findViewById(R.id.textInputLayout);

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "PracticeViewController: constructor has been called");
    }

    public void incrementBar() {
        mBar.incrementProgressBy(1);

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Progress bar has been incremented.");
    }

    public void updateBar(int progress) {
        mBar.setProgress(progress);

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Progress bar has been updated. Current value is " + mBar.getProgress());
    }

    public void setFirstFormAndHint() {
        mFirstFormHint.setText(mPracticeController.getFirstForm());
        mTextInput.setHint("Past simple");

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "First form has been shown");
    }

    public void setSecondFormAndHint() {
        mSecondFormHint.setText(mPracticeController.getSecondForm());
        mTextInput.setHint("Past participle");

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Second form has been shown");
    }

    public void setThirdFormAndHint() {
        mThirdFormHint.setText(mPracticeController.getThirdForm());
        mTextInput.setHint("Check answers & press \"Next\" button");

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Third form has been shown");
    }

    public void setNewHint() {
        mTextInput.setHint("Infinitive");

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Fresh hint has been set");

    }

    public void setFirstFormColor(boolean isMistaken) {
        if (!isMistaken) {
            mFirstFormHint.setTextColor(Color.BLUE);
        } else {
            mFirstFormHint.setTextColor(Color.RED);
        }

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "isMistaken: " + isMistaken);
    }

    public void setSecondFormColor(boolean isMistaken) {
        if (!isMistaken) {
            mSecondFormHint.setTextColor(Color.BLUE);
        } else {
            mSecondFormHint.setTextColor(Color.RED);
        }
        Log.d(PRACTICEVIEWCONTROLLER_LOG, "isMistaken: " + isMistaken);
    }

    public void setThirdFormColor(boolean isMistaken) {
        if (!isMistaken) {
            mThirdFormHint.setTextColor(Color.BLUE);
        } else {
            mThirdFormHint.setTextColor(Color.RED);
        }

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "isMistaken: " + isMistaken);
    }

    public void setTextFormsByDefault() {

        mFirstFormHint.setText("-");
        mSecondFormHint.setText("-");
        mThirdFormHint.setText("-");

        mFirstFormHint.setTextColor(Color.BLACK);
        mSecondFormHint.setTextColor(Color.BLACK);
        mThirdFormHint.setTextColor(Color.BLACK);

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Upper forms has been set by default");
    }

    public void onFinishFragment(int mIncorrectQuantity, int mFullyCorrectVerbs) {
        TextView textViewMistakes = mActivity.findViewById(R.id.textViewMistakes);
        TextView textViewFullyCorrect = mActivity.findViewById(R.id.textViewRight);

        StringBuilder text = new StringBuilder();
        text.append("You made ");
        if (mIncorrectQuantity == 0) {
            text.append("no");
        } else {
            text.append(mIncorrectQuantity);
        }
        if (mIncorrectQuantity == 1) {
            text.append(" mistake");
        } else {
            text.append(" mistakes");
        }
        textViewMistakes.setText(text.toString());

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Amount of mistakes: " + mIncorrectQuantity);

        text = new StringBuilder();
        if (mFullyCorrectVerbs == 0) {
            text.append("None");
        } else {
            text.append(mFullyCorrectVerbs);
        }
        text.append(" of ");
        text.append(mPracticeController.getGroup().getGroupSize());
        text.append(" verbs are absolutely fine");
        textViewFullyCorrect.setText(text);

        Log.d(PRACTICEVIEWCONTROLLER_LOG, "Amount of fully right verbs: " + mFullyCorrectVerbs);
    }
}
