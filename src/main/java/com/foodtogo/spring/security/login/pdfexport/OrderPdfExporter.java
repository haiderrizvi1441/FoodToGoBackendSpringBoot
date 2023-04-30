package com.foodtogo.spring.security.login.pdfexport;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.foodtogo.spring.security.login.models.Food;
import com.foodtogo.spring.security.login.models.Orders;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
public class OrderPdfExporter {
	 private List<Orders> listOrders;
     
	    public OrderPdfExporter(List<Orders> listOrders) {
	        this.listOrders = listOrders;
	    }
	

	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Cost", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Items", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Order_ID", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Status", font));
	        table.addCell(cell); 
	        cell.setPhrase(new Phrase("User_ID", font));
	        table.addCell(cell); 
	    }
	    private void writeTableData(PdfPTable table) {
	        for (Orders orders : listOrders) {
	            table.addCell(String.valueOf(orders.getId()));
	            table.addCell(String.valueOf(orders.getCost()));
	            table.addCell(orders.getItems());
	            table.addCell(orders.getOrder_id().toString());
	            table.addCell(String.valueOf(orders.isStatus()));
	            table.addCell(orders.getUser_id());
	        }
	    }
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph p = new Paragraph("List Of Foods", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }
	
}