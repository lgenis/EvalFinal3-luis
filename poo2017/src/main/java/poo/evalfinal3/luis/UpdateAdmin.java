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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import dao.DBManager;
import data.StringHelper;
import model.Admin;

public class UpdateAdmin extends HttpServlet {

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
	  
	  mockAdm.setName("UpdatedName");
	  dbAdmin.updateAdmin(mockAdm);
	  
	  
    response.setContentType("text/plain");
    response.getWriter().println("Update Admin!");
    response.getWriter().println("Admin updated ID: " + mockAdm.getId());
    response.getWriter().println("Admin updated name: " + mockAdm.getName());
  }
  
  @Override
 	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
 		response.setContentType("text/html");
 	      PrintWriter out = response.getWriter();
 		
 		DBAdmin dbAdmin = new DBAdmin();
 		 DBManager db = new DBManager();
 	      
 	      Boolean pass=true;
 	      
 	      Admin mockAdm =new Admin();
 	      
 	      String strFirstNum = req.getParameter("adminId");
 	      String strSecondNum=req.getParameter("adminName");
 	      
 	      if (StringHelper.isNumber(strFirstNum) && !strSecondNum.equals("")){
		      db.connect();
			  		mockAdm =  db.find(Admin.class, Integer.valueOf(strFirstNum));
			  db.close();
			  if (mockAdm!=null){
	 		      mockAdm.setName(strSecondNum);
	 			  dbAdmin.updateAdmin(mockAdm);
			  }else{
				  pass=false;
			  }
 	      }else {
 	    	  pass=false;
 	      }
 	      
 	   
 	
 	      
 	      if(pass && mockAdm.getId()!=0){	
 	    	  out.println(StringHelper.outHtml("Crear Admin!" , "Crear", mockAdm.getName(), mockAdm.getId()));
 				
 	      }else{
 			
 	    	  out.println(StringHelper.outHtml("Crear Admin!" , "Crear", null, 0));
 			}
 	      
 	}
   
  
}
