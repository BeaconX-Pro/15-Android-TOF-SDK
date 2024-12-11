package com.moko.support;


import com.moko.ble.lib.task.OrderTask;
import com.moko.support.entity.ParamsKeyEnum;
import com.moko.support.task.GetAdvIntervalTask;
import com.moko.support.task.GetTxPowerTask;
import com.moko.support.task.ParamsTask;
import com.moko.support.task.PasswordTask;
import com.moko.support.task.SetAdvIntervalTask;
import com.moko.support.task.SetTxPowerTask;

import org.intellij.lang.annotations.JdkConstants;

public class OrderTaskAssembler {

    public static OrderTask setPassword(String password) {
        PasswordTask task = new PasswordTask();
        task.setData(password);
        return task;
    }


    public static OrderTask getSampleRate() {
        ParamsTask task = new ParamsTask();
        task.getData(ParamsKeyEnum.KEY_GET_SAMPLE_RATE);
        return task;
    }

    public static OrderTask setSampleRate(int rate) {
        ParamsTask task = new ParamsTask();
        task.setSampleRate(rate);
        return task;
    }

    public static OrderTask getTxPower() {
        GetTxPowerTask task = new GetTxPowerTask();
        return task;
    }

    public static OrderTask setTxPower(int txPower) {
        SetTxPowerTask task = new SetTxPowerTask();
        task.setData(txPower);
        return task;
    }

    public static OrderTask getAdvInterval() {
        GetAdvIntervalTask task = new GetAdvIntervalTask();
        return task;
    }

    public static OrderTask setAdvInterval(int interval) {
        SetAdvIntervalTask task = new SetAdvIntervalTask();
        task.setData(interval);
        return task;
    }


    public static OrderTask setAccEnable(int enable) {
        ParamsTask task = new ParamsTask();
        task.setAccEnable(enable);
        return task;
    }

    public static OrderTask setTime() {
        ParamsTask task = new ParamsTask();
        task.setTime();
        return task;
    }

    public static OrderTask getSampleNumber() {
        ParamsTask task = new ParamsTask();
        task.getData(ParamsKeyEnum.KEY_GET_SAMPLE_NUMBER);
        return task;
    }

    public static OrderTask setSingleSampleTime(int time) {
        ParamsTask task = new ParamsTask();
        task.setSingleSampleTime(time);
        return task;
    }
    public static OrderTask getSingleSampleTime() {
        ParamsTask task = new ParamsTask();
        task.getData(ParamsKeyEnum.KEY_GET_SINGLE_SAMPLE_TIME);
        return task;
    }

    public static OrderTask setSampleNumber(int number) {
        ParamsTask task = new ParamsTask();
        task.setSampleNumber(number);
        return task;
    }

    public static OrderTask getTofMode() {
        ParamsTask task = new ParamsTask();
        task.getData(ParamsKeyEnum.KEY_READ_TOF_DISTANCE_MODE);
        return task;
    }

    public static OrderTask setTofMode(int mode) {
        ParamsTask task = new ParamsTask();
        task.setTofMode(mode);
        return task;
    }

    public static OrderTask getLimitDistance(){
        ParamsTask task = new ParamsTask();
        task.getData(ParamsKeyEnum.KEY_GET_LIMIT_DISTANCE);
        return task;
    }

    public static OrderTask setLimitDistance(int distance) {
        ParamsTask task = new ParamsTask();
        task.setLimitDistance(distance);
        return task;
    }
}

