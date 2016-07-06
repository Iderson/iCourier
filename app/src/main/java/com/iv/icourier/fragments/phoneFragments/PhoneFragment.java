package com.iv.icourier.fragments.phoneFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iv.icourier.R;
import com.iv.icourier.activities.PhoneActivity;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.loopj.android.http.RequestParams;

/**
 * Created by iv on 12.03.16.
 */
public class PhoneFragment extends Fragment {
    private         EditText    mPhone;
    private         FrameLayout mPhoneAction;
    private         TextView    mText;
    private         ProgressBar mProgress;
    public  static  String      phoneText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_phone, container, false);
        PhoneActivity.title.setText(getString(R.string.phone_num));

        mText = (TextView) root.findViewById(R.id.tvCode);
        mProgress = (ProgressBar) root.findViewById(R.id.pbConfirm);
        mPhone = (EditText) root.findViewById(R.id.phone_number);
        mPhoneAction = (FrameLayout) root.findViewById(R.id.phone_action);

        TextWatcher textWatcher = ICHelper.phoneWatcher(mPhone, mPhoneAction);
        mPhone.addTextChangedListener(textWatcher);
        mPhone.setText("");
        ICHelper.showKeyboard(getActivity(), mPhone);

        root.findViewById(R.id.phone_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressVisible(true);
                mPhone.setEnabled(false);
                RequestParams params = new RequestParams();
                phoneText = ICHelper.getPhoneText(mPhone);
                params.put(ICConst.PHONE, phoneText);
                ICApiManager.getSmsCode(params, ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                    @Override
                    public void sucExec(Context mContext, Bundle args) {
                        Log.w("getSmsCode","success " + args.toString());
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.phone_container, new PhoneConfirmFragment())
                                .addToBackStack(null)
                                .commitAllowingStateLoss();
                    }
                    @Override
                    public void errExec(Context mContext, Bundle args) {
                        Log.e("getSmsCode","error " + args.toString());
                        mPhone.setEnabled(true);
                        setProgressVisible(false);
                        ICHelper.showDialog(mContext, getString(R.string.error), null, null);
                    }
                }));
            }
        });
        return root;
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
