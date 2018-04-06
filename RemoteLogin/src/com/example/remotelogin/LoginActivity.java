package com.example.remotelogin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {
	
    private EditText id_login;
    private EditText password_login;
    private ImageView avatar_login;
    private CheckBox rememberpassword_login;
    private CheckBox auto_login;
    private Button button_login;
    private SharedPreferences sp;
    private String idvalue;
    private String passwordvalue;
    private static final int PASSWORD_MIWEN = 0x81;	
    private String url="http://116.236.115.125:9000/mytest.php";//�������ӿڵ�ַ
    private TextView result;//���������ؽ��
    

    //Handler���ڽ��շ���˷��ص����ݸ���ui
    private Handler hanlder=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String qq= (String) msg.obj;
                    result.setText("����������: kkk" + qq);

                    break;
            }
            super.handleMessage(msg);
        }
    };    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);		
		
        //�ҵ���Ӧ�Ĳ��ּ��ؼ�
        setContentView(R.layout.activity_login);
        id_login=(EditText) findViewById(R.id.login_id);
        password_login=(EditText) findViewById(R.id.login_password);
        avatar_login=(ImageView) findViewById(R.id.login_avatar);
        rememberpassword_login=(CheckBox) findViewById(R.id.login_rememberpassword);
        auto_login=(CheckBox) findViewById(R.id.login_autologin);
        button_login=(Button) findViewById(R.id.login_button);		
        result= (TextView) findViewById(R.id.result);

        //result.setText("����������: kkk" + "kkkkk");


        if (sp.getBoolean("ischeck",false))
        {
            rememberpassword_login.setChecked(true);
            id_login.setText(sp.getString("PHONEEDIT",""));
            password_login.setText(sp.getString("PASSWORD",""));
            //��������
            password_login.setInputType(PASSWORD_MIWEN);
            if (sp.getBoolean("auto_ischeck",false)){
                auto_login.setChecked(true);
                Intent intent = new Intent(LoginActivity.this,ListImageActivity.class);
                startActivity(intent);
            }
        }        
        
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_login.getPaint().setFlags(0);
                idvalue=id_login.getText().toString();
                password_login.getPaint().setFlags(0);
                passwordvalue=password_login.getText().toString();

                /*
                if (idvalue.equals("18428377130")&&passwordvalue.equals("1234567890")){
                    if (rememberpassword_login.isChecked()){
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString("PHONEEDIT",idvalue);
                        editor.putString("PASSWORD",passwordvalue);
                        editor.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this,ListActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "�ֻ������������������µ�¼", Toast.LENGTH_SHORT).show();
                }
                */
                /**
                 * ����һ�����̷߳������磬������׳��쳣
                 */
                Log.i("xxx","000ko");

                 new Thread() 
                 {
                     @Override
                     public void run() {
                         String name1=id_login.getText().toString().trim();
                         String pwd1=password_login.getText().toString().trim();

                         NameValuePair pair1 = new BasicNameValuePair("name", name1);
                         NameValuePair pair2 = new BasicNameValuePair("password", pwd1);
                         List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                         pairList.add(pair1);
                         pairList.add(pair2);
                         try
                         {
                             HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList,
                                     HTTP.UTF_8);
                             // URl�ǽӿڵ�ַ
                             HttpPost httpPost = new HttpPost(url);
                             Log.d("xxx",url);
                             System.out.println("kkkkkkkkkkkkkkkkkkkk");

                             // �����������ݼ���������
                             httpPost.setEntity(requestHttpEntity);
                             // ��Ҫ�ͻ��˶�������������
                             HttpClient httpClient = new DefaultHttpClient();
                             // ��������
                             HttpResponse response = httpClient.execute(httpPost);
                             // ��ʾ��Ӧ
                             showResponseResult(response);
                             
                         	int id=10;  
                        	Intent intent = new Intent();
                        	intent.setClass(LoginActivity.this, ListImageActivity.class);
                        	//intent.putExtra("id", id+"");             	
                        	startActivity(intent);                               
                         }
                         catch (Exception e)
                         {
                             e.printStackTrace();
                         }
                     }
                 }.start();            
            }
        }); 
        
        rememberpassword_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rememberpassword_login.isChecked()){
                    System.out.println("��ס������ѡ��");
                    sp.edit().putBoolean("ischeck",true).commit();
                }
                else {
                    System.out.println("��ס����û��ѡ��");
                    sp.edit().putBoolean("ischeck",false).commit();
                }
            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()){
                    System.out.println("�Զ���¼��ѡ��");
                    sp.edit().putBoolean("auto_ischeck",true).commit();
                }else {
                    System.out.println("�Զ���¼û��ѡ��");
                    sp.edit().putBoolean("auto_ischeck",false).commit();
                }
            }
        });        
	}
	
	
	private void showResponseResult(HttpResponse response)
    {
        if (null == response)
        {
            return;
        }

        HttpEntity httpEntity = response.getEntity();
        try
        {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String result1 = "";
            String line = "";
            while (null != (line = reader.readLine()))
            {
                result1 += line;

            }
            Log.i("xxxxxxxxxxxx",result1);
            System.out.println(result1);
            /**
             * �ѷ��������صĽ�� ���͵�hanlder�У������߳����ǲ���������ui��
             */
            hanlder.obtainMessage(0,result1).sendToTarget();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
}
