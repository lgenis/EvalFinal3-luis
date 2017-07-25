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

public class RemoveGallery extends HttpServlet {
		@Override
		public void doGet(HttpServletRequest request, HttpServletResponse response) 
				throws IOException {
    
		  DBAdmin dbAdmin = new DBAdmin();
		  DBManager db = new DBManager();
		  
		  Set<Admin> listSet =  dbAdmin.getAllAdmin();
		  List<Admin> list = new ArrayList<Admin>(listSet);
		  
		  int remId= (int)(Math.random()*list.size());
		  
		  db.connect();
		  	Admin mockAdm =  db.find(Admin.class, list.get(remId).getId());
		  db.close();
		  
		  Set<Gallery> setGallery = mockAdm.getGalleries();
		  List<Gallery> listGallery = new ArrayList<Gallery>(setGallery);
		  
		  int remGalId= (int)(Math.random()*listGallery.size());
		  
		  Gallery removedGal = listGallery.get(remGalId);
		  
		  db.connect();
		  		Gallery mockGal = db.find(Gallery.class, removedGal.getId());
		  db.close();
		  
		  dbAdmin.removeGallery(mockGal);
	  
	  
		  response.setContentType("text/plain");
		  response.getWriter().println("Remove Gallery!");
		  response.getWriter().println("Gallery removed ID: " +  removedGal.getId());
		  response.getWriter().println("Gallery removed name: " + removedGal.getName());
		  response.getWriter().println("from  Admin ID: " +  mockAdm.getId());
		  response.getWriter().println("from Admin name: " + mockAdm.getName());
		  
	}
}
