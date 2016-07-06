package com.iv.icourier.network;

import android.content.Context;

import com.iv.icourier.ICApplication;
import com.iv.icourier.helpers.ICConst;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by iv on 25.03.16.
 */
public class ICApiManager {
    public static void getSmsCode(RequestParams params, JsonHttpResponseHandler handler) {
        ICRestClient.post("user/auth", params, handler);
    }
    public static void initUser(RequestParams params, JsonHttpResponseHandler handler) {
        ICRestClient.post("user/code", params, handler);
    }
    public static void getUser(RequestParams params, JsonHttpResponseHandler handler) {
        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.get("user", params, handler);
    }
    public static void updateUser(RequestParams params, JsonHttpResponseHandler handler) {
        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        ICRestClient.patch("user", params, handler);
    }
    public static void userPic(Context context, RequestParams params, JsonHttpResponseHandler handler) {
        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
//        ICRestClient.put(context, "user/userpic", params, handler);
    }
    public static void initOrder(Context context, JSONObject params, JsonHttpResponseHandler handler) {
//        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.put("orders", params,  handler);
    }
    public static void getOrder(JsonHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.get("orders", params, handler);
    }
    public static void getCars(JsonHttpResponseHandler handler) {
//        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.get("cars/make", null, handler);
    }
    public static void getCarMarks(String _id, JsonHttpResponseHandler handler) {
//        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.get("cars/make/" + _id, null, handler);
    }

    public static void getCouriers(RequestParams params, JsonHttpResponseHandler handler) {
        params.put(ICConst.TOKEN, ICApplication.currentProfile.getToken());
        ICRestClient.get("user/find-courier", params, handler);
    }
}
