package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iv.icourier.R;
import com.iv.icourier.adapters.PriceAdapter;
import com.iv.icourier.adapters.RecyclerItemClickListener;
import com.iv.icourier.helpers.ICConst;

import java.util.ArrayList;

public class PriceActivity extends AppCompatActivity {

    private PriceAdapter priceAdapter;
    private ArrayList<String> mPriceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        setListeners();
        mPriceList = new ArrayList<>();
        mPriceList.add("299");
        mPriceList.add("350");
        mPriceList.add("400");
        mPriceList.add("500");

        initAdapter();
    }
    void selectPrice(String _priceName) {
        Intent intent = new Intent();
        intent.putExtra("PRICE", _priceName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    private void setListeners() {
        findViewById(R.id.price_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.add_another_price_ripple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PriceActivity.this, NewPriceActivity.class), ICConst.REQUEST_PRICE);
            }
        });
    }
    private void initAdapter() {
        priceAdapter = new PriceAdapter(mPriceList, PriceActivity.this);
        initRecycler();
//        carsList = ModelHelper.getCarMarksList();
    }
    private void initRecycler() {
        RecyclerView priceView = (RecyclerView) findViewById(R.id.choose_price_recycler);
        priceView.setItemAnimator(new DefaultItemAnimator());
        priceView.setLayoutManager(new LinearLayoutManager(this));
        priceView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            priceAdapter.setSelected(position);
                            String priceName = mPriceList.get(position);
                            selectPrice(priceName);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
        priceView.setHasFixedSize(false);
        priceView.setAdapter(priceAdapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ICConst.REQUEST_PRICE) {
            String price = data.getStringExtra("PRICE");
            selectPrice(price);
        }
    }
}
