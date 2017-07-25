package dao;

import java.util.Set;

import model.Admin;
import model.Gallery;
import model.Item;

public interface AdminServices {
	
	/*VIENE DEL EJEMPLO, NO PERTEENCE A EXAMEN!!!!
	 * ==============================================
	//public void insertAdmin(Admin admin); 
	
	//public Admin findAdmin(int id); 
	
	//public Set<Admin> findAllAdmin();
	
	//public void remove(int id); 
	
	//NADA ARRIBA
	================================================*/
	/**
	 * Insereix un nou administrador Admin en la base de dades
	 * @param admin
	 */
	public void createAdmin(Admin admin);
	
	
	/**
	 * Remou un adminiastrador de la base de dades: Ha de
	 * remoure en cascada el seus Gallery, Item, Comment
	 * @param admin
	 */
	public void removeAdmin(Admin admin);
	
	/**
	 * Actualitza les dades dun Admin
	 * @param admin
	 */
	public void updateAdmin(Admin admin);
	
	/**
	 * Obte la llista de Galleries dun Admin amb adminId
	 * @param adminId
	 * @return
	 */
	public Set<Gallery> getGalleries(int adminId);
	
	/**
	 * Insereix un nou Gallery en la base de dades
	 * @param admin
	 * @param gallery
	 */
	public void createGallery(Admin admin, Gallery gallery);
	
	
	/**
	 * Remou un Gallery de la base de dades: Ha de remoure en cascada els seus Item i Comment
	 * @param gallery
	 */
	public void removeGallery(Gallery gallery);
	
	
	/**
	 * Actualitza les dades d'un Gallery
	 * @param gallery
	 */
	public void update(Gallery gallery);
	
	/**
	 * Obte la llista ditems dun Gallery amb galleryId
	 * @param galleryId
	 * @return
	 */
	public Set<Item> getItems(int galleryId);
	
	
	public Set<Admin> getAllAdmin();
	
}
