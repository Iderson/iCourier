package com.iv.icourier.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.models.ICOrder;

public class OrderActivity extends AppCompatActivity {
    private boolean isCourier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setListeners();
    }

    private void setListeners() {
        findViewById(R.id.order_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.order_courier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCourier = true;
                ICApplication.currentOrder = new ICOrder();

                Intent blank = new Intent(OrderActivity.this, BlankActivity.class);
//                startActivityForResult(blank, ICConst.REQUEST_PHONE);
                startActivity(blank);
            }
        });
        findViewById(R.id.order_furniture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCourier = false;
                ICApplication.currentOrder = new ICOrder();

                Intent blank = new Intent(OrderActivity.this, BlankActivity.class);
                blank.putExtra(ICConst.IS_COURIER, isCourier);
//                startActivityForResult(blank, ICConst.REQUEST_PHONE);
                startActivity(blank);
            }
        });
        findViewById(R.id.order_im_executor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this, RegisterCourierActivity.class));
            }
        });
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ICConst.REQUEST_COURIER:
                if (resultCode == Activity.RESULT_OK) {
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
    @Override
    public void onBackPressed() {
        finish();
    }
}
