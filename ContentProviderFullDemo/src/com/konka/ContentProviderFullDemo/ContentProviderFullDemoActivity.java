package com.konka.ContentProviderFullDemo;

import com.konka.ContentProviderFullDemo.Employees.Employee;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class ContentProviderFullDemoActivity extends Activity implements OnClickListener {
	
	public static String TAG = "ContentProviderFullDemoActivity";
	
	public Uri uri = Employee.CONTENT_URI;
    
	private Button insertData = null;
	private Button queryData = null;
	private Button deleteData = null;
	private Button updateData = null;
	
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);        
        
        insertData = (Button) findViewById(R.id.insertData);
        insertData.setOnClickListener(ContentProviderFullDemoActivity.this);
        
        queryData = (Button) findViewById(R.id.queryData);
        queryData.setOnClickListener(ContentProviderFullDemoActivity.this);
        
        deleteData = (Button) findViewById(R.id.deleteData);
        deleteData.setOnClickListener(ContentProviderFullDemoActivity.this);
        
        updateData = (Button) findViewById(R.id.updateData);
        updateData.setOnClickListener(ContentProviderFullDemoActivity.this);                      
    }
     
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub		
		if(view == insertData){
						
	        ContentValues values = new ContentValues();
	        values.put(Employee.NAME, "amaker");
	        values.put(Employee.GENDER, "male");
	        values.put(Employee.AGE,30);
			
			// 插入
			insert(uri,values);
			Log.i(TAG, "insert");
			
		}else if(view == queryData){
			
			// 查询
			query();
			Log.i(TAG, "query");
			
		}else if(view == deleteData){
			
			//这是删除名字为：amaker的数据的方法：
	        String[] deleteValue = {"amaker"};
			String where = "name";
									
			// 删除
	        del(uri,where,deleteValue);
	        Log.i(TAG, "del");
	        
		}else if(view == updateData){
			
			ContentValues values = new ContentValues();
			values.put(Employee.NAME, "testUpdate");
			values.put(Employee.GENDER, "female");
			values.put(Employee.AGE,39);
						
			String where = "name";
			String[] selectValue = {"amaker"};
			
			// 更新
	        update(uri,values,where,selectValue);
	        Log.i(TAG, "update");
		}	
	}
	
	
	  // 插入
    private void insert(Uri uri, ContentValues values){
        getContentResolver().insert(uri, values);
    }
	
    
    // 查询
    private void query(){
        // 查询列数组
           String[] PROJECTION = new String[] { 
                   Employee._ID,         // 0
                   Employee.NAME,         // 1
                   Employee.GENDER,     // 2
                   Employee.AGE         // 3
        };
        // 查询所有备忘录信息
        Cursor cursor = getContentResolver().query(Employee.CONTENT_URI,PROJECTION, null, null,Employee.DEFAULT_SORT_ORDER);
        //Cursor c = managedQuery(Employee.CONTENT_URI, PROJECTION, null,null, Employee.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            // 遍历游标
            for (int i = 0; i < cursor.getCount(); i++) {
            	cursor.moveToPosition(i);

                String name = cursor.getString(1);
                String gender = cursor.getString(2);
                int age = cursor.getInt(3);

                Log.i(TAG, "db第"+i+"个数据："+"--name:"+name+"--gender:"+gender+"--age:"+age);
            }
        }
        cursor.close();
    }
	
	
	// 删除方法
    private void del(Uri uri,String where, String[] deleteValue){
    	   	
    	/****  删除ID为1的记录的方法：
    	 
        // 删除ID为1的记录
        Uri uri = ContentUris.withAppendedId(Employee.CONTENT_URI, 1);
        // 获得ContentResolver，并删除
        getContentResolver().delete(uri, null, null);
        
        ****/
    	
        getContentResolver().delete(uri, where+"=?", deleteValue);
    }
    
    // 更新
    private void update(Uri uri, ContentValues values, String where, String[] selectValue){
    	
    	/**************************************************************
        // 更新ID为1的记录
    	Uri uri = ContentUris.withAppendedId(Employee.CONTENT_URI, 1);
        
        ContentValues values = new ContentValues();
        // 添加员工信息
        values.put(Employee.NAME, "hz.guo");
        values.put(Employee.GENDER, "male");
        values.put(Employee.AGE,31);
        
        // 获得ContentResolver，并更新
        getContentResolver().update(uri, values, null, null);
        *************************************************************/

        //getContentResolver().update(uri, values, "name"+"=?", selectValue);
    	getContentResolver().update(uri, values, where+"=?", selectValue);
    }
}