package com.moko.support.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.entity.OrderCHAR;
import com.moko.support.entity.ParamsKeyEnum;

import java.util.Calendar;


public class ParamsTask extends OrderTask {
    public byte[] data;

    public ParamsTask() {
        super(OrderCHAR.CHAR_PARAMS, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

    public void getData(ParamsKeyEnum key) {
        switch (key) {
            case KEY_GET_SAMPLE_RATE:
            case KEY_GET_SAMPLE_NUMBER:
            case KEY_GET_SINGLE_SAMPLE_TIME:
            case KEY_READ_TOF_DISTANCE_MODE:
                createGetParamsData(key.getParamsKey());
                break;
        }
    }

    private void createGetParamsData(int paramsKey) {
        data = new byte[]{(byte) 0xEA, (byte) paramsKey, (byte) 0x00, (byte) 0x00};
        response.responseValue = data;
    }

    public void setSampleRate(int rate) {
        byte[] rateBytes = MokoUtils.toByteArray(rate, 3);
        data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_SAMPLE_RATE.getParamsKey(),
                (byte) 0x00,
                (byte) 0x03,
                rateBytes[0],
                rateBytes[1],
                rateBytes[2],
        };
        response.responseValue = data;
    }

    public void setAccEnable(int enable) {
        data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_ACC_ENABLE.getParamsKey(),
                (byte) 0x00,
                (byte) 0x01,
                (byte) enable,
        };
        response.responseValue = data;
    }

    public void setTime() {
        long time = Calendar.getInstance().getTimeInMillis();
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; ++i) {
            bytes[i] = (byte) (time >> 8 * (7 - i) & 255);
        }
        data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_TIME.getParamsKey(),
                (byte) 0x00,
                (byte) 0x08,
                bytes[0],
                bytes[1],
                bytes[2],
                bytes[3],
                bytes[4],
                bytes[5],
                bytes[6],
                bytes[7],
        };
        response.responseValue = data;
    }

    public void setSampleNumber(int number) {
        data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_SAMPLE_NUMBER.getParamsKey(),
                (byte) 0x00,
                (byte) 0x01,
                (byte) number
        };
        response.responseValue = data;
    }

    public void setSingleSampleTime(int time) {
        data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_SINGLE_SAMPLE_TIME.getParamsKey(),
                (byte) 0x00,
                (byte) 0x01,
                (byte) time
        };
        response.responseValue = data;
    }

    public void setTofMode(int mode) {
        response.responseValue = data = new byte[]{
                (byte) 0xEA,
                (byte) ParamsKeyEnum.KEY_SET_TOF_DISTANCE_MODE.getParamsKey(),
                (byte) 0x00,
                (byte) 0x01,
                (byte) mode
        };
    }
}
