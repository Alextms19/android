package com.example.bills;


public class Expense {
    String expenseName;
    Record record;

    public Expense(String expenseName, Record record) {
        this.expenseName = expenseName;
        this.record=record;
        this.record.setLastRecord(record.getLastRecord());

    }

    public Expense(){

    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName){
        this.expenseName = expenseName;
    }


    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseName='" + expenseName + '\'' +
                ", record{"  +
                "lastRecord=" + record.getLastRecord()+
                ", recordPrice="+ record.getRecordPrice()+
                "}"+
                "}";
    }
}
