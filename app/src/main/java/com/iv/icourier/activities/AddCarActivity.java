package com.iv.icourier.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.iv.icourier.R;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;

import java.util.ArrayList;

public class AddCarActivity extends AppCompatActivity {
    private EditText make;
    private EditText    model;
    private EditText    number;
    private String mMake;
    private String mModel;
    private String mNumber;
    private ImageView ivSts;
    private ImageView ivRights;
    private boolean isRight = false;
    private boolean isSts = false;
    private Drawable checked;
    private Drawable unchecked;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        initViews();
        setListeners();
    }
    private void initViews() {
        make =   (EditText)  findViewById(R.id.add_car_make_edit);
        model   =   (EditText)  findViewById(R.id.add_car_model_edit);
        number  =   (EditText)  findViewById(R.id.add_car_number_edit);
        ivRights = (ImageView)  findViewById(R.id.selector_rights_check);
        ivSts = (ImageView)  findViewById(R.id.selector_sts_check);
        checked = getResources().getDrawable(R.drawable.doc_check);
        unchecked = getResources().getDrawable(R.drawable.doc_un_check);
    }
    private void setListeners() {
        findViewById(R.id.add_car_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddCarActivity.this, ChooseCarActivity.class).putExtra("ID",""),
                        ICConst.REQUEST_MAKE);
            }
        });
        findViewById(R.id.add_car_model).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(make.getText().length() > 0)
                startActivityForResult(new Intent(AddCarActivity.this, ChooseCarActivity.class).putExtra("ID",mId),
                    ICConst.REQUEST_MODEL);
            }
        });
        findViewById(R.id.add_car_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(AddCarActivity.this, number, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        mNumber = _args.getString("");
                    }
                }, null);
            }
        });
        findViewById(R.id.courier_individual_docs_rights).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivRights.setBackground(!isRight ? checked : unchecked);
            }
        });
        findViewById(R.id.courier_individual_docs_sts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivSts.setBackground(!isSts ? checked : unchecked);
            }
        });

        findViewById(R.id.add_car_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                ArrayList<String> strings = new ArrayList<>();
                strings.add(mMake);
                strings.add(mModel);
                strings.add(mNumber);
                if (isRight)    strings.add("Права");
                if (isRight)    strings.add("СТС авто");
                intent.putStringArrayListExtra("RESULT_CAR", strings);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ICConst.REQUEST_MAKE) {
            mId = data.getStringExtra("id");
            mMake = data.getStringExtra("make");
            make.setText(mMake);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == ICConst.REQUEST_MODEL) {
            mId = data.getStringExtra("id");
            mModel = data.getStringExtra("model");
            model.setText(mModel);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
