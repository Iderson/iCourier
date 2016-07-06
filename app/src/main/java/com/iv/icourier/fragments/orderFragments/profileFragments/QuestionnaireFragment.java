package com.iv.icourier.fragments.orderFragments.profileFragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.iv.icourier.ICApplication;
import com.iv.icourier.R;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by iv on 12.03.16.
 */
public class QuestionnaireFragment extends Fragment {
    private CircleImageView     mUserPhoto;
    private ProgressDialog      mProgressDialog;
    private FrameLayout         mFlPhoto;
    private FrameLayout         mFlReadyButton;
    private EditText            mLastName;
    private EditText            mName;
    private EditText            mMiddleName;
    private EditText            mDoB;
    private EditText            mEmail;
    private ArrayList<String>   mPhoneList = new ArrayList<>();
    private PhoneAdapter        mPhoneAdapter;
    private TextView            mTextAuth;
    private ProgressBar         mProgressAuth;
    private View                mRoot;
    private ICUser mUser;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        mUser = ICApplication.currentProfile;

        initViews();
        setListeners();
        getUserInfo();

        return mRoot;
    }
    private void initViews() {
        mProgressDialog =   new ProgressDialog(getContext(), 0);
        mProgressDialog     .setMessage(getString(R.string.download));
        mProgressDialog     .setCancelable(false);
        mProgressDialog     .show();

        mProgressAuth   =   (ProgressBar) mRoot.findViewById(R.id.pbAuth_action);
        mTextAuth       =   Find.t(mRoot, R.id.tvAuth);
        mFlPhoto        =   (FrameLayout)       mRoot.findViewById(R.id.questionnaire_photo);
        mFlReadyButton  =   (FrameLayout)       mRoot.findViewById(R.id.questionnaire_action);

        mUserPhoto      =   Find.c(mRoot, R.id.questionnaire_user_photo);
        mLastName       =   Find.e(mRoot, R.id.questionnaire_last_name);
        mName           =   Find.e(mRoot, R.id.questionnaire_name);
        mMiddleName     =   Find.e(mRoot, R.id.questionnaire_middle_name);
        mDoB            =   Find.e(mRoot, R.id.questionnaire_dob);
        mEmail          =   Find.e(mRoot, R.id.questionnaire_email);
    }
    private void setListeners() {
        Do.click(mRoot, R.id.questionnaire_ripple_last_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mLastName, null, null);
            }
        });
        Do.click(mRoot, R.id.questionnaire_ripple_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mName, null, null);
            }
        });
        Do.click(mRoot, R.id.questionnaire_ripple_full_name, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mMiddleName, null, null);
            }
        });
        Do.click(mRoot, R.id.questionnaire_ripple_dob, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithCalendar((AppCompatActivity) getContext(), mDoB, null);
            }
        });
        Do.click(mRoot, R.id.questionnaire_ripple_email, new Do.onCLick() {
            @Override
            public void onClick(View view) {
                ICHelper.showDialogWithInput(getContext(), mEmail, null, null);
            }
        });
        mFlPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ICHelper.changeAvatar(getActivity(), QuestionnaireFragment.this);
            }
        });
        mFlReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoot.findViewById(R.id.tvAuth).setVisibility(View.GONE);
                mRoot.findViewById(R.id.pbAuth_action).setVisibility(View.VISIBLE);

                ICApiManager.updateUser(getParams(), ICHandlers.defaultHandler(getActivity(), new ICHandlers.Executor() {
                    @Override
                    public void sucExec(Context mContext, Bundle args) {
                        Log.w("updateUser", "success " + args.toString());
                        mUser.initDataFromJSON(args);
                        ICApplication.currentProfile = mUser;

                        getActivity().setResult(Activity.RESULT_OK);
                        getActivity().finish();
                    }

                    @Override
                    public void errExec(Context mContext, Bundle args) {
                        Log.e("updateUser", "error " + args.toString());
                        ICHelper.showDialog(mContext, getString(R.string.error), getString(R.string.attention), new ICHelper.Executor() {
                            @Override
                            public void exec() {
                                getActivity().finish();
                            }
                        });
                    }
                }));
            }
        });
    }
    private void getUserInfo() {
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
                mProgressDialog.dismiss();
            }
        }));
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
        Picasso.with(getActivity()).load(mUser.getPhoto_url()).fit().centerCrop().into(mUserPhoto);
        mLastName.setText(mUser.getMiddle_name());
        mName.setText(mUser.getFirst_name());
        mMiddleName.setText(mUser.getLast_name());
        if (mUser.getBirthday() != null)
            mDoB.setText(ICHelper.dobFromServerFormat(mUser.getBirthday()));
        mEmail.setText(mUser.getMail());
        mProgressDialog.dismiss();
        mPhoneList.add(0, mUser.getMainPhone());
        mPhoneList.addAll(1, mUser.getPhones());

        initAdapter();
//        ICHelper.hideKeyboard(getActivity());
    }
    private RequestParams getParams() {
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(false);

        params.put(ICConst.FIRST_NAME, mName.getText().toString());
        params.put(ICConst.MIDDLE_NAME, mLastName.getText().toString());
        params.put(ICConst.LAST_NAME, mMiddleName.getText().toString());
        params.put(ICConst.BIRTHDAY, mDoB.getContentDescription());
        params.put(ICConst.PHONES, getPhones());
        params.put(ICConst.MAIL, mEmail.getText().toString());

        return params;
    }
    private JSONArray getPhones() {
        JSONArray array = new JSONArray();
        for (int i = 1; i < mPhoneAdapter.mPhoneList.size()-1; i++)
            array.put(mPhoneAdapter.mPhoneList.get(i).getText());
        return array;
    }
    private File getFileFromBitmap(Bitmap bitmap) throws IOException {
        File f = new File(getActivity().getCacheDir(), "mFlPhoto");
        f.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
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
                    uCrop.start(getActivity(), QuestionnaireFragment.this);
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
                uCrop.start(getActivity(), QuestionnaireFragment.this);
                break;
            case UCrop.REQUEST_CROP:
                Log.w("QuestionnaireFragment", "onActivityResult REQUEST_CROP");
                try {
                    Uri resultUri = UCrop.getOutput(data);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    mUserPhoto.setImageBitmap(bitmap);

                    changePhoto(bitmap);
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                } catch (IllegalArgumentException iae) {
                    iae.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onStart() {
        mTextAuth.setVisibility(View.VISIBLE);
        mProgressAuth.setVisibility(View.GONE);

        super.onStart();
    }
}
