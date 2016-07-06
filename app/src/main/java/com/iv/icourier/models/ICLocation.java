package com.iv.icourier.models;

/**
 * Created by Andread on 28.05.2016.
 */
public class ICLocation {
    private String name;
    private String country;
    private String city;
    private String address;
    private String metro;
    private String latitude;
    private String longitude;

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String _country) {
        country = _country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String _city) {
        city = _city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String _address) {
        address = _address;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String _metro) {
        metro = _metro;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String _latitude) {
        latitude = _latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String _longitude) {
        longitude = _longitude;
    }
}
