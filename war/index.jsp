
<%@page import="com.google.android.gcm.demo.server.Datastore"%>
<%@page import="org.json.JSONObject"%>
<%@page import="javax.jdo.Query"%>
<%@page import="com.application.datastore.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%@page contentType="text/html"%>
<%@ page import="java.io.*"%>
<%@ page import="com.jdo.*"%>
<%@ page import="java.util.*"%>



<%
	// parses the list of parameters and checks wether the values corresponds to valid Indexes
	ArrayList indexes = new ArrayList();

	String action = request.getParameter("action");
	try {

		if (action == null || "REGISTER".equals(action)) {

			String p1 = action = request.getParameter("user");
			String s1 = action = request.getParameter("site");
			String password = request.getParameter("password");
			String pu = request.getParameter("public");
			String category = request.getParameter("category");
			String name = request.getParameter("name");
			if (pu == null) {
				pu = "no";
			}

			if (s1 == null) {
				s1 = "";
			}

			if (p1 == null) {
				p1 = "";
			}

			CloudContactUtils.insertNew(p1, s1, password, pu, category,
					name);

			response.sendRedirect("login.html");

		} else if ("login".equals(action)) {

			String p1 = request.getParameter("user");
			String password = request.getParameter("password");

			CloudContactEnty ui = CloudContactUtils.getContact(p1);

			String origin = request.getParameter("origin");

			if (ui != null && password.equals(ui.getPassword())) {

				if ("mobile".equals(origin)) {

					out.println("success");

				} else {

					session.setAttribute("userName", p1);

					session.setAttribute("category", ui.getCategory());

					session.setAttribute("org", ui.getOrganization());

					session.setAttribute("site", ui.getWebAddress());

					session.setAttribute("service", ui.getService());

					response.sendRedirect("admin.jsp");

				}

			} else {
				if ("mobile".equals(origin)) {

					out.println("failure");

				} else {

					response.sendRedirect("login.html");

				}
			}

			//out.print(s.getNotificationsJSON(p1));

		} else if ("allusers".equals(action)) {

			String p1 = request.getParameter("user");

			String filter = request.getParameter("filter");

			String status = request.getParameter("status");

			String location = request.getParameter("location");

			out.print(CloudContactUtils.getAllContactsJSONString(p1,
					filter, status, location));

		} else if ("putnot".equals(action)) {

			String p1 = action = request.getParameter("user");
			String s1 = action = request.getParameter("note");
			CloudContactUtils.insertNewNotification(p1, s1);

			//out.print(nofifications.get(p1));

		} else if ("getallnotes".equals(action)) {

			String p1 = request.getParameter("user");

			out.print(CloudContactUtils.getNotificationsJSON(p1));

		} else if ("getallnotesAll".equals(action)) {

			String p1 = request.getParameter("user");

			out.print(CloudContactUtils.getAllNotificationsJSON(p1));

		} else if ("service".equals(action)) {

			String p1 = request.getParameter("user");

			String sv = "in".equals(session.getAttribute("service")) ? "out"
					: "in";

			session.setAttribute("service", sv);

			CloudContactUtils.updateService(p1, sv);

			out.print("in".equals(session.getAttribute("service")) ? "Stop Service"
					: "Start Service");
		}else if ("mobility".equals(action)) {
			
			out.print(Datastore.regIds.toString());
		}

		else if ("getContacts".equals(action)) {

			String p1 = (String) session.getAttribute("userName");

			String category = (String) session.getAttribute("category");

			String filter = null;

			if ("radmin".equals(category)) {

				filter = "distributors";
			}

			out.print(CloudContactUtils.getAllContactsJSONString(p1,
					filter, null, null));

		} else if ("json".equals(action)) {
			try {
				String p1 = request.getParameter("name");

				PersistenceManager pm = PMF.get()
						.getPersistenceManager();

				String user = request.getParameter("user");

				List<CloudRowData> cls = null;

				if (user != null) {

					Query query = pm.newQuery(CloudRowData.class);

					query.setFilter("owner == '" + user
							+ "' && keyName == '" + p1 + "'");

					cls = (List<CloudRowData>) query.execute();

				}

				if (user != null && (cls == null || cls.isEmpty())) {

					CloudContactEnty cen = CloudContactUtils
							.getContact(user);

					Query query = pm.newQuery(CloudRowData.class);

					query.setFilter("owner == '"
							+ cen.getImmediateParent()
							+ "' && keyName == '" + p1 + "'");

					cls = (List<CloudRowData>) query.execute();

					if (cls == null) {

						query = pm.newQuery(CloudRowData.class);

						query.setFilter("ownerCategory == 'admin' && keyName == '"
								+ p1 + "'");

						cls = (List<CloudRowData>) query.execute();

					}

				}

				if (cls == null || cls.isEmpty()) {

					Query query = pm.newQuery(CloudRowData.class);

					query.setFilter("ownerCategory == 'admin' && keyName == '"
							+ p1 + "'");

					cls = (List<CloudRowData>) query.execute();
				}

				if (cls != null) {
					out.print(JSONObject.wrap(cls).toString());

				} else {

					out.print("{\"result\":\"nodata\"}");
				}

			} catch (Exception e) {
				out.print("{\"result\":\"error\"}");
				e.printStackTrace();

			}

		} else if ("updatejson".equals(action)) {
			try {

				String p1 = request.getParameter("name");

				String user = request.getParameter("user");

				String data = request.getParameter("data");

				if (user != null) {

					CloudContactEnty cen = CloudContactUtils
							.getContact(user);

					if (cen != null) {
						PersistenceManager pm = PMF.get()
								.getPersistenceManager();

						Query query = pm.newQuery(CloudRowData.class);

						query.setFilter("owner == '" + user
								+ "' && keyName == '" + p1 + "'");

						List<CloudRowData> cls = (List<CloudRowData>) query
								.execute();

						CloudRowData clr = new CloudRowData();

						if (cls == null || cls.isEmpty()) {

							clr.setKeyName(p1);
							clr.setOwner(user);
							clr.setOwnerCategory(cen.getCategory());
							clr.setLastAccessDate(new Date().getTime()
									+ "");
							clr.setData(data);

						} else {
							clr.setLastAccessDate(new Date().getTime()
									+ "");
							clr = cls.get(0);
							clr.setData(data);

						}
						pm.makePersistent(clr);

						out.print("SUCCESS");

					}
				}
			} catch (Exception e) {
				out.print("{\"result\":\"error\"}");

			}

		} else if ("getComplaints".equals(action)) {

			PersistenceManager pm = PMF.get().getPersistenceManager();

			String p1 = (String) session.getAttribute("userName");

			Query query = pm.newQuery(CloudComplaintsData.class);

			List<CloudComplaintsData> cls = (List<CloudComplaintsData>) query
					.execute();

			out.print(JSONObject.wrap(cls).toString());

		} else if ("notifications".equals(action)) {

			PersistenceManager pm = PMF.get().getPersistenceManager();

			String user = request.getParameter("user");

			CloudContactEnty cen = CloudContactUtils.getContact(user);
			if (cen != null) {

				Query query = pm
						.newQuery(CloudContactNotification.class);

				String imP = "";

				if (cen.getImmediateParent() != null) {

					CloudContactEnty cen1 = CloudContactUtils
							.getContact(cen.getImmediateParent());

					imP = "target == '" + cen1.getImmediateParent()
							+ "'";

				}

				query.setFilter("target == '" + cen.getUserName()
						+ "' || target == '"
						+ cen.getImmediateParent() + "'  || "+ imP);

				List<CloudContactNotification> cls = (List<CloudContactNotification>) query
						.execute();
				
				

				out.print(JSONObject.wrap(cls).toString());

			} else {
				out.print("{\"result\":\"error\"}");
			}
		}

	} catch (Exception exceptionparams) {

		out.println(exceptionparams.getMessage());
	}
%>
