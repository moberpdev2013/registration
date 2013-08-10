package com.application.datastore;
import javax.jdo.PersistenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jdo.CloudContactEnty;
import com.jdo.CloudContactUtils;

public class UserImportManager {

	public static void importData(String key, JSONObject json,
			PersistenceManager pm) {

		try {
			
			System.out.println("Key Data:::");

			String name = json.getString("name");

			JSONArray jsArray = (JSONArray) json.get("data");

			for (int i = 0; i < jsArray.length(); i++) {

				JSONObject jsObj = jsArray.getJSONObject(i);

				CloudContactEnty c=null;
				if(pm!=null){
					c = CloudContactUtils.getContact(jsObj.get(
						"userName").toString());

				}
				if (c == null) {

					c = new CloudContactEnty();
				}

				c.setUserName(jsObj.get("userName").toString());

				c.setName(jsObj.get("name").toString());

				c.setOrganization(jsObj.get("organization").toString());

				c.setIsPublic(jsObj.get("isPublic").toString());

				c.setPassword(jsObj.get("password").toString());

				c.setWebAddress(jsObj.get("webAddress").toString());

				c.setService(jsObj.get("service").toString());
				
				c.setImmediateParent(jsObj.get("immediateParent").toString());

				c.setCategory(jsObj.get("category").toString());

				if(pm!=null){
					pm.makePersistent(c);
				
				}else{
					
					System.out.println("Unable to persist : " +c);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
