package com.iv.icourier.models;

import java.util.ArrayList;

/**
 * Created by Andread on 28.05.2016.
 */
public class ICCourier {
    private String              mId;
    private String              mMail;
    private String              mFirstName;
    private String              mMiddleName;
    private String              mLastName;
    private String              mPhotoUrl;
    private String              mBirthday;
    private ArrayList<String>   mPhones;
    private ICLocation          mLocation;
    private String              mRating;
    private String              mHasCar;
    private ArrayList<ICCar>    mCars;

    public String getHasCar() {
        return mHasCar;
    }

    public void setHasCar(String _hasCar) {
        mHasCar = _hasCar;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String _rating) {
        mRating = _rating;
    }

    public ICLocation getLocation() {
        return mLocation;
    }

    public void setLocation(ICLocation _location) {
        mLocation = _location;
    }

    public ArrayList<String> getPhones() {
        return mPhones;
    }

    public void setPhones(String _phones) {
        mPhones.add(_phones);
    }

    public void setPHONES(int index, String _PHONES) {
        mPhones.add(index, _PHONES);
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String _birthday) {
        mBirthday = _birthday;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String _photoUrl) {
        mPhotoUrl = _photoUrl;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String _lastName) {
        mLastName = _lastName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String _middleName) {
        mMiddleName = _middleName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String _firstName) {
        mFirstName = _firstName;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String _mail) {
        mMail = _mail;
    }

    public String getId() {
        return mId;
    }

    public void setId(String _id) {
        mId = _id;
    }

    public ArrayList<ICCar> getCars() {
        return mCars;
    }

    public void setCars(ArrayList<ICCar> _cars) {
        mCars = _cars;
    }
}