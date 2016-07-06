package com.iv.icourier.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.fragments.orderFragments.myOrdersFragments.PagerOrderFragment;
import com.iv.icourier.fragments.orderFragments.profileFragments.ProfileFragment;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.iv.icourier.views.NonSwipableViewPager;
import com.loopj.android.http.RequestParams;

public class MainClientActivity extends AppCompatActivity {
    private         LinearLayout            mBottomMyOrders;
    private         LinearLayout            mBottomProfile;
    private         FrameLayout             mBottomAction;
    private         NonSwipableViewPager    mPager;
    private         AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private         ICUser                  mUser = new ICUser();
    private         ProgressDialog          mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        mUser = ICApplication.currentProfile;

        initViews();
        setListeners();
        updateValues();

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), this);
        mPager.setAdapter(mAppSectionsPagerAdapter);
    }

    private void updateValues() {
        if (mUser != null){
//            mUser.setUser_role(0);
            ICApiManager.updateUser(new RequestParams(ICConst.USER_ROLE, 0),
                    ICHandlers.defaultHandler(this, new ICHandlers.Executor() {
                        @Override
                        public void sucExec(Context mContext, Bundle args) {
                            Log.w("updateUser", "success " + args.toString());
                            mUser.initDataFromJSON(args);
                            ICApplication.currentProfile = mUser;
                        }
                        @Override
                        public void errExec(Context mContext, Bundle args) {
                            Log.e("updateUser", "error " + args.toString());
//                mProgressDialog.dismiss();
                            ICHelper.showDialog(mContext, getString(R.string.error), getString(R.string.attention), null);
                        }
                    }));
            /*ICApiManager.getUser(new RequestParams(), ICHandlers.defaultHandler(this,
                    new ICHandlers.Executor() {
                        @Override
                        public void sucExec(Context mContext, Bundle args) {
                            Log.w("getUserInfo", "success " + args.toString());
                            mUser.initDataFromJSON(args);
                        }
                        @Override
                        public void errExec(Context mContext, Bundle args) {
                            Log.e("getUserInfo", "error " + args.toString());
                        }
                    }));
            ICApiManager.getOrder(ICHandlers.defaultHandler(this,
                    new ICHandlers.Executor() {
                        @Override
                        public void sucExec(Context mContext, Bundle args) {
                            Log.w("OrderList", "success " + args.toString());
//                                ICApplication.currentOrder .initDataFromJSON(args);
//                                    setResult(Activity.RESULT_OK);

                        }

                        @Override
                        public void errExec(Context mContext, Bundle args) {
                            Log.e("OrderList", "error " + args.toString());
                            ICHelper.showDialog(mContext, getString(R.string.error),
                                    getString(R.string.attention), null);
                        }
                    }));*/
//            mProgressDialog.dismiss();
        } else {
            startActivity(new Intent(this, OnBoardingActivity.class));
            finish();
        }

    }

    private void initViews() {
//        mProgressDialog = new ProgressDialog(this, 0);
//        mProgressDialog.setMessage(getString(R.string.download));
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();
        mBottomMyOrders =   (LinearLayout)          findViewById(R.id.main_bottom_my_orders);
        mBottomProfile =   (LinearLayout)          findViewById(R.id.main_bottom_profile);
        mBottomAction =   (FrameLayout)           findViewById(R.id.main_bottom_action);
        mPager =   (NonSwipableViewPager) findViewById(R.id.main_pager);

        mBottomMyOrders.setActivated(true);

    }
    private void setListeners() {
        mBottomAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainClientActivity.this, OrderActivity.class), ICConst.REQUEST_ORDER);
            }
        });
        mBottomProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomProfile.setActivated(true);
                mBottomMyOrders.setActivated(false);

                mPager.setCurrentItem(1);
            }
        });
        mBottomMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfileActivated();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(!mBottomMyOrders.isActivated())
            setProfileActivated();
        else finish();
    }
    private void setProfileActivated(){
        mBottomProfile.setActivated(false);
        mBottomMyOrders.setActivated(true);

        mPager.setCurrentItem(0);
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
                    return new PagerOrderFragment();
                }
                case 1: {
                    return new ProfileFragment();
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
        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ICConst.REQUEST_PHONE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.w("onActivityResult", "REQUEST_PHONE");
                    ProfileFragment.needAuth = false;
//                    ProfileFragment.initLayout();
                }
                break;
            case ICConst.REQUEST_ORDER:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainClientActivity.this, MainCourierActivity.class));
                    ICApplication.imClient = false;
                    finish();
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
