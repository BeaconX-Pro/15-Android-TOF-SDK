package com.moko.support.tof.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.support.tof.entity.OrderCHAR;


public class GetAdvIntervalTask extends OrderTask {
    public byte[] data;

    public GetAdvIntervalTask() {
        super(OrderCHAR.CHAR_ADV_INTERVAL, OrderTask.RESPONSE_TYPE_READ);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

}
