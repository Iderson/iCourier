package com.iv.icourier.helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by iv on 09.03.16.
 */
public class ICConst {
    //requests
    public  final   static  int REQUEST_RECHARGE        =   12345;
    public  final   static  int REQUEST_PHONE           =   12346;
    public  final   static  int REQUEST_COURIER         =   12347;
    public  final   static  int REQUEST_ORDER           =   12348;
    public  final   static  int REQUEST_EXECUTOR        =   12349;
    public  final   static  int REQUEST_CAR             =   12350;
    public  final   static  int REQUEST_DETAIL_ORDER    =   12351;
    public  final   static  int REQUEST_EDIT_PROFILE    =   12352;
    public  final   static  int REQUEST_CAMERA          =   12353;
    public  final   static  int REQUEST_GALLERY         =   12354;
    public  final   static  int REQUEST_SIZE            =   12355;
    public  final   static  int REQUEST_MAKE            =   12356;
    public  final   static  int REQUEST_MODEL           =   12457;
    public  final   static  int REQUEST_AUTHORIZE       =   12458;
    public  final   static  int REQUEST_PRICE           =   12459;
    //formats
    public  final   static  SimpleDateFormat    formatForServer     =   new SimpleDateFormat("yyyy-MM-dd",    Locale.getDefault());

    public  final   static  SimpleDateFormat    formatForDob        =   new SimpleDateFormat("dd MMMM yyyy",  Locale.getDefault());
    //fields
    public  final   static  String  IS_COURIER      =   "is_courier";
    public  final   static  String  NEED_AUTH       =   "need_auth";
    public  final   static  String  RESPONSE        =   "response";
    public  final   static  String  STATUS          =   "status";
    public  final   static  String  PHONE           =   "phone";
    public  final   static  String  CODE            =   "code";
    public  final   static  String  ID              =   "id";
    public  final   static  String  MAIL            =   "mail";
    public  final   static  String  FIRST_NAME      =   "first_name";
    public  final   static  String  MIDDLE_NAME     =   "middle_name";
    public  final   static  String  LAST_NAME       =   "last_name";
    public  final   static  String  PHOTO_URL       =   "photo_url";
    public  final   static  String  BIRTHDAY        =   "birthday";
    public  final   static  String  PHONES          =   "phones";
    public  final   static  String  COMPANY_NAME    =   "company_name";
    public  final   static  String  USER_ROLE       =   "user_role";
    public  final   static  String  USER_TYPE       =   "user_type";
    public  final   static  String  DOES_HAVE_CAR   =   "does_have_car";
    public  final   static  String  CARS            =   "cars";
    public  final   static  String  COMPANY         =   "company";
    public  final   static  String  TOKEN           =   "token";
    public  final   static  String  USER            =   "user";

    public  final   static  String  FILE            =   "file";
    public  final   static  String  ORDER           =   "orders";
    public  final   static  String  ORDER_TYPE      =   "order_type";
    public  final   static  String  PAYMENT         =   "payment_type";
    public  final   static  String  ORDER_NAME      =   "name";
    public  final   static  String  ORDER_DESCRIPTION=   "description";
    public  final   static  String  FROM            =   "from";
    public  final   static  String  CITY_FROM       =   "city";
    public  final   static  String  ADDRESS_FROM    =   "address";
    public  final   static  String  FROM_PERIOD     =   "fromPeriod";
    public  final   static  String  DATE_FROM       =   "date";
    public  final   static  String  TIME_FROM_START =   "from";
    public  final   static  String  TIME_FROM_TILL  =   "to";
    public  final   static  String  TO              =   "to";
    public  final   static  String  CITY_TO         =   "city";
    public  final   static  String  ADDRESS_TO      =   "address";
    public  final   static  String  TO_PERIOD       =   "toPeriod";
    public  final   static  String  DATE_TO         =   "date";
    public  final   static  String  TIME_TO_START   =   "from";
    public  final   static  String  TIME_TO_TILL    =   "to";
    public  final   static  String  SENDER          =   "sender";
    public  final   static  String  SENDER_NAME     =   "name";
    public  final   static  String  SENDER_PHONE    =   "phone";
    public  final   static  String  RECIPIENT       =   "recipient";
    public  final   static  String  RECIPIENT_NAME  =   "name";
    public  final   static  String  RECIPIENT_PHONE =   "phone";
    public  final   static  String  RECEIVED_BY     =   "receivedBy";
    public  final   static  String  RECEIVED_NAME   =   "name";
    public  final   static  String RECEIVED_COMMENT =   "comment";
    public  final   static  String  CARGO           =   "cargo";
    public  final   static  String  CARGO_NAME      =   "name";
    public  final   static  String  CARGO_DESC      =   "description";
    public  final   static  String  CARGO_SIZE      =   "size";
    public  final   static  String  CARGO_HEIGHT    =   "height";
    public  final   static  String  CARGO_WIDTH     =   "width";
    public  final   static  String  CARGO_LENGTH    =   "length";
    public  final   static  String  LOADERS_COUNT   =   "loaders_count";
    public  final   static  String  PACKAGING       =   "does_need_packaging";

    public static final String LENGTH = "length";
    public static String[] TIME = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00",
            "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
    public static String FURNITURE_TAG = "furniture_tag";
    public static String SIZE_TAG = "size_tag";
    public static String LIST = "list";
    public static String LOCATION = "location";
    public static String LOCATION_NAME = "name";
    public static String LOCATION_COUNTRY = "country";
    public static String LOCATION_CITY = "city";
    public static String LOCATION_ADDRESS = "address";
    public static String LOCATION_METRO = "metro";
    public static String LOCATION_LATITUDE = "latitude";
    public static String LOCATION_LONGITUDE = "longitude";
    public static String RATING = "rating";
    public static String HAS_CAR = "does have car";
    public static String CAR = "cars";
    public static String CAR_ID = "id";
    public static String CAR_MAKE_ID = "make_id";
    public static String CAR_MAKE = "make";
    public static String CAR_MODEL_ID = "model_id";
    public static String CAR_MODEL = "model";
    public static String CAR_NAME = "name";
    public static String CAR_DOCUMENTS = "documents";
    public static String CAR_NUMBER = "plate_number";
}
