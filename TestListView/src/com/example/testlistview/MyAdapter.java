package com.example.testlistview;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class MyAdapter extends BaseAdapter {

	
	
	
	private List<HashMap<String, Object>> listItem;
	private int resource;
	private LayoutInflater inflater;
	private Context context;
	
	
	
	public MyAdapter(Context context,
			List<HashMap<String, Object>> listItem, int resource) {
		this.context = context;
		this.listItem = listItem;
		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return listItem.size();
		
	}

	@Override
	public Object getItem(int position) {
		return listItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		
		//为控件添加一个缓存ViewCache
		TextView textView01 = null;
		if (convertView == null) {
			convertView = inflater.inflate(resource, null);
			textView01 = (TextView) convertView
					.findViewById(R.id.textView01);

			ViewCache cache = new ViewCache();
			cache.textView01 = textView01;
			convertView.setTag(cache);
		} else {
			ViewCache cache = (ViewCache) convertView.getTag();
			textView01 = cache.textView01;
		}
		
		
		HashMap<String, Object> map = listItem.get(position); //得到相应条目的值
		
		//绑定到textView01中
		textView01.setText("city:" + map.get("city") +   "\ncityid:"+ map.get("cityid")+ "	temp:"+map.get("temp") 
				+ "\nWD:"+map.get("WD")
				+ "\nWS:"+map.get("WS")
				+ "\nSD:"+map.get("SD")
				+ "\nWSE:"+map.get("WSE")
				+ "\ntime:"+map.get("time")
				+ "\nisRadar:"+map.get("isRadar")
				+ "\nRadar:"+map.get("Radar"));
		return convertView;
	}

	private final class ViewCache {
		public TextView textView01;
	}

//用于删除对应条目
	public void deleteViewByPosition(int index)	{
		listItem.remove(index);
		notifyDataSetChanged();
	}
	
	
}
