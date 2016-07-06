package com.iv.icourier.fragments.blankFragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.BlankActivity;
import com.iv.icourier.activities.RechargeActivity;
import com.iv.icourier.fragments.TransparentFragment;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.helpers.ModelHelper;
import com.iv.icourier.models.ICCourier;
import com.iv.icourier.models.ICOrder;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.iv.icourier.views.NonSwipableViewPager;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iv on 09.03.16.
 */
public class PagerBlankFragment extends Fragment implements OnMapReadyCallback {
    private static SupportMapFragment mapView;
    public static GoogleMap map;

    //views
    private static RelativeLayout sMapContainer;
    public static NonSwipableViewPager sPager;
    public static NonSwipableViewPager sPagerForDescription;
    private static TextView title;
    private TextView courierCount;
    private static View tabOne;
    private static View tabTwo;
    private static View tabThree;
    private static FrameLayout nextButton;
    private static TextView nextButtonText;
    private static LinearLayout tabs;
    private static FrameLayout changeScreen;

    private static View mapViewBackground;
    private static FrameLayout myLocation;
    //values
    private static int defaultHeightMapContainer = 0;
    public static int currentFragmentItem = 0;
    private int position;
    private LocationManager mLocationManager;
    private boolean mHasLocation = true;
    public static ICOrder currentOrder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank_pager, container, false);

        initViews(root);
        setListeners(root);

        AppSectionsPagerAdapter mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getChildFragmentManager(), getActivity());
        sPager.setAdapter(mAppSectionsPagerAdapter);

        AppSectionsPagerForDescriptionAdapter appSectionsPagerForDescriptionAdapter = new AppSectionsPagerForDescriptionAdapter(getChildFragmentManager(), getActivity());
        sPagerForDescription.setAdapter(appSectionsPagerForDescriptionAdapter);
        return root;
    }

    private void initViews(View root) {
        sPager = (NonSwipableViewPager) root.findViewById(R.id.blank_pager);
        sPagerForDescription = (NonSwipableViewPager) root.findViewById(R.id.blank_pager_for_description);
        title = (TextView) root.findViewById(R.id.blank_title);
        tabOne = root.findViewById(R.id.blank_tab_one);
        tabTwo = root.findViewById(R.id.blank_tab_two);
        tabThree = root.findViewById(R.id.blank_tab_three);
        sMapContainer = (RelativeLayout) root.findViewById(R.id.blank_map_container);
        tabs = (LinearLayout) root.findViewById(R.id.blank_tabs);
        nextButton = (FrameLayout) root.findViewById(R.id.blank_next);
        changeScreen = (FrameLayout) root.findViewById(R.id.blank_map_screen_change);
        mapViewBackground = root.findViewById(R.id.blank_map_view);
        myLocation = (FrameLayout) root.findViewById(R.id.blank_map_location);
        nextButtonText = (TextView) root.findViewById(R.id.blank_next_text);
        courierCount = (TextView) root.findViewById(R.id.blank_courier_count);

        tabOne.setActivated(true);
    }

    private void setListeners(View root) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nextButtonText.getText().toString().equals(getString(R.string.next))) {
                    if ((BlankActivity.isCourier && BlankDescriptionCourierFragment.isFilled())
                            || BlankDescriptionFurnitureFragment.isFilled()) {
                        Intent recharge = new Intent(PagerBlankFragment.this.getContext(), RechargeActivity.class);
                        startActivityForResult(recharge, ICConst.REQUEST_RECHARGE);
                    } else
                        ICHelper.showDialog(getContext(), "Не все поля заполнены", "Ошибка", null);
                } else if (sPager.getCurrentItem() == 0 && BlankSenderFragment.isFilled()) {
                    sPager.setCurrentItem(sPager.getCurrentItem() + 1);
                    position = sPager.getCurrentItem();
                    changeTabs(getActivity(), position);
                } else if (sPager.getCurrentItem() == 1 && BlankReceiverFragment.isFilled()) {
                    position = 2;
                    sPagerForDescription.setCurrentItem(1);
                    changeTabs(getActivity(), position);
                } else if ((sPager.getCurrentItem() == 1 && !BlankReceiverFragment.isFilled()) ||
                        (sPager.getCurrentItem() == 0 && !BlankSenderFragment.isFilled()))
                    ICHelper.showDialog(getContext(), "Не все поля заполнены", "Ошибка", null);
            }
        });
        changeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!changeScreen.isActivated())
                    mapFullScreen(getActivity());
                else mapDefaultScreen(getActivity());
            }
        });
        root.findViewById(R.id.blank_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(getActivity());
            }
        });
        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHasLocation)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ICHelper.mMyLocation, 15));
                else showSettingsAlert();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ICApiManager.initOrder(getActivity(), ModelHelper.getOrderModelParams(),
                    ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                        @Override
                        public void sucExec(Context mContext, Bundle args) {
                            Log.v("OrderModel", " success " + args.toString());
                        }

                        @Override
                        public void errExec(Context mContext, Bundle args) {
                            Log.v("OrderModel", "error" + args.toString());
                        }
                    }));
        } catch (JSONException _e) {
        _e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        mLocationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        setUpMap();
    }

    @SuppressLint("ValidFragment")
    private void setUpMap() {
        mapView = new SupportMapFragment() {
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                if (map == null) {
//                    map = mapView.getMap();
                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap _googleMap) {
                            map = _googleMap;
                            map.setMyLocationEnabled(true);
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                            map.getUiSettings().setRotateGesturesEnabled(false);
//                            getAddressLocation();

                            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                                @Override
                                public void onCameraChange(CameraPosition _cameraPosition) {
                                    getAddressLocation();
//                                    getCouriers();
                                }
//            }
                            });
                        }
                    });
                }
            }
        };
        getChildFragmentManager().beginTransaction().add(R.id.blank_map, mapView).commit();
    }

    private void getAddressLocation() {
        LatLng pin = map.getCameraPosition().target;
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            } else if (pin == null || pin.latitude == 0.0 && pin.longitude == 0.0)
                return;
//            map.setMyLocationEnabled(false);
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> fromLocation = geocoder.getFromLocation(pin.latitude, pin.longitude, 1);
            Address address = fromLocation.get(0);
            /*StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(i))
                        .append(i == address.getMaxAddressLineIndex() - 1 ? "" : ", ");
                Log.v("AddressLine " + i, address.getAddressLine(i));
            }*/
            String addressLine = address.getAddressLine(0);
            String city = address.getAddressLine(1);

            if(currentFragmentItem == 0) {
                BlankSenderFragment.where.setText(addressLine);
                ICApplication.currentOrder.setCityFrom(city);
            }
            if(currentFragmentItem == 1)
                BlankReceiverFragment.where.setText(addressLine);
            ICApplication.currentOrder.setCityTo(city);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition _cameraPosition) {
                getAddressLocation();
//                getCouriers();
                /*LatLng pin = map.getCameraPosition().target;
                try {
                    Geocoder geocoder = new Geocoder(getContext());
                    List<Address> fromLocation = geocoder.getFromLocation(pin.latitude, pin.longitude, 1);
                    Address address = fromLocation.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i))
                                .append(i == address.getMaxAddressLineIndex() - 1 ? "" : ", ");
                    }
                    if(currentFragmentItem == 0) {
                        BlankSenderFragment.where.setText(sb);
                    }
                    if(currentFragmentItem == 1)
                        BlankReceiverFragment.where.setText(sb);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }*/
            }
        });
    }
    public static void mapFullScreen(Activity activity) {
        title.setText(activity.getString(R.string.map));
        changeScreen.setActivated(true);
        if (defaultHeightMapContainer == 0)
            defaultHeightMapContainer = sMapContainer.getLayoutParams().height;

        changeVisibleViews(View.GONE);
        ICHelper.changeHeightWithAnim(activity, sMapContainer, 0);
    }
    @Override
    public void onDetach() {
        map.clear();
        mapView.onDetach();
        super.onDetach();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000, 10,
                locationListener);
        if (mHasLocation && map != null)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ICHelper.mMyLocation, 15));
    }
    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (mHasLocation)   showLocation(location);
        }
        @Override
        public void onProviderDisabled(String provider) {
            showSettingsAlert();
        }
        @Override
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mHasLocation = true;
            showLocation(mLocationManager.getLastKnownLocation(provider));
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mHasLocation = true;
                showLocation(mLocationManager.getLastKnownLocation(provider));
            }
        }
    };

    private void getCouriers() {
        ICUser user = ICApplication.currentProfile;
        if (user != null) {
            ICApiManager.getCouriers(new RequestParams(),
                    ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                @Override
                public void sucExec(Context mContext, Bundle args) {
                    Log.v("FindСourierSuccess", args.toString());
                    ICApplication.currentOrder.getCouriersFromJson(args);
                    if (ICApplication.currentOrder != null && ICApplication.currentOrder.getCouriers() != null) {
                        ArrayList<ICCourier> couriers = ICApplication.currentOrder.getCouriers();
                        int count = 0;
                        for (int item = 0; item < couriers.size(); item++)
                            if (couriers.get(item).getLocation().getCity().equals("Челябинск"))
                                count++;
                        courierCount.setText(String.format("Курьеров: %d", count));
                    }
                }

                @Override
                public void errExec(Context mContext, Bundle args) {
                    Log.v("FindСourierError", args.toString());

                }
            }));
        }
    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        ICHelper.mMyLocation = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ICHelper.mMyLocation, 15));

//        getCouriers();
    }

    public static void mapDefaultScreen(Activity activity) {
        title.setText(currentFragmentItem == 0 ? activity.getString(R.string.sender) :
                activity.getString(R.string.receiver));
        changeScreen.setActivated(false);
        changeVisibleViews(View.VISIBLE);
        ICHelper.changeHeightWithAnim(activity, sMapContainer, defaultHeightMapContainer);
    }

    private static void changeVisibleViews(int value) {
        nextButton.setVisibility(value);
        tabs.setVisibility(value);
        sPager.setVisibility(value);
        myLocation.setVisibility(value == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void showSettingsAlert() {
        ICHelper.showSettingsAlert(getContext(), "Ошибка", "Нет сети или отключена служба геолокации", new ICHelper.ExecutorSettings() {
            @Override
            public void positiveExec() {
                mHasLocation = true;
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getContext().startActivity(intent);
            }

            @Override
            public void negativeExec() {
                mHasLocation = false;
            }
        });
    }

    public static void goBack(Activity activity) {
        if (changeScreen.isActivated())
            mapDefaultScreen(activity);
        else {
            if (currentFragmentItem == 2) {
                changeTabs(activity, 1);
                sPagerForDescription.setCurrentItem(0);
                currentFragmentItem = 1;
            } else if (currentFragmentItem == 1) {
                changeTabs(activity, 0);
                sPager.setCurrentItem(0);
                currentFragmentItem = 0;
            } else activity.finish();
        }
    }

    public static void changeTabs(Context mContext, int position) {
        currentFragmentItem = position;
        if (position == 0) {
            title.setText(mContext.getString(R.string.sender));

            tabOne.setActivated(true);
            tabTwo.setActivated(false);
            tabThree.setActivated(false);
        } else if (position == 1) {
            title.setText(mContext.getString(R.string.receiver));

            tabOne.setActivated(false);
            tabTwo.setActivated(true);
            tabThree.setActivated(false);

            nextButtonText.setText(mContext.getString(R.string.next));
        } else {
            tabOne.setActivated(false);
            tabTwo.setActivated(false);
            tabThree.setActivated(true);

            nextButtonText.setText(BlankActivity.isCourier ? mContext.getString(R.string.call_courier) :
                    mContext.getString(R.string.call_furniture));
        }
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        Context mContext;
        public AppSectionsPagerAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: {
                    return new BlankSenderFragment();
                }
                case 1: {
                    return new BlankReceiverFragment();
                }
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return mContext.getString(R.string.nothing);
                }
                case 1: {
                    return mContext.getString(R.string.nothing);
                }
                default:
                    break;
            }
            return null;
        }
    }
    public static class AppSectionsPagerForDescriptionAdapter extends FragmentPagerAdapter {
        Context mContext;
        public AppSectionsPagerForDescriptionAdapter(FragmentManager fm, Context mContext) {
            super(fm);
            this.mContext = mContext;
        }
        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0: {
                    return new TransparentFragment();
                }
                case 1: {
                    if (BlankActivity.isCourier)
                        return new BlankDescriptionCourierFragment();
                    else
                        return new BlankDescriptionFurnitureFragment();
                }
                default:
                    return null;
            }
        }
        @Override
        public int getCount() {
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return mContext.getString(R.string.nothing);
                }
                case 1: {
                    return mContext.getString(R.string.nothing);
                }
                default:
                    break;
            }
            return null;
        }
    }
    private JSONObject getParams() {
//        RequestParams paramObject = new RequestParams();
        JSONObject jsonObject = new JSONObject();
        try {
//            paramObject.setUseJsonStreamer(false);
//            params.setUseJsonStreamer(false);
            JSONObject params = new JSONObject();
            params.put(ICConst.ORDER_TYPE, 1);
            params.put(ICConst.PAYMENT, 2);
            params.put(ICConst.ORDER_NAME, ICApplication.currentOrder .getOrderName());
            params.put(ICConst.ORDER_DESCRIPTION, ICApplication.currentOrder .getOrderDescription());

            JSONObject addressFrom = new JSONObject();
            addressFrom.put(ICConst.CITY_FROM, ICApplication.currentOrder .getCityFrom());
            addressFrom.put(ICConst.ADDRESS_FROM, ICApplication.currentOrder .getAddressFrom());
            params.put(ICConst.FROM, addressFrom);

            JSONObject periodFrom = new JSONObject();
            periodFrom.put(ICConst.DATE_FROM, ICApplication.currentOrder .getDateFrom());
            periodFrom.put(ICConst.TIME_FROM_START, ICApplication.currentOrder .getTimeFromStart());
            periodFrom.put(ICConst.TIME_FROM_TILL, ICApplication.currentOrder .getTimeFromTill());
            params.put(ICConst.FROM_PERIOD, periodFrom);

            JSONObject addressTo = new JSONObject();
            addressTo.put(ICConst.CITY_TO, ICApplication.currentOrder .getCityTo());
            addressTo.put(ICConst.ADDRESS_TO, ICApplication.currentOrder .getAddressTo());
            params.put(ICConst.TO, addressTo);

            JSONObject periodTo = new JSONObject();
            periodTo.put(ICConst.DATE_TO, ICApplication.currentOrder .getDateTo());
            periodTo.put(ICConst.TIME_TO_START, ICApplication.currentOrder .getTimeToStart());
            periodTo.put(ICConst.TIME_TO_TILL, ICApplication.currentOrder .getTimeToTill());
            params.put(ICConst.TO_PERIOD, periodTo);

            JSONObject sender = new JSONObject();
            JSONArray senderPhone = new JSONArray();
            sender.put(ICConst.SENDER_NAME, ICApplication.currentOrder .getSenderName());
            senderPhone.put(0, ICApplication.currentOrder .getSenderPhone());
            sender.put(ICConst.SENDER_PHONE, senderPhone);
            params.put(ICConst.SENDER, sender);

            JSONObject recipient = new JSONObject();
            JSONArray recipientPhone = new JSONArray();
            recipient.put(ICConst.RECIPIENT_NAME, ICApplication.currentOrder .getRecipientName());
            recipientPhone.put(0, ICApplication.currentOrder .getRecipientPhone());
            recipient.put(ICConst.RECIPIENT_PHONE, recipientPhone);
            params.put(ICConst.RECIPIENT, recipient);

            JSONObject receiver = new JSONObject();
            receiver.put(ICConst.RECEIVED_NAME, ICApplication.currentOrder .getReceiverComment());
            receiver.put(ICConst.RECEIVED_COMMENT, ICApplication.currentOrder .getReceiverComment());
            params.put(ICConst.RECEIVED_BY, receiver);

            params.put(ICConst.CARGO, getCargo());
            jsonObject.put("order", params);
            jsonObject.put("token", ICApplication.currentProfile.getToken());

        } catch (JSONException _e) {
            _e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return jsonObject;
    }
    private JSONArray getCargo() throws JSONException {
        JSONArray array = new JSONArray();
        JSONObject cargoItem = new JSONObject();
        cargoItem.put(ICConst.CARGO_NAME, ICApplication.currentOrder .getCargoName());
        cargoItem.put(ICConst.CARGO_DESC, ICApplication.currentOrder .getCargoDesc());

        JSONObject cargoSize = new JSONObject();
        cargoSize.put(ICConst.CARGO_HEIGHT, ICApplication.currentOrder .getCargoHeight());
        cargoSize.put(ICConst.CARGO_WIDTH, ICApplication.currentOrder .getCargoWidth());
        cargoSize.put(ICConst.CARGO_LENGTH, ICApplication.currentOrder .getCargoLength());
        cargoItem.put(ICConst.CARGO_SIZE, cargoSize);

        cargoItem.put(ICConst.LOADERS_COUNT, ICApplication.currentOrder .getLoaderCount());
        cargoItem.put(ICConst.PACKAGING, ICApplication.currentOrder .getCargoPackaging());
        array.put(0, cargoItem);

        return array;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case ICConst.REQUEST_RECHARGE:
                    try {
                        ICApiManager.initOrder(getContext(), ModelHelper.getOrderModelParams(),
                                ICHandlers.defaultHandler(getContext(),
                                        new ICHandlers.Executor() {
                                    @Override
                                    public void sucExec(Context mContext, Bundle args) {
                                        Log.w("newOrder", "success " + args.toString());
    //                                    ICApplication.currentOrder .initDataFromJSON(args);
    //                                    getActivity().setResult(Activity.RESULT_OK);
                                        ICHelper.showDialog(PagerBlankFragment.this.getContext(), getString(R.string.order_success), getString(R.string.ready),
                                                new ICHelper.Executor() {
                                                    @Override
                                                    public void exec() {
                                                        getActivity().finish();
                                                    }
                                                });
                                    }

                                    @Override
                                    public void errExec(Context mContext, Bundle args) {
                                        Log.e("newOrder", "error " + args.toString());
    //                                    dialog.dismiss();
                                        ICHelper.showDialog(mContext, getString(R.string.error),
                                                getString(R.string.attention), new ICHelper.Executor() {
                                            @Override
                                            public void exec() {
                                                getActivity().finish();
                                            }
                                        });
                                    }
                                }));
                    } catch (JSONException _e) {
                        _e.printStackTrace();
                    }
//                    getActivity().finish();
                    break;
                /*case ICConst.REQUEST_PHONE:
                    Intent recharge = new Intent(PagerBlankFragment.this.getContext(), RechargeActivity.class);
                    startActivityForResult(recharge, ICConst.REQUEST_RECHARGE);
                    break;*/
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
}