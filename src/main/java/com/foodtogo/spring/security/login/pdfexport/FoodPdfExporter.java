package com.foodtogo.spring.security.login.pdfexport;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.foodtogo.spring.security.login.models.Food;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
public class FoodPdfExporter {
	 private List<Food> listFood;
     
	    public FoodPdfExporter(List<Food> listFood) {
	        this.listFood = listFood;
	    }
	
	    
	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("ID", font));
	         
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Food_Category", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Food_Id", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Food_Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Food_Price", font));
	        table.addCell(cell);       
	    }
	    /*
	     * {
"id":"56",
"food_category":"VVeg",
"food_id":"4245",
"food_name":"asdf",
"food_price":"123.45"
}
	     */
	    private void writeTableData(PdfPTable table) {
	        for (Food foods : listFood) {
	            table.addCell(String.valueOf(foods.getId()));
	            table.addCell(foods.getFood_category());
	            table.addCell(foods.getFood_id());
	            table.addCell(foods.getFood_name().toString());
	            table.addCell(String.valueOf(foods.getFood_price()));
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