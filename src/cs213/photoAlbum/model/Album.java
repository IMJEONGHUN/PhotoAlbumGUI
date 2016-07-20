package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Album class.
 * 
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 01-20-2015
 */
public class Album implements Serializable{
	
	//private static final long serialVersionUID = 1;
	
	/**
	 * Album name.
	 */
	public String albumName;
	/**
	 * Album's user's ID.
	 */
	public String ID;
	/**
	 * Album's user's name.
	 */
	public String userName;
	/**
	 * List of photos in this Album.
	 */
	public HashMap<String, Photo> photos;
	/**
	 * Number of photos in this Album.
	 */
	public int numPhotos;
	/**
	 * Date of the earliest Photo taken in this Album.
	 */
	public Calendar startDate;
	/**
	 * Date of the latest Photo taken in this Album.
	 */
	public Calendar endDate;
	
	/**
	 * Album constructor.
	 * 
	 * @param albumName is the name of the Album to be created
	 * @param userName is the user's name
	 * @param userID is the user's ID
	 */
	public Album(String albumName, String userName, String userID) {
		this.albumName = albumName;
		this.userName = userName;
		this.ID = userID;
		photos = new HashMap<String, Photo>();
		numPhotos = 0;
	}
	
	/**
	 * Sets the album's name to the given name.
	 * 
	 * @param albumName album's new name
	 */
	public void setName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * Gets the album's name.
	 * 
	 * @return current Album name
	 */
	public String getName() {
		return this.albumName;
	}
	
	/**
	 * Adds a photo to the Album.
	 * 
	 * @param photo is the Object to be added
	 */
	public void addPhoto(Photo photo) {
		if (!photos.containsKey(photo.getName())) {
			photos.put(photo.getName(), photo);
		}
	}
	
	/**
	 * Deletes a photo from an Album.
	 * 
	 * @param fileName is the name of the Photo to be deleted
	 */
	public void deletePhoto(String fileName) {
		if (photos.containsKey(fileName)) {
			photos.remove(fileName);
		}
	}
	
	/**
	 * Gets the list of photos in the album.
	 * 
	 * @return List of photos in this Album
	 */
	public HashMap<String, Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Sets the list of photos in the album to the given list.
	 * 
	 * @param photos list
	 */
	public void setPhotoList(HashMap<String, Photo> photos) {
		this.photos = photos;
	}
}