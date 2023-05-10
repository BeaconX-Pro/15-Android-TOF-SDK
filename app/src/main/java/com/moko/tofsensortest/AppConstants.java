package com.moko.tofsensortest;

public class AppConstants {
    // data time pattern
    public static final String PATTERN_HH_MM = "HH:mm";
    public static final String PATTERN_HH_MM_SS = "HH:mm:ss";
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_MM_DD = "MM/dd";
    public static final String PATTERN_MM_DD_2 = "MM-dd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    // sp
    public static final String SP_NAME = "sp_name_erp_test";

    public static final String SP_KEY_USER_ID = "SP_KEY_USER_ID";
    public static final String SP_KEY_USER_FULL_NAME = "SP_KEY_USER_FULL_NAME";
    public static final String SP_KEY_USER_USER_NAME = "SP_KEY_USER_USER_NAME";
    public static final String SP_KEY_USER_DEPART_NAME = "SP_KEY_USER_DEPART_NAME";
    public static final String SP_KEY_USER_PASSWORD = "SP_KEY_USER_PASSWORD";
    public static final String SP_KEY_USER_INFO = "SP_KEY_USER_INFO";
    public static final String SP_KEY_AUTO_LOGIN = "SP_KEY_AUTO_LOGIN";

    public static final String SP_KEY_DEVICE_ADDRESS = "sp_key_device_address";
    public static final String SP_KEY_CLOSE_COUNT_BXP = "SP_KEY_CLOSE_COUNT_BXP";
    public static final String SP_KEY_CLOSE_COUNT_IBEACON = "SP_KEY_CLOSE_COUNT_IBEACON";
    public static final String SP_KEY_CLOSE_COUNT_BEACONX = "SP_KEY_CLOSE_COUNT_BEACONX";
    public static final String SP_KEY_PRINTER_MAC = "SP_KEY_PRINTER_MAC";
    public static final String SP_KEY_PRINTER_DENSITY = "SP_KEY_PRINTER_DENSITY";
    public static final String SP_KEY_SENSOR_DISTANCE = "SP_KEY_SENSOR_DISTANCE";
    public static final String SP_KEY_SENSOR_OFFSET = "SP_KEY_SENSOR_OFFSET";
    public static final String SP_KEY_ORIGIN_X = "SP_KEY_ORIGIN_X";
    public static final String SP_KEY_ORIGIN_Y = "SP_KEY_ORIGIN_Y";
    public static final String SP_KEY_CLOSE_PASSWORD_BXP = "SP_KEY_CLOSE_PASSWORD_BXP";
    public static final String SP_KEY_CLOSE_PASSWORD_IBEACON = "SP_KEY_CLOSE_PASSWORD_IBEACON";
    public static final String SP_KEY_CLOSE_PASSWORD_BEACONX = "SP_KEY_CLOSE_PASSWORD_BEACONX";
    public static final String SP_KEY_PRINT_SWITCH_BXP = "sp_key_print_switch_bxp";
    // extra_key
    // 设备列表
    public static final String EXTRA_KEY_RESPONSE_ORDER_TYPE = "EXTRA_KEY_RESPONSE_ORDER_TYPE";
    public static final String EXTRA_KEY_SLOT_DATA = "EXTRA_KEY_SLOT_DATA";
    public static final String EXTRA_KEY_PASSWORD = "EXTRA_KEY_PASSWORD";
    public static final String EXTRA_KEY_IS_OPEN_CLOSE = "EXTRA_KEY_IS_OPEN_CLOSE";
    public static final String EXTRA_KEY_IS_OPEN_RFID = "EXTRA_KEY_IS_OPEN_RFID";
    public static final String EXTRA_KEY_IS_PRINT_MAJOR_MINOR = "EXTRA_KEY_IS_PRINT_MAJOR_MINOR";
    public static final String EXTRA_KEY_DEVICE_TYPE = "EXTRA_KEY_DEVICE_TYPE";
    public static final String EXTRA_KEY_TRIGGER_TYPE = "EXTRA_KEY_TRIGGER_TYPE";
    public static final String EXTRA_KEY_TRIGGER_DATA = "EXTRA_KEY_TRIGGER_DATA";
    public static final String EXTRA_KEY_TEMPLATE_INFO = "EXTRA_KEY_TEMPLATE_INFO";
    public static final String EXTRA_KEY_ROLE_TEMPLATE_INFO = "EXTRA_KEY_ROLE_TEMPLATE_INFO";
    public static final String EXTRA_KEY_PI_INFO = "EXTRA_KEY_PI_INFO";
    public static final String EXTRA_KEY_BOX_INFO = "EXTRA_KEY_BOX_INFO";
    public static final String EXTRA_KEY_LATEST_PI_VALIDATE_DATA = "EXTRA_KEY_LATEST_PI_VALIDATE_DATA";
    public static final String EXTRA_KEY_RSSI = "EXTRA_RSSI";

    public static final String EXTRA_KEY_DEVICE_INFO = "EXTRA_KEY_DEVICE_INFO";
    public static final String EXTRA_KEY_DEVICE_PASSWORD = "EXTRA_KEY_DEVICE_PASSWORD";
    public static final String EXTRA_KEY_DEVICE_THREE_AXIS = "EXTRA_KEY_DEVICE_THREE_AXIS";
    public static final String EXTRA_KEY_DEVICE_THREE_AXIS_MIN = "EXTRA_KEY_DEVICE_THREE_AXIS_MIN";
    public static final String EXTRA_KEY_DEVICE_THREE_AXIS_MAX = "EXTRA_KEY_DEVICE_THREE_AXIS_MAX";
    public static final String EXTRA_KEY_DEVICE_MAC = "EXTRA_KEY_DEVICE_MAC";
    public static final String EXTRA_KEY_DEVICE_UUID = "EXTRA_KEY_DEVICE_UUID";
    public static final String EXTRA_KEY_DEVICE_MAJOR = "EXTRA_KEY_DEVICE_MAJOR";
    public static final String EXTRA_KEY_DEVICE_MINOR = "EXTRA_KEY_DEVICE_MINOR";
    public static final String EXTRA_KEY_DEVICE_MEASURE_POWER = "EXTRA_KEY_DEVICE_MEASURE_POWER";
    public static final String EXTRA_KEY_DEVICE_TRANSMISSION = "EXTRA_KEY_DEVICE_TRANSMISSION";
    public static final String EXTRA_KEY_DEVICE_BROADCASTINTERVAL = "EXTRA_KEY_DEVICE_BROADCASTINTERVAL";
    public static final String EXTRA_KEY_DEVICE_DEVICE_ID = "EXTRA_KEY_DEVICE_DEVICE_ID";
    public static final String EXTRA_KEY_DEVICE_IBEACON_NAME = "EXTRA_KEY_DEVICE_IBEACON_NAME";
    public static final String EXTRA_KEY_DEVICE_IBEACON_THREE_AXIS = "EXTRA_KEY_DEVICE_IBEACON_THREE_AXIS";
    public static final String EXTRA_KEY_DEVICE_IBEACON_THREE_AXIS_MR = "EXTRA_KEY_DEVICE_IBEACON_THREE_AXIS_MR";
    public static final String EXTRA_KEY_DEVICE_CONNECTION_MODE = "EXTRA_KEY_DEVICE_CONNECTION_MODE";

    // request_code
    public static final int REQUEST_CODE_SLOT_DATA = 100;
    public static final int REQUEST_CODE_DEVICE_INFO = 101;
    public static final int REQUEST_CODE_ORDER_INFO = 102;
    public static final int REQUEST_CODE_SCAN_CONNECT = 103;
    public static final int REQUEST_CODE_GET_PI_BOX_LABEL_INFO = 104;
    public static final int REQUEST_CODE_BOX_INFO = 105;
    public static final int REQUEST_CODE_GET_PRODUCT_MAC = 106;
    public static final int REQUEST_CODE_QR_SN = 107;
    public static final int REQUEST_CODE_QR_NAME = 108;
    public static final int REQUEST_CODE_QR_MAC = 109;
    public static final int REQUEST_CODE_QR_ID = 110;
    public static final int REQUEST_CODE_GET_PRODUCT_ID = 111;
    public static final int REQUEST_CODE_GET_BOX_NO = 112;
    public static final int REQUEST_CODE_QR_DEVICE_NAME = 113;
    public static final int REQUEST_CODE_QR_URL = 114;
    public static final int REQUEST_CODE_QR_NAMESPACE = 115;
    public static final int REQUEST_CODE_QR_INSTANCE = 116;
    public static final int REQUEST_CODE_QR_UUID = 117;
    public static final int REQUEST_CODE_QR_MAJOR = 118;
    public static final int REQUEST_CODE_QR_MINOR = 119;
    // iBeacon
    public static final int REQUEST_CODE_SET_UUID = 102;
    public static final int REQUEST_CODE_SET_MAJOR = 103;
    public static final int REQUEST_CODE_SET_MINOR = 104;
    public static final int REQUEST_CODE_SET_MEASURE_POWER = 105;
    public static final int REQUEST_CODE_SET_TRANSMISSION = 106;
    public static final int REQUEST_CODE_SET_BROADCASTINTERVAL = 107;
    public static final int REQUEST_CODE_SET_DEVICE_ID = 108;
    public static final int REQUEST_CODE_SET_IBEACON_NAME = 109;
    public static final int REQUEST_CODE_SET_CONNECTION_MODE = 110;
    public static final int REQUEST_CODE_SET_PASSWORD = 111;
    public static final int REQUEST_CODE_SET_SYSTEM_INFO = 112;
    public static final int REQUEST_CODE_SET_THREE_AXIS = 113;


    public static final int REQUEST_CODE_PERMISSION = 120;
    public static final int REQUEST_CODE_PERMISSION_2 = 121;
    public static final int REQUEST_CODE_LOCATION_SETTINGS = 122;
    public static final int PERMISSION_REQUEST_CODE = 1;

    // result_code
    public static final int RESULT_CONN_DISCONNECTED = 2;
}
