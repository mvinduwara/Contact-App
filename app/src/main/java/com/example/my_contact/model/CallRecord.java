package com.example.my_contact.model;

public class CallRecord {
    private String callType;
    private String date;
    private String time;

    public CallRecord(String callType, String date, String time) {
        this.callType = callType;
        this.date = date;
        this.time = time;
    }

    public String getCallType() { return callType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}