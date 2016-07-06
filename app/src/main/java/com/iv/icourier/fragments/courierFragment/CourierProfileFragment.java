package com.iv.icourier.fragments.courierFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fivehundredpx.android.blur.BlurringView;
import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.BalanceActivity;
import com.iv.icourier.activities.EditProfileActivity;
import com.iv.icourier.activities.EntranceActivity;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by iv on 20.03.16.
 */
public class CourierProfileFragment extends Fragment {
    private BlurringView    blurring;
    private CircleImageView photo;
    private ImageView       photoBack;
    private ICUser user;
    private EditText email;
    private TextView name;
    private RecyclerView mRcPhoneView;
    private EditText profileName;
    private TextView age;
    private TextView car;
    private EditText phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courier_profile, container, false);

        user = ICApplication.currentProfile;
        initViews(root);
        setListeners(root);
        setValues();

        return root;
    }
    private void initViews(View root) {
        blurring    =   (BlurringView)      root.findViewById(R.id.courier_profile_blur);
        photo       =   (CircleImageView)   root.findViewById(R.id.courier_profile_image);
        photoBack   =   (ImageView)         root.findViewById(R.id.courier_profile_photo_back);

        email       =   Find.e(root, R.id.courier_profile_email_edit);
//        profileName =   Find.e(root, R.id.courier_profile_email_edit);
        phone       =   Find.e(root, R.id.courier_profile_phone_edit);
        name        =   Find.t(root, R.id.courier_profile_name);
        age         =   Find.t(root, R.id.courier_profile_age);
        car         =   Find.t(root, R.id.courier_profile_auto_edit);
        Find.price(root, R.id.balance_edit);
        blurring.setBlurredView(photoBack);
    }
    private void setListeners(View root) {
        root.findViewById(R.id.courier_profile_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });
        root.findViewById(R.id.courier_profile_balance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BalanceActivity.class));
            }
        });
        root.findViewById(R.id.courier_profile_ripple_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EntranceActivity.class));
                getActivity().finish();
            }
        });
    }
    private void setValues() {
        String nameText = user.getMiddle_name() + " " + user.getFirst_name()  + " " + user.getLast_name();
        name.setText(nameText);
        if (user.getBirthday() != null) {
            String descriptionText = getString(R.string.client) + " " + ICHelper.calculateAge(user.getBirthday()) + " " + getString(R.string.ages);
            age.setText(descriptionText);
        }
        if (user.getPhoto_url() != null) {
            photoBack.setVisibility(View.GONE);
            Picasso.with(getContext()).load(user.getPhoto_url()).fit().centerCrop().into(photo);
            blurring.setBlurredView(photo);
        } else
            blurring.setBlurredView(photoBack);
        if (user.getMail() != null){
            email.setText(user.getMail());
        }
        if (user.getMainPhone() != null){
            phone.setText(user.getMail());
        }
        if (user.getCars() != null){
            car.setText(user.getCars().get(0).getName());
        }

//        ICHelper.hideKeyboard(getActivity());
    }
}
