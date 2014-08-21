package com.example.model;

import java.sql.Timestamp;

public class Log {

	public String message;
	public String ip;
	public Timestamp timestamp;
	public String loggingLayer;

	public Log(String message, String ip, Timestamp timestamp,
			String loggingLayer) {
		super();
		this.message = message;
		this.ip = ip;
		this.timestamp = timestamp;
		this.loggingLayer = loggingLayer;
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

	public String getLoggingLayer() {
		return loggingLayer;
	}

	public void setLoggingLayer(String loggingLayer) {
		this.loggingLayer = loggingLayer;
	}

}
