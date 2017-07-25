package poo.evalfinal3.luis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;

public class GetGalleries extends HttpServlet{
	
	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String title = "Lista Galerias";
		
	    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	    
	    DBAdmin dbAdmin = new DBAdmin();
	    
	    Set<Admin> listSet =  dbAdmin.getAllAdmin();
		List<Admin> listAdmin = new ArrayList<Admin>(listSet);
		int remId= (int)(Math.random()*listAdmin.size());
	    
	    Set<Gallery> listGallery = listAdmin.get(remId).getGalleries();
	    
	    StringBuilder builder = new StringBuilder();
	    
	    builder.append("<tr>" +
				"<td colspan='2' style='font-weight:bold;'>" +
		"Gallery of Admin: " + listAdmin.get(remId).getName() + " | Id: " + 
				listAdmin.get(remId).getId() + "<br><br>"
	    );
	    
	    for(Gallery gall: listGallery){
	    	String rows = gall.getName() + "| Id: " + gall.getId() + 
	    			"<br>";
	    	builder.append(rows);
	    }
			builder.append("</td></tr>");
			
			
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
