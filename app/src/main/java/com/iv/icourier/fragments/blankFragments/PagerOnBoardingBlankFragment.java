package com.iv.icourier.fragments.blankFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.iv.icourier.R;
import com.iv.icourier.activities.PhoneActivity;
import com.iv.icourier.fragments.OnBoardingFragment;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.views.NonSwipableViewPager;

/**
 * Created by Andread on 30.05.2016.
 */
public class PagerOnBoardingBlankFragment extends Fragment {
    public static NonSwipableViewPager sPager;
    private static View mTabOne;
    private static View mTabTwo;
    private static View mTabThree;
    private LinearLayout mTabs;
    private FrameLayout mStartButton;
    private int mPosition;
    public static int sCurrentFragmentItem = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_auth_blank_pager, container, false);

        initViews(root);
        setListeners();

        AppSectionsPagerAdapter mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getChildFragmentManager(), getActivity());
        sPager.setAdapter(mAppSectionsPagerAdapter);

        return root;
    }
    private void initViews(View root) {
        sPager = (NonSwipableViewPager) root.findViewById(R.id.blank_pager);
        mTabOne = root.findViewById(R.id.blank_tab_one);
        mTabTwo = root.findViewById(R.id.blank_tab_two);
        mTabThree = root.findViewById(R.id.blank_tab_three);
        mTabs = (LinearLayout) root.findViewById(R.id.blank_tabs);
        mStartButton = (FrameLayout) root.findViewById(R.id.blank_start);
        mTabOne.setActivated(true);
    }
    private void setListeners() {
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (sCurrentFragmentItem == 2){
                    Intent phoneIntent = new Intent(getContext(), PhoneActivity.class);
                    phoneIntent.putExtra(ICConst.NEED_AUTH, true);
                    startActivity(phoneIntent);
                    getActivity().finish();
                } else if (sCurrentFragmentItem == 1) {
                    sPager.setCurrentItem(++sCurrentFragmentItem);
                    changeTabs();
                } else if (sCurrentFragmentItem == 0) {
                    sPager.setCurrentItem(++sCurrentFragmentItem);
                    changeTabs();
                }
            }
        });
    }
    private static void changeTabs() {
        mTabOne.setActivated(sCurrentFragmentItem == 0);
        mTabTwo.setActivated(sCurrentFragmentItem == 1);
        mTabThree.setActivated(sCurrentFragmentItem == 2);
    }
    public static void goBack() {
        if (sCurrentFragmentItem == 2) {
            sCurrentFragmentItem--;
            changeTabs();
            sPager.setCurrentItem(1);
        } else if (sCurrentFragmentItem == 1) {
            sCurrentFragmentItem--;
            changeTabs();
            sPager.setCurrentItem(0);
        }
//        else activity.finish();
    }
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        Context mContext;
        public AppSectionsPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: {
                    return new OnBoardingFragment();
                }
                case 1: {
                    return new OnBoardingFragment();
                }
                case 2: {
                    return new OnBoardingFragment();
                }
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return "";
                }
                case 1: {
                    return "";
                }
                case 2: {
                    return "";
                }
                default:
                    break;
            }
            return null;
        }
    }
}
