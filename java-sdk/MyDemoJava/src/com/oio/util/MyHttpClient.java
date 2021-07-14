package com.oio.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {
	static CloseableHttpClient httpClient = null;

	public MyHttpClient() {
		httpClient = HttpClients.createDefault();
		// httpClient = HttpClientBuilder.create().build();
	}

	public void close() throws IOException {
		httpClient.close();
	}

	public String doPost(String url) throws URISyntaxException,
			ClientProtocolException, IOException {
		return doPost(url, null);
	}

	public String doPost(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		System.out.println("doPost >> " + url + " " + params);
		String s = doMethodForm("post", url, params);
		System.out.println("doPost << " + s);
		return s;
	}

	public String doPostJson(String url, String jsonStr)
			throws URISyntaxException, ClientProtocolException, IOException {
		return doPostJson(url, null, jsonStr);
	}

	public String doPostJson(String url, Map<String, String> headers,
			String jsonStr) throws URISyntaxException, ClientProtocolException,
			IOException {
		System.out.println("doPostJson >> " + url + " " + headers + " "
				+ jsonStr);
		String s = doMethodJson("post", url, headers, jsonStr);
		System.out.println("doPostJson << " + s);
		return s;
	}

	public String doPut(String url) throws URISyntaxException,
			ClientProtocolException, IOException {
		return doPut(url, null);
	}

	public String doPut(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		System.out.println("doPut >> " + url + " " + params);
		String s = doMethodForm("put", url, params);
		System.out.println("doPut << " + s);
		return s;
	}

	public String doPutJson(String url, String jsonStr)
			throws URISyntaxException, ClientProtocolException, IOException {
		return doPutJson(url, null, jsonStr);
	}

	public String doPutJson(String url, Map<String, String> headers,
			String jsonStr) throws URISyntaxException, ClientProtocolException,
			IOException {
		System.out.println("doPutJson >> " + url + " " + headers + " "
				+ jsonStr);
		String s = doMethodJson("put", url, headers, jsonStr);
		System.out.println("doPutJson << " + s);
		return s;
	}

	public String doGet(String url) throws URISyntaxException,
			ClientProtocolException, IOException {
		return doGet(url, null);
	}

	public String doGet(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		System.out.println("doGet >> " + url + " " + params);
		String s = doMethodGet("get", url, params);
		System.out.println("doGet << " + s);
		return s;
	}

	public String doDelete(String url) throws URISyntaxException,
			ClientProtocolException, IOException {
		return doDelete(url, null);
	}

	public String doDelete(String url, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		System.out.println("doDelete >> " + url + " " + params);
		String s = doMethodGet("delete", url, params);
		System.out.println("doDelete << " + s);
		return s;
	}

	public String doMethodForm(String method, String url,
			Map<String, Object> params) throws URISyntaxException,
			ClientProtocolException, IOException {
		HttpEntityEnclosingRequestBase httpMethod = null;
		if (method.equalsIgnoreCase("put"))
			httpMethod = new HttpPut(url);
		else
			httpMethod = new HttpPost(url);

		if (params != null) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), String
						.valueOf(entry.getValue())));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					parameters, "UTF-8");
			httpMethod.setEntity(formEntity);
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	public static String doMethodJson(String method, String url,
			Map<String, String> headers, String jsonStr)
			throws URISyntaxException, ClientProtocolException, IOException {
		HttpEntityEnclosingRequestBase httpMethod = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		System.out.println();
		if (method.equalsIgnoreCase("put"))
			httpMethod = new HttpPut(url);
		else
			httpMethod = new HttpPost(url);

		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}

		StringEntity entity = new StringEntity(jsonStr, "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpMethod.setEntity(entity);
		System.out.println(httpMethod + "-----------------url");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpMethod);
			System.out.println(response);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	public static String doMethodGet(String method, String url,
			Map<String, Object> params) throws URISyntaxException,
			ClientProtocolException, IOException {
		HttpRequestBase httpMethod = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if (params != null) {
			URIBuilder builder = new URIBuilder(url);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				builder.addParameter(entry.getKey(),
						String.valueOf(entry.getValue()));
			}
			URI uri = builder.build();
			if (method.equalsIgnoreCase("delete"))
				httpMethod = new HttpDelete(uri);
			else
				httpMethod = new HttpGet(uri);
		} else {
			if (method.equalsIgnoreCase("delete"))
				httpMethod = new HttpDelete(url);
			else
				httpMethod = new HttpGet(url);
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	public static String doMethodGet(String method, String url,
			Map<String, String> headers, Map<String, Object> params)
			throws URISyntaxException, ClientProtocolException, IOException {
		HttpRequestBase httpMethod = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		if (params != null) {
			URIBuilder builder = new URIBuilder(url);
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				builder.addParameter(entry.getKey(),
						String.valueOf(entry.getValue()));
			}
			URI uri = builder.build();
			if (method.equalsIgnoreCase("delete"))
				httpMethod = new HttpDelete(uri);
			else
				httpMethod = new HttpGet(uri);
		} else {
			if (method.equalsIgnoreCase("delete"))
				httpMethod = new HttpDelete(url);
			else
				httpMethod = new HttpGet(url);
		}
		if (headers != null) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				httpMethod.setHeader(entry.getKey(), entry.getValue());
			}
		}

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpMethod);
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	public String doGet(String url, Map<String, String> headers,
			Map<String, Object> params) throws URISyntaxException,
			ClientProtocolException, IOException {
		System.out.println("doGet >> " + url + " " + params);
		String s = doMethodGet("get", url, headers, params);
		System.out.println("doGet << " + s);
		return s;
	}

	public String doDelete(String url, Map<String, String> headers,
			Map<String, Object> params) throws URISyntaxException,
			ClientProtocolException, IOException {
		System.out.println("doDelete >> " + url + " " + params);
		String s = doMethodGet("delete", url, headers, params);
		System.out.println("doDelete << " + s);
		return s;
	}
}
