package com.google.developer.bugmaster.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

//TODO: This class should be used in the insect list to display danger level
public class DangerLevelView extends android.support.v7.widget.AppCompatTextView {

    private int mDangerLevel;

    public DangerLevelView(Context context) {
        super(context);
    }

    public DangerLevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public DangerLevelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    public void setDangerLevel(int dangerLevel) {
        mDangerLevel = dangerLevel;
        setText(mDangerLevel);
        //TODO: Update the view appropriately based on the level input
    }

    public int getDangerLevel() {
        //TODO: Report the current level back as an integer
        return mDangerLevel;
    }
}
