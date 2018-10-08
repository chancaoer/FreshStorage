package com.ce.zigbee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.eclipse.jface.text.templates.Template;

import com.ce.serialport.ByteUtils;
import com.ce.serialport.SerialPortManager;
import com.ce.socket.UrlConnector;

import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Sensor implements SerialPortEventListener {

	public static List<RSht10> rsht10 = new ArrayList<>();
	static {
		rsht10.add(new RSht10());
		rsht10.add(new RSht10());
	}
	
	static SerialPort COM3 = null;
	byte[] getfromsensor = {};
	String getFrame = "";

	// 打开串口
	public void openSerialPort() {
		try {
			COM3 = SerialPortManager.openPort("COM3", 115200);
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			COM3.addEventListener(this);
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		COM3.notifyOnDataAvailable(true);

	}

	// 设置监听
	@Override
	public void serialEvent(SerialPortEvent serialPortEvent) {
		// TODO Auto-generated method stub
		switch (serialPortEvent.getEventType()) {
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			getfromsensor = SerialPortManager.readFromPort(COM3);
			getFrame = ByteUtils.byteArrayToHexString(getfromsensor);// 得到传感器的数据帧
			System.out.println(getFrame);
			if (getFrame.substring(10, 14).equals("3054")) {
				System.out.println("该仓库为01仓库，实时检测温湿度如下：");
				rsht10.get(0).NWaddr = "3054";
				sht10(rsht10.get(0));
				System.out.println(rsht10.get(0).NWaddr);
			}
			if (getFrame.substring(10, 14).equals("4E9C")) {
				System.out.println("该仓库为02仓库，实时监测温湿度如下：");
				rsht10.get(1).NWaddr = "4E9C";
				sht10(rsht10.get(1));
				System.out.println(rsht10.get(1).NWaddr);
			}
			

		default:
			break;
		}
	}
	
	public void sht10(RSht10 rSht10) {
		double temp = 0.0d;
		String oppoTemp = "";
//		rSht10.NWaddr = getFrame.substring(10 ,14);
		oppoTemp = getFrame.substring(16, 20);
		rSht10.temp = ByteUtils.hextoDec(oppoTemp);
		System.out.println("仓库温度为：" + rSht10.temp);

		String oppoHumm = "";
		oppoHumm = getFrame.substring(38, 42);
		rSht10.humm = ByteUtils.hextoDec(oppoHumm);
		System.out.println("仓库湿度为：" + rSht10.humm);
	}

	public static void lightCtrl(String ctrl) {
		
		String info = "";
		String sendFrame = "";
		info = "071800F142B001";//0f
		sendFrame = "02" + info + ctrl + ByteUtils.checkSum(info+ctrl);
		System.out.println("check"+sendFrame);
		SerialPortManager.sendToPort(COM3, ByteUtils.hexStr2Byte(sendFrame));
		System.out.println("04!!!"+sendFrame);
	}
	
}

