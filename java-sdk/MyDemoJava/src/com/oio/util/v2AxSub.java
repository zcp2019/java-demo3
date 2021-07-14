package com.oio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import net.sf.json.JSONObject;

public class v2AxSub {
	public static String V2axSub(String telA, String telX, String appkey,
			String appsecret) {
		String method = "POST";// 请求方式
		// mode101:APP自带x号码
		// mode102:平台分配x号码
		String url = "http://39.106.102.219:9110/v2/ax/mode101";// 请求地址
		String requestId = UUIDGenerator.getUUID();// 绑定业务id
		String ts = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());// 业务时间
		String subts = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());// 绑定时间
		String name = "123";// 绑定姓名，为必传字段，可随机设置。
		JSONObject aa = new JSONObject();
		try {
			aa.put("callrecording", "1");// 录音控制0为不录音，1为可以录音
			aa.put("calldisplay", "0");// 来电显示，如果为0则其他号码拨打X到A，A号码会显示真实号码。1为显示小号码
			aa.put("anucodecalled", "1");// 主叫放音彩铃设置
			aa.put("anucodecaller", "1");// 被叫放音彩铃设置
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("appkey", appkey); // 公司的唯一标识
		inmap.put("ts", ts);// 业务时间
		inmap.put("requestId", requestId);// 业务id
		inmap.put("telA", telA);// 真实号码
		inmap.put("telX", telX);// 号外号小号
		inmap.put("subts", subts);// 绑定时间
		inmap.put("name", name);// 姓名，可随便填写，但必须有数据
		inmap.put("cardtype", "0");// 身份证类型
		inmap.put("cardno", "211382199203031218");// 身份证号码
		inmap.put("areacode", "029");// 地区区号
		inmap.put("expiration", "8000");// 过期时间，设置则0则永不解绑
		inmap.put("callrecording", aa.getString("callrecording"));// 录音控制
		inmap.put("calldisplay", aa.getString("calldisplay"));// 来显控制
		inmap.put("anucodecalled", aa.getString("anucodecalled"));// 主叫播放彩铃设置
		inmap.put("anucodecaller", aa.getString("anucodecaller"));// 被叫播放彩铃设置
		// inmap.put("expiretime","20171128213500");//指定过期时间
		String msgdgt = V2SignDigest.md5(inmap, appsecret);// 进行 md5加密
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("appkey", appkey);// 公司唯一标识
		headers.put("ts", ts);// 业务时间
		headers.put("msgdgt", msgdgt);// md5加密的数据
		System.out.println("header数据--" + headers.toString());// 打印请求头数据可以根据信息找出问题。
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("requestId", requestId);// 业务id
			jsonObject.put("telA", telA);// 真实号码
			jsonObject.put("telX", telX); // 号外号小号
			jsonObject.put("subts", subts);// 绑定时间
			jsonObject.put("name", name);// 姓名可随意填写，但必须传入
			jsonObject.put("cardtype", "0");// 证件类型
			jsonObject.put("cardno", "211382199203031218");// 身份证号码
			jsonObject.put("areacode", "029");// 地区区号
			jsonObject.put("expiration", "8000");// 过期时间
			// jsonObject.put("expiretime","20171128213500");//指定过期时间
			jsonObject.put("extra", aa);// 扩展参数的传递
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
		String tela = "17701373565";
		String telx = "13016420174";
		String appkey = "haowaihao";
		String appsecret = "L9HASRNCM0IQ";
		V2axSub(tela, telx, appkey, appsecret);

	}
}
