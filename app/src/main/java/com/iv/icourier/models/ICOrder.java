package com.iv.icourier.models;

import android.os.Bundle;

import com.iv.icourier.helpers.ICConst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class ICOrder {
    private String id;
    private int orderType;
    private String orderName;
    private int payment_type;
    private String name;
    private String orderDescription;
    private String cityFrom;
    private String addressFrom;
    private String dateFrom;
    private String timeFromStart;
    private String timeFromTill;
    private String cityTo;
    private String addressTo;
    private String dateTo;
    private String timeToStart;
    private String timeToTill;
    private String senderName;
    private String senderPhone;
    private String recipientName;
    private String recipientPhone;
    private String receiverName;
    private String receiverComment;
    private String cargoName;
    private String cargoDesc;
    private String cargoHeight;
    private String cargoWidth;
    private String cargoLength;
    private int     loaderCount;
    private boolean cargoPackaging = false;

    private String number;
    private String price;
    private String distance;
    private String metroStart;
    private String metroEnd;
    private String commentStart;
    private String commentEnd;
    private String status;
    private boolean isActive;
    private ArrayList <ICCourier> mCouriers;

    public void initDataFromJSON(Bundle args) {
        try {
            JSONObject data = new JSONObject(args.getString(ICConst.RESPONSE)).getJSONObject(ICConst.ORDER);
            if (!data.isNull(ICConst.ORDER_TYPE)) setOrderType(data.getInt(ICConst.ORDER_TYPE));
            if (!data.isNull(ICConst.PAYMENT)) setPayment_type(data.getInt(ICConst.PAYMENT));
            if (!data.isNull(ICConst.ORDER_NAME)) setOrderName(data.getString(ICConst.ORDER_NAME));
            if (!data.isNull(ICConst.ORDER_DESCRIPTION)) setOrderDescription(data.getString(ICConst.ORDER_DESCRIPTION));

            if(!data.isNull(ICConst.FROM)) {
                JSONObject from = new JSONObject(data.getString(ICConst.FROM));
                if (!from.isNull(ICConst.CITY_FROM)) setCityFrom(from.getString(ICConst.CITY_FROM));
                if (!from.isNull(ICConst.ADDRESS_FROM)) setAddressFrom(from.getString(ICConst.ADDRESS_FROM));
            }

            if(!data.isNull(ICConst.FROM_PERIOD)) {
                JSONObject fromPeriod = new JSONObject(data.getString(ICConst.FROM_PERIOD));
                if (!fromPeriod.isNull(ICConst.DATE_FROM)) setDateFrom(fromPeriod.getString(ICConst.DATE_FROM));
                if (!fromPeriod.isNull(ICConst.TIME_FROM_START)) setTimeFromStart(fromPeriod.getString(ICConst.TIME_FROM_START));
                if (!fromPeriod.isNull(ICConst.TIME_FROM_TILL)) setTimeFromTill(fromPeriod.getString(ICConst.TIME_FROM_TILL));
            }

            if(!data.isNull(ICConst.TO)) {
                JSONObject to = new JSONObject(data.getString(ICConst.TO));
                if (!to.isNull(ICConst.CITY_TO)) setCityFrom(to.getString(ICConst.CITY_TO));
                if (!to.isNull(ICConst.ADDRESS_TO)) setAddressFrom(to.getString(ICConst.ADDRESS_TO));
            }

            if(!data.isNull(ICConst.TO_PERIOD)) {
                JSONObject toPeriod = new JSONObject(data.getString(ICConst.TO_PERIOD));
                if (!toPeriod.isNull(ICConst.DATE_TO)) setDateTo(toPeriod.getString(ICConst.DATE_TO));
                if (!toPeriod.isNull(ICConst.TIME_TO_START)) setTimeFromStart(toPeriod.getString(ICConst.TIME_TO_START));
                if (!toPeriod.isNull(ICConst.TIME_TO_TILL)) setTimeFromTill(toPeriod.getString(ICConst.TIME_TO_TILL));
            }

            if(!data.isNull(ICConst.SENDER)) {
                JSONObject sender = new JSONObject(data.getString(ICConst.SENDER));

                if (!sender.isNull(ICConst.SENDER_NAME))
                    setSenderName(sender.getString(ICConst.SENDER_NAME));
                if (!sender.isNull(ICConst.SENDER_PHONE))
                    setSenderPhone(sender.getString(ICConst.SENDER_PHONE));
            }

            if(!data.isNull(ICConst.RECIPIENT)) {
                JSONObject recipient = new JSONObject(data.getString(ICConst.RECIPIENT));
                if (!recipient.isNull(ICConst.RECIPIENT_NAME))
                    setRecipientName(recipient.getString(ICConst.RECIPIENT_NAME));
                if (!recipient.isNull(ICConst.RECIPIENT_PHONE))
                    setRecipientPhone(recipient.getString(ICConst.RECIPIENT_PHONE));
            }

            if(!data.isNull(ICConst.RECEIVED_BY)) {
                JSONObject receivedBy = new JSONObject(data.getString(ICConst.RECEIVED_BY));
                if (!receivedBy.isNull(ICConst.RECEIVED_NAME))
                    setReceiverComment(receivedBy.getString(ICConst.RECEIVED_NAME));
                if (!receivedBy.isNull(ICConst.RECEIVED_COMMENT))
                    setReceiverComment(receivedBy.getString(ICConst.RECEIVED_COMMENT));
            }

            if(!data.isNull(ICConst.CARGO)) {
                JSONArray cargo = new JSONArray(data.getString(ICConst.CARGO));
                for (int i = 0; i < cargo.length(); i++){
                    JSONObject cargoItem = (JSONObject) cargo.get(i);
                    if (!cargoItem.isNull(ICConst.CARGO_NAME))
                        setCargoName(cargoItem.getString(ICConst.CARGO_NAME));
                    setCargoDesc(cargoItem.getString(ICConst.CARGO_DESC));
                    if (!cargoItem.isNull(ICConst.CARGO_SIZE)){
                        JSONObject cargoSize = new JSONObject(cargoItem.getString(ICConst.CARGO_SIZE));

                        if (!cargoSize.isNull(ICConst.CARGO_HEIGHT))
                            setCargoHeight(cargoSize.getString(ICConst.CARGO_HEIGHT));
                        if (!cargoSize.isNull(ICConst.CARGO_WIDTH))
                            setCargoWidth(cargoSize.getString(ICConst.CARGO_WIDTH));
                        if (!cargoSize.isNull(ICConst.CARGO_LENGTH))
                            setCargoLength(cargoSize.getString(ICConst.CARGO_LENGTH));
                    }
                    if (!cargoItem.isNull(ICConst.LOADERS_COUNT)) {
                        setLoaderCount(cargoItem.getInt(ICConst.LOADERS_COUNT));
                    setCargoPackaging(cargoItem.getBoolean(ICConst.PACKAGING));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getCouriersFromJson(Bundle _args) {
        try {
            mCouriers = new ArrayList<>();
            JSONArray data = new JSONObject(_args.getString(ICConst.RESPONSE)).getJSONArray(ICConst.LIST);
            for (int item = 0; item < data.length(); item++) {
                ICCourier courier = new ICCourier();
                JSONObject courierItem = (JSONObject) data.get(item);
                if (!courierItem.isNull(ICConst.ID)) courier.setId(courierItem.getString(ICConst.ID));
                if (!courierItem.isNull(ICConst.MAIL)) courier.setMail(courierItem.getString(ICConst.MAIL));
                if (!courierItem.isNull(ICConst.FIRST_NAME)) courier.setFirstName(courierItem.getString(ICConst.FIRST_NAME ));
                if (!courierItem.isNull(ICConst.MIDDLE_NAME)) courier.setMiddleName(courierItem.getString(ICConst.MIDDLE_NAME));
                if (!courierItem.isNull(ICConst.LAST_NAME)) courier.setLastName(courierItem.getString(ICConst.LAST_NAME  ));
                if (!courierItem.isNull(ICConst.PHOTO_URL)) courier.setPhotoUrl(courierItem.getString(ICConst.PHOTO_URL  ));
                if (!courierItem.isNull(ICConst.BIRTHDAY)) courier.setBirthday(courierItem.getString(ICConst.BIRTHDAY   ));
                if (!courierItem.isNull(ICConst.PHONES)) {
                    JSONArray phoneArray = courierItem.getJSONArray(ICConst.PHONES);
//                    for (int i = 0; i < phoneArray.length(); i++)
//                            courier.setPhones((phoneArray.get(0)).toString());
                }
                ICLocation locations = new ICLocation();
                if (!courierItem.isNull(ICConst.LOCATION)) {
                    JSONObject locationObject = courierItem.getJSONObject(ICConst.LOCATION);

                    if (!locationObject.isNull(ICConst.LOCATION_NAME))
                        locations.setName(locationObject.getString(ICConst.LOCATION_NAME));
                    if (!locationObject.isNull(ICConst.LOCATION_COUNTRY))
                        locations.setCountry(locationObject.getString(ICConst.LOCATION_COUNTRY));
                    if (!locationObject.isNull(ICConst.LOCATION_CITY))
                        locations.setCity(locationObject.getString(ICConst.LOCATION_CITY));
                    if (!locationObject.isNull(ICConst.LOCATION_ADDRESS))
                        locations.setAddress(locationObject.getString(ICConst.LOCATION_ADDRESS));
                    if (!locationObject.isNull(ICConst.LOCATION_METRO))
                        locations.setMetro(locationObject.getString(ICConst.LOCATION_METRO));
                    if (!locationObject.isNull(ICConst.LOCATION_LATITUDE))
                        locations.setLatitude(locationObject.getString(ICConst.LOCATION_LATITUDE));
                    if (!locationObject.isNull(ICConst.LOCATION_LONGITUDE))
                        locations.setLongitude(locationObject.getString(ICConst.LOCATION_LONGITUDE));
                    courier.setLocation(locations);
                }
                if (!courierItem.isNull(ICConst.RATING)) courier.setRating(courierItem.getString(ICConst.RATING));
                if (!courierItem.isNull(ICConst.HAS_CAR)){
                    courier.setHasCar(courierItem.getString(ICConst.HAS_CAR));

                    ArrayList<ICCar> cars = new ArrayList<>();
                    JSONArray carArray = courierItem.getJSONArray(ICConst.CAR);
                    for (int i = 0; i < carArray.length(); i++) {
                        JSONObject carObject = (JSONObject) carArray.get(i);
                        ICCar car = new ICCar();

                        if (!carObject.isNull(ICConst.CAR_ID))
                            car.setId(carObject.getString(ICConst.CAR_ID));
                        if (!carObject.isNull(ICConst.CAR_MAKE_ID))
                            car.setMakeId(carObject.getInt(ICConst.CAR_MAKE_ID));
                        if (!carObject.isNull(ICConst.CAR_MAKE))
                            car.setMake(carObject.getString(ICConst.CAR_MAKE));
                        if (!carObject.isNull(ICConst.CAR_MODEL_ID))
                            car.setModelId(carObject.getInt(ICConst.CAR_MODEL_ID));
                        if (!carObject.isNull(ICConst.CAR_MODEL))
                            car.setModel(carObject.getString(ICConst.CAR_MODEL));
                        if (!carObject.isNull(ICConst.CAR_NAME))
                            car.setName(carObject.getString(ICConst.CAR_NAME));
                        if (!carObject.isNull(ICConst.CAR_DOCUMENTS)) {
                            JSONArray jsonArray = carObject.getJSONArray(ICConst.CAR_DOCUMENTS);
                            ArrayList<String> list = new ArrayList<>();
                            for (int k = 0; k < jsonArray.length(); k++)
                                list.add(jsonArray.get(k).toString());
                            car.setDocuments(list);
                        }
                        if (!carObject.isNull(ICConst.CAR_NUMBER))
                            car.setNumber(carObject.getString(ICConst.CAR_NUMBER));
                        cars.add(car);
                    }
                    courier.setCars(cars);
                }

                mCouriers.set(item, courier);
            }
            setCouriers(mCouriers);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public boolean isActive() {
        if(!isActive)
            return false;
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStatus() {
        if (status == null)
            return "";
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        if (number == null)
            return "";
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderDescription() {
        if (orderDescription == null)
            return "";
        return orderDescription;
    }

    public void setOrderDescription(String description) {
        this.orderDescription = description;
    }

    public String getTimeFromStart() {
        if (timeFromStart == null)
            return "00:00:00";
        return timeFromStart;
    }

    public void setTimeFromStart(String timeStart) {
        this.timeFromStart = timeStart;
    }

    public String getTimeToTill() {
        if (timeToTill == null)
            return "23:59:59";
        return timeToTill;
    }

    public void setTimeToTill(String timeEnd) {
        this.timeToTill = timeEnd;
    }

    public String getMetroStart() {
        if (metroStart == null)
            return "";
        return metroStart;
    }

    public void setMetroStart(String metroStart) {
        this.metroStart = metroStart;
    }

    public String getMetroEnd() {
        if (metroEnd == null)
            return "";
        return metroEnd;
    }

    public void setMetroEnd(String metroEnd) {
        this.metroEnd = metroEnd;
    }

    public String getSenderName() {
        if (senderName == null)
            return "";
        return senderName;
    }

    public void setSenderName(String sender) {
        this.senderName = sender;
    }

    public String getSenderPhone() {
        if (senderPhone == null)
            return "";
        return senderPhone;
    }

    public void setSenderPhone(String senderNumber) {
        this.senderPhone = senderNumber;
    }

    public String getReceiverName() {
        if (receiverName == null)
            return "";
        return receiverName;
    }

    public void setReceiverName(String receiver) {
        this.receiverName = receiver;
    }

    public String getReceiverComment() {
        if (receiverComment == null)
            return "";
        return receiverComment;
    }

    public void setReceiverComment(String receiverNumber) {
        this.receiverComment = receiverNumber;
    }

    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateFrom() {
        if (dateFrom == null)
            return "";
        return dateFrom;
    }

    public void setDateFrom(String dateStart) {
        this.dateFrom = dateStart;
    }

    public String getDateTo() {
        if (dateTo == null)
            return "";
        return dateTo;
    }

    public void setDateTo(String dateEnd) {
        this.dateTo = dateEnd;
    }

    public String getAddressFrom() {
        if (addressFrom == null)
            return "";
        return addressFrom;
    }

    public void setAddressFrom(String addressStart) {
        this.addressFrom = addressStart;
    }

    public String getAddressTo() {
        if (addressTo == null)
            return "";
        return addressTo;
    }

    public void setAddressTo(String addressEnd) {
        this.addressTo = addressEnd;
    }

    public String getDistance() {
        if (distance == null)
            return "";
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        if (price == null)
            return "";
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCommentStart() {
        if (commentStart == null)
            return "";
        return commentStart;
    }

    public void setCommentStart(String _commentStart) {
        commentStart = _commentStart;
    }

    public String getCommentEnd() {
        if (commentEnd == null)
            return "";
        return commentEnd;
    }

    public void setCommentEnd(String _commentEnd) {
        commentEnd = _commentEnd;
    }

    public void setId(String _id) {
        id = _id;
    }

    public String getId() {
        if (id == null)
            return "";
        return id;
    }

    public int getOrderType() {
        return orderType;
    }

    public String getOrderName() {
        if (orderName == null)
            return "";
        return orderName;
    }

    public int getPayment_type() {
        return payment_type;
    }

    public String getCityFrom() { if (cityFrom == null)             return "";
        return cityFrom;
    }

    public String getTimeFromTill() {
        if (timeFromTill == null)
            return "23:59:59";
        return timeFromTill;
    }

    public String getCityTo() { if (cityTo == null)             return "";
        return cityTo;
    }

    public String getTimeToStart() {
        if (timeToStart == null)
            return "00:00:00";
        return timeToStart;
    }

    public String getRecipientName() { if (recipientName == null)             return "";
        return recipientName;
    }

    public String getRecipientPhone() { if (recipientPhone == null)             return "";
        return recipientPhone;
    }

    public String getCargoName() { if (cargoName == null)             return "";
        return cargoName;
    }

    public String getCargoDesc() { if (cargoDesc == null)             return "";
        return cargoDesc;
    }

    public String getCargoHeight() {
        return cargoHeight;
    }

    public String getCargoWidth() {
        return cargoWidth;
    }

    public String getCargoLength() {
        return cargoLength;
    }

    public int getLoaderCount() { if (loaderCount == 0)             return 0;
        return loaderCount;
    }

    public boolean getCargoPackaging() {
        return cargoPackaging;
    }

    public void setOrderType(int _orderType) {
        orderType = _orderType;
    }

    public void setPayment_type(int _payment_type) {
        payment_type = _payment_type;
    }

    public void setCityFrom(String _cityFrom) {
        cityFrom = _cityFrom;
    }

    public void setTimeFromTill(String _timeFromTill) {
        timeFromTill = _timeFromTill;
    }

    public void setCityTo(String _cityTo) {
        cityTo = _cityTo;
    }

    public void setTimeToStart(String _timeToStart) {
        timeToStart = _timeToStart;
    }

    public void setRecipientName(String _recipientName) {
        recipientName = _recipientName;
    }

    public void setRecipientPhone(String _recipientPhone) {
        recipientPhone = _recipientPhone;
    }

    public void setCargoName(String _cargoName) {
        cargoName = _cargoName;
    }

    public void setCargoDesc(String _cargoDesc) {
        cargoDesc = _cargoDesc;
    }

    public void setCargoHeight(String _cargoHeight) {
        this.cargoHeight = _cargoHeight;
    }

    public void setCargoWidth(String _cargoWidth) {
        this.cargoWidth = _cargoWidth;
    }

    public void setCargoLength(String _cargoLength) {
        this.cargoLength = _cargoLength;
    }

    public void setLoaderCount(int _loaderCount) {
        loaderCount = _loaderCount;
    }

    public void setCargoPackaging(boolean _bPackaging) {
        cargoPackaging = _bPackaging;
    }

    public void setActive(boolean _active) {
        isActive = _active;
    }

    public void setOrderName(String _orderName) {
        orderName = _orderName;
    }

    public ArrayList<ICCourier> getCouriers() {
        return mCouriers;
    }

    public void setCouriers(ArrayList<ICCourier> _couriers) {
        mCouriers = _couriers;
    }
}