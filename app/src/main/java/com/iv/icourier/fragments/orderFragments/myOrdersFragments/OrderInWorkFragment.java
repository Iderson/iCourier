package com.iv.icourier.fragments.orderFragments.myOrdersFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;

/**
 * Created by iv on 08.03.16.
 */
public class OrderInWorkFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_in_work, container, false);
        if (ICApplication.currentProfile.getUser_role() == 1) {
            root.findViewById(R.id.order_in_work_arrow).setVisibility(View.GONE);
            root.findViewById(R.id.order_first).setVisibility(View.GONE);
        }
        return root;
    }
}
