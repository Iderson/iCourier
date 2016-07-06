package com.iv.icourier.fragments.registerCourierFragments.courierPersonalDataFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import de.greenrobot.event.EventBus;
import github.chenupt.dragtoplayout.DragTopLayout;

/**
 * Created by iv on 15.03.16.
 */
public class PagerPersonalDataFragment extends Fragment {
    private FrameLayout     tabEntity;
    private FrameLayout     tabIndividual;
    private ViewPager       pager;
    private DragTopLayout   dragLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courier_pager, container, false);

        ICApplication.currentProfile.setUser_role(1);

        AppSectionsPagerAdapter mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getChildFragmentManager(), getActivity());
        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) root.findViewById(R.id.courier_pager);
        pager.setAdapter(mAppSectionsPagerAdapter);

        SmartTabLayout tabs = (SmartTabLayout) root.findViewById(R.id.courier_tabs);
        tabs.setViewPager(pager);

        initViews(root);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tabEntity.setActivated(false);
                    tabIndividual.setActivated(true);
                } else {
                    tabEntity.setActivated(true);
                    tabIndividual.setActivated(false);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return root;
    }
    private void initViews(View root) {
        dragLayout  =   (DragTopLayout) root.findViewById(R.id.courier_drag_top);
        tabEntity   =   (FrameLayout)   root.findViewById(R.id.courier_tab_entity);
        tabIndividual = (FrameLayout)   root.findViewById(R.id.courier_tab_individual);

        dragLayout.setOverDrag(false);
        tabIndividual.setActivated(true);

        tabEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });
        tabEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    return new CourierIndividualFragment();
                }
                case 1: {
                    return new CourierEntityFragment();
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
                    return mContext.getString(R.string.individual);
                }
                case 1: {
                    return mContext.getString(R.string.entity_or_ip);
                }
                default:
                    break;
            }
            return null;
        }
    }
    public void onEvent(Boolean b){
        dragLayout.setTouchMode(b);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
