package test;

import java.util.HashSet;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import dao.DBAdmin;
import dao.DBManager;
import model.Admin;
import model.Gallery;
import model.Item;


public class TestUpdateAdmin {
	DBAdmin dbAdmin=   new DBAdmin(); 
	DBManager db = new DBManager();
	
	@Before
	public void init(){
		//dbAdmin =   new DBAdmin();
		db.connect();
		db.deleteAll(Gallery.class);
		db.deleteAll(Admin.class);
		db.close();
	}
	
	@Test
	public void testUpdate(){

		Admin admin1 = getMockAdmin("Mike");

		//Insert without insert mock person & pet
		db.connect();
		   EntityManager entityManager = db.getEntityManager();  
		   
		        entityManager.getTransaction().begin();
					        
		        entityManager.persist(admin1);

			
		        entityManager.getTransaction().commit();

		db.close();
		

		//Get info
		db.connect();		

		Admin admin1update = (Admin) db.find(Admin.class, admin1.getId());

		db.close();
		
		
		//Update object
		admin1update.setName("Pedro");
		
		//person2update.setMascotas(null); 
		admin1update.getGalleries().addAll(new HashSet());
		

		
		//Update db
		DBAdmin dbConn = new DBAdmin();

		db.connect();
			dbConn.updateAdmin(admin1update);

			Admin resultsAdm1 = db.find(Admin.class, admin1update.getId());
			
			Assert.assertEquals("Pedro", resultsAdm1.getName());
			
			db.close();
		
		
		
	}
	
	private Admin getMockAdmin(String name){
		Admin adm = new Admin();
		adm.setName(name);
		
		return adm;
		
	}
	
	private Gallery getMockGallery(String name){
		Gallery gall = new Gallery();
		gall.setName(name);
		gall.setDescription("Nice " + gall.getClass() + " " + name);
	
		return gall;
		
	}
	
}
