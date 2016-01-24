package com.ajay.messenger.resources;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ajay.messenger.domain.Book;
import com.ajay.messenger.domain.User;
import com.ajay.messenger.utils.ExcelUtil;

@Path("/xml")
public class XmlResource {

	@GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/index")
    public User produceXML() {
		User user = new User();
		user.setUsername("mkyong");
		user.setPassword("password");
		user.setPin(123456);
		
		return user; 
    }
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Path("/readXML")
	public String consumeXML(User user) {
		System.out.println("username: " + user.getUsername() + " pin " + user.getPin());
		return "Consumed Successfully !!";
	}
	
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/write-excel")
    public String writeToExcel() {
		Book book1 = new Book("Head First Java", "Kathy Serria", 79);
	    Book book2 = new Book("Effective Java", "Joshua Bloch", 36);
	    Book book3 = new Book("Clean Code", "Robert Martin", 42);
	    Book book4 = new Book("Thinking in Java", "Bruce Eckel", 35);
	 
	    List<Book> listBook = Arrays.asList(book1, book2, book3, book4);
	    ExcelUtil excelUtil = new ExcelUtil("/Users/ajay.kg/Desktop/book.xlsx");
	    try {
			excelUtil.writeToExcel(listBook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return "excel written done!!";
    }
	
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/read-excel")
    public String readToExcel() {
	 
	    ExcelUtil excelUtil = new ExcelUtil("/Users/ajay.kg/Desktop/book.xlsx");
	    excelUtil.readDataFromExcel();
	    
		return "excel written done!!";
    }
	
	@GET
	@Produces("application/vnd.ms-excel")
	@Path("/download-excel")
	public Response downloadExcel() {
		
		Book book1 = new Book("Head First Java", "Kathy Serria", 79);
	    Book book2 = new Book("Effective Java", "Joshua Bloch", 36);
	    Book book3 = new Book("Clean Code", "Robert Martin", 42);
	    Book book4 = new Book("Thinking in Java", "Bruce Eckel", 35);
	 
	    String EXCEL_FILE = "/Users/ajay.kg/Desktop/book.xlsx";
	    List<Book> listBook = Arrays.asList(book1, book2, book3, book4);
	    ExcelUtil excelUtil = new ExcelUtil(EXCEL_FILE);
	    try {
			excelUtil.writeToExcel(listBook);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    File file = new File(EXCEL_FILE);
		return Response.ok((Object) file)
				.status(Status.OK)
				.header("Content-Disposition", "attachment; filename=\"download_file.xlsx\"")
				.build();
	}
}
