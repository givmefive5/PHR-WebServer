package com.example.model;

import java.sql.Timestamp;

public class Log {

	public String message;
	public String ip;
	public Timestamp timestamp;

	public Log(String message, String ip, Timestamp timestamp) {
		super();
		this.message = message;
		this.ip = ip;
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
