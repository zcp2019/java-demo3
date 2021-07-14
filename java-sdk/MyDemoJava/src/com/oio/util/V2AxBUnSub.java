package com.oio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class V2AxBUnSub {
	public static String V2axbUnSub(String subid, String appkey,
			String appsecret) {
		String method = "DELETE";// 请求方式
		String url = "http://39.106.102.219:9110/v2/axb/" + subid;// 请求地址
		String ts = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());// 业务时间
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("appkey", appkey);
		inmap.put("ts", ts);
		String msgdgt = V2SignDigest.md5(inmap, appsecret);// 执行md5加密
		Map<String, Object> params = new HashMap<String, Object>();
		String respCon = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("appkey", appkey);
			map.put("ts", ts);
			map.put("msgdgt", msgdgt);
			respCon = MyHttpClient.doMethodGet(method, url, map, params);
			// 返回参数
			System.out.println("返回参数--" + respCon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respCon;
	}

	public static void main(String[] args) {
		String subid = "A2210X10X0310638589-11-1-NHWH-GXI";
		String appkey = "haowaihao";
		String appsecret = "L9HASRNCM0IQ";
		V2axbUnSub(subid, appkey, appsecret);

	}
}
