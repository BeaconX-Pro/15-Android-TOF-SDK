package com.moko.tofsensortest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.moko.ble.lib.MokoConstants;
import com.moko.ble.lib.event.ConnectStatusEvent;
import com.moko.ble.lib.event.OrderTaskResponseEvent;
import com.moko.ble.lib.task.OrderTask;
import com.moko.ble.lib.task.OrderTaskResponse;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.MokoBleScanner;
import com.moko.support.MokoSupport;
import com.moko.support.OrderTaskAssembler;
import com.moko.support.callback.MokoScanDeviceCallback;
import com.moko.support.entity.DeviceInfo;
import com.moko.support.entity.OrderCHAR;
import com.moko.tofsensortest.adapter.AdInfoAdapter;
import com.moko.tofsensortest.adapter.AdvInfo;
import com.moko.tofsensortest.databinding.ActivityMainBinding;
import com.moko.tofsensortest.utils.AdvInfoAnalysisImpl;
import com.moko.tofsensortest.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements MokoScanDeviceCallback {
    private List<AdvInfo> advInfoList;
    private MokoBleScanner mokoBleScanner;
    private AdInfoAdapter adapter;
    private ActivityMainBinding mBind;
    private String mDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        MokoSupport.getInstance().init(getApplicationContext());
        mokoBleScanner = new MokoBleScanner();
        advInfoList = new ArrayList<>();
        adapter = new AdInfoAdapter(advInfoList);
        mBind.rvList.setAdapter(adapter);
        mBind.btnStart.setOnClickListener(v -> scanStart());
        mBind.btnConnect.setOnClickListener(v -> connectDevice());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 开始扫描
     */
    private void scanStart() {
        if (TextUtils.isEmpty(mBind.etMac.getText())) {
            ToastUtils.showToast(this, "请设置mac过滤条件");
            return;
        }
        if (TextUtils.isEmpty(mBind.etTime.getText())) {
            ToastUtils.showToast(this, "请设置扫描时长，单位秒");
            return;
        }
        if (null != advInfoList && advInfoList.size() > 0) advInfoList.clear();
        adapter.replaceData(advInfoList);
        long time = Long.parseLong(mBind.etTime.getText().toString()) * 1000;
        startScan();
        mBind.btnStart.postDelayed(() -> {
            mokoBleScanner.stopScanDevice();
        }, time);
    }


    private void connectDevice() {
        final String mac = mBind.etMac.getText().toString();
        if (TextUtils.isEmpty(mBind.etMac.getText())) {
            ToastUtils.showToast(this, "请设置mac过滤条件");
            return;
        }
        if (mac.length() != 12) {
            ToastUtils.showToast(this, "MAC地址格式不正确");
            return;
        }
        showLoadingProgressDialog();
        mBind.btnConnect.postDelayed(() -> {
            MokoSupport.getInstance().connDevice(mDevice);
        }, 500);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectStatusEvent(ConnectStatusEvent event) {
        String action = event.getAction();
        if (MokoConstants.ACTION_DISCONNECTED.equals(action)) {
            dismissLoadingProgressDialog();
            dismissMessageProgressDialog();
            ToastUtils.showToast(this, "设备已断开");
        }
        if (MokoConstants.ACTION_DISCOVER_SUCCESS.equals(action)) {
            // 设备连接成功，通知页面更新
            dismissLoadingProgressDialog();
            showMessageProgressDialog("Syncing...");
            mBind.btnConnect.postDelayed(() -> {
                ArrayList<OrderTask> orderTasks = new ArrayList<>();
                orderTasks.add(OrderTaskAssembler.setPassword("MOKOMOKO"));
                MokoSupport.getInstance().sendOrder(orderTasks.toArray(new OrderTask[]{}));
            }, 500);

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderTaskResponseEvent(OrderTaskResponseEvent event) {
        final String action = event.getAction();
        if (MokoConstants.ACTION_ORDER_TIMEOUT.equals(action)) {
        }
        if (MokoConstants.ACTION_ORDER_FINISH.equals(action)) {
        }
        if (MokoConstants.ACTION_ORDER_RESULT.equals(action)) {
            OrderTaskResponse response = event.getResponse();
            OrderCHAR orderCHAR = (OrderCHAR) response.orderCHAR;
            int responseType = response.responseType;
            byte[] value = response.responseValue;
            switch (orderCHAR) {
                case CHAR_PASSWORD:
                    dismissMessageProgressDialog();
                    if (value.length == 1) {
                        if (MokoUtils.toInt(value) == 0) {
                            // 跳转设置页面
                            Intent intent = new Intent(this, DeviceInfoActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(MainActivity.this, "password error");
                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void onStartScan() {
        showLoadingProgressDialog();
    }

    private AdvInfoAnalysisImpl advInfoAnalysisImpl;

    @Override
    public void onScanDevice(DeviceInfo deviceInfo) {
        //只解析过滤条件的数据
        String mac = mBind.etMac.getText().toString().toUpperCase();
        String scanMac = deviceInfo.mac.replaceAll(":", "");
        if (!scanMac.contains(mac)) return;
        AdvInfo advInfo = advInfoAnalysisImpl.analyseDeviceInfo(deviceInfo);
        if (advInfo == null) return;
        mBind.etMac.setText(scanMac);
        mDevice = deviceInfo.mac;
        advInfoList.add(advInfo);
        adapter.replaceData(advInfoList);
    }

    @Override
    public void onStopScan() {
        dismissLoadingProgressDialog();
    }

    private void startScan() {
        if (!MokoSupport.getInstance().isBluetoothOpen()) {
            // 蓝牙未打开，开启蓝牙
            MokoSupport.getInstance().enableBluetooth();
            return;
        }
        advInfoAnalysisImpl = new AdvInfoAnalysisImpl();
        mokoBleScanner.startScanDevice(this);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        mokoBleScanner.stopScanDevice();
        finish();
    }
}
