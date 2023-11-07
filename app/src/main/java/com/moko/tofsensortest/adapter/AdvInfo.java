package com.moko.tofsensortest.adapter;


public class AdvInfo {
    public String scanTime;
    public String mac;
    public String distance;

    public AdvInfo() {
    }

    public AdvInfo(String scanTime, String distance) {
        this.distance = distance;
        this.scanTime = scanTime;
    }
}
