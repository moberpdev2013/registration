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
public class CloudComplaintsData {

	private static final int ENTITIES_PER_PAGE = 3;
	
	public static final String OPEN="open";
	public static final String CLOSED="closed";
	public static final String ASSIGNED="assigned";

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}
	
	@Persistent
	private String keyName;
	
	@Persistent
	private String dataText;
	
	@Persistent
	private String dataImage;
	
	public String getDataText() {
		return dataText;
	}

	public void setDataText(String dataText) {
		this.dataText = dataText;
	}

	public String getDataImage() {
		return dataImage;
	}

	public void setDataImage(String dataImage) {
		this.dataImage = dataImage;
	}

	@Persistent
	private String owner;
	
	@Persistent
	private String ownerCategory;
	
	@Persistent
	private String createtionDate;
	
	@Persistent
	private String resolutionDate;
	
	@Persistent
	private String resolver;
	
	public String getResolver() {
		return resolver;
	}

	public void setResolver(String resolver) {
		this.resolver = resolver;
	}

	@Persistent
	private String resolutionText;
	
	
	public String getResolutionText() {
		return resolutionText;
	}

	public void setResolutionText(String resolutionText) {
		this.resolutionText = resolutionText;
	}

	public String getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(String resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
	
	

	@Persistent
	private String status;	
	
	@Persistent
	private String address;	

	public String getCreatetionDate() {
		return createtionDate;
	}

	public void setCreatetionDate(String createtionDate) {
		this.createtionDate = createtionDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerCategory() {
		return ownerCategory;
	}

	public void setOwnerCategory(String ownerCategory) {
		this.ownerCategory = ownerCategory;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	

	public void setId(Long id) {
		this.id = id;
	}

		  

}


