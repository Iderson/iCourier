package com.iv.icourier.fragments.rechargeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.iv.icourier.R;
import com.iv.icourier.activities.RechargeActivity;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 10.03.16.
 */
public class PromoFragment extends Fragment {
    private EditText    promo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recharge_promo, container, false);
        RechargeActivity.title.setText(getString(R.string.promo));

        promo = (EditText)  root.findViewById(R.id.promo_edit);
        ICHelper.showKeyboard(getActivity(), promo);

        return root;
    }
}
