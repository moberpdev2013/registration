<%@ page import="com.application.datastore.*"%>

<%
            String username = request.getParameter("username");

			String action = request.getParameter("action");
			
			if("create".equalsIgnoreCase(action)){
           
				String calenderInfo = request.getParameter("data");	
			
				CalenderManager.getInstance().addCalenderData(username, calenderInfo);
			
			}else{
				
				out.println(CalenderManager.getInstance().getCalenderData(username));
				
			}
            
%>