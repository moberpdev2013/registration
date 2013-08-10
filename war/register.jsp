<%@ page import="com.application.datastore.*" %>

<%
            String username = request.getParameter("username");
           
			String calenderInfo = request.getParameter("data");	
			
			CalenderManager.getInstance().addCalenderData(username, calenderInfo);
			
            
%>