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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBAdmin;
import model.Admin;

public class CreateAdmin extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
      
	  DBAdmin dbAdmin = new DBAdmin();
	  Admin mockAdm = new Admin(0, "Admin Created");
	  dbAdmin.createAdmin(mockAdm);
	  
    response.setContentType("text/plain");
    response.getWriter().println("Create Admin!");
    response.getWriter().println("Admin created is ID: " + mockAdm.getId());
    response.getWriter().println("Admin created name: " + mockAdm.getName());
  }
}