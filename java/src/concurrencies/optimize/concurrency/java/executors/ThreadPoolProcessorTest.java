package concurrency.java.executors;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class ThreadPoolProcessorTest {

	private static HttpContext localContext = new BasicHttpContext();
	private static HttpClientContext context = HttpClientContext
			.adapt(localContext);

	public static void main(String[] args) throws Exception {
		// testPost();
		testGet();
		// HttpClientTest test = new HttpClientTest();
		// test.testConfig();
		// test.zan();
	}

	private void zan() {
		String url = "https://api.jinse.com/v4/live/zan";
		ThreadPoolProcessor pool = ThreadPoolProcessor.getInstanceFixed(20);
		for (int i = 1; i < 20; i++) {
			MsgSenderPost msgSender = new MsgSenderPost(url);
			pool.execute(msgSender);
		}
		System.out.println("Finish~~~");

	}

	private void testConfig() {
		// String url = "http://www.baidu.com";
		String url = "http://192.168.2.127:8888/api/simulation";
		ThreadPoolProcessor pool = ThreadPoolProcessor.getInstanceFixed(20);
		for (int i = 1; i < 200; i++) {
			MsgSender msgSender = new MsgSender(url);
			pool.execute(msgSender);
		}
		System.out.println("Finish~~~");

	}

	private void testGetNew() {
		String url = "https://www.toutiao.com/i6496332811849433614/?tt_from=weixin&utm_campaign=client_share&app=news_article&utm_source=weixin&iid=18935583673&utm_medium=toutiao_ios&wxshare_count=";
		ThreadPoolProcessor pool = ThreadPoolProcessor.getInstanceFixed(20);
		for (int i = 1; i < 100; i++) {
			url = url + ++i;
			MsgSender msgSender = new MsgSender(url);
			pool.execute(msgSender);
		}
		System.out.println("Finish~~~");

	}

	class MsgSender implements Runnable {

		private String url;

		public MsgSender(String url) {
			super();
			this.url = url;
		}

		@Override
		public void run() {
			System.out.println(HttpClientUtilPool.get(url));
		}
	}

	class MsgSenderPost implements Runnable {

		private String url;

		public MsgSenderPost(String url) {
			super();
			this.url = url;
		}

		@Override
		public void run() {
			try {
				System.out.println(HttpClientUtilPool.post(url, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void testPost() throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 模拟表单
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(
					"download",
					"http://xxx.xxx.xxx/xxxx/FileDownServlet?filename=808080001000116_20090610_20090611031159.txt"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,
					"UTF-8");
			HttpPost httpPost = new HttpPost(
					"http://127.0.0.1:8887/path-cp/notice/chkfdown");
			httpPost.setEntity(entity);

			// 将HttpClientContext传入execute()中
			CloseableHttpResponse response = httpClient.execute(httpPost,
					context);

			try {
				HttpEntity responseEntity = response.getEntity();
				System.out.println("=================:"
						+ EntityUtils.toString(responseEntity));

			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
	}

	private static void testGet() throws Exception {
		CloseableHttpClient httpClient2 = HttpClients.createDefault();

		try {
			HttpGet httpGet = new HttpGet(
					"http://192.168.2.20:8887/path-cp/notice/chkfdown?download=http://xxx.xxx.xxx/xxxx/FileDownServlet?filename=808080001000116_20090610_20090611031159.txt");

			// 设置相同的HttpClientContext
			CloseableHttpResponse response = httpClient2.execute(httpGet,
					context);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("=================:"
						+ EntityUtils.toString(entity));
			} finally {
				response.close();
			}
		} finally {
			httpClient2.close();
		}
	}

}