package test;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import dao.DBManager;
import model.Admin;

import model.Gallery;
import model.Item;

public class TestGetItems {
DBAdmin dbAdmin =   new DBAdmin(); 
DBManager db = new DBManager();
	
	@Before
	public void init(){
		//dbAdmin =   new DBAdmin();
		db.connect();

		db.deleteAll(Admin.class);
		db.close();
	}
	
	@Test
	public void testGetItems(){
		Admin admin1 = getMockAdmin("adminis");
		
		Gallery gallery1 = getMockGallery("Jose");
		Gallery gallery2 = getMockGallery("Juan");
		
		Item item1 = getMockItem("Rex");
		Item item2 = getMockItem("Garfield");
		Item item3 = getMockItem("Piolin");
		Item item4 = getMockItem("Ratata");
		
		db.connect();
		
			db.getEntityManager().getTransaction().begin();
				
				db.getEntityManager().persist(admin1);
				

				admin1.getGalleries().add(gallery1);
				gallery1.setAdmin(admin1);
				
				gallery1.getItems().add(item1);
					item1.setGallery(gallery1);
				gallery1.getItems().add(item2);
					item2.setGallery(gallery1);
				gallery1.getItems().add(item3);
					item3.setGallery(gallery1);
				gallery2.getItems().add(item4);
					item4.setGallery(gallery2);
			
			db.getEntityManager().getTransaction().commit();
			
		db.close();		
	
		
		Set<Item> list = dbAdmin.getItems(gallery1.getId());
		
		Assert.assertEquals(3, list.size());
		
		ArrayList<Item> listArray = new ArrayList<>(list);
		
		Assert.assertEquals(3, listArray.size());

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
