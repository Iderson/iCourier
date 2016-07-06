package com.iv.icourier;

import android.support.multidex.MultiDexApplication;

import com.iv.icourier.helpers.HawkStorage;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.models.ICCourier;
import com.iv.icourier.models.ICOrder;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICRestClient;

import java.util.TimeZone;


/**
 * Created by iv on 25.03.16.
 */
public class ICApplication extends MultiDexApplication {
    public  static  boolean needAuth = true;
    public  static  boolean imClient = true;
    public  static  ICUser  currentProfile = null;
    public  static  ICOrder currentOrder = null;
    public  static ICCourier currentCourier = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setupHttpClient();
        ICConst.formatForServer.setTimeZone(TimeZone.getTimeZone("UTC"));
        ICConst.formatForDob.setTimeZone(TimeZone.getTimeZone("UTC"));
        HawkStorage.hawkInit(this);
    }

    private void setupHttpClient() {
        ICRestClient.getCookies(this);
        ICRestClient.setCookieStore(this);
        ICRestClient.client.setMaxRetriesAndTimeout(1, 500);
    }
}
