package com.iv.icourier.fragments.blankFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.PriceActivity;
import com.iv.icourier.fragments.SizeFragment;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 09.03.16.
 */
public class BlankDescriptionFurnitureFragment extends Fragment {
    private static EditText sEtWhatEdit;
    private static EditText sEtCommentEdit;
    private TextView mTvLoadersCount;
    private SwitchCompat mSwWrap;
    private TextView mTvSize;
    private EditText mEtPrice;
    private View mRoot;

    public static boolean isFilled() {
        return sEtWhatEdit.getText().length() > 0
                && sEtCommentEdit.getText().length() > 0;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_blank_description_furniture, container, false);

        initViews();
        setListeners();

        return mRoot;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initViews() {
        sEtWhatEdit =   Find.e(mRoot, R.id.blank_description_furniture_what_edit);
        sEtCommentEdit =   Find.e(mRoot, R.id.blank_description_furniture_comment_edit);
        mEtPrice =   Find.e(mRoot, R.id.description_furniture_price_number);
        mTvSize =   Find.t(mRoot, R.id.blank_description_furniture_text_size);
        mTvLoadersCount =   Find.t(mRoot, R.id.description_furniture_loaders_count);
        mSwWrap =   Find.s(mRoot, R.id.description_furniture_toggle);
        Find.price(mRoot, R.id.description_furniture_price_number);
        Find.price(mRoot, R.id.description_furniture_price_text);
    }
/*
    @Override
    public void onResume() {
        if(length != null)
            mTvSize.setText(String.format("%s × %s × %s", length, "0,5", "2"));
        super.onResume();
    }*/

    private void setListeners() {
        mRoot.findViewById(R.id.blank_description_furniture_ripple_what).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(getContext(), sEtWhatEdit, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder .setOrderName(_args.getString(""));
                    }
                },null);
            }
        });
        mRoot.findViewById(R.id.blank_description_furniture_ripple_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.showDialogWithInput(getContext(), sEtCommentEdit, new ICHelper.ExecutorValue() {
                    @Override
                    public void sucExec(Bundle _args) {
                        ICApplication.currentOrder .setOrderDescription(_args.getString(""));
                    }
                },null);
            }
        });
        mRoot.findViewById(R.id.description_furniture_loaders_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = Integer.valueOf(mTvLoadersCount.getText().toString());
                if(integer > 0) {
                    ICApplication.currentOrder.setLoaderCount(integer - 1);
                    mTvLoadersCount.setText(String.valueOf(integer - 1));
                }
            }
        });
        mRoot.findViewById(R.id.description_furniture_loaders_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = Integer.valueOf(mTvLoadersCount.getText().toString());
                ICApplication.currentOrder.setLoaderCount(integer + 1);
                mTvLoadersCount.setText(String.valueOf(integer + 1));

            }
        });
        Do.click(mRoot, R.id.description_furniture_packaging, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                mSwWrap.setChecked(!mSwWrap.isChecked());
                ICApplication.currentOrder .setCargoPackaging(mSwWrap.isChecked());
            }
        });
        Do.click(mRoot, R.id.blank_description_furniture_ripple_size, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .add(R.id.blank_container, new SizeFragment(), ICConst.SIZE_TAG).commit();
            }
        });
        mRoot.findViewById(R.id.description_furniture_price).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), PriceActivity.class),
                        ICConst.REQUEST_PRICE);
            }
        });
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                String length = ICApplication.currentOrder.getCargoLength();
                String width = ICApplication.currentOrder.getCargoWidth();
                String height = ICApplication.currentOrder.getCargoHeight();
                mTvSize.setText(String.format("%s × %s × %s", length, width, height));
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == ICConst.REQUEST_PRICE) {
            String priceNumber = data.getStringExtra("PRICE");
            mEtPrice.setText(priceNumber);
            Find.price(mRoot, R.id.description_furniture_price_number);
        }
    }
}
