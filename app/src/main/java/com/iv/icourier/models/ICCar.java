package com.iv.icourier.models;

import android.os.Bundle;

import com.iv.icourier.helpers.ICConst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class ICCar {
    private String id;
    private String name;
    private int    mMakeId;
    private String mMake;
    private String model;
    private int    modelId;
    private ArrayList<String> documents;
    private String number;
    private boolean isCheck;

    public int getModelId() {
        return modelId;
    }

    public int getMakeId() {
        return mMakeId;
    }

    public void setMakeId(int _makeId) {
        mMakeId = _makeId;
    }

    public void setModelId(int _modelId) {
        modelId = _modelId;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<String> _documents) {
        documents = _documents;
    }

    public void setCheck(boolean _check) {
        isCheck = _check;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public java.lang.String getModel() {
        return model;
    }

    public void setModel(java.lang.String model) {
        this.model = model;
    }

    public java.lang.String getNumber() {
        return number;
    }

    public void setNumber(java.lang.String number) {
        this.number = number;
    }


    public String getMake() {
        return mMake;
    }

    public void setMake(String _make) {
        mMake = _make;
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        id = _id;
    }

    public static ArrayList<ICCar> makesFromJson(Bundle _args) {
        ArrayList<ICCar> marksList = new ArrayList<>();
        try {
            JSONArray data = new JSONObject(_args.getString(ICConst.RESPONSE)).getJSONArray("cars");
            for (int i = 0; i < data.length(); i++) {
                ICCar car = new ICCar();
                JSONObject item = (JSONObject) data.get(i);
                car.setId(item.getString("id"));
                car.setName(item.getString("name"));
                car.setMake(item.getString("make"));
                car.setModel(item.getString("model"));
//                marksList.add(getId());
                marksList.add(car);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return marksList;
    }

    public static ArrayList<ICCar> modelsFromJson(Bundle _args) {
        ArrayList<ICCar> marksList = new ArrayList<>();
        try {
            JSONArray data = new JSONObject(_args.getString(ICConst.RESPONSE)).getJSONArray("makers");
            for (int i = 0; i < data.length(); i++) {
                ICCar car = new ICCar();
                JSONObject item = (JSONObject) data.get(i);
                car.setId(item.getString("id"));
                car.setName(item.getString("name"));
//                marksList.add(getId());
                marksList.add(car);
            }
        } catch (JSONException je){
            je.printStackTrace();
        }
        return marksList;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }
}
