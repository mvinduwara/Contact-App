package com.example.my_contact.model;

public class CallRecord {
    private String type;
    private String date;
    private String time;

    public CallRecord(String type, String date, String time) {
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public String getType() { return type; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}