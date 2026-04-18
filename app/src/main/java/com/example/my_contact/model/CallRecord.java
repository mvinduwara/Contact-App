package com.example.my_contact.model;

public class CallRecord {
    private int id; // The database ID
    private String callType, date, time;

    public CallRecord(int id, String callType, String date, String time) {
        this.id = id;
        this.callType = callType;
        this.date = date;
        this.time = time;
    }

    public int getId() { return id; }
    public String getCallType() { return callType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}