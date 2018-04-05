package com.example.testlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager.OnCancelListener;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	private List<HashMap<String, Object>> listItem;
	private List<HashMap<String, Object>> listItemTemp;
	
	private MyAdapter myAdapter;//自定义适配器
	private ListView listview;
	private Button button;
	private ParseWeatherJsonUtil parseWeatherJsonUtil;//解析json数据的工具类
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init()	;
		initListView(listItem);
		 startTask();
		
	}

	void startTask()	{
		String url = "http://www.weather.com.cn/data/sk/101010100.html";
		new MyTask().execute(url);
	}
	
	private void init()	{
		listItem = new ArrayList<HashMap<String,Object>>();
		listItemTemp = new ArrayList<HashMap<String,Object>>();
		parseWeatherJsonUtil = new ParseWeatherJsonUtil(this);
		button =(Button)findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startTask();
				
			}
		});
	}
	
	
	
	public void initListView(final List<HashMap<String,Object>> listItem )	{
		
		listview = (ListView)findViewById(R.id.listview);
	         //生成适配器的Item和动态数组对应的元素
		myAdapter = new MyAdapter(MainActivity.this , listItem, R.layout.list_item);
	         //添加适配器
		listview.setAdapter(myAdapter);
	 
	         //添加点击
		listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position,
                    long arg3) {
            
            	HashMap<String, Object> data = (HashMap<String, Object>) parent
 						.getItemAtPosition(position);

            }
        });
		
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this).setTitle("删除第"+position+"条记录?")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						myAdapter.deleteViewByPosition(position);
						
					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return;
					}
				}).show();
				return false;
			}
		});
    }
	
	
	//从网络加载数据的异步任务类
	private class MyTask extends AsyncTask<String, Void, String> {

		protected void onPostExecute(String status) {
			// TODO Auto-generated method stub
			super.onPostExecute(status);

			//把保存起来的临时数据添加到listItem,注意不能直接 listItem=listItemTemp;
			//因为这样原来的数据源会指向了另外一个地址，会导致notifyDataSetChanged()不起作用
			for(int i=0;i<listItemTemp.size();i++)	{
				listItem.add(listItemTemp.get(i));
			}
			
			
			if(status.equals("success"))	{
				if(listItemTemp.size()>0)	{
					//数据一旦发生变化，马上提醒listview更新它的界面，否则会报错
					myAdapter.notifyDataSetChanged();
					
				}
			}
			
			
		} 
		@Override
		protected String doInBackground(String... params) {
			String url = params[0];
			try {
				String  jsonStr = ReadJsonFeedUtils.readJSONFeed(url);
				
				//先用一个缓存的list把数据保存起来
				listItemTemp= (ArrayList<HashMap<String, Object>>)parseWeatherJsonUtil.parseJosn(jsonStr);
				if(listItemTemp.size()>0)	{
					return "success";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return "fail";
			}
				return "fail";
		}
		
	}
	

}
