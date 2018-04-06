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
			
			// ����
			insert(uri,values);
			Log.i(TAG, "insert");
			
		}else if(view == queryData){
			
			// ��ѯ
			query();
			Log.i(TAG, "query");
			
		}else if(view == deleteData){
			
			//����ɾ������Ϊ��amaker�����ݵķ�����
	        String[] deleteValue = {"amaker"};
			String where = "name";
									
			// ɾ��
	        del(uri,where,deleteValue);
	        Log.i(TAG, "del");
	        
		}else if(view == updateData){
			
			ContentValues values = new ContentValues();
			values.put(Employee.NAME, "testUpdate");
			values.put(Employee.GENDER, "female");
			values.put(Employee.AGE,39);
						
			String where = "name";
			String[] selectValue = {"amaker"};
			
			// ����
	        update(uri,values,where,selectValue);
	        Log.i(TAG, "update");
		}	
	}
	
	
	  // ����
    private void insert(Uri uri, ContentValues values){
        getContentResolver().insert(uri, values);
    }
	
    
    // ��ѯ
    private void query(){
        // ��ѯ������
           String[] PROJECTION = new String[] { 
                   Employee._ID,         // 0
                   Employee.NAME,         // 1
                   Employee.GENDER,     // 2
                   Employee.AGE         // 3
        };
        // ��ѯ���б���¼��Ϣ
        Cursor cursor = getContentResolver().query(Employee.CONTENT_URI,PROJECTION, null, null,Employee.DEFAULT_SORT_ORDER);
        //Cursor c = managedQuery(Employee.CONTENT_URI, PROJECTION, null,null, Employee.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            // �����α�
            for (int i = 0; i < cursor.getCount(); i++) {
            	cursor.moveToPosition(i);

                String name = cursor.getString(1);
                String gender = cursor.getString(2);
                int age = cursor.getInt(3);

                Log.i(TAG, "db��"+i+"�����ݣ�"+"--name:"+name+"--gender:"+gender+"--age:"+age);
            }
        }
        cursor.close();
    }
	
	
	// ɾ������
    private void del(Uri uri,String where, String[] deleteValue){
    	   	
    	/****  ɾ��IDΪ1�ļ�¼�ķ�����
    	 
        // ɾ��IDΪ1�ļ�¼
        Uri uri = ContentUris.withAppendedId(Employee.CONTENT_URI, 1);
        // ���ContentResolver����ɾ��
        getContentResolver().delete(uri, null, null);
        
        ****/
    	
        getContentResolver().delete(uri, where+"=?", deleteValue);
    }
    
    // ����
    private void update(Uri uri, ContentValues values, String where, String[] selectValue){
    	
    	/**************************************************************
        // ����IDΪ1�ļ�¼
    	Uri uri = ContentUris.withAppendedId(Employee.CONTENT_URI, 1);
        
        ContentValues values = new ContentValues();
        // ���Ա����Ϣ
        values.put(Employee.NAME, "hz.guo");
        values.put(Employee.GENDER, "male");
        values.put(Employee.AGE,31);
        
        // ���ContentResolver��������
        getContentResolver().update(uri, values, null, null);
        *************************************************************/

        //getContentResolver().update(uri, values, "name"+"=?", selectValue);
    	getContentResolver().update(uri, values, where+"=?", selectValue);
    }
}