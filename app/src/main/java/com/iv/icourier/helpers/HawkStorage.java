package com.iv.icourier.helpers;

import android.content.Context;

import com.google.gson.Gson;
import com.iv.icourier.models.ICUser;
import com.orhanobut.hawk.GsonParser;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

/**
 * Created by iv on 25.03.16.
 */
public class HawkStorage {
    public static void hawkInit(Context context) {
        Hawk.init(context)
                .setPassword("HawkStorage")
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.HIGHEST)
                .setStorage(HawkBuilder.newSharedPrefStorage(context))
                .setLogLevel(LogLevel.FULL)
                .setParser(new GsonParser(new Gson()))
                .build();
    }
    public static void storeAuthData(String phone, String code) {
        Hawk.put(ICConst.PHONE, phone);
        Hawk.put(ICConst.CODE, code);
    }
    public static String[] getAuthData() {
        String[] data = new String[2];
        String phone;
        String code;
        phone = Hawk.get(ICConst.PHONE, null);
        if (phone == null)
            return null;
        code = Hawk.get(ICConst.CODE, null);
        if (code == null)
            return null;
        data[0] = phone;
        data[1] = code;
        return data;
    }
}
