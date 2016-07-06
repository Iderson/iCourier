package com.iv.icourier.fragments.phoneFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.EntranceActivity;
import com.iv.icourier.activities.PhoneActivity;
import com.iv.icourier.helpers.HawkStorage;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.loopj.android.http.RequestParams;

/**
 * Created by iv on 12.03.16.
 */
public class PhoneConfirmFragment extends Fragment {
    private EditText mCode;
    private TextView mText;
    private ProgressBar mProgress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_phone_confirm, container, false);
        PhoneActivity.title.setText(getString(R.string.phone_confirm));

        mCode = (EditText) root.findViewById(R.id.phone_confirm_code);
        mText = (TextView) root.findViewById(R.id.tvCode);
        mProgress = (ProgressBar) root.findViewById(R.id.pbConfirm);

        mCode.addTextChangedListener(ICHelper.codeWatcher(root.findViewById(R.id.phone_confirm_action)));
        ICHelper.showKeyboard(getActivity(), mCode);
        root.findViewById(R.id.phone_confirm_rules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        root.findViewById(R.id.phone_confirm_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ICHelper.hideKeyboard(getActivity());
                setProgressVisible(true);

                v.setEnabled(false);
                RequestParams params = new RequestParams();
                params.put(ICConst.PHONE, PhoneFragment.phoneText);
                params.put(ICConst.CODE, mCode.getText().toString());
                ICApiManager.initUser(params, ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                    @Override
                    public void sucExec(Context mContext, Bundle args) {
                        Log.w("initUser", "success " + args.toString());
                        ICUser user = new ICUser();
                        user.initDataFromJSON(args);
                        ICApplication.currentProfile = user;
                        HawkStorage.storeAuthData(PhoneFragment.phoneText, mCode.getText().toString());
                        ICHelper.hideKeyboard(getActivity());

                        if (!PhoneActivity.needAuth) {
                            getActivity().setResult(Activity.RESULT_OK);
                            getActivity().finish();
                        } else {
                            /*PhoneActivity.title.setText(getString(R.string.personal_data));
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.phone_container, new QuestionnaireFragment())
                                    .addToBackStack(null)
                                    .commitAllowingStateLoss();*/
                            startActivity(new Intent(getContext(), EntranceActivity.class));
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void errExec(Context mContext, Bundle args) {
                        Log.e("initUser", "error " + args.toString());
                        setProgressVisible(false);
                        ICHelper.showDialog(mContext, getString(R.string.error_fields), null, null);
                        v.setEnabled(true);
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
