package com.collect.it.server.communication;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.collect.it.R;
import com.collect.it.interfaces.OnWebServiceProcess;
import com.collect.it.utils.UtilityMethods;

import android.content.Context;
import android.os.AsyncTask;

/**
 * HTTP-POST used with worker thread to hit server and provide response to
 * associated class(s)
 */
public class WebServiceAsyncHttpPostJson extends AsyncTask<Void, Void, Void> {
	/**
	 * Declare variables
	 */
	HttpResponse response;
	OnWebServiceProcess interfaceListener = null;

	Context context;

	String getUrl = null;
	String getResult = null;
	String TAG = "WEB_SERVICE_ASYNC_HTTP-POST";
	String exception = "";
	int receivedId;
	boolean isSuccess;

	JSONObject jsonObject;

	/**
	 * Constructor Definition
	 * 
	 * @param Activity
	 *            context
	 * @param web
	 *            service url
	 * @param activity
	 *            /process/task id
	 * @param OnWebServiceProcess
	 *            listener
	 * @param Json
	 *            object to post
	 */
	public WebServiceAsyncHttpPostJson(Context context, String url, int id,
			OnWebServiceProcess listener, JSONObject json) {
		// set the global values for the constructor parameters
		this.interfaceListener = listener;
		this.getUrl = url;
		this.context = context;
		this.receivedId = id;
		this.jsonObject = json;

	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		// send result to associated class/activity
		if (interfaceListener != null) {
			interfaceListener.getServerValues(getResult, receivedId, isSuccess,
					exception);
		} else {
			interfaceListener.setServerError(receivedId,
					"Error in interface attached " + exception);
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			/**
			 * check if internet is connected then call web server to fetch
			 * requested data
			 */
			if (UtilityMethods.isInternetConnected(context)) {
				// hit server to fetch response
				hitServer();
			} else {
				if (context != null) {
					exception = context.getResources().getString(
							R.string.connection_error_internet);
				} else {
					exception = "Connection error";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			exception = "Connection error";

		}
		return null;
	}

	/**
	 * This method hits the server for response
	 */
	public void hitServer() {

		try {
			HttpPost httppost = new HttpPost(getUrl.trim());

			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 60 * 1000);

			httppost.setHeader("Content-type", "application/json");
			HttpClient httpclient = new DefaultHttpClient(myParams);
			StringEntity se;
			if (jsonObject.toString() == null) {
				se = new StringEntity("", HTTP.UTF_8);
			} else {
				se = new StringEntity(jsonObject.toString(), HTTP.UTF_8);
			}
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			httppost.setEntity(se);

			response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				getResult = EntityUtils.toString(entity);
			}

			isSuccess = true;

		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			exception = "unknownhost";

		} catch (ClientProtocolException e) {
			e.printStackTrace();
			exception = "clientprotocol";

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			exception = "connectiontimeout";
		} catch (NullPointerException e) {
			e.printStackTrace();
			exception = "nullpointer";

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			exception = "sockettimeout";

		} catch (Exception e) {
			e.printStackTrace();
			exception = e.toString();

		} finally {
			// nothing to do
		}
	}
}
