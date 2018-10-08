package com.ce.serialport;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;

public abstract class FindCardUtils implements Runnable{
	public static SerialPort COM4 = null;
	public static byte[] returnFrame = {};
	public static String subString = "";
	public static String getReFrame = "";
	
	public static void openCOM4() {

		try {
			COM4 = SerialPortManager.openPort("COM4", 19200);
			System.out.println("COM4 opened!");
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeCOM4(){
		SerialPortManager.closePort(COM4);
		System.out.println("COM4 closed!");
	}
	
	@Override
	public void run() {
		
		while (returnFrame.length <12) {//no card
			// TODO Auto-generated method stub
			sendFrame();
			
			if(returnFrame.length == 12){
				getReFrame = ByteUtils.byteArrayToHexString(returnFrame);
				System.out.println("¿¨ºÅ£º"+getReFrame);
			
				subString = getReFrame.substring(12, 20);
				System.out.println(subString);
				listen(subString);

				returnFrame = new byte[9];
			}

		}				

	}

	public abstract void listen(String subString);

	public static void sendFrame(){
		String findFrame = "";
		findFrame = "0200000446529C03";
		SerialPortManager.sendToPort(COM4,ByteUtils.hexStr2Byte(findFrame));

		byte[] midFrame = {};
		String midData = "";
		midFrame = SerialPortManager.readFromPort(COM4);
		midData = ByteUtils.byteArrayToHexString(midFrame);

		String getFrame = "";
		getFrame = "0200000447044F03";
		SerialPortManager.sendToPort(COM4,ByteUtils.hexStr2Byte(getFrame));

		// µÃµ½¿¨ºÅ
		// byte[] returnFrame = {};
		returnFrame = SerialPortManager.readFromPort(COM4);
		
	}
}
