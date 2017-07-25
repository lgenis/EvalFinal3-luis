package poo.evalfinal3.luis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import model.Admin;

public class GetAllAdmin extends HttpServlet{
	
	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String title = "Lista Administradores";
		
	    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	    
	    DBAdmin dbhelper = new DBAdmin();
	    Set<Admin> listAdmin = dbhelper.getAllAdmin();
	    
	    StringBuilder builder = new StringBuilder();
	    
	    for(Admin admin: listAdmin){
	    	String rows = "<tr>" +
	    					"<td colspan='2' style='font-weight:bold;'>" +
	    			admin.getName() + "| Id: " + admin.getId() + 
	    			"</td>" +
	    			"</tr>";
	    	builder.append(rows);
	    }
		
	    out.println(docType +
	    		"<html>\n" +
	    			"<head><title>" + title + "</title></head>\n" +
	    			"<body bgcolor = \"#f0f0f0\">\n" +
	    			"<h1 align = \"center\">" + title + "</h1>\n" +
	    			"<table>" +
	    			builder.toString() +
	    			"</table>" +
	    			"</body> </html>");
	}
	
	
	
}
