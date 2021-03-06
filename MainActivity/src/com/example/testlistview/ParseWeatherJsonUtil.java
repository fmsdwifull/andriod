package com.example.testlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

/*
 * 用于解析json数据的工具类
 */
public class ParseWeatherJsonUtil {
	
	private  List<HashMap<String, Object>> listItem;
	private Context context;
	public ParseWeatherJsonUtil(Context context) {
		this.context =context;
		listItem =new ArrayList<HashMap<String,Object>>();
	}

	public  List<HashMap<String, Object>> parseJosn(String strJson)	{
		
		try {
			
			JSONArray JsonArray = new JSONArray("["+strJson+"]");
			listItem.clear();
			// ---listItem---print out the content of the json feed-----
			for (int i = 0; i < JsonArray.length(); i++) {
				   JSONObject jsonObject = JsonArray.getJSONObject(i);
					String item = jsonObject.getString("weatherinfo");
					JSONArray itemJsonArray = new JSONArray("["+item+"]");
					for(int j = 0; j < itemJsonArray.length(); j++)	{
						JSONObject itmeJsonObject = itemJsonArray.getJSONObject(j);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("city", itmeJsonObject.getString("city"));
						map.put("temp", itmeJsonObject.getString("temp"));
						map.put("WD", itmeJsonObject.getString("WD"));
						map.put("WS", itmeJsonObject.getString("WS"));
						map.put("SD", itmeJsonObject.getString("SD"));
						map.put("WSE", itmeJsonObject.getString("WSE"));
						map.put("time", itmeJsonObject.getString("time"));
						map.put("isRadar", itmeJsonObject.getString("isRadar"));
						map.put("Radar", itmeJsonObject.getString("Radar"));
						listItem.add(map);
					}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		
		}
		return listItem;
	}
	
	
}
