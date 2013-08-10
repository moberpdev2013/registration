package com.application.datastore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

public class POIManager {

	public static void main(String[] args) {

		try {
			FileInputStream fis = new FileInputStream("Linux.xlsx");

			//System.out.println(createDataXmlFromAllWorkSheet(fis));
			
			HashMap<String, JSONObject> dataMap = createDataXmlFromAllWorkSheet(fis);
			
			for(String key:dataMap.keySet()){
	    		
	    		if("User".equalsIgnoreCase(key) || "Users".equalsIgnoreCase(key)){
	    			
	    		    UserImportManager.importData(key, dataMap.get(key),null);
	    			
	    		}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String createDataXmlFromWorkSheet(InputStream fis,
			int worksheetIndex) {

		try {

			Workbook wb = new XSSFWorkbook(fis);

			Sheet sheet = wb.getSheetAt(worksheetIndex);

			System.out.println(sheet.getSheetName());

			Iterator<Row> row = sheet.iterator();

			Vector<String> headerList = new Vector<String>();

			JSONArray colDataArray = new JSONArray();

			int index = 0;

			while (row.hasNext()) {

				Row r = row.next();			

				Iterator<Cell> cellIt = r.cellIterator();

				System.out.println("\n\n==================================");

				String data[] = new String[headerList.size()];			

				while (cellIt.hasNext()) {

					Cell cell = cellIt.next();
					
					cell.setCellType(Cell.CELL_TYPE_STRING);

					String cval = "";
					
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_BOOLEAN:
						cval = cell.getBooleanCellValue() + "";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cval = ""
								+ ((Double) cell.getNumericCellValue())
										.intValue();
						break;
					case Cell.CELL_TYPE_STRING:
						cval = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_BLANK:
						break;
					case Cell.CELL_TYPE_ERROR:
						break;

					}

					System.out.println(cval);
					
					if (r.getRowNum() == 0) {

						headerList.add(cval);
					} else {

						data[cell.getColumnIndex()] = cval;

					}

				}

				if (index > 0) {

					JSONObject jsob = new JSONObject();
					for (int i = 0; i < headerList.size(); i++) {
						String dd = data[i];
						if (dd != null) {
							dd = dd.replaceAll("&", "&amp;");
							dd = dd.replaceAll("~", "");
						} else {
							dd = "";
						}

						jsob.put(headerList.get(i), dd);

					}

					colDataArray.put(jsob);

				}

				index++;
				
			}

			JSONObject finalD = new JSONObject();

			finalD.put("name", sheet.getSheetName());

			finalD.put("data", colDataArray);

			return finalD.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static HashMap<String, JSONObject> createDataXmlFromAllWorkSheet(
			InputStream fis) {

		try {
			HashMap<String, JSONObject> dataMap = new HashMap<String, JSONObject>();

			Workbook wb = new XSSFWorkbook(fis);

			for (int j = 0; j < wb.getNumberOfSheets(); j++) {

				try {

					Sheet sheet = wb.getSheetAt(j);

					System.out.println(sheet.getSheetName());

					Iterator<Row> row = sheet.iterator();

					Vector<String> headerList = new Vector<String>();

					JSONArray colDataArray = new JSONArray();

					int index = 0;

					while (row.hasNext()) {

						Row r = row.next();
						
						Iterator<Cell> cellIt = r.cellIterator();

						String data[] = new String[headerList.size()];

						while (cellIt.hasNext()) {

							Cell cell = cellIt.next();
							
							cell.setCellType(Cell.CELL_TYPE_STRING);

							String cval = "";

							switch (cell.getCellType()) {

							case Cell.CELL_TYPE_BOOLEAN:
								cval = cell.getBooleanCellValue() + "";
								break;
							case Cell.CELL_TYPE_NUMERIC:
								cval = ""
										+ ((Double) cell.getNumericCellValue())
												.intValue();
								break;
							case Cell.CELL_TYPE_STRING:
								cval = cell.getStringCellValue();
								break;
							case Cell.CELL_TYPE_BLANK:
								break;
							case Cell.CELL_TYPE_ERROR:
								break;

							}
							
							
							if (r.getRowNum() == 0) {

								headerList.add(cval);
							} else {

								data[cell.getColumnIndex()] = cval;

							}

						}

						if (index > 0) {

							JSONObject jsob = new JSONObject();
							for (int i = 0; i < headerList.size(); i++) {
								String dd = data[i];
								if (dd != null) {
									dd = dd.replaceAll("&", "&amp;");
									dd = dd.replaceAll("~", "");
								} else {
									dd = "";
								}

								jsob.put(headerList.get(i), dd);

							}

							colDataArray.put(jsob);

						}

						index++;

					}

					JSONObject finalD = new JSONObject();

					finalD.put("name", sheet.getSheetName());

					finalD.put("data", colDataArray);

					dataMap.put(sheet.getSheetName(), finalD);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return dataMap;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
