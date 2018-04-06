package com.example.remotelogin;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.ListActivity;


public class ListImageActivity extends ListActivity {
	//private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //setListAdapter(aa);
		setListAdapter(new  MyAdapter());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MyAdapter extends BaseAdapter{
		String[] items = {"Android手机","Andriod 苹果","Andriod平板","Andriod电视"};

        LayoutInflater inflater;
        MyAdapter()
        {
        	inflater = LayoutInflater.from(ListImageActivity.this);
        }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v=inflater.inflate(R.layout.listview_item,null);
			ImageView iv=(ImageView) v.findViewById(R.id.imageView1);
			TextView  tv=(TextView) v.findViewById(R.id.textView1);
			tv.setText(items[position]);
			iv.setImageResource(R.drawable.ic_launcher);
			return v;
		}
		
	};
}
