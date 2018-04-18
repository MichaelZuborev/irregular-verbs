package com.kek.irregularverbs.practice;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


import com.kek.irregularverbs.R;
import com.kek.irregularverbs.grouplist.data.Group;
import com.kek.irregularverbs.practice.view.PracticeViewController;

import java.util.ArrayList;
import java.util.Collections;

//This class controls process of practice
public class PracticeController {

    private static final String PRACTICECONTROLLER_LOG = "practiceController";

    private Group mGroup;
    private Context mContext;
    private String mFirstForm;
    private String mSecondForm;
    private String mThirdForm;

    //Array of shuffled ids
    private ArrayList<Integer> mShuffledArray;
    private boolean mIsShuffled;

    private PracticeViewController mViewController;
    private TextInputEditText mEditText;


    private int mId;

    //Quantity of incorrect answers
    private int mIncorrectQuantity;

    //Quantity of fully correct answers(3 forms at once)
    private int mFullyCorrectVerbs;

    //Has been mistake made or not(only about one verb)
    private boolean mIsMistaken;

    //Values 1, 2 & 3 are allowed(1 - infinitive, 2 - past simple, 3 - past participle)
    private int mForm;


    public PracticeController(Context context) {
        mContext = context;
        mId = 0;
        mIncorrectQuantity = 0;
        mIsShuffled = false;

        Log.d(PRACTICECONTROLLER_LOG, "PracticeController: constructor has been called");
    }

    public void setShuffled(boolean shuffled) {
        mIsShuffled = shuffled;
    }

    //Make a random sequence for verbs
    private void initialiseArray() {
        mShuffledArray = new ArrayList<>();

        for (int i = 0; i < mGroup.getGroupSize(); i++) {
            mShuffledArray.add(i);
        }
        Collections.shuffle(mShuffledArray);
        mIsShuffled = true;

        Log.d(PRACTICECONTROLLER_LOG, "Array of IDs has been initialized");
    }

    public void initialiseController(String groupName) {
        mGroup = new Group(mContext, groupName);
        mIsMistaken = false;
        mForm = 1;

        if (!mIsShuffled) {
            initialiseArray();
        }

        mFirstForm = mGroup.getGroupItems().get(mShuffledArray.get(mId)).getFirstForm();
        mSecondForm = mGroup.getGroupItems().get(mShuffledArray.get(mId)).getSecondForm();
        mThirdForm = mGroup.getGroupItems().get(mShuffledArray.get(mId)).getThirdForm();

        mViewController = new PracticeViewController(this);
        mEditText = ((Activity) mContext).findViewById(R.id.textInputEditText);

        Log.d(PRACTICECONTROLLER_LOG, "Practice controller has been initialised");
    }

    public ArrayList<Integer> getShuffledArray() {
        return mShuffledArray;
    }

    //call when answer is incorrect
    public void incrementIncorrectQuantity() {
        mIncorrectQuantity++;
        Log.d(PRACTICECONTROLLER_LOG, "IncorrectQuantity var has been incremented");

    }

    public void incrementFullyCorrectAnswers() {
        mFullyCorrectVerbs++;
        Log.d(PRACTICECONTROLLER_LOG, "FullyCorrectAnswers var has been incremented");
    }

    public int getId() {
        return mId;
    }

    public Group getGroup() {
        return mGroup;
    }

    public int getGroupSize() {
        return mGroup.getGroupSize();
    }

    public Context getContext() {
        return mContext;
    }

    public void incrementId() {
        mId++;
    }

    public String getFirstForm() {
        return mFirstForm;
    }

    public String getSecondForm() {
        return mSecondForm;
    }

    public String getThirdForm() {
        return mThirdForm;
    }


    public void onNextBtnClicked() {
        switch (mForm) {
            case 1:
                if (mEditText.getText().toString().toLowerCase().equals(getFirstForm().toLowerCase())) {
                    mViewController.setFirstFormColor(false);

                    Log.d(PRACTICECONTROLLER_LOG, "First form color has been sent to blue");

                } else {
                    mViewController.setFirstFormColor(true);
                    mIsMistaken = true;
                    incrementIncorrectQuantity();

                    Log.d(PRACTICECONTROLLER_LOG, "First form color has been sent to red");
                }

                mEditText.setText("");
                mViewController.setFirstFormAndHint();
                mForm++;
                break;
            case 2:
                if (mEditText.getText().toString().toLowerCase().equals(getSecondForm().toLowerCase())) {
                    mViewController.setSecondFormColor(false);

                    Log.d(PRACTICECONTROLLER_LOG, "Second form color has been sent to blue");
                } else {
                    mViewController.setSecondFormColor(true);
                    mIsMistaken = true;
                    incrementIncorrectQuantity();

                    Log.d(PRACTICECONTROLLER_LOG, "Second form color has been sent to red");
                }

                mEditText.setText("");
                mViewController.setSecondFormAndHint();
                mForm++;
                break;
            case 3:
                if (mEditText.getText().toString().toLowerCase().equals(getThirdForm().toLowerCase())) {
                    mViewController.setThirdFormColor(false);

                    if (!mIsMistaken) {
                        incrementFullyCorrectAnswers();
                    }

                    Log.d(PRACTICECONTROLLER_LOG, "Third form color has been sent to blue");

                } else {
                    mViewController.setThirdFormColor(true);
                    mIsMistaken = true;
                    incrementIncorrectQuantity();

                    Log.d(PRACTICECONTROLLER_LOG, "Third form color has been sent to red");
                }

                mEditText.setText("");
                mViewController.setThirdFormAndHint();
                if (!mIsMistaken) {
                    finishFragment();
                } else {
                    mForm++;
                    mViewController.incrementBar();
                }
                break;
            case 4:
                finishFragment();
        }
    }

    private void finishFragment() {
        mForm = 1;
        mViewController.setTextFormsByDefault();
        mIsMistaken = false;
        mViewController.setNewHint();
        incrementId();

        FragmentTransaction ft = ((PracticeActivity) mContext).getSupportFragmentManager().beginTransaction();
        if (mId < mGroup.getGroupSize()) {

            ft.replace(R.id.practice_group_list_placeholder, new PracticeScreenFragment());

        } else {
            ft.replace(R.id.practice_group_list_placeholder, new PracticeFinishFragment());
        }
        ft.commit();

        Log.d(PRACTICECONTROLLER_LOG, "Fragment has finished");
    }

    public void updateBar() {
        mViewController.updateBar(mId);
    }

    public void onFinishFragment() {
        mViewController.onFinishFragment(mIncorrectQuantity, mFullyCorrectVerbs);
        mIncorrectQuantity = 0;
        mFullyCorrectVerbs = 0;
        mId = 0;
    }

}
