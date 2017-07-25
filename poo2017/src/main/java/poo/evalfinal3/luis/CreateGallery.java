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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import dao.DBManager;
import model.Admin;
import model.Gallery;

public class CreateGallery extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  
	  DBAdmin dbAdmin = new DBAdmin();
	  DBManager db = new DBManager();
	  
	  Set<Admin> setAdmin = dbAdmin.getAllAdmin();
	  List<Admin> listAdmin = new ArrayList<Admin>(setAdmin);
	  
	  int remId= (int)(Math.random()*listAdmin.size());
	  db.connect();
	  	Admin mockAdm = (Admin) db.find(Admin.class, listAdmin.get(remId).getId());
	  db.close();
	  
	  
	  Gallery mockGall= new Gallery(0, "Mock Gallery " + (int)(Math.random()*1000));
	  mockGall.setDescription("MocGallery of " + mockAdm.getId() );
	  dbAdmin.createGallery(mockAdm, mockGall);
	  
	  
    response.setContentType("text/plain");
    response.getWriter().println("Create Gallery!");
    response.getWriter().println("Admin ID gallery created: " + mockAdm.getId());
    response.getWriter().println("Admin name gallery created: " + mockAdm.getName());
    response.getWriter().println("Admin ID gallery created: " + mockGall.getId());
    response.getWriter().println("Admin name gallery created: " + mockGall.getName());
  }
  
  
 




}

