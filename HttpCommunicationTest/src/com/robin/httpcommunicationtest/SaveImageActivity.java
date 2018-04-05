package com.robin.httpcommunicationtest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SaveImageActivity extends Activity {

	private static final String TAG = "SaveImageActivity";
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory() + "/download_test/";
	private ImageView mImageView;
	private Button mBtnSave;
	private ProgressDialog mSaveDialog = null;
	private Bitmap mBitmap;
	private String mFileName;
	private String mSaveMessage;
	private Thread connectThread;
	private Thread saveThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_download);

		mImageView = (ImageView) findViewById(R.id.imgSource);
		mBtnSave = (Button) findViewById(R.id.btnSave);

		connectThread = new Thread(connectNet);
		connectThread.start();

		mBtnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mSaveDialog = ProgressDialog.show(SaveImageActivity.this,
						"保存图片", "图片正在保存，请稍后...", true);
				saveThread = new Thread(saveFileRunnable);
				saveThread.start();
			}
		});
	}

	private Runnable connectNet = new Runnable() {

		@Override
		public void run() {
			try {
				String filePath = "http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
				mFileName = "robin.jpg";
				// 取得的是inputstream，直接从inputstream生成bitmap
				mBitmap = BitmapFactory.decodeStream(getImageStream(filePath));
				// 发送消息，通知handler在主线程中更新ui
				connectHanlder.sendEmptyMessage(0);
				Log.d(TAG, "set image ...");
			} catch (Exception e) {
				Toast.makeText(SaveImageActivity.this, "无法链接网络！", 1).show();
				e.printStackTrace();
			}

		}
	};
	private Handler connectHanlder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "display image");
			// 更新UI，显示图片
			if (mBitmap != null) {
				mImageView.setImageBitmap(mBitmap);// display image
			}
		}
	};
	private Runnable saveFileRunnable = new Runnable() {

		@Override
		public void run() {
			try {
				saveFile(mBitmap, mFileName);
				mSaveMessage = "图片保存成功！";
			} catch (Exception e) {
				mSaveMessage = "图片保存失败！";
				e.printStackTrace();
			}
			messageHandler.sendMessage(messageHandler.obtainMessage());
		}
	};

	private Handler messageHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mSaveDialog.dismiss();
			Log.d(TAG, mSaveMessage);
			Toast.makeText(SaveImageActivity.this, mSaveMessage,
					Toast.LENGTH_SHORT).show();
		}
	};

	/*
	 * 从网络获取图片
	 */
	protected InputStream getImageStream(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(10 * 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return conn.getInputStream();
		}
		return null;
	}

	/*
	 * 保存文件
	 */
	protected void saveFile(Bitmap bm, String fileName) throws IOException {
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		bos.flush();
		bos.close();
	}
}
