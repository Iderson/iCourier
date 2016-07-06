package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.iv.icourier.R;
import com.iv.icourier.fragments.courierFragment.CourierPagerFragment;
import com.iv.icourier.fragments.courierFragment.CourierProfileFragment;
import com.iv.icourier.fragments.courierFragment.SearchFragment;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.views.NonSwipableViewPager;

public class MainCourierActivity extends AppCompatActivity {
    private LinearLayout            bottomMyOrders;
    private LinearLayout            bottomProfile;
    private LinearLayout            bottomSearch;
    private NonSwipableViewPager pager;
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    public static RelativeLayout    tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courier);

        initViews();
        setListeners();

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(mAppSectionsPagerAdapter);
    }
    private void initViews() {
        bottomMyOrders  =   (LinearLayout)          findViewById(R.id.main_courier_bottom_my_orders);
        bottomProfile   =   (LinearLayout)          findViewById(R.id.main_courier_bottom_profile);
        bottomSearch    =   (LinearLayout)          findViewById(R.id.main_courier_bottom_search);
        pager           =   (NonSwipableViewPager) findViewById(R.id.main_courier_pager);
        tabs            =   (RelativeLayout)        findViewById(R.id.order_courier_bottom_bar);

        bottomSearch.setActivated(true);
    }
    private void setListeners() {
        bottomSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSearch.setActivated(true);
                bottomProfile.setActivated(false);
                bottomMyOrders.setActivated(false);

                pager.setCurrentItem(0);
            }
        });
        bottomMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSearch.setActivated(false);
                bottomProfile.setActivated(false);
                bottomMyOrders.setActivated(true);

                pager.setCurrentItem(1);
            }
        });
        bottomProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSearch.setActivated(false);
                bottomProfile.setActivated(true);
                bottomMyOrders.setActivated(false);

                pager.setCurrentItem(2);
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
                    return new SearchFragment();
                }
                case 1: {
                    return new CourierPagerFragment();
                }
                case 2: {
                    return new CourierProfileFragment();
                }
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 3;
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
                case 2: {
                    return mContext.getString(R.string.nothing);
                }
                default:
                    break;
            }
            return null;
        }
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ICConst.REQUEST_DETAIL_ORDER:
                if (resultCode == Activity.RESULT_OK)
                    showConfirmDialog();
                break;
        }
    }
    private void showConfirmDialog() {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.dialog_theme);
            dialog.setTitle(getString(R.string.confirm));
            dialog.setMessage(getString(R.string.confirm_text))
                    .setPositiveButton(getString(R.string.agree), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
