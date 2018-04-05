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
		// ʹ��android�Դ���HttpClient get��������ͨ��
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

		// ʹ��android�Դ���HttpClient post��������ͨ��
		btn2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				connectPost("http://116.236.115.122:9009/test.php"); // ���Ե�ַ
			}
		});

		// ʹ��java HttpURLConnection get��������ͨ��
		btn3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Log.v("-----","xxxxxxxxxxxx");
				javaHttpGet("http://116.236.115.122:9009/test.php");

			}
		});

		// ʹ��java HttpURLConnection post��������ͨ��
		btn4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				javaHttpPost("http://116.236.115.122:9009/test.php"); // ���Ե�ַ
			}
		});
		btn5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						connectPost("http://116.236.115.122:9009/test.php"); // ���Ե�ַ
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
		// ���Ե�ַ

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
			// ����һ��URL����
			URL pathUrl = new URL(url);
			// ����һ��HttpURLConnection����
			HttpURLConnection urlConnect = (HttpURLConnection) pathUrl
					.openConnection();
			// �������ӳ�ʱʱ��
			urlConnect.setConnectTimeout(10000);
			// post������������������
			urlConnect.setDoOutput(true);
			// post������ʹ�û���
			urlConnect.setUseCaches(false);
			// ����post��ʽ����
			urlConnect.setRequestMethod("POST");
			urlConnect.setInstanceFollowRedirects(true);
			// ��������Content-Type
			urlConnect.setRequestProperty("Content-Type",
					"application/x-www-form-urlencode");
			// ��ʼ����,ע�����ϵ��������ñ���Ҫ��conect֮ǰ���
			urlConnect.connect();
			// �����������,��OutputStream��װ��DataOutputStream
			/*
			 * DataOutputStream dos =(DataOutputStream)
			 * urlConnect.getOutputStream();
			 */
			DataOutputStream dos = new DataOutputStream(
					urlConnect.getOutputStream());
			dos.write(postData);
			dos.flush();
			dos.close();

			if (urlConnect.getResponseCode() == 200) {// ����ɹ�
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
		// read(byte b[ ]):��ȡb.length���ֽڵ����ݷŵ�b�����С�����ֵ�Ƕ�ȡ���ֽ���.�������һֱ������β��
		while ((len = inStream.read(buffer)) != -1) {
			// write(byte b[ ], int off, int len) ��������b�Ĵ�ƫ����off��ʼ��len���ֽ�д���������
			outStream.write(buffer, 0, len);
		}
		// ת��Ϊ����������
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	protected void javaHttpGet(String url) {
		URL pathUrl;
		try {
			// ����һ��URL����
			pathUrl = new URL(url);
			HttpURLConnection urlConnect = (HttpURLConnection) pathUrl
					.openConnection(); // ��һ��HttpURLConnection����
			urlConnect.setConnectTimeout(10000); // �������ӳ�ʱʱ��
			urlConnect.connect();

			// ��������ȡ�ֽڣ��ٽ�����ת�����ַ�.�õ���ȡ������
			InputStreamReader in = new InputStreamReader(
					urlConnect.getInputStream());
			// BufferedReader:����Reader������Ϊ����������������ַ���������ʹ��readline()�������Զ�ȡһ��
			BufferedReader buffer = new BufferedReader(in);
			String inputLine = null;
			String resultData = null;
			while ((inputLine = buffer.readLine()) != null) {
				// ����ѭ������ȡ����
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
		HttpClient httpClient = new DefaultHttpClient(); // �½�HttpClient����
		HttpPost httpPost = new HttpPost(url); // �½�HttpPost����
		List<NameValuePair> params = new ArrayList<NameValuePair>(); // ʹ��NameValuePair������Ҫ���ݵ�Post����
		params.add(new BasicNameValuePair("username", "hello")); // ���Ҫ���ݵĲ���
		params.add(new BasicNameValuePair("password", "eoe"));
		try {
			HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8); // �����ַ���
			httpPost.setEntity(entity);// ���ò���ʵ��
			HttpResponse httpResp = httpClient.execute(httpPost);// ��ȡHttpResponseʵ��
			if (httpResp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// ��Ӧͨ��
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
		HttpClient httpClient = new DefaultHttpClient(); // �½�HttpClient����
		HttpConnectionParams
				.setConnectionTimeout(httpClient.getParams(), 10000); // �������ӳ�ʱ
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000); // �������ݶ�ȡʱ�䳬ʱ
		ConnManagerParams.setTimeout(httpClient.getParams(), 10000); // ���ô����ӳ���ȡ���ӳ�ʱ

		HttpGet httpget = new HttpGet(url); // ��ȡ����

		try {
			HttpResponse response = httpClient.execute(httpget); // ִ�����󣬻�ȡ��Ӧ���
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // ��Ӧͨ��
				String result = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				textView.setText(result);
			} else {
				// ��Ӧδͨ��
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
