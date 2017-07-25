package dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import model.Admin;
import model.Gallery;
import model.Item;

public class DBAdmin implements AdminServices{
	




	DBManager dbManager = new DBManager();
	


	public DBManager getDbManager() {
		return dbManager;
	}
	
	@Override
	public void createAdmin(Admin admin) {
		if(admin==null)
			throw new RuntimeException("Admin cannot be null");
		if(admin.getId()!=0)
			throw new RuntimeException("Admin tiene id diferente de cero");

		dbManager.connect();
			EntityManager entityManager = dbManager.getEntityManager();  
	        entityManager.getTransaction().begin();
	        entityManager.persist(admin);
	        entityManager.getTransaction().commit();
		dbManager.close();
	}

	@Override
	public void removeAdmin(Admin admin) {
		if(admin==null)
			throw new RuntimeException("Admin cannot be null");
		dbManager.connect();
			EntityManager entiManager = dbManager.getEntityManager();
			entiManager.getTransaction().begin();
			Admin  recovered = entiManager.find(Admin.class, admin.getId());
			entiManager.remove(recovered);
			entiManager.getTransaction().commit();
		dbManager.close();
	}

	@Override
	public void updateAdmin(Admin admin) {
		//Runtime exception illegal parameters if not ID
		if (admin.getId()<=0 || admin.equals(null)){
			throw new RuntimeException("Wrong id or null");
		}
		dbManager.connect();
			EntityManager entiManager = dbManager.getEntityManager();
		try{	
			entiManager.getTransaction().begin();
			Admin  recovered = entiManager.find(Admin.class, admin.getId());
			
			recovered.setName(admin.getName());
			//recovered.setGalleries(admin.getGalleries());
			entiManager.getTransaction().commit();
			
		}catch (IllegalArgumentException e){
			throw new RuntimeException("Bad argument in find. Wrong class or Id for " + admin.getName());
		}finally{
			dbManager.close();
		}
	}
	
	@Override
	public Set<Gallery> getGalleries(int adminId) {
		Set<Gallery> out = new HashSet<Gallery>();
		dbManager.connect();
		HashSet<Gallery> gal = new HashSet<Gallery>(dbManager.selectAll(Gallery.class));
		for (Gallery gl: gal){
			if (gl.getAdmin().getId()==adminId){
				out.add(gl);
			}
		}
		dbManager.close();
		return out;
	}

	@Override
	public void createGallery(Admin admin, Gallery gallery) {
		if(gallery==null)
			throw new RuntimeException("Gallery cannot be null");
		
		if(admin.getId()==0 || admin==null)
			throw new RuntimeException("La admin no  existe o es null");
		
		dbManager.connect();
		   EntityManager entityManager = dbManager.getEntityManager();  
		   
		        entityManager.getTransaction().begin();
		        Admin recovered = entityManager.find(Admin.class, admin.getId());
		        //entityManager.persist(gallery);
		        //if (admin!=null){
		        	recovered.getGalleries().add(gallery);
		        	gallery.setAdmin(recovered);

		        //}
		        entityManager.getTransaction().commit();

		dbManager.close();
		
	}

	@Override
	public void removeGallery(Gallery gallery) {
		if(gallery==null)
			throw new RuntimeException("Gallery cannot be null");
		
		dbManager.connect();
			EntityManager entiManager = dbManager.getEntityManager();
			entiManager.getTransaction().begin();
			Admin recoveredAdmin = entiManager.find(Admin.class, gallery.getAdmin().getId());
			Gallery  recoveredGallery = entiManager.find(Gallery.class, gallery.getId());
			
			recoveredAdmin.getGalleries().remove(recoveredGallery);
			entiManager.getTransaction().commit();
		dbManager.close();
	}

	@Override
	public void update(Gallery gallery) {
		//Runtime exception illegal parameters if not ID
		if (gallery.getId()<=0 || gallery.equals(null)){
			throw new RuntimeException("Wrong id or null");
			
		}
		dbManager.connect();
		EntityManager entiManager = dbManager.getEntityManager();
			
		try{	
			entiManager.getTransaction().begin();
			Gallery  recovered = entiManager.find(Gallery.class, gallery.getId());
			
			recovered.setName(gallery.getName());
			recovered.setDescription(gallery.getDescription());
			//recovered.setAdmin(gallery.getAdmin());
			//recovered.setItems(gallery.getItems());
			entiManager.getTransaction().commit();
			
		}catch (IllegalArgumentException e){
			throw new RuntimeException("Bad argument in find. Wrong class or Id for " + gallery.getName());
			
		}finally{
			dbManager.close();
			
		}
		
	}

	@Override
	public Set<Item> getItems(int galleryId) {
		Set<Item> out = new HashSet<Item>();
		dbManager.connect();
		HashSet<Item> gal = new HashSet<Item>(dbManager.selectAll(Item.class));
		for (Item gl: gal){
			if (gl.getGallery().getId()==galleryId){
				out.add(gl);
			}
		}
		dbManager.close();
		return out;
	}

	@Override
	public Set<Admin> getAllAdmin() {
		Set<Admin> out = new HashSet<Admin>();
		dbManager.connect();
		HashSet<Admin> gal = new HashSet<Admin>(dbManager.selectAll(Admin.class));
		for (Admin gl: gal){
			
				out.add(gl);
			
		}
		dbManager.close();
		return out;
	}

	public void deleteAll(Class<Admin> class1) {
		dbManager.connect();
		dbManager.deleteAll(Admin.class);
		dbManager.close();		
	}
	
	
	
	
	
}
