/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jdo;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.json.JSONObject;

import com.application.datastore.PMF;
import com.google.android.gcm.demo.server.Datastore;

public class CloudContactUtils {

  private static final int ENTITIES_PER_PAGE = 3;

  public static String insertNew(
      String user, String site, String password,
      String isPublic,String category,String name) {
	  
	  CloudContactEnty ce=new CloudContactEnty();
	  
	  	if(isPublic==null){
	  		isPublic="no";
		}
		
		if(site==null){
			site="";
		}
		
		if(category==null){
			category="";
		}
		
		
		
		ce.setUserName(user);
		ce.setWebAddress(site);			
		ce.setPassword(password);		
		ce.setIsPublic(isPublic);
		ce.setCategory(category);
		ce.setName(name);
		Profile pf =new Profile();
		pf.setUserName(user);
		ce.setProfile(pf);
		pf.setContact(ce);

    PersistenceManager pm = PMF.get().getPersistenceManager();
    pm.makePersistent(ce);

    System.out.println(
        "The ID of the new entry is: " + ce.getId().toString());
    
    return ce.getId().toString();
  }
  
  public static String insertNewNotification(
	      String user, String text) {
		  
		  CloudContactNotification ce=new CloudContactNotification();
		  
		  
			ce.setUserName(user);
			ce.setText(text);
			ce.setLastAccess(new Date().toString());
	    PersistenceManager pm = PMF.get().getPersistenceManager();
	    pm.makePersistent(ce);

	    System.out.println(
	        "The ID of the new entry is: " + ce.getId().toString());
	    
	    return ce.getId().toString();
	  }
  public static Profile fetchProfile(String user){
	  PersistenceManager pm = PMF.get().getPersistenceManager();
	  return fetchProfile(pm, user);
	  
  }
  public static Profile fetchProfile(PersistenceManager pm,String user){

	  Query query = pm.newQuery(Profile.class);
	  query.declareParameters("String user");
	  
	    query.setFilter("userName == user");
	    List<Profile> lc1 =null;
	    	lc1 =(List<Profile>) query.execute(user);
	    	
	    	if(lc1!=null && lc1.size()>0){
	    		return lc1.get(0);
	    	}else{
	    		return null;
	    	}	    	
	  
  }
	  
  public static String insertProfile( PersistenceManager pm,Profile prof) {
		  
		 
	    //PersistenceManager pm = PMF.get().getPersistenceManager();
	    pm.makePersistent(prof);

	    System.out.println(
	        "The ID of the new entry is: " + prof.getUserName().toString());
	    
	    return prof.getUserName().toString();
	  }
	  
  public static List<CloudContactEnty> getPage(int indexOffset) {
    PersistenceManager pm = PMF.get().getPersistenceManager();
    
    Query query = pm.newQuery(CloudContactEnty.class);

    query.setRange(indexOffset, indexOffset + ENTITIES_PER_PAGE + 1);
    return (List<CloudContactEnty>) query.execute();
  }
  
  public static String getNotificationsJSON(String user){
PersistenceManager pm = PMF.get().getPersistenceManager();
	  
	  
	  Query query = pm.newQuery(CloudContactNotification.class);
	  query.declareParameters("String user");
	  query.setOrdering("lastAccess DESC");
	    query.setFilter("userName == user");
	    List<CloudContactNotification> lc =null;
	    	lc =(List<CloudContactNotification>) query.execute(user);
	    	List<CloudContactNotification> lc1=new ArrayList<CloudContactNotification>();
	    	
	    	for(CloudContactNotification cn : lc){
	    		//Date d = cn.getLastAccess();
//	    		if(d!=null){
//	    			
//	    			cn.setLastAccessDate(d.toString());
//	    			cn.setLastAccess(null);
//	    		}
	    		lc1.add(cn);
	    	}
	    	
if(lc1!=null && lc1.size()>0){
	return JSONObject.wrap(lc1).toString();
}else{
	return "";
}
  }

  public static String getAllNotificationsJSON(String users){
PersistenceManager pm = PMF.get().getPersistenceManager();
	  String us[]=users.split(",");
	  
	  Query query = pm.newQuery(CloudContactNotification.class);
	  //query.declareParameters("String user");
	  query.setOrdering("lastAccess DESC");
	  String filter="";
	  String app="";
	  for(String u:us){
	    
	    filter+=app+"userName == '"+u+"'";
	    app=" || ";
	  }
	  query.setFilter(filter);
	    List<CloudContactNotification> lc =null;
	    	lc =(List<CloudContactNotification>) query.execute();
	    	List<CloudContactNotification> lc1=new ArrayList<CloudContactNotification>();
	    	
	    	for(CloudContactNotification cn : lc){
//	    		Date d = cn.getLastAccess();
//	    		if(d!=null){
//	    			
//	    			cn.setLastAccessDate(d.toString());
//	    			cn.setLastAccess(null);
//	    		}
	    		lc1.add(cn);
	    	}
	    	
if(lc1!=null && lc1.size()>0){
	return JSONObject.wrap(lc1).toString();
}else{
	return "";
}
  }

  public static List<CloudContactEnty> getAllContacts(String user,String filter,String status,String location){
	  
	  
	  
	  PersistenceManager pm = PMF.get().getPersistenceManager();
	  
	  
	  Query query = pm.newQuery(CloudContactEnty.class);
	  List<String> cats = null;
	  query.declareParameters("String user");
	  String filterS="";
	  if(filter!=null && !filter.isEmpty()){
		 // query=  pm.newQuery(CloudContactEnty.class, ":p.contains(category)");
		  String dd[] = filter.split(",");
		  filterS="(";
		  String append="";
		  for(String sa:dd){
			  
			  if(!sa.isEmpty()){
				  
				  filterS+=append+" category =='"+sa+"'";
				  append=" || ";
			  }
		  }
		  filterS+=") && ";
		  query.setFilter(filterS);
		  
	  }
	  
//	  if(location!=null && !location.isEmpty()){
//		  
//		  
//		  filterS+=" profile.city == '"+location+"' && ";
//	  }
//	  
	  if(status!=null && !status.isEmpty() && !"all".equalsIgnoreCase(status)){
		  
		  String sta ="in";
			if("offline".equalsIgnoreCase(status)){
				
			  sta="out";	
			}
			
			 filterS+="service =='"+sta+"' && ";
	  }
	  
	  filterS+="userName != user";
	    query.setFilter(filterS);
	    List<CloudContactEnty> lc =null;
	   	    	lc =(List<CloudContactEnty>) query.execute(user);
	    
	     //pm.detachCopyAll(lc);
	    
	    if(lc!=null && lc.size()>0){
	    	
	    	List<CloudContactEnty> lc1=new ArrayList<CloudContactEnty>();
	    	
	    	for(CloudContactEnty cn : lc){
	    		
	    		if(location!=null && !location.isEmpty() ){
	    			
	    			if(cn.getProfile()==null){
	    				
	    				continue;
	    			}else
	    				if( !((cn.getProfile().getDistrict()!=null && cn.getProfile().getDistrict().toLowerCase().startsWith(location)) || (cn.getProfile().getCity()!=null && cn.getProfile().getCity().toLowerCase().startsWith(location)))){
	    				
	    					continue;
	    				}
	    		}
	    		cn=(CloudContactEnty) cloneEntity(cn);
	    		
	    		
	    		cn.setProfile(null);
	    		lc1.add(cn);
	    	}
	    	return lc1;
	    }else{
	    	return null;
	    }
  }

  public static String getAllContactsJSONString(String user,String filter,String status,String location){
	  
	  
	  
	  PersistenceManager pm = PMF.get().getPersistenceManager();
	  
	  
	  Query query = pm.newQuery(CloudContactEnty.class);
	  List<String> cats = null;
	  query.declareParameters("String user");
	  String filterS="";
	  if(filter!=null && !filter.isEmpty()){
		 // query=  pm.newQuery(CloudContactEnty.class, ":p.contains(category)");
		  String dd[] = filter.split(",");
		  filterS="(";
		  String append="";
		  for(String sa:dd){
			  
			  if(!sa.isEmpty()){
				  
				  filterS+=append+" category =='"+sa+"'";
				  append=" || ";
			  }
		  }
		  filterS+=") && ";
		  query.setFilter(filterS);
		  
	  }
	  
//	  if(location!=null && !location.isEmpty()){
//		  
//		  
//		  filterS+=" profile.city == '"+location+"' && ";
//	  }
//	  
	  if(status!=null && !status.isEmpty() && !"all".equalsIgnoreCase(status)){
		  
		  String sta ="in";
			if("offline".equalsIgnoreCase(status)){
				
			  sta="out";	
			}
			
			 filterS+="service =='"+sta+"' && ";
	  }
	  
	  filterS+="userName != user";
	    query.setFilter(filterS);
	    List<CloudContactEnty> lc =null;
	   	    	lc =(List<CloudContactEnty>) query.execute(user);
	    
	     //pm.detachCopyAll(lc);
	    
	    if(lc!=null && lc.size()>0){
	    	
	    	List<CloudContactEnty> lc1=new ArrayList<CloudContactEnty>();
	    	
	    	for(CloudContactEnty cn : lc){
	    		
	    		if(location!=null && !location.isEmpty() ){
	    			
	    			if(cn.getProfile()==null){
	    				
	    				continue;
	    			}else
	    				if( !((cn.getProfile().getDistrict()!=null && cn.getProfile().getDistrict().toLowerCase().startsWith(location)) || (cn.getProfile().getCity()!=null && cn.getProfile().getCity().toLowerCase().startsWith(location)))){
	    				
	    					continue;
	    				}
	    		}
	    		cn=(CloudContactEnty) cloneEntity(cn);
	    		if(Datastore.regIds.containsKey(cn.getUserName())){
	    			
	    			cn.setMobilityStatus(CloudContactEnty.SERVICE_INSTATUS);
	    		}else{
	    			cn.setMobilityStatus(CloudContactEnty.SERVICE_OUTSTATUS);
	    		}
	    		
	    		
	    		cn.setProfile(null);
	    		lc1.add(cn);
	    	}
	    	
	    	return JSONObject.wrap(lc1).toString();
	    }else{
	    	return "";
	    }
  }
  
  
 
  public static CloudContactEnty getContact(String user){
	  PersistenceManager pm = PMF.get().getPersistenceManager();
	    
	    Query query = pm.newQuery(CloudContactEnty.class);
	    query.declareParameters("String user");
	    query.setFilter("userName == user");
	   
	    List<CloudContactEnty> lc =(List<CloudContactEnty>) query.execute(user);
	    //pm.detachCopyAll(lc);
	    if(lc!=null && lc.size()>0){
	    	CloudContactEnty cn=(CloudContactEnty) cloneEntity(lc.get(0));
    		cn.setProfile(null);
    		
	    //	lc.get(0).setProfile(null);
	    	return cn;
	    }else{
	    	return null;
	    }

	  
  }
  
  public static void updateService(String user,String sval){

	  PersistenceManager pm = PMF.get().getPersistenceManager();
	  
	  Query query = pm.newQuery(CloudContactEnty.class);
	    query.declareParameters("String user");
	    query.setFilter("userName == user");
	   
	    List<CloudContactEnty> lc =(List<CloudContactEnty>) query.execute(user);
	    
	    if(lc!=null && lc.size()>0){

	  	  CloudContactEnty ce =  lc.get(0);
	  	  
	  	 if(ce!=null){
			  
			  ce.setService(sval);
			  
			  pm.makePersistent(ce);
		  }
	    }

	  
	 
	  
  }
  public static void main(String[] args) {
//	
//	  List<CloudContactNotification> lc=new ArrayList<CloudContactNotification>();
//	  CloudContactNotification a=new CloudContactNotification();
//	  a.setLastAccess(""+new Date().getTime());
//	  a.setText("tt");
//	  a.setUserName("dhaneesh");
//	  lc.add(a);
//	  
//	  System.out.println(JSONObject.wrap(lc));
//	
	  
	  
	try {
		//Asia/Calcutta
		 String[] allTimeZones = TimeZone.getAvailableIDs();
		    Date now = new Date();
		    for (int i = 0; i < allTimeZones.length; i++) {
		    	//System.out.println(allTimeZones[i]);
		        TimeZone tz = TimeZone.getTimeZone(allTimeZones[i]);
		        System.out.format("%s;%s; %f \n", 
		                          allTimeZones[i],
		                          tz.getDisplayName(), 
		                          (float) (tz.getOffset(now.getTime())/3600000.0));
		    }
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     
  }
  
  private static float getOffset(String timeId){
	  
	  try {
			//Asia/Calcutta
			// String[] allTimeZones = TimeZone.getTimeZone(timeId);
			    Date now = new Date();
			   // for (int i = 0; i < allTimeZones.length; i++) {
			    	//System.out.println(allTimeZones[i]);
			        TimeZone tz = TimeZone.getTimeZone(timeId);
			        return (float) (tz.getOffset(now.getTime())/3600000.0);
			    //}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  return 0;
	  
  }
  public static void updateSingle(CloudContactEnty cn, PersistenceManager pm){
	  
	  updateSingle(cn, pm,null);
  }
  public static void updateSingle(CloudContactEnty cn, PersistenceManager pm,PrintWriter pr){
	  

		
		Profile p =  cn.getProfile();
		
		String ss = p.getServiceTimings();
		HashMap<String, String> seMap = new HashMap<String, String>();
		if(pr!=null){
			
			pr.println("Service: "+ss);
		}
		if(ss!=null && !ss.isEmpty()){
			
			String[] ssA = ss.split(",");
			
			for(String sa:ssA){
				
				String sa1[]=sa.trim().split("=");
				
				seMap.put(sa1[0], sa1[1]);
			}
			
			String dateS = getDay();
			
			if(pr!=null){
				
			pr.println("Day : " +dateS);
			//pr.println("Day : " +new Date().toString());
			TimeZone.getTimeZone("Asia").getOffset(Calendar.getInstance().getTimeInMillis());
			}
			
			String start=seMap.get(dateS+"_start");
			String end=seMap.get(dateS+"_end");
			
			if(start!=null && !start.isEmpty() && end!=null && !end.isEmpty()){
				try{
				float f1=Float.parseFloat(start);
				float f2=Float.parseFloat(end);
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
				float f = Float.parseFloat(cal.get(Calendar.HOUR_OF_DAY)+"."+cal.get(Calendar.MINUTE));
				
				
				//float offset=getOffset("Asia/Calcutta"); 
				//f=f+offset;
				
				
				if(pr!=null){
					
					pr.println("Current Time : " +f);
				}
				if(f>f1 && f<f2){
					if(!"in".equals(cn.getService())){
						cn.setService("in");
						if(pm!=null)
						pm.makePersistent(cn);
						
						if(pr!=null){
							
							pr.println("Done Inside Inservice");
						}
					}
					
				}else{
					if(!"out".equals(cn.getService())){
					cn.setService("out");
					if(pm!=null)
					pm.makePersistent(cn);
					if(pr!=null){
						
						pr.println("Done Inside Outofservice");
					}
					}
				}
				
						
				}catch(Exception e){
					e.printStackTrace();
					if(pr!=null){
						
						pr.println(e.getMessage());
					}
					
				}
			}
		}
		
		
		
		
	
  }
  
  public static void update(PrintWriter pr){
	  PersistenceManager pm = PMF.get().getPersistenceManager();
	  
	  
	  Query query = pm.newQuery(CloudContactEnty.class);
	  List<String> cats = null;
	 // query.declareParameters("String user");
	 
	  List<CloudContactEnty> lc =null;
	   	    	lc =(List<CloudContactEnty>) query.execute();
	   
	    if(lc!=null && lc.size()>0){
	    	
	    	List<CloudContactEnty> lc1=new ArrayList<CloudContactEnty>();
	    	
	    	
	    	
	    	for(CloudContactEnty cn : lc){
	    		//cn.setProfile(null);
	    		pr.println("not null Update :"+cn.getUserName());
	    		if(cn.getProfile()!=null){
	    			pr.println("Before Update :"+cn.getUserName());
	    			updateSingle(cn, pm,pr);
	    			pr.println("After Update :"+cn.getUserName());
	    		}
	    		
	    	}
	    	
	    }
  }
  
  public static String getDay(){
	  
	  int d= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	  String day="sun";
	  switch(d){
	  
	  case Calendar.SUNDAY:
		  day="sun";
		  break;
		  

	  case Calendar.MONDAY:
		  day="mon";
		  break;
		

	  case Calendar.TUESDAY:
		  day="tue";
		  break;
		

	  case Calendar.WEDNESDAY:
		  day="wed";
		  break;
		

	  case Calendar.THURSDAY:
		  day="thu";
		  break;
		

	  case Calendar.FRIDAY:
		  day="fri";
		  break;
		

	  case Calendar.SATURDAY:
		  day="sat";
		  break;		
	  }
	  
	  return day;
  }
  
  public static Object cloneEntity(Object obj){
	  Object obj1=null;
	  try {
		 obj1=obj.getClass().newInstance();
		
		Field f[] = obj.getClass().getDeclaredFields();
		
		for(Field f1:f){
			
			if (!java.lang.reflect.Modifier.isStatic(f1.getModifiers())) {
			
			f1.setAccessible(true);
			
			f1.set(obj1, f1.get(obj));
			}
		}
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  return obj1;
	  
  }
  
}
