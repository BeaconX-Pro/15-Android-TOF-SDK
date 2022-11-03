package com.moko.support.service;

import com.moko.support.entity.DeviceInfo;

public interface AdvInfoAnalysis<T> {
    T analyseDeviceInfo(DeviceInfo deviceInfo);
}
