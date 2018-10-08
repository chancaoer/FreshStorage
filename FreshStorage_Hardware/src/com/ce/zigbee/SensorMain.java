package com.ce.zigbee;

import com.ce.socket.UrlConnector;

public class SensorMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Sensor sen = new Sensor();
		sen.openSerialPort();
		
		UrlConnector.sensorSendtoUrl();

		UrlConnector.getTempfromUrl();

	}

}

