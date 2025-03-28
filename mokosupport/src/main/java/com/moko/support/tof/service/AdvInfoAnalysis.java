package com.moko.support.tof.service;

import com.moko.support.tof.entity.DeviceInfo;

public interface AdvInfoAnalysis<T> {
    T analyseDeviceInfo(DeviceInfo deviceInfo);
}
