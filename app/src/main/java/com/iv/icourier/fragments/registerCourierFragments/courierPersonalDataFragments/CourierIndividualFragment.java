package com.iv.icourier.fragments.registerCourierFragments.courierPersonalDataFragments;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
import com.iv.icourier.activities.AddCarActivity;
import com.iv.icourier.activities.EntranceActivity;
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
public class CourierIndividualFragment extends Fragment {
    private CircleImageView     mCivUserPhoto;
    private FrameLayout         mFlPhoto;
    private SwitchCompat        mScShipping;
    private EditText            mEtLastName;
    private EditText            mEtFirstName;
    private EditText            mEtMiddleName;
    private EditText            mEtDoB;
    private EditText            mEtEmail;
    private TextView            mTvLoaderCount;
    private EditText            mEtCar;

    private ICUser              mUser = new ICUser();
    private ProgressDialog      mDialog;
    private ArrayList<String>   mPhoneList = new ArrayList<>();
    private PhoneAdapter        mPhoneAdapter;
    private View                mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_courier_individual, container, false);

        initViews(mRoot);
        setListeners(mRoot);
        getUserInfo();

        return mRoot;
    }

    private void initViews(View root) {
        mFlPhoto =   (FrameLayout)   root.findViewById(R.id.courier_individual_photo);

        mCivUserPhoto =   Find.c(root, R.id.courier_individual_user_photo);
        mScShipping =   Find.s(root, R.id.courier_individual_shipping_switch);
        mEtLastName =   Find.e(root, R.id.courier_individual_last_name);
        mEtFirstName =   Find.e(root, R.id.courier_individual_name);
        mEtMiddleName =   Find.e(root, R.id.courier_individual_full_name);
        mEtDoB =   Find.e(root, R.id.courier_individual_dob);
        mEtEmail =   Find.e(root, R.id.courier_individual_email);
        mEtCar =   Find.e(root, R.id.courier_individual_car_name);
        mTvLoaderCount =   Find.t(root, R.id.courier_individual_loaders_count);
    }

    private void setListeners(final View root) {
        if (mUser != null && mUser.getPhones() != null){
//            mEtPhone.setText(mUser.getPhone());
        }
        Do.click(root, R.id.courier_individual_ripple_last_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mEtLastName, null, null);
            }
        });
        Do.click(root, R.id.courier_individual_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mEtFirstName, null, null);
            }
        });
        Do.click(root, R.id.courier_individual_ripple_middle_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mEtMiddleName, null, null);
            }
        });
        Do.click(root, R.id.courier_individual_ripple_dob, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getActivity(), mEtDoB, null);
            }
        });
        Do.click(root, R.id.courier_individual_ripple_email, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mEtEmail, null, null);
            }
        });
        Do.click(root, R.id.courier_individual_shipping_ripple, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                mScShipping.setChecked(!mScShipping.isChecked());
            }
        });
        root.findViewById(R.id.courier_individual_loaders_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = Integer.valueOf(mTvLoaderCount.getText().toString());
                mTvLoaderCount.setText(String.valueOf(integer + 1));
//                ICApplication.currentOrder.setLoaderCount(integer + 1);
            }
        });
        root.findViewById(R.id.courier_individual_loaders_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = Integer.valueOf(mTvLoaderCount.getText().toString());
                if(integer > 0) {
                    mTvLoaderCount.setText(String.valueOf(integer - 1));
//                    ICApplication.currentOrder.setLoaderCount(integer - 1);
                }
            }
        });
        Find.scroll(root, R.id.courier_individual_scroll).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EventBus.getDefault().post(AttachUtil.isScrollViewAttach((ScrollView) v));
                return false;
            }
        });
        mFlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.changeAvatar(getActivity(), CourierIndividualFragment.this);
            }
        });
        mScShipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Do.vis(Find.v(root, R.id.courier_individual_text), Find.v(root, R.id.courier_individual_cars_linear));
            }
        });
        Do.click(root, R.id.courier_individual_action, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                updateValues();
                startActivity(new Intent(getContext(), MainCourierActivity.class));
            }
        });
        Do.click(root, R.id.courier_individual_add_car, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), AddCarActivity.class), ICConst.REQUEST_CAR);
            }
        });
    }
    private void getUserInfo() {
        if(mUser == null) {
            startActivity(new Intent(getContext(), EntranceActivity.class));
        } else {
            mUser = ICApplication.currentProfile;
            mDialog = new ProgressDialog(getContext(), 0);
            mDialog.setMessage(getString(R.string.download));
            mDialog.setCancelable(false);
            mDialog.show();

            ICApiManager.getUser(new RequestParams(), ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                @Override
                public void sucExec(Context mContext, Bundle args) {
                    Log.w("getUserInfo", "success " + args.toString());
                    if (mDialog != null) mDialog.dismiss();
                    mUser.initDataFromJSON(args);
                    setValues();
                }

                @Override
                public void errExec(Context mContext, Bundle args) {
                    Log.e("getUserInfo", "error " + args.toString());
                    if (mDialog != null) mDialog.dismiss();
                }
            }));
        }
    }
    private void setValues() {
        Picasso.with(getActivity()).load(mUser.getPhoto_url()).fit().centerCrop().into(mCivUserPhoto);
        mEtLastName.setText(mUser.getLast_name());
        mEtFirstName.setText(mUser.getFirst_name());
        mEtMiddleName.setText(mUser.getMiddle_name());
        if (mUser.getBirthday() != null)
            mEtDoB.setText(ICHelper.dobFromServerFormat(mUser.getBirthday()));
        mEtEmail.setText(mUser.getMail());
        mPhoneList.add(mUser.getMainPhone());
        mPhoneList.addAll(mUser.getPhones());

        initAdapter();
//        ICHelper.hideKeyboard(getActivity());
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
    private void updateValues() {
        mRoot.findViewById(R.id.tvAuth).setVisibility(View.GONE);
        mRoot.findViewById(R.id.pbAuth_action).setVisibility(View.VISIBLE);

        ICApiManager.updateUser(getParams(), ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
            @Override
            public void sucExec(Context mContext, Bundle args) {
                Log.w("updateUser", "success " + args.toString());
                mUser.setUser_role(1);
//                mDialog.dismiss();
                mUser.initDataFromJSON(args);
                ICApplication.currentProfile = mUser;
                startActivity(new Intent(getContext(), MainCourierActivity.class));
                getActivity().finish();
            }

            @Override
            public void errExec(Context mContext, Bundle args) {
                Log.e("updateUser", "error " + args.toString());
//                mDialog.dismiss();
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

        params.put(ICConst.FIRST_NAME, mEtFirstName.getText().toString());
        params.put(ICConst.MIDDLE_NAME, mEtMiddleName.getText().toString());
        params.put(ICConst.LAST_NAME, mEtLastName.getText().toString());
        params.put(ICConst.BIRTHDAY, mEtDoB.getContentDescription());
        params.put(ICConst.PHONES, getPhones());
        params.put(ICConst.MAIL, mEtEmail.getText().toString());
        params.put(ICConst.USER_ROLE, mUser.getUser_role());

        return params;
    }
    private JSONArray getPhones() {
        JSONArray array = new JSONArray();
        for (int i = 1; i < mPhoneAdapter.mPhoneList.size()-1; i++)
            array.put(mPhoneAdapter.mPhoneList.get(i).getText());
        return array;
    }

    private void addPhone(final View _root) {
        LayoutInflater factory = LayoutInflater.from(getContext());
        final MaterialRippleLayout rippleLayout = (MaterialRippleLayout)factory.inflate(R.layout.item_phone, null);
        LinearLayout container = (LinearLayout) _root.findViewById(R.id.phone_container);
        container.addView(rippleLayout);
        rippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhone(_root);
            }
        });
    }

    private File getFileFromBitmap(Bitmap bitmap) throws IOException {
        File f = new File(getActivity().getCacheDir(), "mFlPhoto");
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
                    uCrop.start(getActivity(), CourierIndividualFragment.this);
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
                uCrop.start(getActivity(), CourierIndividualFragment.this);
                break;
            case UCrop.REQUEST_CROP:
                Log.w("QuestionnaireFragment", "onActivityResult REQUEST_CROP");
                try {
                    Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    mCivUserPhoto.setImageBitmap(bitmap);

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
                    mEtCar.setText(stringBuffer.toString());
                }
                break;
        }
    }
}
