
<%@page import="com.application.datastore.UserImportManager"%>
<%@page import="javax.jdo.Query"%>
<%@page import="org.json.JSONObject"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page import="com.application.datastore.PMF"%>
<%@page import="com.jdo.CloudRowData"%>
<%@page import="com.application.datastore.POIManager"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>

<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>

<%@ page import="java.io.*"%>

<%@ page import="java.util.*"%>

<%
try {
	
    ServletFileUpload upload = new ServletFileUpload();
    response.setContentType("text/plain");

    FileItemIterator iterator = upload.getItemIterator(request);
    while (iterator.hasNext()) {
      FileItemStream item = iterator.next();
      InputStream stream = item.openStream();

      if (item.isFormField()) {
       		 out.println("Got a form field: " + item.getFieldName());
       		 
      } else {    	  
	    	POIManager poi = new POIManager();
	    	
	    	HashMap<String,JSONObject> dataMap= poi.createDataXmlFromAllWorkSheet(stream);
	    	
	    	PersistenceManager  pm = PMF.get().getPersistenceManager();
	    	
	    	String owner = (String)session.getAttribute("userName");
	    	
	    	
	    	if(owner==null){
	    		
	    		out.println("Fail :  user not authenticated");
	    		
	    		return;
	    	}else{
		    	for(String key:dataMap.keySet()){
		    		
		    		if("User".equalsIgnoreCase(key) || "Users".equalsIgnoreCase(key)){
		    			
		    		    UserImportManager.importData(key, dataMap.get(key),pm);
		    			
		    		}else{
		    			
						Query query = pm.newQuery(CloudRowData.class);
						  
						query.setFilter("keyName == '"+key+"' && owner == '"+owner+"'");
						
						List<CloudRowData> clrd = (List<CloudRowData>)query.execute();
						
						for(CloudRowData cl:clrd){						
						
							pm.deletePersistent(cl);
						
						}	
						
						
			    		CloudRowData cld = new CloudRowData();
				    	
				    	cld.setKeyName(key);
				    	
				    	cld.setLastAccessDate(new Date().toString());
				    	
				    	cld.setOwner(owner);
						
				    	cld.setOwnerCategory((String)session.getAttribute("category"));	
				    	
				    	cld.setData(dataMap.get(key).toString());	    	
				  	  		    	
				    	pm.makePersistent(cld);
			    	
		    		}
		    	}
	    	}
	    	  
	  		out.println("Send Success :  name = " + item.getName());
  		}
    }
  } catch (Exception ex) {
    throw new ServletException(ex);
  }
%>
