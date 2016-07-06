package com.iv.icourier.models;

import android.os.Bundle;

import com.iv.icourier.helpers.ICConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by iv on 25.03.16.
 */
public class ICUser {
    private String  id;
    private String  token;
    private String mMainPhone;
    private String  mail;
    private String  first_name;
    private String  middle_name;
    private String  last_name;
    private String  photo_url;
    private String  birthday;
    private ArrayList<String>  phones;
    private String  company_name;
    private String  company;
    private int     user_role;
    private int     user_type;
    private boolean does_have_car;
    private ArrayList<ICCar> cars;
    private int     loaders_count;

    public void initDataFromJSON(Bundle args) {
        try {
            JSONObject data = new JSONObject(args.getString(ICConst.RESPONSE)).getJSONObject(ICConst.USER);
            if (!data.isNull(ICConst.ID))
                setId(data.getString(ICConst.ID));
            if (!data.isNull(ICConst.TOKEN))
                setToken(data.getString(ICConst.TOKEN));
            if (!data.isNull(ICConst.PHONE))
                setMainPhone(data.getString(ICConst.PHONE));
            if (!data.isNull(ICConst.MAIL))
                setMail(data.getString(ICConst.MAIL));
            if (!data.isNull(ICConst.FIRST_NAME))
                setFirst_name(data.getString(ICConst.FIRST_NAME));
            if (!data.isNull(ICConst.MIDDLE_NAME))
                setMiddle_name(data.getString(ICConst.MIDDLE_NAME));
            if (!data.isNull(ICConst.LAST_NAME))
                setLast_name(data.getString(ICConst.LAST_NAME));
            if (!data.isNull(ICConst.PHOTO_URL))
                setPhoto_url(data.getString(ICConst.PHOTO_URL));
            if (!data.isNull(ICConst.BIRTHDAY))
                setBirthday(data.getString(ICConst.BIRTHDAY));
            if (!data.isNull(ICConst.PHONES)) {
                JSONArray jsonArray = data.getJSONArray(ICConst.PHONES);
                ArrayList<String> phonesList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) phonesList.add(jsonArray.getString(i));
                setPhones(phonesList);
            }
            if (!data.isNull(ICConst.COMPANY_NAME))
                setCompany_name(data.getString(ICConst.COMPANY_NAME));
            if (!data.isNull(ICConst.COMPANY))
                setCompany(data.getString(ICConst.COMPANY));
            if (!data.isNull(ICConst.USER_ROLE))
                setUser_role(data.getInt(ICConst.USER_ROLE));
            if (!data.isNull(ICConst.USER_TYPE))
                setUser_type(data.getInt(ICConst.USER_TYPE));
            if (!data.isNull(ICConst.DOES_HAVE_CAR))
                setDoes_have_car(data.getBoolean(ICConst.DOES_HAVE_CAR));
            if (!data.isNull(ICConst.CARS)){}
//                setCars(data.getJSONObject(ICConst.CARS));
            if (!data.isNull(ICConst.LOADERS_COUNT))
                setLoaders_count(data.getInt(ICConst.LOADERS_COUNT));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setMainPhone(String phone) {
        this.mMainPhone = phone;
    }

    public String getMainPhone() {
        return mMainPhone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFirst_name() {
        if (first_name == null)
            return "";
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        if (middle_name == null)
            return "";
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        if (last_name == null)
            return "";
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isPhones() {
        return true;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getUser_role() {
        return user_role;
    }

    public void setUser_role(int user_role) {
        this.user_role = user_role;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public boolean isDoes_have_car() {
        return does_have_car;
    }

    public void setDoes_have_car(boolean does_have_car) {
        this.does_have_car = does_have_car;
    }

    public ArrayList<ICCar> getCars() {
        return cars;
    }

    public void setCars(JSONObject data) {
        //TODO: parse JSON to Array
    }

    public int getLoaders_count() {
        return loaders_count;
    }

    public void setLoaders_count(int loaders_count) {
        this.loaders_count = loaders_count;
    }
}
