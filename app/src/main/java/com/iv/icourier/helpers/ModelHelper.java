package com.iv.icourier.helpers;

import android.util.Log;

import com.iv.icourier.ICApplication;
import com.iv.icourier.models.ICCar;
import com.iv.icourier.models.ICOrder;
import com.iv.icourier.models.ICUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class ModelHelper {
    public static ArrayList<ICOrder> getOrdersList() {
        ArrayList<ICOrder> data = new ArrayList<ICOrder>();
        ICOrder order;

        order = new ICOrder();
        order.setStatus("Отправление забрано");
        order.setAddressTo("ул. Пролетарская, 235");
        order.setAddressFrom("ул. Ленина, 56А");
        order.setDateTo("19 декабря");
        order.setDateFrom("18 декабря");
        order.setDistance("1,2 км");
        order.setName("Букет цветов");
        order.setPrice("500 Р");
        order.setNumber("213124");
        order.setOrderDescription("Необходимо передать прямо в руки получателю");
        order.setTimeFromStart("09:00 - 12:00");
        order.setTimeToTill("12:00 - 15:00");
        order.setMetroStart("Первомайская");
        order.setSenderName("Козырев Игорь Николаевич");
        order.setSenderPhone("+7 ** *** ** **");
        order.setMetroEnd("Белорусская");
        order.setReceiverName("Золотов Олег Игоревич");
        order.setReceiverComment("+7 ** *** ** **");
        order.setIsActive(false);
        data.add(order);

        order = new ICOrder();
        order.setStatus("Отправление забрано");
        order.setAddressTo("ул. Пролетарская, 235");
        order.setAddressFrom("ул. Ленина, 56А");
        order.setDateTo("19 декабря");
        order.setDateFrom("18 декабря");
        order.setDistance("1,7 км");
        order.setName("Документы");
        order.setPrice("300 Р");
        order.setNumber("8756473");
        order.setOrderDescription("Необходимо передать прямо в руки получателю");
        order.setTimeFromStart("09:00 - 12:00");
        order.setTimeToTill("12:00 - 15:00");
        order.setMetroStart("Первомайская");
        order.setSenderName("Козырев Игорь Николаевич");
        order.setSenderPhone("+7 ** *** ** **");
        order.setMetroEnd("Белорусская");
        order.setReceiverName("Золотов Олег Игоревич");
        order.setReceiverComment("+7 ** *** ** **");
        order.setIsActive(false);
        data.add(order);

        return data;
    }

    public static JSONObject getOrderModelParams() throws JSONException{

        ICUser user = new ICUser();
        user = ICApplication.currentProfile;
        JSONObject paramObject = new JSONObject();
//            paramObject.setUseJsonStreamer(false);
            JSONObject params = new JSONObject();
//            RequestParams params = new JSONObject();
            params.put(ICConst.ORDER_TYPE, "1");
            params.put(ICConst.PAYMENT, "2");
            params.put(ICConst.ORDER_NAME, "Какой-то груз");
            params.put("cost", "350");
            params.put(ICConst.ORDER_DESCRIPTION, "");

            JSONObject addressFrom = new JSONObject();
            addressFrom.put("latitude", "55.15919993700593");
            addressFrom.put("longitude", "65.15919993700593");
            params.put(ICConst.FROM, addressFrom);

            JSONObject periodFrom = new JSONObject();
            periodFrom.put(ICConst.DATE_FROM, "1464944049");
            periodFrom.put(ICConst.TIME_FROM_START, "15");
            periodFrom.put(ICConst.TIME_FROM_TILL, "18");
            params.put(ICConst.FROM_PERIOD, periodFrom);

            JSONObject addressTo = new JSONObject();
            addressTo.put(ICConst.CITY_TO, "Челябинск");
            addressTo.put(ICConst.ADDRESS_TO, "Ленина, 3, 3");
        addressFrom.put("latitude", "55.15919993700593");
        addressFrom.put("longitude", "65.15919993700593");
            params.put(ICConst.TO, addressTo);

            JSONObject periodTo = new JSONObject();
            periodTo.put(ICConst.DATE_TO, "1464944075");
            periodTo.put(ICConst.TIME_TO_START, "15");
            periodTo.put(ICConst.TIME_TO_TILL, "18:30");
            params.put(ICConst.TO_PERIOD, periodTo);

            JSONObject sender = new JSONObject();
            JSONArray senderPhone = new JSONArray();
            sender.put(ICConst.SENDER_NAME, "Попов");
            sender.put("comment", "Попов");
            senderPhone.put("+70000009111");
            sender.put(ICConst.SENDER_PHONE, senderPhone);
            params.put(ICConst.SENDER, sender);

            JSONObject recipient = new JSONObject();
            JSONArray recipientPhone = new JSONArray();
            recipient.put(ICConst.RECIPIENT_NAME, "Иванов Иван Иваныч");
            recipient.put("comment", "Иванов Иван Иваныч");
            recipientPhone.put("70000009112");
            recipient.put(ICConst.RECIPIENT_PHONE, recipientPhone);
            params.put(ICConst.RECIPIENT, recipient);
/*
            JSONObject receiver = new JSONObject();
            receiver.put(ICConst.RECEIVED_NAME, "Получатель");
            receiver.put(ICConst.RECEIVED_COMMENT, "jjj");
            params.put(ICConst.RECEIVED_BY, receiver);*/

            params.put(ICConst.CARGO, getCargo());
        paramObject.put("order", params);
        paramObject.put(ICConst.TOKEN, user.getToken());
        Log.v("TOKEN", user.getToken());
//        return params;
        return paramObject;
    }

    private static JSONArray getCargo() throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject cargoItem = new JSONObject();
        cargoItem.put(ICConst.CARGO_NAME, "wwww");
        cargoItem.put(ICConst.CARGO_DESC, "wwww");

        JSONObject cargoSize = new JSONObject();
        cargoSize.put(ICConst.CARGO_HEIGHT, "2");
        cargoSize.put(ICConst.CARGO_WIDTH, "3");
        cargoSize.put(ICConst.CARGO_LENGTH, "4");
        cargoItem.put(ICConst.CARGO_SIZE, cargoSize);

        cargoItem.put(ICConst.LOADERS_COUNT, "1");
        cargoItem.put(ICConst.PACKAGING, false);
        array.put(0, cargoItem);

        return array;
    }

    public static ArrayList<ICCar> getCarMarksList() {
        ArrayList<ICCar> data = new ArrayList<ICCar>();
        ICCar car;



        car = new ICCar();

        car = new ICCar();
        car.setModel("FIAT Ducato");
        car.setNumber("т017рл77");
        car.setIsCheck(false);
        data.add(car);

        return data;
    }

    public static ArrayList<ICCar> getCarModelsList(int id) {
        ArrayList<ICCar> data = new ArrayList<ICCar>();
        ICCar car;

        car = new ICCar();
        car.setModel("FIAT Ducato");
        car.setNumber("т017рл77");
        car.setIsCheck(false);
        data.add(car);

        car = new ICCar();
        car.setModel("MAN TGS");
        car.setNumber("т872ар77");
        car.setIsCheck(false);
        data.add(car);

        car = new ICCar();
        car.setModel("Газель");
        car.setNumber("р732нт77");
        car.setIsCheck(false);
        data.add(car);

        return data;
    }
}
