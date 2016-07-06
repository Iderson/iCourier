package com.iv.icourier.network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iv.icourier.R;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by iv on 25.03.16.
 */
public class ICHandlers {
    public interface Executor {
        void sucExec(Context mContext, Bundle args);

        void errExec(Context mContext, Bundle args);
    }

    public interface ExecutorWithStart {
        void startExec(Context mContext, Bundle args);

        void sucExec(Context mContext, Bundle args);

        void errExec(Context mContext, Bundle args);
    }

    public static JsonHttpResponseHandler defaultHandler(final Context mContext, final Executor exec) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.w("network", "defaultHandler success");
                Log.w("network", response != null ? "json " + response : "json is null");

                Bundle json = new Bundle();
                json.putString(ICConst.RESPONSE, response != null ? response.toString() : "");
                if (exec != null)
                    exec.sucExec(mContext, json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler error");
                        Log.w("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        if (exec != null)
                            exec.sucExec(mContext, json);
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        json.putInt(ICConst.STATUS, statusCode);
                        if (exec != null)
                            exec.errExec(mContext, json);
                    }
                }
//                else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
                else Log.e("network", errorResponse != null ? "json " + errorResponse : "json is null");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler success");
                        if (exec != null)
                            exec.sucExec(mContext, new Bundle());
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", responseString != null ? "responseString " + responseString : "responseString is null");

                        Bundle json = new Bundle();
                        json.putInt(ICConst.STATUS, statusCode);
                        if (exec != null)
                            exec.errExec(mContext, json);
                    }
                }
//                else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
                else Log.e("network", responseString != null ? "responseString " + responseString : "responseString is null");
            }
        };
    }

    public static JsonHttpResponseHandler handlerWithProgress(final Context mContext, final ExecutorWithStart exec) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onStart() {

                if (exec != null)
                    exec.startExec(mContext, Bundle.EMPTY);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.w("network", "defaultHandler success");
                Log.w("network", response != null ? "json " + response : "json is null");

                Bundle json = new Bundle();
                json.putString(ICConst.RESPONSE, response != null ? response.toString() : "");
                if (exec != null)
                    exec.sucExec(mContext, json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler success");
                        Log.w("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        if (exec != null)
                            exec.sucExec(mContext, json);
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        json.putInt("status", statusCode);
                        if (exec != null)
                            exec.errExec(mContext, json);
                    }
                } else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler success");
                        if (exec != null)
                            exec.sucExec(mContext, new Bundle());
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", responseString != null ? "responseString " + responseString : "responseString is null");

                        if (exec != null)
                            exec.errExec(mContext, new Bundle());
                    }
                } else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
            }
        };
    }

    public static JsonHttpResponseHandler handlerWithResultArray(final Context mContext, final Executor exec) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.w("network", "defaultHandler success");
                Log.w("network", response != null ? "json " + response : "json is null");

                Bundle json = new Bundle();
                json.putString(ICConst.RESPONSE, response != null ? response.toString() : "");
                if (exec != null)
                    exec.sucExec(mContext, json);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler success");
                        Log.w("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        if (exec != null)
                            exec.sucExec(mContext, json);
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", errorResponse != null ? "json " + errorResponse : "json is null");

                        Bundle json = new Bundle();
                        json.putString(ICConst.RESPONSE, errorResponse != null ? errorResponse.toString() : "");
                        json.putInt("status", statusCode);
                        if (exec != null)
                            exec.errExec(mContext, json);
                    }
                } else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (statusCode != 0) {
                    if (statusCode == 200) {
                        Log.w("network", "defaultHandler success");
                        if (exec != null)
                            exec.sucExec(mContext, new Bundle());
                    } else {
                        Log.e("network", "defaultHandler error");
                        Log.e("network", "statuscode " + statusCode);
                        Log.e("network", responseString != null ? "responseString " + responseString : "responseString is null");

                        if (exec != null)
                            exec.errExec(mContext, new Bundle());
                    }
                } else ICHelper.showDialog(mContext, mContext.getString(R.string.connection_error), null, null);
            }
        };
    }
}
