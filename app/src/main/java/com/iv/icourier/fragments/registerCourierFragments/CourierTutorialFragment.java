package com.iv.icourier.fragments.registerCourierFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iv.icourier.R;
import com.iv.icourier.activities.RegisterCourierActivity;
import com.iv.icourier.fragments.registerCourierFragments.courierPersonalDataFragments.PagerPersonalDataFragment;

/**
 * Created by iv on 14.03.16.
 */
public class CourierTutorialFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courier_tutorial, container, false);
        RegisterCourierActivity.title.setText(getString(R.string.register));

        root.findViewById(R.id.courier_tutorial_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.courier_container, new PagerPersonalDataFragment())
                        .commitAllowingStateLoss();
            }
        });
        return root;
    }


}
