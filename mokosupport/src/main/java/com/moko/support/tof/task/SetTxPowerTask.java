package com.moko.support.tof.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.support.tof.entity.OrderCHAR;


public class SetTxPowerTask extends OrderTask {
    public byte[] data;

    public SetTxPowerTask() {
        super(OrderCHAR.CHAR_TX_POWER, OrderTask.RESPONSE_TYPE_WRITE);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

    public void setData(int txPower) {
        data = new byte[]{(byte) txPower};
        response.responseValue = data;
    }
}
