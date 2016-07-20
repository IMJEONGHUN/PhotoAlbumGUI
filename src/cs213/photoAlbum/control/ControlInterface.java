package cs213.photoAlbum.control;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

/**
 * ControllerInterface interface.
 * Interface for the class responsible for all the nitty-gritty in the Photo Album application. Performs all data and logic related operations.
 * 
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 01-20-2015
 */

public interface ControlInterface {
	
	User currentUser = null;
	
	/* command line mode */
	
	/**
	 * Returns the ArrayList of users.
	 */
	public ArrayList<User> listUsers();
	
	
	/**
	 * Adds a new user with the given ID and userName to the list of users.
	 * 
	 * @param ID is the user's ID
	 * @param userName is the user's name 
	 * @return True on success, false on failure (user already exists).
	 */
	public boolean addUser(String ID, String userName);
	
	
	/**
	 * Deletes a user with the given string ID from the user list.
	 * 
	 * @param ID is the user's ID
	 * @return True on success, false on failure (user doesn't exists).
	 */
	public boolean deleteUser(String ID);
	
	
	/**
	 * Sets the global variable currentUser to the given userID.
	 * 
	 * @param ID is the user's ID
	 * @return True if successful (user exists), false otherwise.
	 */
	public boolean login(String ID);
	
	/**
	 * Sets the global variable currentUser to the given userID.
	 * 
	 * @param ID is the user's ID
	 * @return True if successful (user exists), false otherwise.
	 */
	public void setCurrentUser(User userID);
	
	/**
	 * Gets the currentUser's User object.
	 * 
	 * @return The current user's User object on success, null on failure.
	 */
	public User getCurrentUser();
	
	/* interactive mode */
	
	/**
	 * Creates a new album and adds it to the current user's list of albums.
	 * 
	 * @param albumName is the name of the new album
	 * @return True if the operation is successful, false if the album already exists.
	 */
	public boolean createAlbum(String albumName);
	
	
	/**
	 * Deletes an album from the current user's list of albums.
	 * 
	 * @param albumName is the name of the new album
	 * @return True if the operation is successful, false if the album doesn't exists.
	 */
	public boolean deleteAlbum(String albumName);
	
	
	/**
	 * Returns all the albums the current user has.
	 * 
	 * @return ArrayList of albums or null if the list is empty.
	 */
	public ArrayList<Album> getUserAlbums();
	
	
	/**
	 * Gets a list of photos inside of the album with the given name.
	 * 
	 * @return ArrayList of photo objects, null if the album doesn't exist.
	 */
	public ArrayList<Photo> getAlbumPhotos(String albumName);
	
	
	/**
	 * Adds a photo to the chosen album.
	 * 
	 * @param filename is the name of the photo
	 * @param caption is the caption to add to the photo
	 * @param albumName is the album to add the photo to
	 * @param pic is the pictures File object
	 * @return 1 = Success -1 = Photo already exists in the album -2 = Album doesn't exists
	 * @throws IOException 
	 */
	public int addPhoto(String filename, String caption, String albumName, File pic) throws IOException;
	
	/**
	 * Recaptions photo with a new caption.
	 * 
	 * @param fineName is the name of the picture to be recaptioned
	 * @param newCaption is the new caption
	 * @return return true on success, false on failure
	 */
	public boolean recaptionPhoto(String fileName, String newCaption);
	
	/**
	 * Moves a photo from one album to the other. If it already exists in the new Album the method does nothing.
	 * 
	 * @param photoName is the target photo name
	 * @param oldAlbum is the old Album name (Album to move from)
	 * @param newAlbum is the new Album name (Album to move to)
	 * @return 1 = Success -1 = Invalid old album -2 = Invalid new album -3 = Photo already exists in the new album -4 = Photo doesn't exist in the old album
	 */
	public int movePhoto(String photoName, String oldAlbum, String newAlbum);
	
	
	/**
	 * Removes a photo from an album.
	 * 
	 * @param fileName is the name of the photo
	 * @param albumName is the Album the photo is in
	 * @return 1 = Success -1 = Album doesn't exist -2 = Photo doesn't exist
	 */
	public int removePhoto(String fileName, String albumName);
	
	
	/**
	 * Adds a tag to a photo with a type and value.
	 * 
	 * @param fileName is the name of the photo
	 * @param tagType is the Tag's type
	 * @param tagValue is the Tag's value
	 * @return 1 = Success -1 = specified tag already exists -2 = failure (photo not found)
	 */
	public int addTag(String fileName, String tagType, String tagValue);
	
	
	/**
	 * Deletes a tag with a type and value from a specified photo.
	 * 
	 * @param filename of the photo
	 * @param tagType is the Tag's type
	 * @param tagValue is the Tag's value
	 * @return 1 = Success -1 = specified tag doesn't exists
	 */
	public int deleteTag(String fileName, String tagType, String tagValue);
	
	
	/**
	 * Returns a list of photos whose dates lie between the start and end date.
	 * 
	 * @param startDate is the starting date
	 * @param endDate is the ending date
	 * @return List of photos that lie in the date range specified (in chronological order) on success, null on failure.
	 */
	public ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException;
	
	
	/**
	 * Returns a list of photos with the given tag values.
	 * 
	 * @param keys is a list of tag type:values pairs
	 * @param tagsByVal is a list of values specifies in the input (with no tag type)
	 * @return List of photos with given tag values
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> keys, ArrayList<String> tagByVal);
	
	
	/**
	 * Finds the photo in the list of Albums and then returns it to be displayed by the calling method.
	 * 
	 * @param fileName is the name of the photo to be searched for
	 * @return the photo object with properly sorted tags on success, null on failure (photo doesn't exist)
	 */
	public Photo listPhotoInfo(String fileName);
	
	
	/**
	 * 	Tries to save the all user's information contained in the user list (albums, photos and tags) and then exits the program.
	 */
	public void logout();
	
	/**
	 * Returns the photo with the oldest date in a given album.
	 * 
	 * @param album to be searched
	 * @return name of the photo with the earliest date on success, null on failure.
	 */
	public String getStartDate(Album album);

	/**
	 * Returns the photo with the most recent date in a given album.
	 * 
	 * @param album to be searched
	 * @return name of the photo with the latest date on success, null on failure.
	 */
	public String getEndDate(Album album);
}
