package com.moko.support.task;

import com.moko.ble.lib.task.OrderTask;
import com.moko.support.entity.OrderCHAR;


public class PasswordTask extends OrderTask {
    public byte[] data;

    public PasswordTask() {
        super(OrderCHAR.CHAR_PASSWORD, OrderTask.RESPONSE_TYPE_WRITE_NO_RESPONSE);
    }

    @Override
    public byte[] assemble() {
        return data;
    }

    public void setData(String password) {
       data = password.getBytes();
       response.responseValue = data;
    }
}
