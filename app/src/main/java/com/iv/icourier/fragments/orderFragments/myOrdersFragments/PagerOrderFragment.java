package com.iv.icourier.fragments.orderFragments.myOrdersFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.iv.icourier.R;
import com.iv.icourier.views.NonSwipableViewPager;

/**
 * Created by iv on 08.03.16.
 */
public class PagerOrderFragment extends Fragment {
    private NonSwipableViewPager pager;
    private FrameLayout             inWork;
    private FrameLayout             completed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_pager, container, false);

        initViews(root);
        setListeners();

        AppSectionsPagerAdapter mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getChildFragmentManager(), getActivity());
        pager.setAdapter(mAppSectionsPagerAdapter);

        return root;
    }
    private void initViews(View root) {
        pager =     (NonSwipableViewPager) root.findViewById(R.id.my_orders_pager);
        inWork  =   (FrameLayout)           root.findViewById(R.id.my_orders_tab_in_work);
        completed = (FrameLayout)           root.findViewById(R.id.my_orders_tab_completed);

        inWork.setActivated(true);
    }
    private void setListeners() {
        inWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inWork.setActivated(true);
                completed.setActivated(false);

                pager.setCurrentItem(0);
            }
        });
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inWork.setActivated(false);
                completed.setActivated(true);

                pager.setCurrentItem(1);
            }
        });
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
                    return new OrderInWorkFragment();
                }
                case 1: {
                    return new OrderCompletedFragment();
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
                    return mContext.getString(R.string.nothing);
                }
                case 1: {
                    return mContext.getString(R.string.nothing);
                }
                default:
                    break;
            }
            return null;
        }
    }
}
