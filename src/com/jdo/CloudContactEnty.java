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

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CloudContactEnty {

	private static final int ENTITIES_PER_PAGE = 3;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	
	

	public static final String USER_NAME = "user";
	public static final String WEB_ADDR = "webaddress";
	public static final String PASSWORD = "password";
	public static final String PUBLIC = "PUBLIC";
	public static final String TYPE = "user";
	public static final String SERVICE_TYPE = "service";
	public static final String CATEGORY = "category";
	
	public static final String SERVICE_INSTATUS = "on";
	public static final String SERVICE_OUTSTATUS = "out";

	
	@Persistent
	public String mobilityStatus = "out";
	
	public String getMobilityStatus() {
		return mobilityStatus;
	}

	public void setMobilityStatus(String mobilityStatus) {
		this.mobilityStatus = mobilityStatus;
	}

	@Persistent
	public String service = "in";

	@Persistent
	public String category;
	
	@Persistent
	private String userName;
	
	@Persistent
	private String webAddress;
	
	@Persistent
	private String password = "";
	
	@Persistent
	private String isPublic = "";
	
	@Persistent
	private String deviceKey = "";
	
	@Persistent
	private String organization = "";
	
	@Persistent
	public String name;	
	
	@Persistent
	@Unowned
	private Profile profile;
	
	@Persistent
	private String immediateParent = "";
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String toString() {

		return userName + ":" + webAddress;
	}
	

	public String getImmediateParent() {
		return immediateParent;
	}

	public void setImmediateParent(String immediateParent) {
		this.immediateParent = immediateParent;
	}
	
	 

		  

}


