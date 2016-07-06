package com.iv.icourier.fragments.blankFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.PriceActivity;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 09.03.16.
 */
public class BlankDescriptionCourierFragment extends Fragment {
    private static EditText sEtWhatEdit;
    private static EditText sEtCommentEdit;
    private EditText mEtPrice;
    private View        mRoot;

    public static boolean isFilled() {
        return sEtWhatEdit.getText().length() > 0
                && sEtCommentEdit.getText().length() > 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_blank_description_courier, container, false);

        initViews();
        setListener();

        return mRoot;
    }
    private void initViews() {
        sEtWhatEdit =   Find.e(mRoot, R.id.blank_description_courier_what_edit);
        sEtCommentEdit =   Find.e(mRoot, R.id.blank_description_courier_comment_edit);
        mEtPrice =   Find.e(mRoot, R.id.description_courier_price_edit);
        Find.price(mRoot, R.id.description_courier_price_edit);
        Find.price(mRoot, R.id.description_courier_price_text);
    }
    private void setListener() {
        mRoot.findViewById(R.id.blank_description_courier_ripple_what).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(getContext(), sEtWhatEdit, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setCargoName(_args.getString(""));
                    }
                }, null);
            }
        });
        mRoot.findViewById(R.id.blank_description_courier_ripple_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(getContext(), sEtCommentEdit, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder.setCargoDesc(_args.getString(""));
                    }
                }, null);
            }
        });
        mRoot.findViewById(R.id.description_courier_price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), PriceActivity.class),
                        ICConst.REQUEST_PRICE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ICConst.REQUEST_PRICE) {
            String priceNumber = data.getStringExtra("PRICE");
            mEtPrice.setText(priceNumber);
            Find.price(mRoot, R.id.description_courier_price_edit);
        }
    }
}
