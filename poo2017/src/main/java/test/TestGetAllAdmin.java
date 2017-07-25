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

public class TestGetAllAdmin {
	DBAdmin dbAdmin=   new DBAdmin(); 
	DBManager db = new DBManager();
	
	@Before
	public void init(){
		//dbAdmin =   new DBAdmin();
		db.connect();

		db.deleteAll(Admin.class);
		db.close();
	}
	
	@Test
	public void testGetGalleries(){
		
		
		Admin admin1 = getMockAdmin("Jose");
		Admin admin2 = getMockAdmin("Juan");
		
		
		
		db.connect();
		
			db.getEntityManager().getTransaction().begin();
	
				db.getEntityManager().persist(admin1);
				db.getEntityManager().persist(admin2);

	
			
			db.getEntityManager().getTransaction().commit();
			
		db.close();		
	
		
		Set<Admin> list = dbAdmin.getAllAdmin();
		
		Assert.assertEquals(2, list.size());
		
		ArrayList<Admin> listArray = new ArrayList<>(list);
		
		Assert.assertEquals(2, listArray.size());

	}




	private Admin getMockAdmin(String name){
		Admin adm = new Admin();
		adm.setName(name);
		
		return adm;
		
	}
	

	
}
