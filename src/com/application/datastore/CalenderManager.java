package com.application.datastore;

import java.util.HashMap;

public class CalenderManager {
	
	private HashMap<String, String> dataMap=new HashMap<String, String>();
	
	private CalenderManager(){
		
	}
	private static CalenderManager m_CalenderManager;
	
	public static CalenderManager getInstance(){
		
		if(m_CalenderManager==null){
			
			m_CalenderManager = new CalenderManager();
		}
		
		return m_CalenderManager;
	}
	
	public void addCalenderData(String userName,String calenderData){
		
		dataMap.put(userName, calenderData);
		
		
	}
	
	public String getCalenderData(String userName){
		
		return dataMap.get(userName);
		
		
	}

}
