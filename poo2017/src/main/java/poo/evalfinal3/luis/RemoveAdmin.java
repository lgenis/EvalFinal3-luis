/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package poo.evalfinal3.luis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import dao.DBManager;
import model.Admin;

public class RemoveAdmin extends HttpServlet{
	
	@Override
	  public void doGet(HttpServletRequest request, HttpServletResponse response) 
		      throws IOException {
      
	  DBAdmin dbAdmin = new DBAdmin();
	  DBManager db = new DBManager();
	  
	  Set<Admin> listSet =  dbAdmin.getAllAdmin();
	  List<Admin> list = new ArrayList<Admin>(listSet);
	  int remId= (int)(Math.random()*list.size());
	  
	  db.connect();
	  	Admin mockAdm = (Admin) db.find(Admin.class, list.get(remId).getId());
	  db.close();
	  
	  dbAdmin.removeAdmin(mockAdm);
	  
	  
    response.setContentType("text/plain");
    response.getWriter().println("Remove Admin!");
    response.getWriter().println("Admin removed ID: " +  mockAdm.getId());
    response.getWriter().println("Admin removed name: " + mockAdm.getName());
  }
	
	
/*
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		DBAdmin dbAdmin = new DBAdmin();
		
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	      
	      
	      
	      String strFirstNum = req.getParameter("adminId");
	      //String strSecondNum = req.getParameter("second_number");
	      //String operation = req.getParameter("option");
	
	      
	      if(true){	
		      out.println(docType +
				         "<html>\n" +
				            "<head><title>" + title + "</title></head>\n" +
				            "<body bgcolor = \"#f0f0f0\">\n" +
				 
				               "<h1 align = \"center\"> La " + operation + " es: " + sum  + "</h1>\n" +
				               "<h2 align = \"center\">" + date.toString() + "</h2>\n" +
				            "</body> </html>"
				      );
				
	      }else{
			out.println(docType +
			         "<html>\n" +
			            "<head><title>" + title + "</title></head>\n" +
			            "<body bgcolor = \"#f0f0f0\">\n" +
			 
			               "<h1 align = \"center\"> No se puede hacer operacion </h1>\n" +
			               "<h2 align = \"center\">" + date.toString() + "</h2>\n" +
			            "</body> </html>"
			      );
			}
	}*/
}
