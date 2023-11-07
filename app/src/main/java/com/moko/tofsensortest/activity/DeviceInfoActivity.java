package com.moko.tofsensortest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.MokoSupport;
import com.moko.support.OrderTaskAssembler;
import com.moko.support.entity.OrderCHAR;
import com.moko.support.entity.ParamsKeyEnum;
import com.moko.support.entity.TxPowerEnum;
import com.moko.tofsensortest.databinding.ActivityDeviceInfoBinding;
import com.moko.tofsensortest.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;


public class DeviceInfoActivity extends BaseActivity {
    private ActivityDeviceInfoBinding mBind;

    private boolean savedParamsError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityDeviceInfoBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());

        EventBus.getDefault().register(this);
        showLoadingProgressDialog();
        ArrayList<OrderTask> orderTasks = new ArrayList<>();
        orderTasks.add(OrderTaskAssembler.setTime());
        orderTasks.add(OrderTaskAssembler.getTxPower());
        orderTasks.add(OrderTaskAssembler.getAdvInterval());
        orderTasks.add(OrderTaskAssembler.getSampleRate());
        orderTasks.add(OrderTaskAssembler.getSampleNumber());
        orderTasks.add(OrderTaskAssembler.getSingleSampleTime());
        orderTasks.add(OrderTaskAssembler.getTofMode());
        orderTasks.add(OrderTaskAssembler.setAccEnable(1));
        MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[]{}));
        mBind.btnRange.setOnClickListener(v -> startActivity(new Intent(this, RangeDataActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        String action = event.getAction();
        if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
            dismissLoadingProgressDialog();
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderTaskResponseEvent(OrderTaskResponseEvent event) {
        final String action = event.getAction();
        if (MokoConstants.ACTION_CURRENT_DATA.equals(action)) {
            OrderTaskResponse response = event.getResponse();
            OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
            int responseType = response.responseType;
            byte[] value = response.responseValue;
            switch (orderCHAR) {
                case CHAR_PARAMS:
                    int length = value[3] & 0xFF;
                    if (length != 6)
                        return;
                    short x = MokoUtils.byte2short(Arrays.copyOfRange(value, 4, 6));
                    short x_short = (short) (x >> 4);
                    short y = MokoUtils.byte2short(Arrays.copyOfRange(value, 6, 8));
                    short y_short = (short) (y >> 4);
                    short z = MokoUtils.byte2short(Arrays.copyOfRange(value, 8, 10));
                    short z_short = (short) (z >> 4);
                    mBind.tvAcc.setText(String.format("X:%dmg Y:%dmg Z:%dmg", x_short, y_short, z_short));
                    break;
            }
        }
        if (MokoConstants.ACTION_ORDER_TIMEOUT.equals(action)) {
        }
        if (MokoConstants.ACTION_ORDER_FINISH.equals(action)) {
            dismissLoadingProgressDialog();
        }
        if (MokoConstants.ACTION_ORDER_RESULT.equals(action)) {
            OrderTaskResponse response = event.getResponse();
            OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
            int responseType = response.responseType;
            byte[] value = response.responseValue;
            switch (orderCHAR) {
                case CHAR_TX_POWER:
                    if (responseType == OrderTask.RESPONSE_TYPE_READ) {
                        TxPowerEnum txPowerEnum = TxPowerEnum.fromOrdinal(value[0]);
                        assert txPowerEnum != null;
                        mBind.etTxPower.setText(String.valueOf(txPowerEnum.getTxPower()));
                    }
                    break;
                case CHAR_ADV_INTERVAL:
                    if (responseType == OrderTask.RESPONSE_TYPE_READ)
                        mBind.etAdvInterval.setText(String.valueOf(MokoUtils.toInt(value)));
                    break;
                case CHAR_PARAMS:
                    int head = value[0] & 0xFF;
                    int cmd = value[1] & 0xFF;
                    int length = value[3] & 0xFF;
                    if (head != 0xEB)
                        return;
                    ParamsKeyEnum paramsKeyEnum = ParamsKeyEnum.fromParamKey(cmd);
                    if (paramsKeyEnum == null)
                        return;
                    switch (paramsKeyEnum) {
                        case KEY_GET_SAMPLE_RATE:
                            byte[] rawBytes = Arrays.copyOfRange(value, 4, 4 + length);
                            mBind.etSampleRate.setText(String.valueOf(MokoUtils.toInt(rawBytes)));
                            break;
                        case KEY_GET_SAMPLE_NUMBER:
                            int number = value[4] & 0xFF;
                            mBind.etSampleNumber.setText(String.valueOf(number));
                            break;
                        case KEY_GET_SINGLE_SAMPLE_TIME:
                            int time = value[4] & 0xFF;
                            mBind.etSingleSampleTime.setText(String.valueOf(time));
                            break;
                        case KEY_SET_SAMPLE_NUMBER:
                        case KEY_SET_SINGLE_SAMPLE_TIME:
                        case KEY_SET_SAMPLE_RATE:
                            if (value[4] == 0) {
                                savedParamsError = true;
                            }
                            break;
                        case KEY_SET_TOF_DISTANCE_MODE:
                            if (value[4] == 0) {
                                savedParamsError = true;
                            }
                            if (savedParamsError) {
                                ToastUtils.showToast(this, "Save Failed！");
                            } else {
                                ToastUtils.showToast(this, "Save Success！");
                            }
                            break;

                        case KEY_READ_TOF_DISTANCE_MODE:
                            int mode = value[4] & 0xff;
                            //短距模式
                            mBind.btnShort.setChecked(mode == 1);
                            mBind.btnLong.setChecked(mode == 2);
                            break;
                    }
                    break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (isWindowLocked()) return;
        MokoSupport.getInstance().disConnectBle();
    }

    public void onBack(View view) {
        back();
    }

    public void onSave(View view) {
        if (isWindowLocked()) return;
        String txPowerStr = mBind.etTxPower.getText().toString();
        String advIntervalStr = mBind.etAdvInterval.getText().toString();
        String sampleRateStr = mBind.etSampleRate.getText().toString();
        String sampleNumberStr = mBind.etSampleNumber.getText().toString();
        String singleSampleTimeStr = mBind.etSingleSampleTime.getText().toString();
        if (TextUtils.isEmpty(txPowerStr)
                || TextUtils.isEmpty(advIntervalStr)
                || TextUtils.isEmpty(sampleNumberStr)
                || TextUtils.isEmpty(singleSampleTimeStr)
                || TextUtils.isEmpty(sampleRateStr)) {
            ToastUtils.showToast(this, "The param can not be empty");
            return;
        }
        int txPower = Integer.parseInt(txPowerStr);
        TxPowerEnum txPowerEnum = TxPowerEnum.fromTxPower(txPower);
        if (txPowerEnum == null) {
            ToastUtils.showToast(this, "Tx Power Error");
            return;
        }
        int advInterval = Integer.parseInt(advIntervalStr);
        if (advInterval < 1 || advInterval > 86400) {
            ToastUtils.showToast(this, "Adv Interval Error");
            return;
        }
        int sampleRate = Integer.parseInt(sampleRateStr);
        if (sampleRate < 1 || sampleRate > 86400) {
            ToastUtils.showToast(this, "ToF interval Error");
            return;
        }
        int sampleNumber = Integer.parseInt(sampleNumberStr);
        if (sampleNumber < 2 || sampleNumber > 255) {
            ToastUtils.showToast(this, "Period Sampling Times Error");
            return;
        }
        int singleSampleTime = Integer.parseInt(singleSampleTimeStr);
        if (singleSampleTime < 8 || singleSampleTime > 140) {
            ToastUtils.showToast(this, "Period Sampling duration Error");
            return;
        }
        showLoadingProgressDialog();
        ArrayList<OrderTask> orderTasks = new ArrayList<>();
        orderTasks.add(OrderTaskAssembler.setTxPower(txPowerEnum.ordinal()));
        orderTasks.add(OrderTaskAssembler.setAdvInterval(advInterval));
        orderTasks.add(OrderTaskAssembler.setSampleNumber(sampleNumber));
        orderTasks.add(OrderTaskAssembler.setSingleSampleTime(singleSampleTime));
        orderTasks.add(OrderTaskAssembler.setSampleRate(sampleRate));
        orderTasks.add(OrderTaskAssembler.setTofMode(mBind.btnShort.isChecked() ? 1 : 2));
        MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[]{}));
    }
}
