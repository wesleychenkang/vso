package com.vsoyou.sdk.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.AsyncTask;

import com.vsoyou.sdk.compents.progress.ProgressView;
import com.vsoyou.sdk.main.Constants;
import com.vsoyou.sdk.main.PayManager;
import com.vsoyou.sdk.util.ConfigUtil;
import com.vsoyou.sdk.util.LogUtil;
import com.vsoyou.sdk.util.StringUtil;

public class HttpRequest<T> extends AsyncTask<String, Integer, T> {

	public static final String TAG = "HttpRequest";

	private Context context;
	private ProgressView progressView;
	private ResponseParser<T> responseParser;
	private HttpCallback<T> callBack;

	private int errorCode;
	private String errorMessage;

	public HttpRequest(Context context, ProgressView progressView,
			ResponseParser<T> responseParser, HttpCallback<T> callBack) {
		this.context = context;
		this.progressView = progressView;
		this.responseParser = responseParser;
		this.callBack = callBack;
	}

	@Override
	protected void onPreExecute() {
		if (progressView != null)
			progressView.show();
	}

	@Override
	protected T doInBackground(String... strings) {

		if (strings.length == 0) {
			errorCode = -1;
			errorMessage = "参数错误！";
			return null;
		}

		try {

			String url = strings[0];
			String postData = strings.length == 2 ? strings[1] : null;

			HttpClient client = new DefaultHttpClient();
			setProxyIfNecessary(context, client);
			HttpParams localHttpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(localHttpParams, 30000);
			HttpConnectionParams.setSoTimeout(localHttpParams, 30000);
			HttpClientParams.setRedirecting(localHttpParams, false);

			HttpPost post = new HttpPost(url);
			post.setHeader("Accept", "*/*");
			post.setHeader("Accept-Charset", "UTF-8,*;q=0.5");
			post.setHeader("Accept-Encoding", "gzip,deflate");
			post.setHeader("Accept-Language", "zh-CN");
			post.setHeader("User-Agent", "VSOYOUSDK");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("appId", ""
					+ ConfigUtil.getAppID(context)));
			params.add(new BasicNameValuePair("sdkCode", ""
					+ Constants.SDK_CODE));
			params.add(new BasicNameValuePair("param", postData));
			try {
				post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}

			// //设置post的数据
			// if (postData != null) {
			// HttpEntity postEntify = new
			// ByteArrayEntity(postData.getBytes("UTF-8"));
			// LogUtil.i(TAG, "postEntify-->" +
			// postEntify.getContent().toString());
			// post.setEntity(postEntify);
			// }
			LogUtil.i(TAG, url);
			LogUtil.i(TAG, postData);
			HttpResponse response = client.execute(post);
			LogUtil.i(TAG, "response.getStatusLine().getStatusCode()-->"
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity entity = response.getEntity();
				final Header encoding = entity.getContentEncoding();
				if (encoding != null) {
					for (HeaderElement element : encoding.getElements()) {
						if (element.getName().equalsIgnoreCase("gzip")) {
							entity = new InflatingEntity(response.getEntity());
							break;
						}
					}
				}

				String content = EntityUtils.toString(entity);
				LogUtil.i(TAG, content);
				if (!StringUtil.isEmpty(content)) {
					if (responseParser != null) {
						return responseParser.getResponse(context, content);
					}
				} else {
					return null;
				}

			}

		} catch (Exception ex) {
			errorCode = -1;
			errorMessage = "网络错误，网络不给力";
		}
		return null;

	}

	@Override
	protected void onPostExecute(T response) {
		if (progressView != null)
			progressView.close();
		LogUtil.i(TAG, "response-->" + response);
		LogUtil.i(TAG, "callBack-->" + callBack);
		if (response != null && callBack != null) {
			callBack.onSuccess(response);
		} else {
			if (callBack != null) {
				callBack.onFailure(errorCode, errorMessage);
			}
		}

	}

	private static void setProxyIfNecessary(Context context, HttpClient client) {

		NetworkInfo localNetworkInfo = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())
				&& (localNetworkInfo.getType() == 0)) {
			String host = Proxy.getDefaultHost();
			int port = Proxy.getDefaultPort();
			if ((host != null) && (port >= 0)) {
				HttpHost httpHost = new HttpHost(host, port);
				client.getParams().setParameter("http.route.default-proxy",
						httpHost);
			}
		}

	}

}
