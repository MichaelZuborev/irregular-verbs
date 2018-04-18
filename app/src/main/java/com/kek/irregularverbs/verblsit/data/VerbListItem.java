package com.kek.irregularverbs.verblsit.data;

import android.util.Log;

public class VerbListItem {
    private static final String VERBLIST_ITEM = "verbListItem";

    private String mMainVerb;
    private String mFirstForm;
    private String mSecondForm;
    private String mThirdForm;

    private int id;

    public VerbListItem(String mainVerb, String firstForm, String secondForm, String thirdForm, int id) {
        Log.d(VERBLIST_ITEM, "Initialising verbs");
        mMainVerb = mainVerb;
        mFirstForm = firstForm;
        mSecondForm = secondForm;
        mThirdForm = thirdForm;
        this.id = id;

        Log.d(VERBLIST_ITEM, "Verbs has been Initialised. Id is " + id);
    }

    public String getMainVerb() {
        return mMainVerb;
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
}
