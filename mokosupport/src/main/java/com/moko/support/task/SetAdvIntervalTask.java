package com.moko.support.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.entity.OrderCHAR;


public class SetAdvIntervalTask extends OrderTask {
    public byte[] data;

    public SetAdvIntervalTask() {
        super(OrderCHAR.CHAR_ADV_INTERVAL, OrderTask.RESPONSE_TYPE_WRITE);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

    public void setData(int advInterval) {
//        if (advInterval > 65535)
        data = MokoUtils.toByteArray(advInterval, 3);
//        else if (advInterval > 255)
//            data = MokoUtils.toByteArray(advInterval, 2);
//        else
//            data = MokoUtils.toByteArray(advInterval, 1);
        response.responseValue = data;
    }
}
