package testeval;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

import dao.DBAdmin;
import dao.DBManager;
import model.Admin;

import model.Gallery;
import model.Item;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCaseEval2505 {

	DBManager manager;
	DBAdmin dbAdmin; 
	
	@Before
	public void init(){
		
		dbAdmin = new DBAdmin(); 
		
		manager = new DBManager();
		manager.connect();
		manager.deleteAll(Admin.class);
		manager.close();
	}
	
	
	/***********************   services Admin   ******************************/
	
	
	@Test
	public void test01_CreateAdmin(){
		Admin admin = getMockAdmin("MockAdmin");
		dbAdmin.createAdmin(admin);

		Assert.assertEquals(true,admin.getId()>0);
		Admin recovered = find(Admin.class, admin.getId()); 
		Assert.assertNotNull(recovered);
		Assert.assertEquals("MockAdmin", recovered.getName());		
	}
	
	
	@Test(expected = RuntimeException.class)
	public void test02_CreateAdminNull(){
		Admin admin1 = null;
		dbAdmin.createAdmin(admin1);
	}
	
	@Test(expected = RuntimeException.class)
	public void test03_CreateAdminWidthId(){
		Admin admin = getMockAdmin("AdminWidhId"); 
		dbAdmin.createAdmin(admin);
		dbAdmin.createAdmin(admin);
	}
	
	@Test
	public void test04_RemoveAdmin(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		dbAdmin.removeAdmin(admin); 
		
		Admin adminRecovered = find(Admin.class, admin.getId()); 		
		Gallery galleryRecovered = find(Gallery.class, gallery.getId());
		Item itemRecovered = find(Item.class, item.getId()); 
		
		Assert.assertNull(adminRecovered);		
		Assert.assertNull(galleryRecovered);		
		Assert.assertNull(itemRecovered);				
	}
	
	
	
	@Test
	public void test05_UpdateAdmin(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		admin.setName("NameUpdated"); 
		dbAdmin.updateAdmin(admin); 
		
		Admin recovered = find(Admin.class, admin.getId());
		Assert.assertNotNull(recovered);
		Assert.assertEquals("NameUpdated", recovered.getName());		
	}
	
	

	@Test
	public void test06_UpdateAdminError(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery");
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		admin.setName("NameUpdated");
		//admin.setGalleries(null); 
		dbAdmin.updateAdmin(admin); 
		
		
		Admin adminRecovered = find(Admin.class, admin.getId()); 		
		Gallery galleryRecovered = find(Gallery.class, gallery.getId());
		
		Assert.assertNotNull(adminRecovered);		
		Assert.assertNotNull(adminRecovered.getGalleries());	
		Assert.assertNotNull(galleryRecovered);
		
		Assert.assertEquals("NameUpdated",adminRecovered.getName());		
	}
	
	
	@Test
	public void test07_GetGalleries(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		Set<Gallery> list = 	dbAdmin.getGalleries(admin.getId());  
		
		Assert.assertNotNull( list);		
		Assert.assertEquals(1,list.size());	
		Gallery galleryRecovered = list.iterator().next();
		Assert.assertEquals("MockGallery",galleryRecovered.getName() );		
		Assert.assertEquals("MockAdmin",galleryRecovered.getAdmin().getName());		
		
	}
	

	/***********************   services Gallery  ******************************/
	
	@Test
	public void test10_CreateGallery(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		persist(admin); 
		
		dbAdmin.createGallery(admin, gallery);
				
		Admin adminRecovered = find(Admin.class, admin.getId()); 		
		Set<Gallery> list = adminRecovered.getGalleries();  
		Assert.assertNotNull( list);		
		Assert.assertEquals(1,list.size());	
		Gallery galleryRecovered = list.iterator().next();
		Assert.assertEquals("MockGallery",galleryRecovered.getName() );		
		Assert.assertEquals("MockAdmin",galleryRecovered.getAdmin().getName());
		
	}
	
	
    @Test(expected = RuntimeException.class)
	public void test11_CreateGalleryErrorAdminIdCero(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		//persist(admin); 		
		dbAdmin.createGallery(admin, gallery);	
	}
	
    @Test(expected = RuntimeException.class)
	public void test12_CreateGalleryErrorAdminNull(){
		Gallery gallery = getMockGallery("MockGallery"); 		
		dbAdmin.createGallery(null, gallery);	
	}
	
    
    
    @Test
	public void test13_RemoveGallery(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		dbAdmin.removeGallery(gallery); 
		Admin adminRecovered = find(Admin.class, admin.getId());
		Item itemRecovered = find(Item.class,item.getId()); 
		Gallery galleryRecovered = find(Gallery.class,gallery.getId()); 
		
		Set<Gallery> list = adminRecovered.getGalleries();  
		Assert.assertNotNull(adminRecovered);
		Assert.assertNull(galleryRecovered);
		Assert.assertNull(itemRecovered);
		Assert.assertNotNull( list);		
		Assert.assertEquals(0,list.size());	
    }
    
    
    @Test
   	public void test14_UpdateGallery(){
   		Admin admin = getMockAdmin("MockAdmin");
   		Gallery gallery = getMockGallery("MockGallery"); 
   		Item item = getMockItem("MockItem");
   		
   		
   		persist(admin,gallery,item);
   		
   		gallery.setName("NameUpdated");
   		dbAdmin.update(gallery);
   		
   		Gallery recovered = find(Gallery.class, gallery.getId());
   		Assert.assertNotNull(recovered);
		Assert.assertEquals("NameUpdated", recovered.getName());		
   		
       }
       
	@Test
	public void test15_GetItems(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery"); 
		Item item = getMockItem("MockItem");
		
		persist(admin,gallery,item);
		
		Set<Item> list =	dbAdmin.getItems(gallery.getId());  
		
		Assert.assertNotNull( list);		
		Assert.assertEquals(1,list.size());	
		Item itemRecovered = list.iterator().next();
		Assert.assertEquals("MockItem",itemRecovered.getName() );		
		Assert.assertEquals("MockGallery",itemRecovered.getGallery().getName());		
		
	}
    
	
	
	
    
    
    
	/***********************   services item  ******************************/
	
	/*
	@Test
	public void test30_CreateItem(){
		Admin admin = getMockAdmin("MockAdmin");
		Gallery gallery = getMockGallery("MockGallery");
		Item item = getMockItem("MockItem");  
		persist(admin,gallery); 
		
		dbAdmin.createItem(gallery, item);		
		
		Gallery galleryRecovered = find(Gallery.class, gallery.getId()); 		
		Set<Item> list = galleryRecovered.getItems();  
		Assert.assertNotNull(list);		
		Assert.assertEquals(1,list.size());	
		Item itemRecovered = list.iterator().next();
		Assert.assertEquals("MockItem",itemRecovered.getName() );		
		Assert.assertEquals("MockGallery",itemRecovered.getGallery().getName());		
	}
	
	
	 @Test(expected = RuntimeException.class)
		public void test31_CreateItemErrorGalleryIdCero(){
			Gallery gallery = getMockGallery("MockGallery"); 
			Item item = getMockItem("MockItem"); 			
			dbAdmin.createItem(gallery, item);					
	}
		
	 @Test(expected = RuntimeException.class)
		public void test32_CreateItemErrorGalleryNull(){
			Item item = getMockItem("MockItem"); 			
			dbAdmin.createItem(null, item);					
	}*/
	
	
	/* @Test
		public void test33_RemoveItem(){
			Admin admin = getMockAdmin("MockAdmin");
			Gallery gallery = getMockGallery("MockGallery"); 
			Item item = getMockItem("MockItem");
			Comment comment = getMockComment("Mock Comment Message"); 
			persist(admin,gallery,item,comment);
	
			
			manager.removeItem(item); 
			Admin adminRecovered = find(Admin.class, admin.getId());
			Gallery galleryRecovered = find(Gallery.class,gallery.getId()); 
			Item itemRecovered = find(Item.class,item.getId()); 
			Comment commentRecovered = find(Comment.class, item.getId()); 
			Set<Item> list = galleryRecovered.getItems();  
			
			Assert.assertNotNull(adminRecovered);
			Assert.assertNotNull(galleryRecovered);
			Assert.assertNull(itemRecovered);
			Assert.assertNull(commentRecovered);
			Assert.assertNotNull( list);		
			Assert.assertEquals(0,list.size());	
	    }*/
	
	
	

	/*
	    @Test
	   	public void test34_UpdateItem(){
	   		Admin admin = getMockAdmin("MockAdmin");
	   		Gallery gallery = getMockGallery("MockGallery"); 
	   		Item item = getMockItem("MockItem");
	   		
	   		persist(admin,gallery,item);
	   		item.setName("NameUpdated");
	   		dbAdmin.updateItem(item);
	   		Item recovered = find(Item.class, item.getId());
	   		Assert.assertNotNull(recovered);
			Assert.assertEquals("NameUpdated", recovered.getName());		
	       }
	       
	*/
    
		/*@Test
		public void test35_GetComments(){
			Admin admin = getMockAdmin("MockAdmin");
			Gallery gallery = getMockGallery("MockGallery"); 
			Item item = getMockItem("MockItem");
			Comment comment = getMockComment("Mock Comment Message"); 
			persist(admin,gallery,item,comment);
			
			Set<Comment> list = dbadmin.getComment(item.getId());  
			
			Assert.assertNotNull( list);		
			Assert.assertEquals(1,list.size());	
			Comment commentRecovered = list.iterator().next();
			Assert.assertEquals("Mock Comment Message",commentRecovered.getMessage());		
			Assert.assertEquals("MockItem",commentRecovered.getItem().getName());		
			
		}*/
	

	
	/***************************      Utility test case model   *************************************/
	
	public <T> T find(Class<T> clazz, int id){
		manager.connect();
		T objectFound=manager.find(clazz, id); 
		manager.close();
		return objectFound; 
	}
	
	
	
	/*public void persist(Admin  admin, Gallery gallery, Item item, Comment comment){ 
		manager.connect();
		manager.getEntityManager().getTransaction().begin();
		manager.getEntityManager().persist(admin);
			
		    admin.getGalleries().add(gallery); 
			gallery.setAdmin(admin); 
			
			gallery.getItems().add(item);
			item.setGallery(gallery);
			
			item.getComments().add(comment); 
			comment.setItem(item); 
			
		manager.getEntityManager().getTransaction().commit(); 
		manager.close();
	}*/
	
	
	
	public void persist(Admin  admin, Gallery gallery, Item item){ 
		manager.connect();
		manager.getEntityManager().getTransaction().begin();
		manager.getEntityManager().persist(admin);
			
		    admin.getGalleries().add(gallery); 
			gallery.setAdmin(admin); 
			
			gallery.getItems().add(item);
			item.setGallery(gallery);
			
		manager.getEntityManager().getTransaction().commit(); 
		manager.close();
	}
	
	
	public void persist(Admin  admin, Gallery gallery){ 
		manager.connect();
		manager.getEntityManager().getTransaction().begin();
		manager.getEntityManager().persist(admin);
			admin.getGalleries().add(gallery); 
			gallery.setAdmin(admin); 
		manager.getEntityManager().getTransaction().commit(); 
		manager.close();
	}
	
	
	public void persist(Admin  admin){ 
		manager.connect();
		manager.getEntityManager().getTransaction().begin();
		manager.getEntityManager().persist(admin);
		manager.getEntityManager().getTransaction().commit(); 
		manager.close();
	}
	
	public ArrayList<Admin> getAll(){ 
		manager.connect();
		ArrayList<Admin> list = manager.selectAll(Admin.class);
	    manager.close();
	    return list; 
	}
	
	
	/*public static Comment getMockComment(String message){
		Comment comment = new Comment();
		comment.setMessage(message);
		comment.setRate(5);
		return comment;
	}*/

	public static Item getMockItem(String name){
		Item item = new Item(); 
		item.setName(name);
		item.setDescription("Description of item");
		item.setPrice(10);
		return item;
	}
	
	public static Gallery getMockGallery(String name){
		Gallery gallery = new Gallery();
		gallery.setDescription("Description of gallery test");
		gallery.setName(name);
		return gallery;

	}
	
	public static Admin getMockAdmin(String name){
		Admin admin = new Admin();
		admin.setName(name);
		return admin;
	}
	
}
