package com.iv.icourier.network;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

/**
 * Created by iv on 25.03.16.
 */
public class ICRestClient {
    public  static final String             BASE_URL =              "http://icourier.check-host.ru:8082/api/ver1/";
    public  static final String             BASE_URL_WITHOUT_API =  "http://icourier.check-host.ru:8082/";

    public  static AsyncHttpClient client =                  new AsyncHttpClient();
    public static void setCookieStore(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }
    public static void getCookies(Context context) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
        BasicClientCookie clientCookie = new BasicClientCookie("icourier_cookie", "alias_cookie");
        clientCookie.setVersion(1);
        clientCookie.setDomain("icourier.com");
        clientCookie.setPath("/");
        myCookieStore.addCookie(clientCookie);
    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void patch(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.patch(getAbsoluteUrl(url), params, responseHandler);
    }
    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context,getAbsoluteUrl(url),entity,contentType,responseHandler);
    }
    public static void post(Context context, String url, Header[] headers, RequestParams params, String contentType,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), headers, params, contentType, responseHandler);
    }

    public static void put(Context context, String url, JSONObject params, AsyncHttpResponseHandler handler) {

//        client.addHeader("Content-Type", "application/x-www-form-urlencoded;json");
//        client.addHeader("Accept", "application/json");
        client.setURLEncodingEnabled(true);
        try {
            client.put(context, getAbsoluteUrl(url), new StringEntity(String.valueOf(params)), "application/x-www-form-urlencoded", handler);
        } catch (IOException _e) {
            _e.printStackTrace();
        }
    }

    public static void put(final String url, final JSONObject _params, final AsyncHttpResponseHandler handler) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpClient httpclient = new DefaultHttpClient();
                try {
                    HttpPut httpPut = new HttpPut();
                    httpPut.setURI(new URI(getAbsoluteUrl(url)));
//                    List<NameValuePair> pairs = new ArrayList<>();
//                    pairs.add((new BasicNameValuePair("order", params.toString())));
//                    pairs.add((new BasicNameValuePair(ICConst.TOKEN, ICApplication.currentProfile.getToken())));
//                    httpPut.addHeader("Accept", "application/json");
                    StringEntity entity = new StringEntity(_params.toString(), "UTF-8");
//                    entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
//                    entity.setContentType("application/json");
                    entity.setContentType("application/x-www-form-urlencoded");
                    httpPut.setEntity(entity);
//                    httpPut.setHeader("Content-Type", "application/json");
//                    httpPut.setHeader(HTTP.CONTENT_TYPE,
//                            "application/x-www-form-urlencoded");
                    httpPut.addHeader("Accept", "application/json");
                    HttpResponse response = httpclient.execute(httpPut);
                    String request = inputStreamToString(response.getEntity().getContent());
                    Log.v("requestStringEntity", entity + "!");
                    Log.v("request", request + "!");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException _e) {
                    _e.printStackTrace();
                }
            }
            private String inputStreamToString(InputStream is) throws IOException {
                String line = "";
                StringBuilder total = new StringBuilder();

                BufferedReader rd = new BufferedReader(new InputStreamReader(is));

                while ((line = rd.readLine()) != null)
                    total.append(line);

                return total.toString();
            }

        }).start();
    }
    private static String inputStreamToString(InputStream is) throws IOException {
        String line = "";
        StringBuilder total = new StringBuilder();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        while ((line = rd.readLine()) != null)
            total.append(line);

        return total.toString();
    }
    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler handler ) {
        client.delete(getAbsoluteUrl(url), params, handler);
    }
//    public static void post(Context context, ICCar url, HttpEntity entity, ICCar contentType, AsyncHttpResponseHandler handler) {
//        client.setURLEncodingEnabled(false);
//        client.post(context, url, entity, contentType, handler);
//    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
