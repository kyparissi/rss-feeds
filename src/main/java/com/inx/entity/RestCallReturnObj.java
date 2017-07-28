package com.inx.entity;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.ResponseBody;

public class RestCallReturnObj{
	private ResponseBody responseBody;
	private long callTook;
	private Headers headers;
	private boolean succssful;

	public ResponseBody getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(ResponseBody responseBody) {
		this.responseBody = responseBody;
	}
	public long getCallTook() {
		return callTook;
	}
	public void setCallTook(long callTook) {
		this.callTook = callTook;
	}

	public Headers getHeaders() {
		return headers;
	}
	public void setHeaders(Headers headers) {
		this.headers = headers;
	}
	public boolean isSuccssful() {
		return succssful;
	}
	public void setSuccssful(boolean succssful) {
		this.succssful = succssful;
	}
}
