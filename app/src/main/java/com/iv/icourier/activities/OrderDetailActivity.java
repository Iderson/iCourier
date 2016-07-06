package com.iv.icourier.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.iv.icourier.R;
import com.iv.icourier.adapters.OrdersAdapter;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICOrder;

public class OrderDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment  mapView;
    public  GoogleMap           map;

    private FrameLayout         backButton;
    private FrameLayout         screenChange;
    private RelativeLayout      mapContainer;
    private FrameLayout         myLocation;
    private View                backgroundMap;
    private LinearLayout        orderDetailLinear;
    private FrameLayout         actionButton;
    private TextView            title;
    private TextView            distance;
    private TextView            price;
    private TextView            name;
    private TextView            description;
    private TextView            dateStart;
    private TextView            timeStart;
    private TextView            dateEnd;
    private TextView            timeEnd;
    private TextView            addressStart;
    private TextView            metroStart;
    private TextView            sender;
    private TextView            senderPhone;
    private TextView            addressEnd;
    private TextView            metroEnd;
    private TextView            receiver;
    private TextView            receiverPhone;

    private ICOrder             currentOrder;
    private int                 defaultHeightMapContainer = 0;

    @Override
    public void onBackPressed() {
        if (screenChange.isActivated())
            mapDefaultScreen(OrderDetailActivity.this);
        else super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        map.clear();
        mapView.onDetach();
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail); // TODO: 15.04.2016 Searching for orders
        currentOrder = OrdersAdapter.currentOrder;
        initViews();
        setValues();
        setListeners();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setUpMap();
            }
        }).start();
    }
    @SuppressLint("ValidFragment")
    private void setUpMap() {
        mapView = new SupportMapFragment() {
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                if (map == null) {
                    map = mapView.getMap();
                }
            }
        };
        getSupportFragmentManager().beginTransaction().add(R.id.order_detail_map, mapView).commit();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mapView.getMapAsync(OrderDetailActivity.this);
            }
        });
    }
    private void initViews() {
        screenChange    =   (FrameLayout)   findViewById(R.id.order_detail_map_screen_change);
        backgroundMap   =                   findViewById(R.id.order_detail_map_view);
        myLocation      =   (FrameLayout)   findViewById(R.id.order_detail_map_location);
        mapContainer    =   (RelativeLayout)findViewById(R.id.order_detail_map_container);
        orderDetailLinear = (LinearLayout)  findViewById(R.id.order_detail_linear);
        backButton      =   (FrameLayout)   findViewById(R.id.order_detail_back);
        actionButton    =   (FrameLayout)   findViewById(R.id.order_detail_action);
        title           =   (TextView)      findViewById(R.id.order_detail_title);
        distance        =   (TextView)      findViewById(R.id.order_detail_distance);
        price           =   (TextView)      findViewById(R.id.order_detail_price);
        name            =   (TextView)      findViewById(R.id.order_detail_name);
        description     =   (TextView)      findViewById(R.id.order_detail_description);
        dateStart       =   (TextView)      findViewById(R.id.order_detail_date_start);
        dateEnd         =   (TextView)      findViewById(R.id.order_detail_date_end);
        timeStart       =   (TextView)      findViewById(R.id.order_detail_time_start);
        timeEnd         =   (TextView)      findViewById(R.id.order_detail_time_end);
        addressStart    =   (TextView)      findViewById(R.id.order_detail_address_start);
        addressEnd      =   (TextView)      findViewById(R.id.order_detail_address_end);
        metroStart      =   (TextView)      findViewById(R.id.order_detail_metro_start);
        metroEnd        =   (TextView)      findViewById(R.id.order_detail_metro_end);
        sender          =   (TextView)      findViewById(R.id.order_detail_sender);
        senderPhone     =   (TextView)      findViewById(R.id.order_detail_sender_phone);
        receiver        =   (TextView)      findViewById(R.id.order_detail_receiver);
        receiverPhone   =   (TextView)      findViewById(R.id.order_detail_receiver_phone);
    }
    private void setValues() {
        String titleString = String.format("Заказ №%s%n", currentOrder.getNumber());
        title.setText(titleString);
        distance.setText(currentOrder.getDistance());
        price.setText(currentOrder.getPrice());
        name.setText(currentOrder.getName());
        description.setText(currentOrder.getOrderDescription());
        dateStart.setText(currentOrder.getDateFrom());
        dateEnd.setText(currentOrder.getDateTo());
        timeStart.setText(currentOrder.getTimeFromStart());
        timeEnd.setText(currentOrder.getTimeToTill());
        addressStart.setText(currentOrder.getAddressFrom());
        addressEnd.setText(currentOrder.getAddressTo());
        metroStart.setText(currentOrder.getMetroStart());
        metroEnd.setText(currentOrder.getMetroEnd());
        sender.setText(currentOrder.getSenderName());
        senderPhone.setText(currentOrder.getSenderPhone());
        receiver.setText(currentOrder.getReceiverName());
        receiverPhone.setText(currentOrder.getReceiverComment());
    }
    private void setListeners() {
        screenChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!screenChange.isActivated())
                    mapFullScreen(OrderDetailActivity.this);
                else mapDefaultScreen(OrderDetailActivity.this);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(OrderDetailActivity.this, ChooseCarActivity.class), ICConst.REQUEST_CAR);
            }
        });
    }
    public void mapFullScreen(Activity activity) {
        screenChange.setActivated(true);
        if (defaultHeightMapContainer == 0)
            defaultHeightMapContainer = mapContainer.getLayoutParams().height;

        changeVisibleViews(View.GONE);
        ICHelper.changeHeightWithAnim(activity, mapContainer, 0);
    }
    public void mapDefaultScreen(Activity activity) {
        screenChange.setActivated(false);
        changeVisibleViews(View.VISIBLE);
        ICHelper.changeHeightWithAnim(activity, mapContainer, defaultHeightMapContainer);
    }
    private void changeVisibleViews(int value) {
        orderDetailLinear.setVisibility(value);
        myLocation.setVisibility(value == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ICConst.REQUEST_CAR :
                if (resultCode == Activity.RESULT_OK) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                break;
        }
    }
}
