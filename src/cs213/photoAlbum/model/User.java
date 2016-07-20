package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * User class.
 * 
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 01-20-2015
 */

public class User implements Serializable{
	
	//private static final long serialVersionUID = 1;
	
	/**
	 * User ID.
	 */
	private String ID;
	/**
	 * User name.
	 */
	private String userName;
	/**
	 * HashMap of user albums.
	 */
	private HashMap<String, Album> myAlbums;
	/**
	 * HashMap of user tags.
	 */
	private HashMap<String, Tag> myTags;
	/**
	 * List of user photos.
	 */
	private HashMap<String, Photo> myPhotos;
	
	/**
	 * User constructor.
	 * 
	 * @param ID is the ID of the user to be created
	 * @param userName is the name of the user to be created
	 */
	public User(String ID, String userName) {
		this.ID = ID;
		this.userName = userName;
		myAlbums = new HashMap<String, Album>();
	}
	
	/**
	 * Sets the user's ID to the given ID.
	 * 
	 * @param ID is user's new ID
	 */
	public void setID(String ID) {
		this.ID = ID;
	}
	
	/**
	 * Sets the user's name to the given name.
	 * 
	 * @param userName is the user's new name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Gets the user's current ID.
	 * 
	 * @return current user ID
	 */
	public String getID() {
		return this.ID;
	}
	
	/**
	 * Gets the user's current name.
	 * 
	 * @return current user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Adds an Album to the User.
	 * 
	 * @param albumName is the name of the album to be added
	 */
	public void addAlbum(Album albumName) {
		myAlbums.put(albumName.getName(), albumName);
	}
	
	/**
	 * Deletes an album.
	 * 
	 * @param name is the name of the album to be deleted
	 */
	public void deleteAlbum(String name) {
		if (myAlbums.containsKey(name)) {
			myAlbums.remove(name);
		}
	}
	
	/**
	 * Renames the album.
	 * 
	 * @param albumName is the target Album's old name
	 * @param newName is the target Album's new name to change to
	 */
	public void renameAlbum(String albumName, String newName) {
		if (myAlbums.containsKey(albumName)) {
			myAlbums.get(albumName).setName(newName);
		}
	}
	
	/**
	 * Gets all User albums.
	 * 
	 * @return HashMap of Albums
	 */
	public HashMap<String, Album> getMyAlbums() {
		return this.myAlbums;
	}
	
	/**
	 * Gets all User Photos.
	 * 
	 * @return HashMap of Photos
	 */
	public HashMap<String, Photo> getMyPhotos() {
		return this.myPhotos;
	}
	
	/**
	 * Prints all Photos that fall between the dates given, in order.
	 * 
	 * @param date is the target date range
	 */
	public void photosByDate(String date) {
		return;
	}
	
	/**
	 * Finds an Album given its name.
	 * 
	 * @param albumName is the target Album's name
	 * @return the Album object on success (if found) and NULL on failure (not found)
	 */
	public Album findAlbum(String albumName) {
		return myAlbums.get(albumName);
	}
	
	/**
	 * Finds a Photo given its name.
	 * 
	 * @param photoName is the target Photo's name
	 * @return the Photo object on success (if found) and NULL on failure (not found)
	 */
	public Photo findPhoto(String photoName) {
		return myPhotos.get(photoName);
	}
}