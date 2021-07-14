package com.oio.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 */
public class V2SignDigest {

	static String md5(Map<String, String> params, String xhKey) {
		List<String> keys = new ArrayList<String>();
		Set<Entry<String, String>> aSet = params.entrySet();
		Iterator<Entry<String, String>> iter = aSet.iterator();
		while(iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			keys.add(key);
		}		
		Collections.sort(keys, new Comparator<String>() {
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });		
		
		StringBuffer sb = new StringBuffer();
		sb.append( xhKey);
		for(String key : keys) {
			String value = params.get(key);
			if(sb.length() == 0)
				sb.append(key + value);
			else
				sb.append( key  + value);
		}
		String string = sb.toString();
		//Logger.getLogger(CustServiceImpl.class.getName()).info("进行加密的字符串："+string);
		System.out.println("加密的字符串"+string);
		return V2SignDigest.MD5(string);
	}
	
	public static String MD5(String s) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	
	
	
}
