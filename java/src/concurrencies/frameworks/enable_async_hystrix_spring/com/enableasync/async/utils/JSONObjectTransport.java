package com.enableasync.async.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * java concurrency
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class JSONObjectTransport {

	/**
	 * Post request to server with the requestObject payload and 
	 * return the repose as json object
	 *
	 * @param urlString the url string
	 * @param requestObject the request object
	 * @return the JSON object
	 * @throws TrasportException the trasport exception
	 * @throws JSONException 
	 */
	public static JSONObject postRequestExec(String urlString, JSONObject requestObject) throws TrasportException, JSONException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(urlString);
		System.out.println("in post in dash"+requestObject);

		StringEntity entiry = null;
		try {
			entiry = new StringEntity(requestObject.toString());
		} catch (UnsupportedEncodingException e) {
			throw new TrasportException("json object has unsuported encoding", e);
		}
		entiry.setContentType("application/json");
		post.setEntity(entiry);
		HttpResponse response = null;
		try {
			response = client.execute(post);			
		} catch (ClientProtocolException e) {
			throw new TrasportException("post request error ClientProtocolException", e);
		} catch (IOException e) {
			throw new TrasportException("post request error IOException", e);
		}
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new TrasportException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode(), null);
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			throw new TrasportException("could not get respoce stream: UnsupportedOperationException", e);
		} catch (IOException e) {
			throw new TrasportException("could not get respoce stream: IOException", e);
		}
		String output;
		StringBuffer buffer = new StringBuffer();
		try {
			while ((output = br.readLine()) != null) {
				buffer.append(output);
			}
		} catch (IOException e) {
			throw new TrasportException("error reading data from server: IOException", e);
		}
		return new JSONObject(buffer.toString());
	}

	/**
	 * Gets the request to server and return the json response from the server
	 *
	 * @param url the url
	 * @return the request exec
	 * @throws TrasportException the trasport exception
	 * @throws JSONException 
	 */
	public static JSONObject getRequestExec(String url) throws TrasportException, JSONException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e) {
			throw new TrasportException("could not get respoce stream: ClientProtocolException", e);
		} catch (IOException e) {
			throw new TrasportException("could not get respoce stream: IOException", e);
		}
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			throw new TrasportException("could not get respoce stream: UnsupportedOperationException", e);
		} catch (IOException e) {
			throw new TrasportException("could not get respoce stream: IOException", e);
		}
		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		} catch (IOException e) {
			throw new TrasportException("error reading data from server: IOException", e);
		}

		return new JSONObject(result.toString());

	}

}
