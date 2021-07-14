package com.oio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

public class v2AxyBSub {
	public static String v2AxyBSub(String telA, String telX, String telB,
			String appkey, String appsecret) {
		String method = "POST";
		// mode101:APP自带x号码
		// mode102:平台分配x号码
		String url = "http://39.106.102.219:9110/v2/axyb/mode102";// 请求地址
		String requestId = UUIDGenerator.getUUID();// 绑定业务id
		String ts = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());// 业务时间
		String subts = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());// 绑定时间
		JSONObject aa = new JSONObject();
		try {
			aa.put("callrecording", "1");// 录音控制0为不录音1为录音
		} catch (org.json.JSONException e2) {
			e2.printStackTrace();
		}
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("appkey", appkey);// 公司唯一标识
		inmap.put("ts", ts);// 业务时间
		inmap.put("requestId", requestId);// 业务id
		inmap.put("telA", telA);// 主叫A号码
		inmap.put("telX", telX);// 号外号小号
		inmap.put("telB", telB);// 被叫B号码
		inmap.put("subts", subts);// 绑定时间北京时间年月日时分秒
		inmap.put("anucode", "1,2,3");// 放音编码
		inmap.put("areacode", "10");// 区号
		// inmap.put("expiration", "10");//过期时间
		inmap.put("smsdisplay", "1");// 0(默认):A发短信给Y到B，来显为Y；B发短信给Y到A，来显为Y；B发短信给X到A，来显为Y
										// 1:A发短信给Y到B，来显为X；B发短信给X到A，来显为Y
		inmap.put("callrecording", "1");// 录音控制 0为不录音 1 为录音
		inmap.put("expiretime", "20171208143130");// 指定过期时间，此字段和unsubmethod可同时生效unsubmethod优先级最高
		inmap.put("unsubmethod", "1");// 解绑方式 0为振铃解绑 1为挂机解绑
		System.out.println(inmap.entrySet());// 打印绑定的参数
		String msgdgt = V2SignDigest.md5(inmap, appsecret);// 执行md5加密
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", appkey);// 公司唯一标识
		headers.put("ts", ts);// 业务时间
		headers.put("msgdgt", msgdgt);// md5加密的字段
		System.out.println(headers.toString());
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("requestId", requestId);// 业务id
			jsonObject.put("telA", telA);// 主叫A号码
			jsonObject.put("telX", telX);// 号外号小号
			jsonObject.put("telB", telB);// 主叫B号码
			jsonObject.put("subts", subts);// 绑定时间
			jsonObject.put("anucode", "1,2,3");// 放音编码比如：“1,2,3”表示A->X放音编号为1，
														// B->X放音编号为2，
														// 其他号码->X放音编号为3。
			jsonObject.put("areacode", "10");// 号码地区区号
			// jsonObject.put("expiration", "10");//过期时间
			jsonObject.put("smsdisplay", "1");// 0(默认):A发短信给Y到B，来显为Y；B发短信给Y到A，来显为Y；B发短信给X到A，来显为Y
												// 1:A发短信给Y到B，来显为X；B发短信给X到A，来显为Y
			jsonObject.put("expiretime", "20171208143130");// 过期时间
			jsonObject.put("unsubmethod", "1");// 指定解绑方式。0为振铃解绑 1为挂机解绑
			jsonObject.put("extra", aa);// 扩展参数
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String jsonStr = jsonObject.toString();
		System.out.println("请求参数" + jsonStr.toString());// 打印的请求参数
		String respCon = null;
		try {
			respCon = MyHttpClient.doMethodJson(method, url, headers, jsonStr);
			// 返回参数
			System.out.println("返回参数" + respCon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respCon;
	}

	public static void main(String[] args) {
		String tela = "17072970114";
		String telx = "13016420184";
		String telb = "18510797288";
		String appkey = "haowaihao";
		String appsecret = "L9HASRNCM0IQ";
		v2AxyBSub(tela, telx, telb, appkey, appsecret);
	}
}
