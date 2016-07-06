package com.iv.icourier.fragments.registerCourierFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.helpers.ICHelper;

/**
 * Created by iv on 18.03.16.
 */
public class CourierPhoneConfirmFragment extends Fragment {

    private EditText mCode;
    private TextView mText;
    private ProgressBar mProgress;
    private FrameLayout mPhoneConfirm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_phone_confirm, container, false);
//        getActivity().setTitle((getString(R.string.phone_confirm)));

        initViews(root);
        setListeners();

        return root;
    }
    private void initViews(View root) {
        mCode = (EditText) root.findViewById(R.id.phone_confirm_code);
        mText = (TextView) root.findViewById(R.id.tvCode);
        mPhoneConfirm = (FrameLayout) root.findViewById(R.id.phone_confirm_action);

        mProgress = (ProgressBar) root.findViewById(R.id.pbConfirm);
    }
    private void setListeners() {
        ICHelper.showKeyboard(getActivity(), mCode);
        mCode.addTextChangedListener(ICHelper.codeWatcher(mPhoneConfirm));

        mPhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressVisible(true);
                getActivity().finish();
            }
        });
    }
    private void setProgressVisible(boolean _visible) {
        if(_visible){
            mText.setVisibility(View.GONE);
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mText.setVisibility(View.VISIBLE);
            mProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        setProgressVisible(false);
        super.onStart();
    }
}
