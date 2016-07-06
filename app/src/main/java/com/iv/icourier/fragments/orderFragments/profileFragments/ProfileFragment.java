package com.iv.icourier.fragments.orderFragments.profileFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.QuestionnaireActivity;
import com.iv.icourier.adapters.PhoneAdapter;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by iv on 08.03.16.
 */
public class ProfileFragment extends Fragment {
    public  static boolean      needAuth = true;

    private EditText        email;
    private CircleImageView photo;
    private TextView        name;
    private TextView        description;

    private ICUser          user;
    private RecyclerView    mRcPhoneView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = ICApplication.currentProfile;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order_profile, container, false);
        needAuth = user == null;

        initViews(root);
        setListeners(root);

        if (!needAuth) updateValues();

        return root;
    }
    private void initViews(View root) {
        photo           =   Find.c(root, R.id.order_profile_photo);
        email           =   Find.e(root, R.id.order_profile_email);
        name            =   Find.t(root, R.id.order_profile_name);
        description     =   Find.t(root, R.id.order_profile_description);
        mRcPhoneView    =   Find.recycler(root, R.id.phone_recycler);
    }
    private void updateValues() {
        String nameText = user.getMiddle_name() + " " + user.getFirst_name()  + " " + user.getLast_name();
        name.setText(nameText);
        if (user.getBirthday() != null) {
            String descriptionText = getString(R.string.client) + " " + ICHelper.calculateAge(user.getBirthday()) + " " + getString(R.string.ages);
            description.setText(descriptionText);
        }
        if (user.getPhoto_url() != null) {
            Picasso.with(getContext()).load(user.getPhoto_url()).fit().centerCrop().into(photo);
        }
        if (user.getMail() != null){
            email.setText(user.getMail());
        }
        initRecycler();
    }

    private void initRecycler() {
        ArrayList<String> phoneList = new ArrayList<>();
        phoneList.add(user.getMainPhone());
        phoneList.addAll(user.getPhones());
        PhoneAdapter phoneAdapter = new PhoneAdapter(phoneList, getContext(), false);
        mRcPhoneView.setAdapter(phoneAdapter);
    }

    private void setListeners(final View root) {
        root.findViewById(R.id.phone_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        Do.click(root, R.id.order_profile_edit, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), QuestionnaireActivity.class), ICConst.REQUEST_EDIT_PROFILE);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ICConst.REQUEST_EDIT_PROFILE)
            if (resultCode == Activity.RESULT_OK) {
             user = ICApplication.currentProfile;
                updateValues();
            }
    }
}
