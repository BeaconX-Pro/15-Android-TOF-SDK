package com.moko.tofsensortest.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.elvishew.xlog.XLog;
import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.MokoBleScanner;
import com.moko.support.MokoSupport;
import com.moko.support.callback.MokoScanDeviceCallback;
import com.moko.support.entity.DeviceInfo;
import com.moko.tofsensortest.R;
import com.moko.tofsensortest.databinding.ActivityCarMonitorBinding;
import com.moko.tofsensortest.utils.SPUtiles;
import com.moko.tofsensortest.utils.ToastUtils;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;

/**
 * @author: jun.liu
 * @date: 2023/5/10 9:33
 * @des:
 */
public class CarMonitorActivity extends BaseActivity implements MokoScanDeviceCallback {
    private ActivityCarMonitorBinding mBind;
    private MokoBleScanner mokoBleScanner;
    private boolean isScan;
    private int num;
    private double distance;
    private int continuousCountMoKoIn;
    private int continuousCountMoKoOut;
    private int continuousCountOtherIn;
    private int continuousCountOtherOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = ActivityCarMonitorBinding.inflate(getLayoutInflater());
        setContentView(mBind.getRoot());
        mokoBleScanner = new MokoBleScanner();
        initViews();
    }

    private void initViews() {
        mBind.etNum.setText(SPUtiles.getStringValue(this, "num", ""));
        mBind.etNum.setSelection(null == mBind.etNum.getText() ? 0 : mBind.etNum.getText().length());
        mBind.etDistance.setText(SPUtiles.getStringValue(this, "distance", ""));
        mBind.etDistance.setSelection(null == mBind.etDistance.getText() ? 0 : mBind.etDistance.getText().length());
        mBind.etMacMoko.setText(SPUtiles.getStringValue(this, "macMoKo", ""));
        mBind.etMacMoko.setSelection(null == mBind.etMacMoko.getText() ? 0 : mBind.etMacMoko.getText().length());
        mBind.etMacOther.setText(SPUtiles.getStringValue(this, "macOther", ""));
        mBind.etMacOther.setSelection(null == mBind.etMacOther.getText() ? 0 : mBind.etMacOther.getText().length());
        mBind.ivBack.setOnClickListener(v -> finish());
        mBind.btnStart.setOnClickListener(v -> {
            if (!isScan) {
                if (TextUtils.isEmpty(mBind.etNum.getText())) {
                    ToastUtils.showToast(this, "输入连续次数");
                    return;
                }
                if (TextUtils.isEmpty(mBind.etDistance.getText())) {
                    ToastUtils.showToast(this, "输入有效距离");
                    return;
                }
                if (TextUtils.isEmpty(mBind.etMacMoko.getText())) {
                    ToastUtils.showToast(this, "输入公司产品的mac");
                    return;
                }
                if (TextUtils.isEmpty(mBind.etMacOther.getText())) {
                    ToastUtils.showToast(this, "输入竞品产品mac");
                    return;
                }
                startScan();
            } else {
                mokoBleScanner.stopScanDevice();
            }
        });
    }

    private void startScan() {
        num = Integer.parseInt(mBind.etNum.getText().toString());
        distance = Double.parseDouble(mBind.etDistance.getText().toString()) * 1000;
        if (!MokoSupport.getInstance().isBluetoothOpen()) {
            // 蓝牙未打开，开启蓝牙
            MokoSupport.getInstance().enableBluetooth();
            return;
        }
        SPUtiles.setStringValue(this, "num", mBind.etNum.getText().toString().trim());
        SPUtiles.setStringValue(this, "distance", mBind.etDistance.getText().toString().trim());
        SPUtiles.setStringValue(this, "macMoKo", mBind.etMacMoko.getText().toString().trim());
        SPUtiles.setStringValue(this, "macOther", mBind.etMacOther.getText().toString().trim());
        mokoBleScanner.startScanDevice(this);
    }

    @Override
    public void onStartScan() {
        continuousCountMoKoIn = 0;
        continuousCountMoKoOut = 0;
        continuousCountOtherIn = 0;
        continuousCountOtherOut = 0;
        mBind.tvMacMoko.setText("");
        mBind.tvMacOther.setText("");
        mBind.tvResultMoko.setText("");
        mBind.tvResultOther.setText("");
        isFirstMoKo = true;
        isFirstOther = true;
        builderMoKo = new StringBuilder();
        builderOther = new StringBuilder();
        mBind.btnStart.setText("停止扫描");
        isScan = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mokoBleScanner) mokoBleScanner.stopScanDevice();
    }

    private StringBuilder builderMoKo = new StringBuilder();
    private boolean isFirstMoKo = true;
    private StringBuilder builderOther = new StringBuilder();
    private boolean isFirstOther = true;

    @Override
    public void onScanDevice(DeviceInfo device) {
        String mac = device.mac.replaceAll(":", "");
        if (mac.contains(mBind.etMacMoko.getText().toString().toUpperCase())) {
            //扫描到了公司设备
            ScanRecord scanRecord = device.scanResult.getScanRecord();
            if (null == scanRecord) return;
            byte[] data = scanRecord.getManufacturerSpecificData(0x0059);
            if (null == data) return;
            XLog.i("333333moKoAdv**************="+ MokoUtils.bytesToHexString(data));
            mBind.tvMacMoko.setText(device.mac);
            int distance = (((data[8] & 0xFF) << 8) + (data[7] & 0xFF));
            if (distance < 100) return;
            if (isFirstMoKo) {
                isFirstMoKo = false;
                builderMoKo.append(distance);
            } else {
                builderMoKo.append("-").append(distance);
            }
            mBind.tvResultMoko.setText(builderMoKo.toString());
            String[] split = builderMoKo.toString().split("-");
            if (split.length == 3) {
                builderMoKo.delete(0, split[0].length() + 1);
            }
//            XLog.i("333333distanceMoko************=" + distance);
            if (distance <= this.distance) {
                //范围内
                continuousCountMoKoIn++;
                continuousCountMoKoOut = 0;
            } else {
                continuousCountMoKoOut++;
                //在范围内不连续了 清零
                continuousCountMoKoIn = 0;
            }
            if (continuousCountMoKoIn == this.num) {
                mBind.tvStatusMoko.setText("占用");
                mBind.tvStatusMoko.setBackgroundResource(R.drawable.shape_radius6_red);
                continuousCountMoKoIn = 0;
                continuousCountMoKoOut = 0;
            } else if (continuousCountMoKoOut == this.num) {
                mBind.tvStatusMoko.setText("空闲");
                mBind.tvStatusMoko.setBackgroundResource(R.drawable.shape_radius6_green);
                continuousCountMoKoIn = 0;
                continuousCountMoKoOut = 0;
            }
        } else if (mac.contains(mBind.etMacOther.getText().toString().toUpperCase())) {
            //竞品设备
            ScanRecord scanRecord = device.scanResult.getScanRecord();
            if (null == scanRecord) return;
            byte[] data = scanRecord.getManufacturerSpecificData(0x000D);
            if (null == data) return;
            XLog.i("333333Adv="+ MokoUtils.bytesToHexString(data));
            mBind.tvMacOther.setText(device.mac);
            //83bc370100aaaa1900000013070600
            int distance = (((data[8] & 0xFF) << 8) + (data[7] & 0xFF));
            if (distance < 100) return;
            if (isFirstOther) {
                isFirstOther = false;
                builderOther.append(distance);
            } else {
                builderOther.append("-").append(distance);
            }
            mBind.tvResultOther.setText(builderOther.toString());
            String[] split = builderOther.toString().split("-");
            if (split.length == 3) {
                builderOther.delete(0, split[0].length() + 1);
            }
//            XLog.i("333333distance=" + distance);
            if (distance <= this.distance) {
                //范围内
                continuousCountOtherIn++;
                continuousCountOtherOut = 0;
            } else {
                continuousCountOtherOut++;
                continuousCountOtherIn = 0;
            }
            if (continuousCountOtherIn == this.num) {
                mBind.tvStatusOther.setText("占用");
                mBind.tvStatusOther.setBackgroundResource(R.drawable.shape_radius6_red);
                continuousCountOtherIn = 0;
                continuousCountOtherOut = 0;
            } else if (continuousCountOtherOut == this.num) {
                mBind.tvStatusOther.setText("空闲");
                mBind.tvStatusOther.setBackgroundResource(R.drawable.shape_radius6_green);
                continuousCountOtherIn = 0;
                continuousCountOtherOut = 0;
            }
        }
    }

    @Override
    public void onStopScan() {
        mBind.btnStart.setText("开始扫描");
        isScan = false;
    }
}
