package com.google.android.gcm.demo.server;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import com.application.datastore.PMF;
import com.application.datastore.Serve;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.jdo.CloudContactEnty;
import com.jdo.CloudContactNotification;

@SuppressWarnings("serial")
public class SendAllMessagesServlet extends BaseServlet {

	private static final int MULTICAST_SIZE = 1000;
	private Sender sender;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private static final Executor threadPool = Executors.newFixedThreadPool(5);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sender = newSender(config);
	}

	
	protected Sender newSender(ServletConfig config) {
		String key = (String) config.getServletContext().getAttribute(
				ApiKeyInitializer.ATTRIBUTE_ACCESS_KEY);
		return new Sender(key);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("infofile");
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String p1 = (String) req.getSession().getAttribute("userName");
		String category = (String) req.getSession().getAttribute("category");
		String user = getParameter(req, RegisterServlet.USER_ID);
		String info = getParameter(req, "data");
		String infocaegory = getParameter(req, "category");
		CloudContactNotification cln = new CloudContactNotification();
		if (info != null && p1 != null) {
			cln.setText(info);
			Calendar c = Calendar.getInstance(TimeZone
					.getTimeZone("Asia/Calcutta"));
			SimpleDateFormat fmt = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy");
			String today = "";
			try {
				fmt.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
				today = fmt.format(c.getTime());
			} catch (Exception e) {			
				e.printStackTrace();
			}		
			cln.setLastAccess(today);
			cln.setLastAccessDate(c.getTimeInMillis() + "");
			cln.setUserName(p1);
			cln.setCategory(infocaegory);
			boolean isZip = false;
			if (blobKey != null) {
				BlobInfo infoB = new BlobInfoFactory()
						.loadBlobInfo(blobstoreService.getUploadedBlobs(req)
								.get("infofile"));
				if (infoB != null && infoB.getFilename() != null
						&& !infoB.getFilename().trim().isEmpty()) {
					if (infoB.getFilename().endsWith(".zip")) {
						isZip = true;
						HashMap<String, JSONObject> metData = new HashMap<String, JSONObject>();						
						String orgName = (String) req.getSession().getAttribute("org");						
						HashMap<String, CloudContactEnty> contactMap = fetchAllUsersForOrg(orgName);
						Vector<String> vf = getAllFiles(blobKey,
								resp.getWriter(), metData);
						if(metData==null || metData.size()==0){
						
							for (String fName : vf) {
								CloudContactNotification clni = new CloudContactNotification();
								clni.setText(info);
								clni.setLastAccess(today);
								clni.setLastAccessDate(c.getTimeInMillis() + "");
								clni.setUserName(p1);
								clni.setCategory(infocaegory);
								clni.setDataKey(blobKey.getKeyString());
								clni.setContentType(getContentTypeFromName(fName));
								clni.setContentName(infoB.getFilename() + "::"
										+ fName);
								clni.setTarget(user);							
								if(metData.size()>0 && metData.containsKey(fName)&& contactMap!=null && contactMap.size()>0){								
									try {								
										setDataOntoNotificationObject(clni, metData.get(fName), contactMap);								
									} catch (JSONException e) {									
										e.printStackTrace();
									}
								}							
								pm.makePersistent(clni);
							}
						
						}else{
							
							for(Entry<String,JSONObject> e:metData.entrySet()){
								
								CloudContactNotification clni = new CloudContactNotification();
								clni.setText(info);
								clni.setLastAccess(today);
								clni.setLastAccessDate(c.getTimeInMillis() + "");
								clni.setUserName(p1);
								clni.setCategory(infocaegory);
								clni.setDataKey(blobKey.getKeyString());
								
								String keyFName = e.getKey();
								
								String fName = keyFName.split("::")[0];
								
								clni.setContentType(getContentTypeFromName(fName));
								clni.setContentName(infoB.getFilename() + "::"
										+ fName);
								clni.setTarget(user);							
								//if(metData.size()>0 && metData.containsKey(fName)&& contactMap!=null && contactMap.size()>0){								
								try {
									setDataOntoNotificationObject(clni,
											metData.get(keyFName), contactMap);
								} catch (JSONException e1) {
									e1.printStackTrace();
								}
								//}							
								pm.makePersistent(clni);
								
							}
							
							
						}
						//resp.getWriter().print(vf.toString());
						//return;
					} else {
						cln.setDataKey(blobKey.getKeyString());
						cln.setContentType(infoB.getContentType());
						cln.setContentName(infoB.getFilename());
					}
				}
			}
			if (!isZip) {
				cln.setTarget(user);
				pm.makePersistent(cln);

			}
		}
		if (info == null) {
			info = "Test";
		}
		if (p1 == null) {
			resp.getWriter().write("USER NOT LOGGED IN");
			return;
		}
		if (category == null) {
			category = "admin";
		}
		HashMap<String, String> devices = Datastore.getDevices();
		String status = "";
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			Message message = new Message.Builder().addData("info", info)
					.addData("userName", p1).addData("category", category)
					.build();
			if ("all".equals(user)) {
				for (String registrationId : devices.values()) {
					Result result = sender.send(message, registrationId, 5);
				}
			} else {
				String registrationId = devices.get(user);
				if (registrationId != null) {
					Result result = sender.send(message, registrationId, 5);
					status = "Sent message to one device: " + result;
				}
			}

		}
		resp.sendRedirect("/admin.jsp");
		// req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
		// getServletContext().getRequestDispatcher("/admin.jsp").forward(req,
		// resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}

	private Vector<String> getAllFiles(BlobKey blobKey, PrintWriter pw,
			HashMap<String, JSONObject> metadata) {
		InputStream is = null;
		ZipInputStream stream = null;
		Vector<String> vf = new Vector<String>();
		try {
			is = new BlobstoreInputStream(blobKey);
			stream = new ZipInputStream(is);
			ZipEntry entry;
			while ((entry = stream.getNextEntry()) != null) {
				if (entry.getName().equalsIgnoreCase(Serve.EXCEL_META_FILE_NAME)) {					
					ByteArrayOutputStream bos = new ByteArrayOutputStream();					
					try {											
						ByteArrayInputStream bis = new ByteArrayInputStream(Serve.readNumbytesFromStream((int) entry.getSize(), stream));						
						metadata.putAll(Serve.getDataFromExcelStream(bis));						
					} catch (IOException e) {						
						e.printStackTrace();
					}
				} else {
					vf.add(entry.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vf;
	}


	public static String getContentTypeFromName(String fileName) {
		String tF = fileName.toLowerCase();
		String suffix = tF.substring(tF.lastIndexOf("[.]") + 1);
		if (suffix.equals("jpg") || suffix.endsWith(".jpeg")
				|| suffix.endsWith(".png") || suffix.endsWith(".gif")
				|| suffix.endsWith(".rtf")|| suffix.endsWith(".bmp")) {

			return "image/" + suffix;
			
		} else if (suffix.equals("pdf")) {
			return "application/pdf";

		} else if (suffix.equals("txt")) {
			return "text/plain";

		} else {
			return "application/octet-stream";
		}

	}

	public static final String COLUMN_USER_ID = "Distributor Name";
	public static final String COLUMN_DESCRIPTION = "Desciption";
	public static final String COLUMN_SUB_HEADING = "Subheading";
	public static final String COLUMN_HEADING = "Heading";

	public void setDataOntoNotificationObject(CloudContactNotification cln,
			JSONObject jso, HashMap<String, CloudContactEnty> cotactMap)
			throws JSONException {

		if (jso != null) {

			if (!jso.isNull(COLUMN_USER_ID)) {
				String nameB = (String) jso.get(COLUMN_USER_ID);
				String names[] = null;
				if (nameB != null && !nameB.isEmpty()) {
					names = nameB.split(",");
					for (String name : names) {
						if (name != null && !name.isEmpty()
								&& cotactMap.containsKey(name)) {
							CloudContactEnty cn = cotactMap.get(name);
							cln.setTarget(cn.getUserName());
						}
					}
				}
			}
			if (!jso.isNull(COLUMN_DESCRIPTION)) {
				String des = jso.get(COLUMN_DESCRIPTION).toString();
				if (des != null && !des.isEmpty()) {
					cln.setText(des);
				}
			}
			if (!jso.isNull(COLUMN_HEADING)) {
				String des = jso.get(COLUMN_HEADING).toString();
				if (des != null && !des.isEmpty()) {
					cln.setCategory(des);
				}
			}
			if (!jso.isNull(COLUMN_SUB_HEADING)) {
				String des = jso.get(COLUMN_SUB_HEADING).toString();
				if (des != null && !des.isEmpty()) {
					cln.setSubCategory(des);
				}
			}
		}

	}

	public HashMap<String, CloudContactEnty> fetchAllUsersForOrg(String org) {
		if(org==null)
			return null;
		HashMap<String, CloudContactEnty> contactMap = new HashMap<String, CloudContactEnty>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(CloudContactEnty.class);
		query.setFilter("organization == '" + org + "'");
		List<CloudContactEnty> cls = (List<CloudContactEnty>) query.execute();
		for (CloudContactEnty c : cls) {
			contactMap.put(c.getName(), c);
			contactMap.put(c.getUserName(), c);
		}
		return contactMap;
	}

}
