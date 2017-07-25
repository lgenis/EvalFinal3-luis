package test;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import dao.DBManager;
import model.Admin;

import model.Gallery;
import model.Item;


public class TestRemoveAdmin {
	DBAdmin dbAdmin=new DBAdmin();; 
	DBManager db = new DBManager();
	
	@Before
	public void init(){
		//dbAdmin =   new DBAdmin();
		db.connect();
		db.deleteAll(Admin.class);
		db.close();
	}
	
	
	
	@Test
	public void testRemoveAdmin(){
		
		Admin admin1 = getMockAdmin("Federico");
		
		db.connect();
			EntityManager entityManager=db.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(admin1);
			entityManager.getTransaction().commit();
		db.close();
		
		//Check inserted
		db.connect();		
			Admin results1 = (Admin) db.find(Admin.class, admin1.getId());
		db.close();
		
		Assert.assertEquals(admin1.getName(), results1.getName());
		Assert.assertEquals(admin1.getGalleries(), results1.getGalleries());
		
		
		//Remove and check
		dbAdmin.removeAdmin(results1);
		
		db.connect();	
			Assert.assertEquals(0, db.selectAll(Admin.class).size());
		db.close();
	}
	
	
	
	//@Test(expected = RuntimeException.class)
	public void testNullRemoveAdmin() {
		Admin admin1 = getMockAdmin("Federico");
		
		db.connect();
			EntityManager entityManager=db.getEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(admin1);
			entityManager.getTransaction().commit();
		db.close();
		
		//Check inserted
		db.connect();		
			Admin results1 = (Admin) db.find(Admin.class, admin1.getId());
		db.close();
		
		Assert.assertEquals(admin1.getName(), results1.getName());
		Assert.assertEquals(admin1.getGalleries(), results1.getGalleries());
		
		
		//Remove and check
		
		dbAdmin.removeAdmin(null);
		
	}
	
	
	@Test
	public void testRemoveAdminCascade(){
		Item item2 = getMockItem("La Republique Guidant le Peuple");
		Item item1 = getMockItem("La Gioconda");
		Gallery gallery11 = getMockGallery("Louvre");
		Admin admin1 = getMockAdmin("Lucille");
		
		db.connect();
		
		EntityManager entityManager=db.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(admin1);
		
		admin1.getGalleries().add(gallery11);
		gallery11.setAdmin(admin1);
		
		
		gallery11.getItems().add(item1);
		gallery11.getItems().add(item2);
		item1.setGallery(gallery11);
		item2.setGallery(gallery11);
		


		entityManager.getTransaction().commit();
		
		db.close();
		
		db.connect();	
			Admin resultsAdm1 = (Admin) db.find(Admin.class, admin1.getId());


			Assert.assertEquals(1, db.selectAll(Admin.class).size());
			Assert.assertEquals(1, db.selectAll(Gallery.class).size());
			Assert.assertEquals(2, db.selectAll(Item.class).size());

		
		db.close();
		
		//Remove and check
		dbAdmin.removeAdmin(resultsAdm1);
			
		db.connect();	
			Assert.assertEquals(0, db.selectAll(Admin.class).size());
			Assert.assertEquals(0, db.selectAll(Gallery.class).size());
			Assert.assertEquals(0, db.selectAll(Item.class).size());

			
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
	
	private Item getMockItem(String name){
		Item ite = new Item();
		ite.setName(name);
		ite.setDescription("Nice " + ite.getClass() + " " + name);
		ite.setPrice(1000f*name.length());
		return ite;
		
	}
	

	
}
