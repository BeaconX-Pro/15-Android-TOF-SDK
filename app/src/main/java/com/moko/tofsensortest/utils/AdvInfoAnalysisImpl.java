package com.moko.tofsensortest.utils;

import com.moko.ble.lib.utils.MokoUtils;
import com.moko.support.MokoBleScanner;
import com.moko.support.entity.DeviceInfo;
import com.moko.support.service.AdvInfoAnalysis;
import com.moko.tofsensortest.adapter.AdvInfo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import no.nordicsemi.android.support.v18.scanner.ScanRecord;
import no.nordicsemi.android.support.v18.scanner.ScanResult;


public class AdvInfoAnalysisImpl implements AdvInfoAnalysis<AdvInfo> {
    private final SimpleDateFormat sdf;

    public AdvInfoAnalysisImpl() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    }

    @Override
    public AdvInfo analyseDeviceInfo(DeviceInfo deviceInfo) {
        ScanResult result = deviceInfo.scanResult;
        ScanRecord record = result.getScanRecord();
        byte[] manufacturerSpecificData = record.getManufacturerSpecificData(MokoBleScanner.MANUFACTURER_ID);
        if (manufacturerSpecificData == null || manufacturerSpecificData.length < 15) {
            return null;
        }
        int beaconType = MokoUtils.toInt(Arrays.copyOfRange(manufacturerSpecificData, 0, 2));
        if (beaconType != 0x076C)
            return null;
        int distance = ((manufacturerSpecificData[8] & 0xFF) << 8) + (manufacturerSpecificData[7] & 0xFF);
        AdvInfo advInfo = new AdvInfo();
        advInfo.scanTime = sdf.format(new Date(System.currentTimeMillis()));
        advInfo.mac = deviceInfo.mac;
        advInfo.distance = String.format("%dmm", distance);
        return advInfo;
    }
}
