package com.robin.httpcommunicationtest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;
	private Button btn6;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());

		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
				.penaltyLog().penaltyDeath().build());

		setViews();
		setListeners();
	}

	private void setListeners() {
		// 使用android自带的HttpClient get方法进行通信
		btn1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				String info = "123456";

				// String xxx=Des4.mytest("ssssssssssssssss");
				// try{
				String result = null;
				try {
					result = Des4.encode(info);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }catch(Exception e){
				// e.printStackTrace();
				// }
				textView.setText(result);
				// TODO Auto-generated method stub
				// connect("http://116.236.115.125:9000/workdir/ernet/ernet/index.php/Shop4S/YfBusiness/getInsuranceType");
			}
		});

		// 使用android自带的HttpClient post方法进行通信
		btn2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				connectPost("http://116.236.115.122:9009/test.php"); // 测试地址
			}
		});

		// 使用java HttpURLConnection get方法进行通信
		btn3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Log.v("-----","xxxxxxxxxxxx");
				javaHttpGet("http://116.236.115.122:9009/test.php");

			}
		});

		// 使用java HttpURLConnection post方法进行通信
		btn4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				javaHttpPost("http://116.236.115.122:9009/test.php"); // 测试地址
			}
		});
		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						connectPost("http://116.236.115.122:9009/test.php"); // 测试地址
					}
				}).start();

				// Intent intent = new Intent(MainActivity.this,
				// SaveImageActivity.class);
				// startActivity(intent);
			}
		});
		// btn7.setOnClickListener(new View.OnClickListener() {

		// @Override
		// public void onClick(View arg0) {
		// connectPost("http://116.236.115.122:9090/DMB/getTvBoxUpdate"); //
		// 测试地址

		// Intent intent = new Intent(MainActivity.this,
		// SaveImageActivity.class);
		// startActivity(intent);
		// }
		// });
	}

	protected void javaHttpPost(String url) {
		String params;

		try {
			params = "username=" + URLEncoder.encode("hello", "UTF-8")
					+ "&password=" + URLEncoder.encode("eoe", "UTF-8");
			byte[] postData = params.getBytes();
			// 创建一个URL对象
			URL pathUrl = new URL(url);
			// 创建一个HttpURLConnection连接
			HttpURLConnection urlConnect = (HttpURLConnection) pathUrl
					.openConnection();
			// 设置连接超时时间
			urlConnect.setConnectTimeout(10000);
			// post请求必须设置允许输出
			urlConnect.setDoOutput(true);
			// post请求不能使用缓存
			urlConnect.setUseCaches(false);
			// 设置post方式请求
			urlConnect.setRequestMethod("POST");
			urlConnect.setInstanceFollowRedirects(true);
			// 配置请求Content-Type
			urlConnect.setRequestProperty("Content-Type",
					"application/x-www-form-urlencode");
			// 开始连接,注意以上的所有设置必须要在conect之前完成
			urlConnect.connect();
			// 发送请求参数,将OutputStream封装成DataOutputStream
			/*
			 * DataOutputStream dos =(DataOutputStream)
			 * urlConnect.getOutputStream();
			 */
			DataOutputStream dos = new DataOutputStream(
					urlConnect.getOutputStream());
			dos.write(postData);
			dos.flush();
			dos.close();

			if (urlConnect.getResponseCode() == 200) {// 请求成功
				byte[] data = readInputStream(urlConnect.getInputStream());
				String string = new String(data, "UTF-8");
				textView.setText(string);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// read(byte b[ ]):读取b.length个字节的数据放到b数组中。返回值是读取的字节数.这个就是一直读到结尾咯
		while ((len = inStream.read(buffer)) != -1) {
			// write(byte b[ ], int off, int len) ：将参数b的从偏移量off开始的len个字节写到输出流。
			outStream.write(buffer, 0, len);
		}
		// 转化为二进制数据
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	protected void javaHttpGet(String url) {
		URL pathUrl;
		try {
			// 创建一个URL对象
			pathUrl = new URL(url);
			HttpURLConnection urlConnect = (HttpURLConnection) pathUrl
					.openConnection(); // 打开一个HttpURLConnection连接
			urlConnect.setConnectTimeout(10000); // 设置连接超时时间
			urlConnect.connect();

			// 输入流读取字节，再将它们转换成字符.得到读取的内容
			InputStreamReader in = new InputStreamReader(
					urlConnect.getInputStream());
			// BufferedReader:接受Reader对象作为参数，并对其添加字符缓冲器，使用readline()方法可以读取一行
			BufferedReader buffer = new BufferedReader(in);
			String inputLine = null;
			String resultData = null;
			while ((inputLine = buffer.readLine()) != null) {
				// 利用循环来读取数据
				resultData += inputLine;
			}
			textView.setText(resultData);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setViews() {
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);
		textView = (TextView) findViewById(R.id.text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	String str = "";

	private void connectPost(String url) {
		HttpClient httpClient = new DefaultHttpClient(); // 新建HttpClient对象
		HttpPost httpPost = new HttpPost(url); // 新建HttpPost对象
		List<NameValuePair> params = new ArrayList<NameValuePair>(); // 使用NameValuePair来保存要传递的Post参数
		params.add(new BasicNameValuePair("username", "hello")); // 添加要传递的参数
		params.add(new BasicNameValuePair("password", "eoe"));
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8); // 设置字符集
			httpPost.setEntity(entity);// 设置参数实体
			HttpResponse httpResp = httpClient.execute(httpPost);// 获取HttpResponse实例
			if (httpResp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 响应通过
				String result = EntityUtils.toString(httpResp.getEntity(),
						"UTF-8");
				str = result;
				mHandler.sendEmptyMessage(1);
			} else {

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:

				textView.setText(str);

				break;

			default:
				break;
			}
		};
	};

	private void connect(String url) {
		HttpClient httpClient = new DefaultHttpClient(); // 新建HttpClient对象
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000); // 设置连接超时
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000); // 设置数据读取时间超时
		ConnManagerParams.setTimeout(httpClient.getParams(), 10000); // 设置从连接池中取连接超时

		HttpGet httpget = new HttpGet(url); // 获取请求

		try {
			HttpResponse response = httpClient.execute(httpget); // 执行请求，获取响应结果
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 响应通过
				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				textView.setText(result);
			} else {
				// 响应未通过
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
