package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.adapters.CarsAdapter;
import com.iv.icourier.adapters.RecyclerItemClickListener;
import com.iv.icourier.models.ICCar;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;

import java.util.ArrayList;

public class ChooseCarActivity extends AppCompatActivity {
    private RecyclerView        carsView;
    private CarsAdapter         carsAdapter;
    private ArrayList<ICCar>    carsList;
    private String mCarsName = "";
    private String requestString = "";
    private String mCarsId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);

        setListeners();
        initAdapter();
    }
    private void setListeners() {
        findViewById(R.id.choose_car_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.choose_car_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(requestString, mCarsName);
                intent.putExtra("id", mCarsId);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    private void initAdapter() {
        String markId = getIntent().getExtras().getString("ID");
        if (markId != null && markId.length() > 0){
            requestString = "model";
            ((TextView)findViewById(R.id.title)).setText("Выбор модели машины");
            ICApiManager.getCarMarks(markId, ICHandlers.defaultHandler(this, new ICHandlers.Executor() {
                @Override
                public void sucExec(Context mContext, Bundle args) {
                    Log.w("carMarksList", "success " + args.toString());
                    carsList = ICCar.makesFromJson(args);
                    carsAdapter = new CarsAdapter(carsList, ChooseCarActivity.this);
                    initRecycler();
                }

                @Override
                public void errExec(Context mContext, Bundle args) {
                    Log.w("carMarksList", "error " + args.toString());

                }
            }) );
        } else {
            requestString = "make";
            ((TextView)findViewById(R.id.title)).setText("Выбор марки машины");
            ICApiManager.getCars(ICHandlers.defaultHandler(this,
                    new ICHandlers.Executor() {
                        @Override
                        public void sucExec(Context mContext, Bundle args) {
                            Log.w("carModelList", "success " + args.toString());
                            carsList = ICCar.modelsFromJson(args);
                            carsAdapter = new CarsAdapter(carsList, ChooseCarActivity.this);
                            initRecycler();
                        }

                        @Override
                        public void errExec(Context mContext, Bundle args) {
                            Log.w("carModelList", "error " + args.toString());

                        }
                    }) );
        }
//        carsList = ModelHelper.getCarMarksList();
    }
    private void initRecycler() {
        carsView = (RecyclerView) findViewById(R.id.choose_car_recycler);
        carsView.setItemAnimator(new DefaultItemAnimator());
        carsView.setLayoutManager(new LinearLayoutManager(this));
        carsView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            carsAdapter.setSelected(position);
                            mCarsName = carsList.get(position).getName();
                            mCarsId = carsList.get(position).getId();
                            Log.v("carPosition", String.valueOf(position));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        carsView.setHasFixedSize(false);
        carsView.setAdapter(carsAdapter);
        findViewById(R.id.pbLoadList).setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
