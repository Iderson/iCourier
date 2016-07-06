package com.iv.icourier.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.iv.icourier.R;
import com.iv.icourier.fragments.blankFragments.PagerOnBoardingBlankFragment;

/**
 * Created by Andread on 30.05.2016.
 */
public class OnBoardingActivity extends AppCompatActivity {

    public interface OnBackPressedListener {
        void onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportFragmentManager().beginTransaction().add(R.id.blank_container,
                new PagerOnBoardingBlankFragment()).commitAllowingStateLoss();
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
            PagerOnBoardingBlankFragment.goBack();
        }
    }
}
