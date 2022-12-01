package com.moko.support.handler;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import com.moko.support.entity.OrderCHAR;
import com.moko.support.entity.OrderServices;

import java.util.HashMap;

public class MokoCharacteristicHandler {
    private HashMap<OrderCHAR, BluetoothGattCharacteristic> mCharacteristicMap;

    public MokoCharacteristicHandler() {
        //no instance
        mCharacteristicMap = new HashMap<>();
    }

    public HashMap<OrderCHAR, BluetoothGattCharacteristic> getCharacteristics(final BluetoothGatt gatt) {
        if (mCharacteristicMap != null && !mCharacteristicMap.isEmpty()) {
            mCharacteristicMap.clear();
        }
        if (gatt.getService(OrderServices.SERVICE_BATTERY.getUuid()) != null) {
            final BluetoothGattService service = gatt.getService(OrderServices.SERVICE_BATTERY.getUuid());
            if (service.getCharacteristic(OrderCHAR.CHAR_BATTERY.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_BATTERY.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_BATTERY, characteristic);
            }
        }
        if (gatt.getService(OrderServices.SERVICE_DEVICE_INFO.getUuid()) != null) {
            final BluetoothGattService service = gatt.getService(OrderServices.SERVICE_DEVICE_INFO.getUuid());
            if (service.getCharacteristic(OrderCHAR.CHAR_MODEL_NUMBER.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_MODEL_NUMBER.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_MODEL_NUMBER, characteristic);
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_SERIAL_NUMBER.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_SERIAL_NUMBER.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_SERIAL_NUMBER, characteristic);
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_FIRMWARE_REVISION.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_FIRMWARE_REVISION.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_FIRMWARE_REVISION, characteristic);
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_HARDWARE_REVISION.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_HARDWARE_REVISION.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_HARDWARE_REVISION, characteristic);
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_SOFTWARE_REVISION.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_SOFTWARE_REVISION.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_SOFTWARE_REVISION, characteristic);
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_MANUFACTURER_NAME.getUuid()) != null) {
                final BluetoothGattCharacteristic characteristic = service.getCharacteristic(OrderCHAR.CHAR_MANUFACTURER_NAME.getUuid());
                mCharacteristicMap.put(OrderCHAR.CHAR_MANUFACTURER_NAME, characteristic);
            }
        }
        if (gatt.getService(OrderServices.SERVICE_PARAMS.getUuid()) != null) {
            final BluetoothGattService service = gatt.getService(OrderServices.SERVICE_PARAMS.getUuid());
            if (service.getCharacteristic(OrderCHAR.CHAR_PARAMS.getUuid()) != null) {
                mCharacteristicMap.put(OrderCHAR.CHAR_PARAMS, service.getCharacteristic(OrderCHAR.CHAR_PARAMS.getUuid()));
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_PASSWORD.getUuid()) != null) {
                mCharacteristicMap.put(OrderCHAR.CHAR_PASSWORD, service.getCharacteristic(OrderCHAR.CHAR_PASSWORD.getUuid()));
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_TX_POWER.getUuid()) != null) {
                mCharacteristicMap.put(OrderCHAR.CHAR_TX_POWER, service.getCharacteristic(OrderCHAR.CHAR_TX_POWER.getUuid()));
            }
            if (service.getCharacteristic(OrderCHAR.CHAR_ADV_INTERVAL.getUuid()) != null) {
                mCharacteristicMap.put(OrderCHAR.CHAR_ADV_INTERVAL, service.getCharacteristic(OrderCHAR.CHAR_ADV_INTERVAL.getUuid()));
            }
        }
        return mCharacteristicMap;
    }
}
