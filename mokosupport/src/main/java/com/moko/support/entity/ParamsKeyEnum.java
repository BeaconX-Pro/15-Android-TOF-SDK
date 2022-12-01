package com.moko.support.entity;


import java.io.Serializable;

public enum ParamsKeyEnum implements Serializable {
    KEY_GET_SAMPLE_RATE(0x73),
    KEY_SET_SAMPLE_RATE(0x72),
    KEY_SET_ACC_ENABLE(0x6C),
    KEY_SET_TIME(0x74),
    ;

    private int paramsKey;

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
