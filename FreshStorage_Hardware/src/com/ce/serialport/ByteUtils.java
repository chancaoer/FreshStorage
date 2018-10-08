package com.ce.serialport;

import java.nio.ByteBuffer;
import java.util.Locale;

public class ByteUtils {
	
	//sendFrame  十六进制字符串转byte[]
	public static byte[] hexStr2Byte(String hex) {
		if (hex == null) {
			return new byte[] {};
		}

		// 奇数位补0
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		int length = hex.length();
		ByteBuffer buffer = ByteBuffer.allocate(length / 2);
		for (int i = 0; i < length; i++) {
			String hexStr = hex.charAt(i) + "";
			i++;
			hexStr += hex.charAt(i);
			byte b = (byte) Integer.parseInt(hexStr, 16);
			buffer.put(b);
		}
		return buffer.array();
	}

	//getFrame  byte[]转十六进制字符串
	public static String byteArrayToHexString(byte[] array) {
		if (array == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buffer.append(byteToHex(array[i]));
		}
		return buffer.toString();
	}

	//byte转十六进制字符
	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase(Locale.getDefault());
	}
	
	//十六进制转十进制
	public static double hextoDec(String hex){
		String a = hex.substring(0,2);
		String b = hex.substring(2,4);
		String temp = "";
		temp = a;
		a = b;
		b = temp;
		return Integer.parseInt(a+b,16)/100.00;
	}
	
	//校验和，可能没什么用
	public static String checkSum(String para) {  
	    int length = para.length() / 2;  
	    String[] dateArr = new String[length];  

	    for (int i = 0; i < length; i++) {  
	        dateArr[i] = para.substring(i * 2, i * 2 + 2);  
	    }  
	    String code = "00";  
	    for (int i = 0; i < dateArr.length; i++) {  
	        code = Xor(code, dateArr[i]);  
	    }  
	    return code;  
	}
	
	private static String Xor(String strHex_X,String strHex_Y){ 

		//将x、y转成二进制形式 

		String anotherBinary=Integer.toBinaryString(Integer.valueOf(strHex_X,16)); 

		String thisBinary=Integer.toBinaryString(Integer.valueOf(strHex_Y,16)); 

		String result = ""; 

		//判断是否为8位二进制，否则左补零 
		if(anotherBinary.length() != 8){ 
		for (int i = anotherBinary.length(); i <8; i++) { 

				anotherBinary = "0"+anotherBinary; 
			} 
		} 
		if(thisBinary.length() != 8){ 
		for (int i = thisBinary.length(); i <8; i++) { 
				thisBinary = "0"+thisBinary; 
			} 
		} 

		//异或运算 
		for(int i=0;i<anotherBinary.length();i++){ 
		//如果相同位置数相同，则补0，否则补1 
				if(thisBinary.charAt(i)==anotherBinary.charAt(i)) 
					result+="0"; 
				else{ 
					result+="1"; 
				} 
			}
		return Integer.toHexString(Integer.parseInt(result, 2)); 
	}

}
