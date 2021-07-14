package com.oio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import net.sf.json.JSONObject;

public class V2AxBSub {

	public static String V2AxBSub(String telA, String telX, String telB, String appkey,
			String appsecret) {
		String method = "POST";// 请求方式
		// mode101:APP自带x号码
		// mode102:平台分配x号码
		String url = "http://39.106.102.219:9110/v2/axb/mode102";// 请求的地址
		String requestId = UUIDGenerator.getUUID();// 请求的业务id
		String ts = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());// 业务时间
		String subts = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());// 绑定时间
		JSONObject aa = new JSONObject();
		try {
			aa.put("callrecording", "1");// 录音控制1录音，0不录音。
			aa.put("calldisplay", "0,0");// 被叫来显0,0不显示双方真实号码，0,1为B拨打A，A显示B号码，1,0A拨打B，B测显示A号码
			aa.put("callrestrict", "1");// 呼叫控制 1为现有专属AXB模式
			aa.put("calldisplayshow", "1");// 推送被叫来显号码 1为推送 0为不推送
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("appkey", appkey); // 公司唯一标识
		inmap.put("ts", ts);// 业务时间
		inmap.put("requestId", requestId);// 业务请求id
		inmap.put("telA", telA);// 主叫真实号码
		inmap.put("telX", telX);// 号外号小号码
		inmap.put("telB", telB);// 被叫真实号码
		inmap.put("subts", subts);// 绑定时间
		inmap.put("anucode", "1,2,3");// 彩铃的配置
		// inmap.put("areacode","010");//号码地区区号
		inmap.put("expiration", "1000");// 过期时间
		inmap.put("callrecording", aa.getString("callrecording"));// 录音控制
		inmap.put("calldisplay", aa.getString("calldisplay"));// 被叫来电显示
		inmap.put("callrestrict", aa.getString("callrestrict"));// 呼叫的控制方式
		inmap.put("calldisplayshow", aa.getString("calldisplayshow"));// 推送被叫来显号码
		// inmap.put("unsubmethod","1");//指定解绑方式0为振铃解绑，1为挂机解绑，此字段可以和过期时间和指定绑定都时间同时生效。
		// inmap.put("expiretime","20171208144500");//指定过期时间配置，它和过期解绑不能同时生效。
		String msgdgt = V2SignDigest.md5(inmap, appsecret);// 进行md5加密
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", appkey);// 将appkey放入请求头
		headers.put("ts", ts);// 将业务时间截放到请求头中
		headers.put("msgdgt", msgdgt);// 将加密的md5放大请求头当中
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("requestId", requestId);// 业务请求id
			jsonObject.put("telA", telA);// 主叫真实号码
			jsonObject.put("telX", telX);// 号外号小号吗
			jsonObject.put("telB", telB);// 被叫真实号码
			jsonObject.put("subts", subts);// 绑定时间
			jsonObject.put("anucode", "1,2,3");// 彩铃配置
			// jsonObject.put("areacode","010");//地区区号
			jsonObject.put("expiration", "1000");// 过期时间
			// jsonObject.put("expiretime","20171208144500");//指定的过期时间
			// jsonObject.put("unsubmethod","1");//解绑方式
			jsonObject.put("extra", aa);// 传的扩展字段
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String jsonStr = jsonObject.toString();
		String respCon = null;
		try {
			respCon = MyHttpClient.doMethodJson(method, url, headers, jsonStr);
			System.out.println("绑定返回参数" + respCon);// 打印绑定成功的字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respCon;

	}

	// 测试绑定关系main的方法
	public static void main(String[] args) {
		String telX = "13164206072";		//绑定的小号
		String telA = "18211132622";		//员工号码
		String telB = "15037900958";		//客户号码
		String appkey = "haowaihao";
		String appsecret = "L9HASRNCM0IQ";
		V2AxBSub(telA, telX, telB, appkey, appsecret);
	}
}
