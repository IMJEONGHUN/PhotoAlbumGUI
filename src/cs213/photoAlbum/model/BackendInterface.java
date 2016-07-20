package cs213.photoAlbum.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
/**
 * Backend Class Interface.
 * 
 * Primarily deals with saving and loading session info; also manages users
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 01-20-2015
 */
public interface BackendInterface {
	/**
	 * variables to store data
	 */
	public final String storeDir = "data";
	/**
	 * Stored data file
	 */
	public final String storeFile = "users";
	
	/**
	 * Reads user.data file to populate user database with users and all data associate with them
	 * @throws@throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public HashMap<String, User> readUsers() throws FileNotFoundException, IOException, ClassNotFoundException;
	
	/**
	 * Write users (including all constituent user data) to storage from memory
	 * @throws IOException 
	 */
	public void writeUsers(HashMap<String, User> userDB) throws IOException;
	
	/**
	 * Delete a user, identified by user ID
	 * @param userID is the user ID
	 */
	public void deleteUser(String userID);
	
	/**
	 * Adds a user, identified by user ID
	 * @param u is user to be added.
	 */
	public void addUser(User u);
	
	/**
	 * Gets a user, identified by user IDs
	 * @param userID is the user ID
	 */
	public User getUser(String userID);
	
	/**
	 * @return a HashMaps of existing users
	 */
	public HashMap<String, User> getUserDB() ;
	
	/**
	 * sets a HashMaps of users
	 * @param userDB is a hashmap of users of the format <userIDs, user>
	 */
	public void setUserDB(HashMap<String, User> userDB);
}