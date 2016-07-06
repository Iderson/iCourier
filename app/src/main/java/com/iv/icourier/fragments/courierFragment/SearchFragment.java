package com.iv.icourier.fragments.courierFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.iv.icourier.R;
import com.iv.icourier.activities.MainCourierActivity;
import com.iv.icourier.adapters.OrdersAdapter;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICOrder;

import java.util.ArrayList;

/**
 * Created by iv on 18.03.16.
 */
public class SearchFragment extends Fragment implements OnMapReadyCallback {
    private SupportMapFragment  mapView;
    public  GoogleMap           map;

    //views
    private ArrayList<ICOrder>  ordersList;
    private RecyclerView        ordersView;
    private OrdersAdapter       orderAdapter;
    private FrameLayout         distanceSort;
    private FrameLayout         priceSort;
    private FrameLayout         screenChange;
    private View                backgroundMap;
    private FrameLayout         myLocation;
    private LinearLayout        tabs;
    private RelativeLayout      mapContainer;

    private int                 defaultHeightMapContainer = 0;

    @Override
    public void onDetach() {
        map.clear();
        mapView.onDetach();
        super.onDetach();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        getChildFragmentManager().beginTransaction().add(R.id.search_map, mapView).commit();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mapView.getMapAsync(SearchFragment.this);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courier_search, container, false);

        initViews(root);
        setListeners();
        initAdapter();
//        initRecycler(root);

        return root;
    }
    private void initViews(View root) {
        distanceSort    =   Find.f(root, R.id.search_tab_distance);
        priceSort       =   Find.f(root, R.id.search_tab_price);
        screenChange    =   Find.f(root, R.id.search_map_screen_change);
        backgroundMap   =   Find.v(root, R.id.search_map_view);
        myLocation      =   Find.f(root, R.id.search_map_location);
        tabs            =   Find.l(root, R.id.search_tabs);
        mapContainer    =   Find.r(root, R.id.search_map_container);

        distanceSort.setActivated(true);
    }
    private void setListeners() {
        screenChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!screenChange.isActivated())
                    mapFullScreen(getActivity());
                else mapDefaultScreen(getActivity());
            }
        });
        distanceSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceSort.setActivated(true);
                priceSort.setActivated(false);
            }
        });
        priceSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distanceSort.setActivated(false);
                priceSort.setActivated(true);
            }
        });
    }
    private void initAdapter() {
//        ordersList = ModelHelper.getOrdersList();
//        orderAdapter = new OrdersAdapter(ordersList, getActivity());
    }
    private void initRecycler(View root) {
        ordersView = (RecyclerView) root.findViewById(R.id.courier_search_recycler);
        ordersView.setHasFixedSize(false);
        ordersView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ordersView.setItemAnimator(new DefaultItemAnimator());
        ordersView.setAdapter(orderAdapter);
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
        tabs.setVisibility(value);
        MainCourierActivity.tabs.setVisibility(value);
        myLocation.setVisibility(value == View.VISIBLE ? View.GONE : View.VISIBLE);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
