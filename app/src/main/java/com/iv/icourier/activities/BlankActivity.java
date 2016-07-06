package com.iv.icourier.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.iv.icourier.R;
import com.iv.icourier.fragments.SizeFragment;
import com.iv.icourier.fragments.blankFragments.BlankDescriptionFurnitureFragment;
import com.iv.icourier.fragments.blankFragments.PagerBlankFragment;
import com.iv.icourier.helpers.ICConst;

public class BlankActivity extends AppCompatActivity {
    public static  boolean     isCourier = true;

    public interface OnBackPressedListener {
        void onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        isCourier = getIntent().getBooleanExtra(ICConst.IS_COURIER, true);
        getSupportFragmentManager().beginTransaction().add(R.id.blank_container,
                new PagerBlankFragment()).commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment: fm.getFragments()) {
            if (fragment instanceof  OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            PagerBlankFragment.goBack(this);
        }
    }

}
