package com.ce.socket;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ce.entranceguard.AddUi;
import com.ce.entranceguard.InUi;
import com.ce.serialport.FindCardUtils;

import com.ce.zigbee.Sensor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UrlConnector {

	private static String verStr;
	private static String addStr;
	private static String inStr;
	private static String outStr;
	
	
	//验证
	public static boolean EGsendtoUrl(){
		OkHttpClient client = new OkHttpClient();
		String url = "http://172.20.10.4:8000/loginc/";

		FormBody.Builder formBody = new FormBody.Builder();
		formBody.add("cardId",FindCardUtils.subString);
		Request request = new Request.Builder()
							.url(url)
							.post(formBody.build())
							.build();
		
		try {
			Response verResponse = client.newCall(request).execute();
			verStr = verResponse.body().string();
			System.out.println(verStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(verStr.equals("true")){
			return true;
		}else {
			return false;
		}
	}
	
	//添加用户
	public static boolean addUserUrl(){
		OkHttpClient client = new OkHttpClient();
		String url = "http://172.20.10.4:8000/regis/";

		FormBody.Builder formBody = new FormBody.Builder();
		formBody.add("cardId",AddUi.cardId);
		formBody.add("name",AddUi.name);
		
		Request request = new Request.Builder()
							.url(url)
							.post(formBody.build())
							.build();
		
		try {
			Response addResponse = client.newCall(request).execute();
			addStr = addResponse.body().string();
			System.out.println(addStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(addStr.equals("true")){
			return true;
		}else {
			return false;
		}
		
	}

	//添加商品
	public static boolean addGoodsUrl(){
		OkHttpClient client = new OkHttpClient();
		String url = "http://172.20.10.4:8000/regisg/";

		FormBody.Builder formBody = new FormBody.Builder();
		formBody.add("rid",InUi.storageId);
		formBody.add("gid",InUi.goodsId);
		formBody.add("gclass", InUi.goodsName);
		formBody.add("gstate", InUi.goodsStatus);
		
		Request request = new Request.Builder()
							.url(url)
							.post(formBody.build())
							.build();
		
		try {
			Response addResponse = client.newCall(request).execute();
			inStr = addResponse.body().string();
			System.out.println(inStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(inStr.equals("true")){
			return true;
		}else {
			return false;
		}
		
	}
	
	//商品出库
	public static boolean outGoodstoUrl(){
		OkHttpClient client = new OkHttpClient();
		String url = "http://172.20.10.4:8000/loging/";

		FormBody.Builder formBody = new FormBody.Builder();
		formBody.add("gid",FindCardUtils.subString);
		Request request = new Request.Builder()
							.url(url)
							.post(formBody.build())
							.build();
		
		try {
			Response verResponse = client.newCall(request).execute();
			outStr = verResponse.body().string();
			System.out.println(outStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(outStr.equals("true")){
			return true;
		}else {
			return false;
		}
	}
	
	//温湿度
	public static void sensorSendtoUrl(){
		final long timeInterval = 5000;
		
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					
					OkHttpClient client = new OkHttpClient();
					String url = "http://172.20.10.4:8000/date/";
					
					for(int i=0;i<Sensor.rsht10.size();i++) {

						System.out.println(Sensor.rsht10.size());
						
						if (!Sensor.rsht10.get(i).NWaddr.equals(" ")) {
							FormBody.Builder formBody = new FormBody.Builder();
							formBody.add("sid",Sensor.rsht10.get(i).NWaddr);
							formBody.add("nowtem",Sensor.rsht10.get(i).temp+"");
							formBody.add("nowhum",Sensor.rsht10.get(i).humm+"");
							
							Request request = new Request.Builder()
												.url(url)
												.post(formBody.build())
												.build();
							
							Call call = client.newCall(request);
							call.enqueue(new Callback() {

								@Override
								public void onFailure(Call call, IOException arg1) {
									// TODO Auto-generated method stub
									Request r = call.request();
									System.out.println("请求失败" + r.url());
								}

								@Override
								public void onResponse(Call call, Response response)
										throws IOException {
									// TODO Auto-generated method stub
									
								}

							});
						}
						
					}
					
					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		
	}
	
	public static void getTempfromUrl() {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					
					OkHttpClient client = new OkHttpClient();
					String url = "http://172.20.10.4:8000/modify/";
					
						FormBody.Builder formBody = new FormBody.Builder();
						formBody.add("rid","");
						formBody.add("tem", "");
						Request request = new Request.Builder()
											.url(url)
											.post(formBody.build())
											.build();
						
						try {
							Response response = client.newCall(request).execute();
							String responseData = response.body().string();
							
							JSONArray jsonArray;
							try {
								jsonArray = new JSONArray(responseData);
								JSONObject jsonObject = jsonArray.getJSONObject(0);
								String rid = jsonObject.getString("rid");
								String state = jsonObject.getString("state");
								String temp = jsonObject.getString("nowtem");
								String humm = jsonObject.getString("nowhum");
								
								if (state.equals("OK")) {
									if (rid.equals("01")) {
										if (humm.equals("")) {
											Sensor.lightCtrl("04");
										}else {
											Sensor.lightCtrl("08");
										}
									}
									if (rid.equals("02")) {
										if (humm.equals("")) {
											Sensor.lightCtrl("02");
										}else {
											Sensor.lightCtrl("01");
										}
									}
								}
								if (state.equals("NO")) {
									Sensor.lightCtrl("00");
								}
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		thread.start();
	}

}
