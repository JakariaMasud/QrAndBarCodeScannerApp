package com.example.qrandbarcodescanner.models;

public class BarCodeDataModel {
    private String barCodeData, timeOfScan;

    public BarCodeDataModel(String barCodeData, String timeOfScan) {
        this.barCodeData = barCodeData;
        this.timeOfScan = timeOfScan;
    }

    public BarCodeDataModel() {
    }

    public String getBarCodeData() {
        return barCodeData;
    }

    public void setBarCodeData(String barCodeData) {
        this.barCodeData = barCodeData;
    }

    public String getTimeOfScan() {
        return timeOfScan;
    }

    public void setTimeOfScan(String timeOfScan) {
        this.timeOfScan = timeOfScan;
    }
}
