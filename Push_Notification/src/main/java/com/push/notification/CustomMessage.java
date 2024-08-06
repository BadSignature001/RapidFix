package com.push.notification;

public class CustomMessage {

    private String to;
    private String text;

    // Constructors
    public CustomMessage() {}

    public CustomMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
