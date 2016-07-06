package com.iv.icourier.fragments.registerCourierFragments.courierPersonalDataFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.AddCarActivity;
import com.iv.icourier.activities.MainCourierActivity;
import com.iv.icourier.adapters.PhoneAdapter;
import com.iv.icourier.helpers.Do;
import com.iv.icourier.helpers.Find;
import com.iv.icourier.helpers.ICConst;
import com.iv.icourier.helpers.ICHelper;
import com.iv.icourier.models.ICUser;
import com.iv.icourier.network.ICApiManager;
import com.iv.icourier.network.ICHandlers;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import github.chenupt.dragtoplayout.AttachUtil;

/**
 * Created by iv on 15.03.16.
 */
public class CourierEntityFragment extends Fragment {
    private CircleImageView userPhoto;
    private FrameLayout     photo;
    private SwitchCompat shippingSwitch;
    private EditText        companyName;
    private EditText        inn;
    private EditText        kpp;
    private EditText        ogrn;
    private EditText        lastName;
    private EditText        name;
    private EditText        middleName;
    private EditText        dob;
    private EditText        email;
    private EditText        model;

    private ICUser mUser;
    private ArrayList<String>   mPhoneList;
    private PhoneAdapter        mPhoneAdapter;
    private View                mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_courier_entity, container, false);

        initViews();
        setListeners();
        getUserInfo();

        return mRoot;
    }
    private void initViews() {
        photo           =   (FrameLayout)   mRoot.findViewById(R.id.courier_entity_photo);

        userPhoto       =   Find.c(mRoot, R.id.courier_entity_user_photo);
        shippingSwitch  =   Find.s(mRoot, R.id.courier_entity_shipping_switch);
        companyName     =   Find.e(mRoot, R.id.courier_entity_company_name);
        inn             =   Find.e(mRoot, R.id.courier_entity_inn);
        kpp             =   Find.e(mRoot, R.id.courier_entity_kpp);
        ogrn            =   Find.e(mRoot, R.id.courier_entity_ogrn);
        lastName        =   Find.e(mRoot, R.id.courier_entity_last_name);
        name            =   Find.e(mRoot, R.id.courier_entity_name);
        middleName      =   Find.e(mRoot, R.id.courier_entity_middle_name);
        dob             =   Find.e(mRoot, R.id.courier_entity_dob);
        email           =   Find.e(mRoot, R.id.courier_entity_email);
        model           =   Find.e(mRoot, R.id.courier_entity_car_name);
    }
    private void setListeners() {
        Do.click(mRoot, R.id.courier_entity_shipping_ripple, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                shippingSwitch.setChecked(!shippingSwitch.isChecked());
            }
        });
        Find.scroll(mRoot, R.id.courier_entity_scroll).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EventBus.getDefault().post(AttachUtil.isScrollViewAttach((ScrollView) v));
                return false;
            }
        });
        shippingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Do.vis(Find.v(mRoot, R.id.courier_entity_text), Find.v(mRoot, R.id.courier_entity_cars_linear));
            }
        });
        Do.click(mRoot, R.id.courier_entity_action, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                    updateValues(mRoot);
                    startActivity(new Intent(getContext(), MainCourierActivity.class));
            }
        });
        Do.click(mRoot, R.id.courier_entity_add_car, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddCarActivity.class), ICConst.REQUEST_CAR);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_company_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), companyName, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_inn, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), inn, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_kpp, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), kpp, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_ogrn, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), ogrn, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_last_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), lastName, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), name, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_middle_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), middleName, null, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_dob, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getActivity(), dob, null);
            }
        });
        Do.click(mRoot, R.id.courier_entity_ripple_email, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getActivity(), email, null, null);
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.changeAvatar(getActivity(), CourierEntityFragment.this);
            }
        });
    }
    private void getUserInfo() {
        mUser = ICApplication.currentProfile;
        if (mUser != null) {
            ICApiManager.getUser(new RequestParams(), ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                @Override
                public void sucExec(Context mContext, Bundle args) {
                    Log.w("getUserInfo", "success " + args.toString());
                    mUser.initDataFromJSON(args);
                    setValues();
                }
                @Override
                public void errExec(Context mContext, Bundle args) {
                    Log.e("getUserInfo", "error " + args.toString());
                }
            }));
        }
    }
    private void initAdapter() {
        if(mPhoneList.size() < 2) mPhoneList.add("");
        mPhoneList.add("");
        mPhoneAdapter = new PhoneAdapter(mPhoneList, getContext(), true);
        initRecycler();
    }
    private void initRecycler() {
        RecyclerView rcPhoneView = (RecyclerView) mRoot.findViewById(R.id.phone_recycler);
        RecyclerViewDragDropManager dragMgr = new RecyclerViewDragDropManager();

        dragMgr.setInitiateOnMove(false);
        dragMgr.setInitiateOnLongPress(true);

        rcPhoneView.setItemAnimator(new DefaultItemAnimator());
        rcPhoneView.setLayoutManager(new LinearLayoutManager(getContext()));
        rcPhoneView.setHasFixedSize(false);
        rcPhoneView.setAdapter(dragMgr.createWrappedAdapter(mPhoneAdapter));
        dragMgr.attachRecyclerView(rcPhoneView);
    }
    private void setValues() {
        Picasso.with(getContext()).load(mUser.getPhoto_url()).fit().centerCrop().into(userPhoto);
        lastName.setText(mUser.getLast_name());
        name.setText(mUser.getFirst_name());
        middleName.setText(mUser.getMiddle_name());
        if (mUser.getBirthday() != null)
            dob.setText(ICHelper.dobFromServerFormat(mUser.getBirthday()));
        mPhoneList = mUser.getPhones();
        email.setText(mUser.getMail());
        initAdapter();
//        ICHelper.hideKeyboard(getActivity());
    }
    private void updateValues(View root) {
        mUser.setUser_role(1);
        root.findViewById(R.id.tvAuth).setVisibility(View.GONE);
        root.findViewById(R.id.pbAuth_action).setVisibility(View.VISIBLE);

        ICApiManager.updateUser(getParams(), ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
            @Override
            public void sucExec(Context mContext, Bundle args) {
                Log.w("updateUser", "success " + args.toString());
//                dialog.dismiss();
                mUser.initDataFromJSON(args);
                ICApplication.currentProfile = mUser;
                startActivity(new Intent(getContext(), MainCourierActivity.class));
                getActivity().finish();
            }

            @Override
            public void errExec(Context mContext, Bundle args) {
                Log.e("updateUser", "error " + args.toString());
//                dialog.dismiss();
                ICHelper.showDialog(mContext, getString(R.string.error), getString(R.string.attention), new ICHelper.Executor() {
                    @Override
                    public void exec() {
                        getActivity().finish();
                    }
                });
            }
        }));
    }

    private RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(false);

        params.put(ICConst.FIRST_NAME, name.getText().toString());
        params.put(ICConst.MIDDLE_NAME, middleName.getText().toString());
        params.put(ICConst.LAST_NAME, lastName.getText().toString());
        params.put(ICConst.BIRTHDAY, dob.getContentDescription());
        params.put(ICConst.PHONES, getPhones());
        params.put(ICConst.MAIL, email.getText().toString());
        params.put(ICConst.USER_ROLE, mUser.getUser_role());

        return params;
    }

    private JSONArray getPhones() {
        JSONArray array = new JSONArray();
        for (int i = 1; i < mPhoneAdapter.mPhoneList.size()-1; i++)
            array.put(mPhoneAdapter.mPhoneList.get(i).getText());
        return array;
    }

    private File getFileFromBitmap(Bitmap bitmap) throws IOException {
        File f = new File(getActivity().getCacheDir(), "photo");
        f.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.withAspectRatio(1, 1);
        return uCrop;
    }
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);

        options.setCompressionQuality(100);

        options.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.dark_blue));
        options.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.dark_blue));
        options.setActiveWidgetColor(ContextCompat.getColor(getActivity(), R.color.dark_blue));
        options.setToolbarTitleTextColor(ContextCompat.getColor(getActivity(), R.color.white));

        return uCrop.withOptions(options);
    }
    private void changePhoto(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = cz.msebera.android.httpclient.extras.Base64.encodeToString(byteArray, cz.msebera.android.httpclient.extras.Base64.DEFAULT);

        RequestParams params = new RequestParams();
        params.put(ICConst.FILE, encoded);
        ICApiManager.userPic(getActivity(), params, ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
            @Override
            public void sucExec(Context mContext, Bundle args) {
                Log.w("userPic", "success " + args.toString());
            }
            @Override
            public void errExec(Context mContext, Bundle args) {
                Log.e("userPic", "error " + args.toString());
            }
        }));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ICConst.REQUEST_CAMERA:
                Log.w("QuestionnaireFragment", "onActivityResult REQUEST_CAMERA");
                try {
                    Bundle extras = data.getExtras();

                    Bitmap mPhoto = (Bitmap) extras.get("data");
                    UCrop uCrop = UCrop.of(Uri.fromFile(getFileFromBitmap(mPhoto)),
                            Uri.fromFile(new File(getActivity().getCacheDir(), "image.jpeg")));
                    uCrop = basisConfig(uCrop);
                    uCrop = advancedConfig(uCrop);
                    uCrop.start(getActivity(), CourierEntityFragment.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case ICConst.REQUEST_GALLERY:
                Log.w("QuestionnaireFragment", "onActivityResult REQUEST_GALLERY");
                Uri selectedPhoto = data.getData();
                UCrop uCrop = UCrop.of(selectedPhoto, Uri.fromFile(new File(getActivity().getCacheDir(), "image.jpeg")));
                uCrop = basisConfig(uCrop);
                uCrop = advancedConfig(uCrop);
                uCrop.start(getActivity(), CourierEntityFragment.this);
                break;
            case UCrop.REQUEST_CROP:
                Log.w("QuestionnaireFragment", "onActivityResult REQUEST_CROP");
                try {
                    Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    userPhoto.setImageBitmap(bitmap);

                    changePhoto(bitmap);
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                } catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case ICConst.REQUEST_CAR:
                if (resultCode == Activity.RESULT_OK){
                    ArrayList<String> result_car = data.getStringArrayListExtra("RESULT_CAR");
                    StringBuilder stringBuffer = new StringBuilder();
                    stringBuffer.append(result_car.get(1)).append(" ").append(result_car.get(1));
                    model.setText(stringBuffer.toString());
                }
                break;
        }
    }
}
