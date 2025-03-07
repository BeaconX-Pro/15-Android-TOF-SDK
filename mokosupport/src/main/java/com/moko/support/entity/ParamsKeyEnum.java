package com.moko.support.entity;


import java.io.Serializable;

public enum ParamsKeyEnum implements Serializable {
    KEY_GET_SAMPLE_RATE(0x73),
    KEY_SET_SAMPLE_RATE(0x72),
    KEY_SET_ACC_ENABLE(0x6C),
    KEY_SET_TIME(0x74),
    KEY_GET_SAMPLE_NUMBER(0x79),
    KEY_SET_SAMPLE_NUMBER(0x78),
    KEY_GET_SINGLE_SAMPLE_TIME(0x7B),
    KEY_SET_SINGLE_SAMPLE_TIME(0x7A),
    KEY_READ_TOF_DISTANCE_MODE(0x7D),
    KEY_SET_TOF_DISTANCE_MODE(0x7C),
    KEY_GET_LIMIT_DISTANCE(0x89),
    KEY_SET_LIMIT_DISTANCE(0x88),
    KEY_GET_POWER_OFF_ENABLE(0x71),
    KEY_SET_POWER_OFF_ENABLE(0x70),
    KEY_SET_DEFAULT(0x6E),
    ;

    private final int paramsKey;

    ParamsKeyEnum(int paramsKey) {
        this.paramsKey = paramsKey;
    }


    public int getParamsKey() {
        return paramsKey;
    }

    public static ParamsKeyEnum fromParamKey(int paramsKey) {
        for (ParamsKeyEnum paramsKeyEnum : ParamsKeyEnum.values()) {
            if (paramsKeyEnum.getParamsKey() == paramsKey) {
                return paramsKeyEnum;
            }
        }
        return null;
    }
}
