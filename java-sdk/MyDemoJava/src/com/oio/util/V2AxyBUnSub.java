package com.oio.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class V2AxyBUnSub {
	public static String V2AxyBDeleteSubid(String subid, String appkey,
			String appsecret) {
		String method = "DELETE";
		String url = "http://39.106.102.219:9110/v2/axyb/" + subid;
		String ts = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("appkey", "HWHT");// 公司唯一标识
		inmap.put("ts", ts);// 业务时间
		String msgdgt = V2SignDigest.md5(inmap, "hwht_123456");// md5加密
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println(params.toString());
		String respCon = null;
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("appkey", "HWHT");// 公司唯一标识
			map.put("ts", ts);// 业务时间
			map.put("msgdgt", msgdgt);// 加密的md5
			respCon = MyHttpClient.doMethodGet(method, url, map, params);
			// 返回参数
			System.out.println("返回参数" + respCon);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respCon;
	}

	public static void main(String[] args) {
		String subid = "A4210X10X0143612834-31-1-NHWH-GXI";
		String appkey = "HWHT";
		String appsecret = "hwht_123456";
		V2AxyBDeleteSubid(subid, appkey, appsecret);
	}
}
