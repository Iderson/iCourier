package com.iv.icourier.helpers;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.iv.icourier.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by iv on 09.03.16.
 */
public class ICHelper {
    private static LatLng mLatLng;
    public static LatLng mMyLocation = new LatLng(55.7494733,37.3523199);

    public static void showSettingsAlert(Context _context, String _title, String _text, final ExecutorSettings _settings) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                _context);

        alertDialog.setTitle(_title);
        alertDialog.setMessage(_text);

        alertDialog.setPositiveButton("НАТРОЙКИ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _settings.positiveExec();
                    }
                });

        alertDialog.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        _settings.negativeExec();
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    public interface Executor {
        void exec();
    }

    public interface ExecutorValue {
        void sucExec(Bundle _args);
    }
    public interface ExecutorSettings {
        void positiveExec();
        void negativeExec();
    }

    public static void changeHeightWithAnim(Activity activity, final RelativeLayout relativeLayout, int finiteHeight) {
        if (finiteHeight == 0) {
            Rect rectangle= new Rect();
            Window window= activity.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            int statusBarHeight= rectangle.top;
            int contentViewTop=
                    window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight= contentViewTop - statusBarHeight;

            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            finiteHeight = size.y + titleBarHeight;
        }

        ValueAnimator va = ValueAnimator.ofInt(relativeLayout.getLayoutParams().height, finiteHeight);
        va.setDuration(100);
        va.setInterpolator(new FastOutSlowInInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                relativeLayout.getLayoutParams().height = value.intValue();
                relativeLayout.requestLayout();
            }
        });
        va.start();
    }
    public static void hideKeyboard(final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                View view = activity.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }, 200);
    }
    public static void showKeyboard(final Activity activity, final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

            }
        }, 200);
    }
    public static TextWatcher codeWatcher(final View _submit) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() < 4) _submit
                        .setVisibility(View.GONE);
                else if (s.toString().length() == 4) _submit
                        .setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
    public static TextWatcher phoneWatcher(final EditText _phone, final View _submit) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _phone.setGravity(Gravity.CENTER);
            }
            @Override
            public void onTextChanged(CharSequence text, int position, int before, int arg3) {
                try {
                    if(_phone.getText().length() == 18 && _submit != null)
                        _submit.setVisibility(View.VISIBLE);
                    if(_phone.getText().length() < 18 && _submit != null)
                        _submit.setVisibility(View.GONE);
                    if (text.length() < 2) {
                        _phone.setText("+7");
                        _phone.setSelection(2);
                    } else if (text.length() > 18 && arg3 == 1) {
                        _phone.setText(_phone.getText().toString()
                                .substring(0, 18));
                        _phone.setSelection(_phone.getText().length());
                    } else {
                        String mask = "+7 (###) ###-##-##";
                        for (int i = 0; i < _phone.getText().toString().length(); i++) {
                            String maskChar = mask.substring(i, i + 1);
                            String Char = _phone.getText().toString()
                                    .substring(i, i + 1);
                            String nextChar = "";
                            try {
                                nextChar = _phone.getText().toString()
                                        .substring(i + 1, i + 2);
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }

                            if (!maskChar.equals("#")) {
                                if (!Char.equals(maskChar)) {
                                    _phone.setText(_phone.getText().toString()
                                            .substring(0, i)
                                            + maskChar
                                            + _phone.getText().toString()
                                            .substring(i));
                                    position = _phone.getText().toString().length();
                                    _phone.setSelection(position);
                                    break;
                                }
                            } else {  if (Char.equals("-") || Char.equals("(")
                                        || Char.equals(" ")
                                        || (Char.equals("7") && nextChar.equals(" "))
                                        || Char.equals(")") || Char.equals("+")) {
                                    _phone.setText(_phone.getText().toString()
                                            .substring(0, i)
                                            + _phone.getText().toString()
                                            .substring(i + 1));
                                    position = _phone.getText().toString().length();
                                    _phone.setSelection(position);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
    public static int calculateAge(String date) {
        try {
            Calendar dob = Calendar.getInstance();
            dob.setTime(ICConst.formatForServer.parse(date));
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
                age--;
            return age;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    public static String dobFromServerFormat(String dateString) {
        try {
            Date date = ICConst.formatForServer.parse(dateString);
            return ICConst.formatForDob.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    public static void changeAvatar(final Activity activity, final Fragment fragment) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity, R.style.dialog_theme);
        dialog.setItems(activity.getResources().getStringArray(R.array.picture_array), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    dialog.dismiss();
                    cameraAction(activity, fragment);
                } else {
                    dialog.dismiss();
                    galleryAction(activity, fragment);
                }
            }
        }).show();
    }
    private static void cameraAction(Activity activity, Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            fragment.startActivityForResult(intent, ICConst.REQUEST_CAMERA);
        }
    }
    private static void galleryAction(Activity activity, Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(activity.getPackageManager()) != null)
            fragment.startActivityForResult(intent, ICConst.REQUEST_GALLERY);
    }
    public static void showDialog(Context mContext, String text, String title, final Executor exec) {
        try {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext, R.style.dialog_theme);
            if (title != null)
                dialog.setTitle(title);
            dialog.setMessage(text)
                    .setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (exec != null)
                                exec.exec();
                        }
                    }).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void showDialogWithInput(final Context mContext, final TextView textView, final ExecutorValue exec, String hint) {
        String prevText = textView.getText().toString();
        if (hint == null) {
            hint = prevText.equals("") ? textView.getHint().toString() :
                    prevText;
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext, R.style.dialog_theme);
        final View input = LayoutInflater.from(mContext).inflate(R.layout.dialog_with_input, null);
        alert.setView(input);
        final EditText editText = (EditText) input.findViewById(R.id.dialog_edit_text);
        editText.setHint(hint);
//        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS );

        showKeyboard((Activity) mContext, editText);
        alert.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    textView.setText(text);
                } if (exec != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("", text);
                    exec.sucExec(bundle);
                }
                hideKeyboard((Activity) mContext);
            }
        });
        alert.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        hideKeyboard((Activity) mContext);
                    }
                });
        alert.show();
        editText.requestFocus();
    }
    public static void showDialogWithInputNumber(final Context mContext, final TextView textView, final ExecutorValue exec, String hint) {
        String prevText = textView.getText().toString();
        if (hint == null) {
            hint = prevText.equals("") ? textView.getHint().toString() :
                    prevText;
        }

        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext, R.style.dialog_theme);
        final View input = LayoutInflater.from(mContext).inflate(R.layout.dialog_with_input, null);
        alert.setView(input);
        final EditText editText = (EditText) input.findViewById(R.id.dialog_edit_text);
        editText.setHint(hint);
        editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setKeyListener(DigitsKeyListener.getInstance("0123456789,."));

        showKeyboard((Activity) mContext, editText);
        alert.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    textView.setText(text);
                } if (exec != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("", text);
                    exec.sucExec(bundle);
                }
                hideKeyboard((Activity) mContext);
            }
        });
        alert.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        hideKeyboard((Activity) mContext);
                    }
                });
        alert.show();
        editText.requestFocus();
    }

    public static void showDialogWithPlace(final Context mContext, final EditText editTextView, final GoogleMap _map, final ExecutorValue exec, String hint) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext, R.style.dialog_theme);
        final View input = LayoutInflater.from(mContext).inflate(R.layout.dialog_with_places, null);
        alert.setView(input);

        final AutoCompleteTextView editText = (AutoCompleteTextView)input.findViewById(R.id.dialog_places);
        editText.setAdapter(new GeoAutoCompleteAdapter(mContext));

        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        if (editTextView.getText().toString().isEmpty()) {
            editText.setHint(hint);
        } else editText.setText(editTextView.getText().toString());

        showKeyboard((Activity) mContext, editText);
        alert.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    try{
                        editTextView.setText(text);
                        _map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 15));
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                } if (exec != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("", text);
                    exec.sucExec(bundle);
                }
                hideKeyboard((Activity) mContext);
            }
        });

        alert.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        hideKeyboard((Activity) mContext);
                    }
                });
        alert.show();
        editText.requestFocus();

    }
    public static void showDialogWithInputPhone(final Context mContext, final EditText editTextView, final ExecutorValue exec,  String hint) {
        if (hint == null)
            hint = editTextView.getText().toString().equals("") ? editTextView.getHint().toString() :
                    editTextView.getText().toString();
        final AlertDialog.Builder alert = new AlertDialog.Builder(mContext, R.style.dialog_theme);
        final View input = LayoutInflater.from(mContext).inflate(R.layout.dialog_with_input_phone, null);
        alert.setView(input);
        final EditText editText = (EditText) input.findViewById(R.id.dialog_edit_text);
        editText.setGravity(Gravity.CENTER);
        editText.setHint(hint);

        editText.addTextChangedListener(phoneWatcher(editText, null));
        editText.setText("");

        showKeyboard((Activity) mContext, editText);
        alert.setPositiveButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String text = editText.getText().toString();
                if (text.length() > 0) {
                    editTextView.setText(text);
                } if (exec != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("", text);
                    exec.sucExec(bundle);
                }
                hideKeyboard((Activity) mContext);
            }
        });

        alert.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        hideKeyboard((Activity) mContext);
                    }
                });
        alert.show();
        editText.requestFocus();
    }
    public static String getPhoneText(EditText mPhone) {
        return mPhone.getText().toString().replace("+7 (", "7").replace(") ", "").replace("-", "");
    }


    public static class GeoAutoCompleteAdapter extends BaseAdapter implements Filterable {

        private static final int MAX_RESULTS = 10;
        private Context mContext;
        private List resultList = new ArrayList();

        public GeoAutoCompleteAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public GeoSearchResult getItem(int index) {
            return (GeoSearchResult) resultList.get(index);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.geo_search_result_item, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.geo_search_result_text)).setText(getItem(position).getAddress());

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        List locations = findLocations(mContext, constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = locations;
                        filterResults.count = locations.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        resultList = (List) results.values;
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }

        private List<GeoSearchResult> findLocations(Context context, String query_text) {

            List<GeoSearchResult> geo_search_results = new ArrayList<GeoSearchResult>();

            Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
            List<Address> addresses = null;

            try {
                // Getting a maximum of 15 Address that matches the input text
                addresses = geocoder.getFromLocationName(query_text, 15);

                for(int i=0;i<addresses.size();i++){
                    Address address = (Address) addresses.get(i);
                    if(address.getMaxAddressLineIndex() != -1)
                    {
                        geo_search_results.add(new GeoSearchResult(address));
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return geo_search_results;
        }
    }
    public static class GeoSearchResult {

        private Address address;

        public GeoSearchResult(Address address)
        {
            this.address = address;
        }

        public String getAddress(){

            String display_address = "";

            display_address += address.getAddressLine(0) + "\n";

            for(int i = 1; i < address.getMaxAddressLineIndex(); i++)
            {
                display_address += address.getAddressLine(i) + ", ";
            }

            display_address = display_address.substring(0, display_address.length() - 2);

            return display_address;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(i))
                        .append(i == address.getMaxAddressLineIndex() - 1 ? "" : ", ");
            }
            ICHelper.mLatLng = new LatLng(address.getLatitude(), address.getLongitude());

            return sb.toString();
        }
    }
    public static void showDialogWithClock(AppCompatActivity activity, ExecutorValue exec) {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(exec);
        timePickerFragment.show(activity.getSupportFragmentManager(), "timePicker");
    }
    public static class TimePickerFragment extends DialogFragment {
        public static ExecutorValue sExecutor;

        public static TimePickerFragment newInstance(ExecutorValue _executor) {
            TimePickerFragment dpf = new TimePickerFragment();
            sExecutor = _executor;
            return dpf;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);

            AlertDialog.Builder timeDialog = new AlertDialog.Builder(getContext());
            View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_with_time, null);

            final NumberPicker timeFrom = (NumberPicker) layout.findViewById(R.id.npTimeFrom);
            final NumberPicker timeTill = (NumberPicker) layout.findViewById(R.id.npTimeTill);
            timeTill.setMinValue(0);
            timeTill.setMaxValue(23);
            timeTill.setDisplayedValues(ICConst.TIME);
            timeFrom.setMinValue(0);
            timeFrom.setMaxValue(23);
            timeFrom.setDisplayedValues(ICConst.TIME);

            timeFrom.setValue(hourOfDay);
            timeTill.setValue(hourOfDay + 1);

            timeDialog.setView(layout)
                    .setTitle("Время")
                    .setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (timeFrom.getValue() < timeTill.getValue()) {
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add(String.valueOf(timeFrom.getValue()));
                        strings.add(String.valueOf(timeTill.getValue()));
                        Bundle args = new Bundle();
                        args.putStringArrayList("", strings);
                        if (sExecutor != null)
                            sExecutor.sucExec(args);
                        dialog.dismiss();
                    } else showDialog(getContext(), "Неправильно указано время", "Ошибка", null);
                }
            })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            return timeDialog.create();
        }

    }
    public static void showDialogWithCalendar(AppCompatActivity activity, EditText editText, ExecutorValue exec) {
        DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(editText, exec);
        datePickerFragment.show(activity.getSupportFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private static EditText textView;
        private static ExecutorValue executor;

        public static DatePickerFragment newInstance(EditText newTextView, ExecutorValue exec) {
            DatePickerFragment dpf = new DatePickerFragment();
            textView = newTextView;
            executor = exec;
            return dpf;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), 0, this, year, month, day);
        }
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            long currentTimeInMillis = c.getTimeInMillis();
            c.set(year, month, day);
            long setTimeInMillis = c.getTimeInMillis();
            /*if (setTimeInMillis < currentTimeInMillis) {
                showDialog(getContext(), "Неправильно указана дата", "Ошибка", null);
                return;
            }*/
            Date date = new Date(c.getTimeInMillis());

            String dob = ICConst.formatForServer.format(date);
            textView.setContentDescription(dob);
            textView.setText(ICConst.formatForDob.format(date));
            Bundle bundle = new Bundle();
            bundle.putString("", dob);
            if(executor != null)
                executor.sucExec(bundle);
        }
    }
}
