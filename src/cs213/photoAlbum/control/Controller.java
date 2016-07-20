package cs213.photoAlbum.control;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cs213.photoAlbum.GuiView.GuiView;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.util.DateComparator;
import cs213.photoAlbum.util.TagComparator;

/**
 * Controller class.
 * Responsible for all the nitty-gritty in the Photo Album application. Performs all data and logic related operations.
 * 
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 01-20-2015
 */

public class Controller implements ControlInterface {
	
	private User currentUser;

	/* Command Line Mode */
	
	/**
	 * Returns the ArrayList of users.
	 */
	public ArrayList<User> listUsers() {
		ArrayList<User> users = new ArrayList<User>(GuiView.backend.getUserDB().values());
		return users;
	}
	
	
	/**
	 * Adds a new user with the given ID and userName to the list of users.
	 * 
	 * @param ID is the user's ID
	 * @param userName is the user's name 
	 * @return True on success, false on failure (user already exists).
	 */
	public boolean addUser(String ID, String userName) {
		//System.out.println("here");
		//GuiView.backend.getUserDB();
		//System.out.println("here2");
		if (!GuiView.backend.getUserDB().containsKey(ID)) {
			User newUser = new User(ID, userName);
			GuiView.backend.addUser(newUser);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Deletes a user with the given string ID from the user list.
	 * 
	 * @param ID is the user's ID
	 * @return True on success, false on failure (user doesn't exists).
	 */
	public boolean deleteUser(String ID) {
		if (GuiView.backend.getUserDB().containsKey(ID)) {
			GuiView.backend.deleteUser(ID);
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Sets the global variable currentUser to the given userID.
	 * 
	 * @param ID is the user's ID
	 * @return True if successful (user exists), false otherwise.
	 */
	public boolean login(String ID) {
		if (GuiView.backend.getUserDB().containsKey(ID)) {
			GuiView.control.setCurrentUser(GuiView.backend.getUserDB().get(ID));
			//System.out.println("The current user is: " + currentUser.getID().toString());
			return true;
		} else {
			GuiView.control.setCurrentUser(null);
			//System.out.println("That userID does not exist in the database!");
			return false;
		}
	}
	
	/**
	 * Sets the global variable currentUser to the given userID.
	 * 
	 * @param userID is the user's ID
	 */
	public void setCurrentUser(User userID) {
		this.currentUser = userID;
	}
	
	/**
	 * Gets the currentUser's User object.
	 * 
	 * @return The current user's User object on success, null on failure.
	 */
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	/* Interactive Mode */
	
	/**
	 * Creates a new album and adds it to the current user's list of albums.
	 * 
	 * @param albumName is the name of the new album
	 * @return True if the operation is successful, false if the album already exists.
	 */
	public boolean createAlbum(String albumName) {
		if(GuiView.control.getCurrentUser() == null){
			JFrame temp = new JFrame();
			JOptionPane.showMessageDialog(temp, "user is null");
			return true;
		}
		if (!GuiView.control.getCurrentUser().getMyAlbums().containsKey(albumName)) {
			Album album = new Album(albumName, currentUser.getUserName(), currentUser.getID());
			GuiView.control.getCurrentUser().addAlbum(album);
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Deletes an album from the current user's list of albums.
	 * 
	 * @param albumName is the name of the new album
	 * @return True if the operation is successful, false if the album doesn't exists.
	 */
	public boolean deleteAlbum(String albumName) {
		if (GuiView.control.getCurrentUser().getMyAlbums().containsKey(albumName)) {
			GuiView.control.getCurrentUser().deleteAlbum(albumName);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns all the albums the current user has.
	 * 
	 * @return ArrayList of albums or null if the list is empty.
	 */
	public ArrayList<Album> getUserAlbums() {
		if (GuiView.control.getCurrentUser().getMyAlbums().size() != 0) {
			ArrayList<Album> albumList = new ArrayList<Album>(GuiView.control.getCurrentUser().getMyAlbums().values());
			return albumList;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Gets a list of photos inside of the album with the given name.
	 * 
	 * @return ArrayList of photo objects, null if the album doesn't exist.
	 */
	public ArrayList<Photo> getAlbumPhotos(String albumName) {
		if (GuiView.control.getCurrentUser().getMyAlbums().get(albumName) != null){
			ArrayList<Photo> photoList = new ArrayList<Photo>(GuiView.control.getCurrentUser().getMyAlbums().get(albumName).getPhotos().values());
			return photoList;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Adds a photo to the chosen album.
	 * 
	 * @param filename is the name of the photo
	 * @param caption is the caption to add to the photo
	 * @param albumName is the album to add the photo to
	 * @param pic is the pictures File object
	 * @return 1 = Success -1 = Photo already exists in the album -2 = Album doesn't exists -3 = File does not exist
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public int addPhoto(String filename, String caption, String albumName, File pic) throws IOException {
		Photo photo = new Photo(filename, caption, albumName, pic);
		
		if (GuiView.control.getCurrentUser().getMyAlbums().containsKey(albumName)) {
			Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<String, Album> albums = (Map.Entry<String, Album>)iterator.next();
				
				if (albums.getValue().getPhotos().containsKey(filename)) {
					for (int x = 0; x < albums.getValue().getPhotos().get(filename).getAlbums().size(); x++) {
						if (!photo.getAlbums().contains(albums.getValue().getPhotos().get(filename).getAlbums().get(x))) {
							photo.addAlbum(albums.getValue().getPhotos().get(filename).getAlbums().get(x));
						}
					}
					if (!albums.getValue().getPhotos().get(filename).getAlbums().contains(albumName)) {
						albums.getValue().getPhotos().get(filename).getAlbums().add(albumName);
					}
					if (!photo.getAlbums().contains(albumName)) {
						photo.addAlbum(albumName);
					}
				}
			}
			
			if (!GuiView.control.getCurrentUser().getMyAlbums().get(albumName).getPhotos().containsKey(filename)) {
				GuiView.control.getCurrentUser().getMyAlbums().get(albumName).addPhoto(photo);
				return 1;
			} else {
				return -1;
			}
		} else {
			return -2;
		}
	}
	
	/**
	 * Recaptions photo with a new caption.
	 * 
	 * @param fineName is the name of the picture to be recaptioned
	 * @param newCaption is the new caption
	 * @return return true on success, false on failure
	 */
	public boolean recaptionPhoto(String fileName, String newCaption) {
		int recaptionCount = 0;
		Collection<Album> albumCollection = GuiView.control.getCurrentUser().getMyAlbums().values();
		ArrayList<Album> albums = new ArrayList<Album>(albumCollection);
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		
		for (int x = 0; x < albums.size(); x++) {
			if (albums.get(x).getPhotos().containsKey(fileName)) {
				albums.get(x).getPhotos().get(fileName).setCaption(newCaption);
				recaptionCount++;
			}
		}
		
		if (recaptionCount == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * Moves a photo from one album to the other. If it already exists in the new Album the method does nothing.
	 * 
	 * @param photoName is the target photo name
	 * @param oldAlbum is the old Album name (Album to move from)
	 * @param newAlbum is the new Album name (Album to move to)
	 * @return 1 = Success -1 = Invalid old album -2 = Invalid new album -3 = Photo already exists in the new album -4 = Photo doesn't exist in the old album
	 */
	public int movePhoto(String photoName, String oldAlbum, String newAlbum) {
		Photo photo;
		
		if (GuiView.control.getCurrentUser().getMyAlbums().containsKey(oldAlbum) == false) {
			return -1;
		}
		if (GuiView.control.getCurrentUser().getMyAlbums().containsKey(newAlbum) == false) {
			return -2;
		}
		if (GuiView.control.getCurrentUser().getMyAlbums().get(oldAlbum).getPhotos().containsKey(photoName)) {
			if (GuiView.control.getCurrentUser().getMyAlbums().get(newAlbum).getPhotos().containsKey(photoName)){
				return -3;
			}
			
			Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<String, Album> albums = (Map.Entry<String, Album>)iterator.next();
				
				if (albums.getValue().getPhotos().containsKey(photoName)) {
					albums.getValue().getPhotos().get(photoName).deleteAlbum(oldAlbum);
					if (!albums.getValue().getPhotos().get(photoName).getAlbums().contains(newAlbum))
						albums.getValue().getPhotos().get(photoName).addAlbum(newAlbum);
				}
			}
			
			photo = GuiView.control.getCurrentUser().getMyAlbums().get(oldAlbum).getPhotos().get(photoName);
			GuiView.control.getCurrentUser().getMyAlbums().get(newAlbum).addPhoto(photo);
			GuiView.control.getCurrentUser().getMyAlbums().get(oldAlbum).deletePhoto(photoName);
			return 1;
		} else {
			return -4;
		}
	}
	
	
	/**
	 * Removes a photo from an album.
	 * 
	 * @param fileName is the name of the photo
	 * @param albumName is the Album the photo is in
	 * @return 1 = Success -1 = Album doesn't exist -2 = Photo doesn't exist
	 */
	@SuppressWarnings("unchecked")
	public int removePhoto(String fileName, String albumName) {
		if (GuiView.control.getCurrentUser().getMyAlbums().containsKey(albumName) == false) {
			return -1;
		}
		if (GuiView.control.getCurrentUser().getMyAlbums().get(albumName).getPhotos().containsKey(fileName)) {
			Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
			
			while (iterator.hasNext()) {
				Map.Entry<String, Album> albums = (Map.Entry<String, Album>)iterator.next();
				
				if (albums.getValue().getPhotos().containsKey(fileName)) {
					albums.getValue().getPhotos().get(fileName).deleteAlbum(albumName);
				}
			}
			
			GuiView.control.getCurrentUser().getMyAlbums().get(albumName).deletePhoto(fileName);
			return 1;
		} else {
			return -2;
		}
	}
	
	
	/**
	 * Adds a tag to a photo with a type and value.
	 * 
	 * @param filename of the photo
	 * @param tagType is the Tag's type
	 * @param tagValue is the Tag's value
	 * @return 1 = Success -1 = specified tag already exists -2 = failure (photo not found)
	 */
	@SuppressWarnings("unchecked")
	public int addTag(String fileName, String tagType, String tagValue) {
		Tag tag = new Tag(tagType, tagValue);
		Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
		
		StringBuilder sb = new StringBuilder();
		String key = "";
		sb.append(tag.getType());
		sb.append(":");
		sb.append(tag.getData());
		key = sb.toString();
		
		while (iterator.hasNext()) {
			Map.Entry<String, Album> albums = (Map.Entry<String, Album>)iterator.next();
			
			if (albums.getValue().getPhotos().containsKey(fileName)) {
				if (albums.getValue().getPhotos().get(fileName).getTags().containsKey(key)) {
					return -1;
				} else if (!albums.getValue().getPhotos().get(fileName).getTags().containsKey(tagValue)) {
					albums.getValue().getPhotos().get(fileName).addTag(tag);
					return 1;
				}
			}
		}
		
		return -2;
	}
	
	
	/**
	 * Deletes a tag with a type and value from a specified photo.
	 * 
	 * @param filename of the photo
	 * @param tagType is the Tag's type
	 * @param tagValue is the Tag's value
	 * @return 1 = Success -1 = specified tag doesn't exist for photo
	 */
	@SuppressWarnings("unchecked")
	public int deleteTag(String fileName, String tagType, String tagValue) {
		Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
		int deleteCounter = 0;
		
		StringBuilder sb = new StringBuilder();
		String key = "";
		sb.append(tagType);
		sb.append(":");
		sb.append(tagValue);
		key = sb.toString();
		
		while (iterator.hasNext()) {
			Map.Entry<String, Album> albums = (Map.Entry<String, Album>) iterator.next();
			
			if (albums.getValue().getPhotos().containsKey(fileName)) {
				if (albums.getValue().getPhotos().get(fileName).getTags().containsKey(key)) {
					albums.getValue().getPhotos().get(fileName).deleteTag(key);
					deleteCounter++;
				}
			}
		}
		
		if (deleteCounter == 0) {
			return -1;
		}
		
		return 1;
	}
	
	/**
	 * Returns a list of photos whose dates lie between the start and end date.
	 * 
	 * @param startDate is the starting date
	 * @param endDate is the ending date
	 * @return List of photos that lie in the date range specified (in chronological order) on success, null on failure.
	 */
	public ArrayList<Photo> getPhotosByDate(String startDate, String endDate) throws ParseException {
		Date start = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss").parse(startDate);
		Date end = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss").parse(endDate);
		Collection<Album> albumCollection = GuiView.control.getCurrentUser().getMyAlbums().values();
		ArrayList<Album> albums = new ArrayList<Album>(albumCollection);
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		
		for (int x = 0; x < albums.size(); x++) {
			Collection<Photo> photoCollection = albums.get(x).getPhotos().values();
			ArrayList<Photo> photos = new ArrayList<Photo>(photoCollection);
			
			for (int y = 0; y < photos.size(); y++) {
				if (photos.get(y).getCalendar().after(start) && photos.get(y).getCalendar().before(end)) {
					photoList.add(photos.get(y));
				}
			}
		}
		
		DateComparator dateComp = new DateComparator();
		
		Collections.sort(photoList, dateComp);
		return photoList;
	}
	
	
	/**
	 * Returns a list of photos with the given tag values.
	 * 
	 * @param keys is a list of tag type:values pairs
	 * @param tagsByVal is a list of values specifies in the input (with no tag type)
	 * @return List of photos with given tag values
	 */
	public ArrayList<Photo> getPhotosByTag(ArrayList<String> keys, ArrayList<String> tagByVal) {
		Collection<Album> albumCollection = GuiView.control.getCurrentUser().getMyAlbums().values();
		ArrayList<Album> albums = new ArrayList<Album>(albumCollection);
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		
		for (int x = 0; x < albums.size(); x++) {
			Collection<Photo> photoCollection = albums.get(x).getPhotos().values();
			ArrayList<Photo> photos = new ArrayList<Photo>(photoCollection);
			
			for (int y = 0; y < photos.size(); y++) {
				for (int z = 0; z < tagByVal.size(); z++) {
					if (photos.get(y).getTagsByVal().contains(tagByVal.get(z)) && !photoList.contains(photos.get(y))) {
						photoList.add(photos.get(y));
					}
				}
			}
			
			for (int y = 0; y < photos.size(); y++) {
				for (int z = 0; z < keys.size(); z++) {
					if (photos.get(y).getTags().containsKey(keys.get(z)) && !photoList.contains(photos.get(y))) {
						photoList.add(photos.get(y));
					}
				}
			}
		}
		
		DateComparator dateComp = new DateComparator();
		
		Collections.sort(photoList, dateComp);
		return photoList;
	}
	
	/**
	 * Finds the photo in the list of Albums and then returns it to be displayed by the calling method.
	 * 
	 * @param fileName is the name of the photo to be searched for
	 * return the photo object with properly sorted tags on success, null on failure (photo doesn't exist)
	 */
	@SuppressWarnings("unchecked")
	public Photo listPhotoInfo(String fileName) {
		Iterator iterator = GuiView.control.getCurrentUser().getMyAlbums().entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, Album> pairs = (Map.Entry<String, Album>) iterator.next();
			if (pairs.getValue().getPhotos().containsKey(fileName)) {
				Collection<Tag> tagCollection = pairs.getValue().getPhotos().get(fileName).getTags().values();
				ArrayList<Tag> tags = new ArrayList<Tag>(tagCollection);
				TagComparator tagComp = new TagComparator();
				
				Collections.sort(tags, tagComp);
				pairs.getValue().getPhotos().get(fileName).setTagsList(tags);
				return pairs.getValue().getPhotos().get(fileName);
			}
		}
		return null;
	}
	
	/**
	 * 	Tries to save the all user's information contained in the user list (albums, photos and tags) and then exits the program.
	 */
	public void logout() {
		try {
			GuiView.backend.writeUsers(GuiView.backend.getUserDB());
		} catch (IOException e) {
			//e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Returns the photo with the oldest date in a given album.
	 * 
	 * @param album to be searched
	 * @return name of the photo with the earliest date on success, null on failure.
	 */
	public String getStartDate(Album album) {
		Collection<Photo> photoCollection = album.getPhotos().values();
		ArrayList<Photo> photos = new ArrayList<Photo>(photoCollection);
		DateComparator dateComp = new DateComparator();
		
		Collections.sort(photos, dateComp);
		DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return dateForm.format(photos.get(0).getCalendar());
	}
	
	/**
	 * Returns the photo with the most recent date in a given album.
	 * 
	 * @param album to be searched
	 * @return name of the photo with the latest date on success, null on failure.
	 */
	public String getEndDate(Album album) {
		Collection<Photo> photoCollection = album.getPhotos().values();
		ArrayList<Photo> photos = new ArrayList<Photo>(photoCollection);
		DateComparator dateComp = new DateComparator();
		
		Collections.sort(photos, dateComp);
		DateFormat dateForm = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return dateForm.format(photos.get(photos.size() - 1).getCalendar());
	}
}