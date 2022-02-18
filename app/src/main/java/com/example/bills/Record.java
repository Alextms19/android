package com.example.bills;

public class Record {
    private float lastRecord;
    private float recordPrice;


    public Record(float expenseLastRecord, float recordPrice){
        this.lastRecord=expenseLastRecord;
        this.recordPrice = recordPrice;
    }

    public double calculateExpanse(double expenseCurrentRecord){
        double result = (expenseCurrentRecord - lastRecord) * recordPrice;
        return result;
    }

    public void setLastRecord(float expenseLastRecord) {
        this.lastRecord = expenseLastRecord;
    }

    public void setRecordPrice(float recordPrice) {
        this.recordPrice = recordPrice;
    }

    public float getRecordPrice() {
        return recordPrice;
    }

    public float getLastRecord() {
        return lastRecord;
    }
}
