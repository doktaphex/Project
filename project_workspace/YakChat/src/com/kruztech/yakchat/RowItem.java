package com.kruztech.yakchat;

public class RowItem {
	static String user_name;
	 static String message;
	 static String timeStamp;

	 public RowItem(String user_name, String message, String timeStamp) {

	  this.user_name = user_name;
	  this.message = message;
	  this.timeStamp = timeStamp;
	 }

	 public String getUser_name() {
	  return user_name;
	 }

	 public void setUser_name(String user_name) {
	  this.user_name = user_name;
	 }

	 public String getMessage() {
	  return message;
	 }

	 public void setMessage(String message) {
	  this.message = message;
	 }

	 public String getTimeStamp() {
	  return timeStamp;
	 }

	 public void setTimeStamp(String timeStamp) {
	  this.timeStamp = timeStamp;
	 }

	}