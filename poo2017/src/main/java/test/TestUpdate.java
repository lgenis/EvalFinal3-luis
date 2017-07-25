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


public class TestUpdate {
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
	
		Gallery gallery1 = getMockGallery("Mickey");
		Admin admin1 = getMockAdmin("Mike");
		
		
		//Insert without insert mock person & pet
		db.connect();
		   EntityManager entityManager = db.getEntityManager();  
		   
		        entityManager.getTransaction().begin();
					        
		        entityManager.persist(admin1);
		        	admin1.getGalleries().add(gallery1); 
		        	
		        	gallery1.setAdmin(admin1);
			
		        entityManager.getTransaction().commit();

		db.close();
		

		//Get info
		db.connect();		

		Admin admin1update = (Admin) db.find(Admin.class, admin1.getId());
		Gallery gallery1update = (Gallery) db.find(Gallery.class, gallery1.getId());
		db.close();
		
		
		//Update object
		admin1update.setName("Pedro");
		
		
		//person2update.setMascotas(null); 
		admin1update.getGalleries().addAll(new HashSet());
		
		gallery1update.setName("PublicHouse");
		
	
		
		//masc2update.setOwner(person2update);
		
		//Update db
		DBAdmin dbConn = new DBAdmin();

		db.connect();
			dbConn.updateAdmin(admin1update);
			dbConn.update(gallery1update);
			
			Admin resultsAdm1 = db.find(Admin.class, admin1update.getId());
			Gallery resultGall1 =  db.find(Gallery.class, gallery1update.getId());
			
			
			
			
			Assert.assertEquals("Pedro", resultsAdm1.getName());
			
			
			Assert.assertEquals("PublicHouse", resultGall1.getName());
			
			Assert.assertEquals("Pedro", resultGall1.getAdmin().getName());
			
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
