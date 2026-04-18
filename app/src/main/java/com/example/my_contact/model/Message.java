package com.example.my_contact.model;

public class Message {
    private int id; // NEW: Database ID
    private String text;
    private boolean isSentByMe;

    public Message(int id, String text, boolean isSentByMe) {
        this.id = id;
        this.text = text;
        this.isSentByMe = isSentByMe;
    }

    public int getId() { return id; }
    public String getText() { return text; }
    public boolean isSentByMe() { return isSentByMe; }
}