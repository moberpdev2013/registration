
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobKey" %>

<%@ page import="java.util.*" %>

<%
/*try {
	
    ServletFileUpload upload = new ServletFileUpload();
    response.setContentType("text/plain");

    FileItemIterator iterator = upload.getItemIterator(request);
    
    StringBuffer sb = new StringBuffer();
    
    HashMap<String,String> myDataMap = new HashMap<String,String>();
    while (iterator.hasNext()) {
      FileItemStream item = iterator.next();
      InputStream stream = item.openStream();

      if (item.isFormField()) {
       		
    	  out.println("Got a form field: " + item.getFieldName());
       		 
       		 //sb.append(item.getFieldName()+":"+item.getHeaders().getHeaderNames().toString());
       		 
      } else {    	    	
	    	
	    	String owner = request.getParameter("user");
	    	
	    	
	    	
	    		
	  		
  		}
    }
    out.println("Send Success :  name = " + sb.toString());
  } catch (Exception ex) {
    throw new ServletException(ex);
  }*/
  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  
  out.print(blobstoreService.createUploadUrl("/complaints"));
  
  //request.getRequestDispatcher(blobstoreService.createUploadUrl("/complaints")).forward(request, response);
  
%>
