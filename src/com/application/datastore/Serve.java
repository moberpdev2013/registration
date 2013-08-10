package com.application.datastore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.demo.server.SendAllMessagesServlet;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Serve extends HttpServlet {
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public static String EXCEL_META_SHEET_NAME="From CEO's desk";
	public static String EXCEL_META_FILE_NAME="info.xlsx";
	public static String EXCEL_CONTENT_FILE_HEADER="File Name";
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));

		String file = req.getParameter("file");

		if (file != null && file.contains(".zip")
				&& file.split("::").length > 1) {

			String secName = file.split("::")[1];

			InputStream is = null;

			if (res != null) {

				res.setContentType(SendAllMessagesServlet
						.getContentTypeFromName(secName));

			}

			try {
				is = new BlobstoreInputStream(blobKey);

				OutputStream os = res.getOutputStream();

				readOutFileFromStream(secName, new ZipInputStream(is), os);
				
				return;

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			blobstoreService.serve(blobKey, res);

		}
	}

	public static void readOutFileFromStream(String secName,
			ZipInputStream stream, OutputStream os) throws IOException {

		ZipEntry entry;

		while ((entry = stream.getNextEntry()) != null) {

			if (!entry.getName().equals(secName)) {

				continue;
			}			
			os.write(readNumbytesFromStream((int) entry.getSize(), stream));

			os.flush();
			os.close();

			return;

		}
	}
	
	public static byte[] readNumbytesFromStream(int size,InputStream stream) throws IOException{
		final byte[] content = new byte[size];

		int offset = 0;

		while (offset < content.length) {
			final int read = stream.read(content, offset, content.length
					- offset);
			if (read == -1)
				break;
			offset += read;
		}

		return content;
		
	}
	
	public static void main(String[] args) {
		
		try {
			ZipInputStream zins  = new ZipInputStream(new FileInputStream("C://Documents and Settings//dtb//Desktop/20057860.zip"));
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			try {
				readOutFileFromStream(EXCEL_META_FILE_NAME, zins, bos);
				
				ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
				
				System.out.println(getDataFromExcelStream(bis));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static HashMap<String, JSONObject> getDataFromExcelStream(ByteArrayInputStream bis){
		
		HashMap<String, JSONObject> dataMap = POIManager.createDataXmlFromAllWorkSheet(bis);
		
		HashMap<String, JSONObject> retdata = new HashMap<String, JSONObject>();
		try {
			JSONArray jso = (JSONArray) dataMap.get(EXCEL_META_SHEET_NAME).get("data");
			
			for(int i=0;i<jso.length();i++){
				
				JSONObject jsod = (JSONObject) jso.get(i);
				
				String cH = !jsod.isNull(SendAllMessagesServlet.COLUMN_HEADING)?jsod.getString(SendAllMessagesServlet.COLUMN_HEADING):"";
				String cSH = !jsod.isNull(SendAllMessagesServlet.COLUMN_SUB_HEADING)?jsod.getString(SendAllMessagesServlet.COLUMN_SUB_HEADING):"";
				
				//retdata.put(jsod.getString(EXCEL_CONTENT_FILE_HEADER), jsod);
				retdata.put(jsod.getString(EXCEL_CONTENT_FILE_HEADER)+"::"+cH+"::"+cSH, jsod);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retdata;
	}
	
}
