package com.ce.zigbee;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import com.ce.serialport.ByteUtils;
import com.ce.serialport.SerialPortManager;

public class ExecuteB {
public static SerialPort COM3 = null;
	
	public static void setSerialPort(){
		try {
			COM3 = SerialPortManager.openPort("COM3", 115200);
			System.out.println("COM3 opened!!!");
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			System.out.println("error!!!");
			e.printStackTrace();
		}
	}
	
	public static void closeSerialPort() {
		COM3.close();
		System.out.println("COM3 closed!!!!");
	}
	
	public static void light(String ctrl) {
		
		String info = "";
		String sendFrame = "";
		info = "071800F142B001";//0f
		sendFrame = "02" + info + ctrl + ByteUtils.checkSum(info+ctrl);
		System.out.println("check"+sendFrame);
		SerialPortManager.sendToPort(COM3, ByteUtils.hexStr2Byte(sendFrame));
		System.out.println("04!!!"+sendFrame);
	}
	
}
