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

import java.util.Collection;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Profile {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

  @Persistent
  private String userName;
  
  @Persistent
  private String description="";
  
  @Persistent
  private String address="";
  
  @Persistent
  private String location="";
  
  @Persistent
  private String serviceTimings="";
  
  @Persistent
  private String state="";
  
  @Persistent
  private String district="";
  
  @Persistent
  private String city="";
  
  @Persistent
  private String country="";
  
  
  public String getState() {
	return state;
}


public void setState(String state) {
	this.state = state;
}


public String getDistrict() {
	return district;
}


public void setDistrict(String district) {
	this.district = district;
}


public String getCity() {
	return city;
}


public void setCity(String city) {
	this.city = city;
}


public String getCountry() {
	return country;
}


public void setCountry(String country) {
	this.country = country;
}

@Persistent
  @Unowned
  private CloudContactEnty contact;
  

  public CloudContactEnty getContact() {
	return contact;
}


public void setContact(CloudContactEnty contact) {
	this.contact = contact;
}


public String getServiceTimings() {
	return serviceTimings;
}


public void setServiceTimings(String serviceTimings) {
	this.serviceTimings = serviceTimings;
}


@Persistent(defaultFetchGroup = "true")
  private Collection<String> imageBlobKeys;
  
public String getUserName() {
	return userName;
}


public void setUserName(String userName) {
	this.userName = userName;
}


public String getDescription() {
	return description;
}


public void setDescription(String description) {
	this.description = description;
}


public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}


public String getLocation() {
	return location;
}


public void setLocation(String location) {
	this.location = location;
}


public Collection<String> getImageBlobKeys() {
	return imageBlobKeys;
}


public void setImageBlobKeys(Collection<String> imageBlobKeys) {
	this.imageBlobKeys = imageBlobKeys;
}
  
}
