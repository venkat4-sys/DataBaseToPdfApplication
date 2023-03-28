package com.ait;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;






public class PdfApplication {

	public static void main(String[] args) throws SQLException, FileNotFoundException, DocumentException {
		// TODO Auto-generated method stub
		
		 String jdbcUrl = "jdbc:mysql://localhost:3306/organization";
	        String username = "root";
	        String password = "Venkat_147";
	        String query = "SELECT * FROM emp";
	        ArrayList<String[]> data = new ArrayList();
	        String[] headers = null;
	                
	                Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	                     Statement statement = connection.createStatement();
	                     ResultSet resultSet = statement.executeQuery(query);
	                    
	                    // Retrieve data and column names from database
	                    ResultSetMetaData metadata = resultSet.getMetaData();
	                    int columnCount = metadata.getColumnCount();
	                    headers = new String[columnCount];
	                    for (int i = 1; i <= columnCount; i++) {
	                        headers[i-1] = metadata.getColumnName(i);
	                    }
	                    while (resultSet.next()) {
	                        String[] record = new String[columnCount];
	                        for (int i = 1; i <= columnCount; i++) {
	                            record[i-1] = resultSet.getString(i);
	                        }
	                        data.add(record);
	                    }
	                    
	                    // Create PDF document
	                    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
	                    PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
	                    document.open();
	                    
	                    // Create table and add header row
	                    PdfPTable table = new PdfPTable(columnCount);
	                    for (String header : headers) {
	                        PdfPCell cell = new PdfPCell(new com.itextpdf.text.Paragraph(header));
	                        table.addCell(cell);
	                    }
	                    
	                    // Add data rows
	                    for (String[] record : data) {
	                        for (String field : record) {
	                            PdfPCell cell = new PdfPCell(new com.itextpdf.text.Paragraph(field));
	                            table.addCell(cell);
	                        }
	                    }
	                    
	                    document.add(table);
	                    document.close();
	                    System.out.println("PDF created successfully!");
	                    
	                
	            }
	}
