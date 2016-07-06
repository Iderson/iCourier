package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.helpers.HawkStorage;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.loopj.android.http.RequestParams;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String[] userData = HawkStorage.getAuthData();
//        String[] userData = null;
        if (userData == null) {
            Log.w("initUser", "userData == null");
            Intent authIntent = new Intent(SplashScreen.this, OnBoardingActivity.class);
//            phoneIntent.putExtra(ICConst.NEED_AUTH, true);
//            startActivityForResult(phoneIntent,
//                    ICConst.REQUEST_PHONE);
            startActivity(authIntent);
            finish();
        } else {
            RequestParams params = new RequestParams();
            params.put(ICConst.PHONE, userData[0]);
            params.put(ICConst.CODE,  userData[1]);
            ICApiManager.initUser(params, ICHandlers.defaultHandler(this, new ICHandlers.Executor() {
                @Override
                public void sucExec(Context mContext, Bundle args) {
                    Log.w("initUser", "success " + args.toString());
                    ICUser user = new ICUser();
                    user.initDataFromJSON(args);
                    ICApplication.currentProfile = user;
                    if (user.getUser_role() == 0)
                        startActivity(new Intent(mContext, MainClientActivity.class));
                    else
                        startActivity(new Intent(mContext, MainCourierActivity.class));
                    finish();
                }
                @Override
                public void errExec(Context mContext, Bundle args) {
                    Log.w("initUser", "error " + args.toString());
                    startActivity(new Intent(mContext, MainClientActivity.class));
                    finish();
                }
            }));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ICConst.REQUEST_AUTHORIZE)
            if (resultCode == Activity.RESULT_OK) {
                startActivity(new Intent(this, OnBoardingActivity.class));
                finish();
            }
    }
}
