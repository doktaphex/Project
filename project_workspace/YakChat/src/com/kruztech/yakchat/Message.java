package com.kruztech.yakchat;

public class Message {
	
	private String username;
	private String message;
	private String timeStamp;

    // Required default constructor for Firebase object mapping
    @SuppressWarnings("unused")
    private Message() { }

    Message(String username, String message, String timeStamp) {
        this.message = message;
        this.username = username;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }
}
