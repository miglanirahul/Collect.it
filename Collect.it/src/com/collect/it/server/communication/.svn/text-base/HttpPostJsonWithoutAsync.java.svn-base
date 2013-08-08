package com.collect.it.server.communication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * This class used to make Http Post and call back server response through inner
 * functions
 */
public class HttpPostJsonWithoutAsync {

	/**
	 * Declare variables
	 */
	final String TAG = "HTTP_POST_JSON_WITHOUT_ASYNC_CLASS";

	/**
	 * Default Constructor definition
	 */
	public HttpPostJsonWithoutAsync() {
		// nothing to do
	}

	/**
	 * Functionality of HTTP POST and return response
	 * 
	 * @param url
	 *            to hit
	 * @param json
	 *            to post
	 * 
	 * @return string response
	 */
	public String httpPostWithJson(String url, JSONObject json) {
		String result = "";
		try {
			HttpPost httppost = new HttpPost(url);

			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 60000);
			HttpConnectionParams.setTcpNoDelay(myParams, true);

			httppost.setHeader("Content-type", "application/json");
			HttpClient httpclient = new DefaultHttpClient(myParams);

			StringEntity se = new StringEntity(json.toString(), HTTP.UTF_8);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(se);

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
		}
		return result;
	}
}
