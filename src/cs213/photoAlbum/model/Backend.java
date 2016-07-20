package cs213.photoAlbum.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Backend Class.
 * 
 * Primarily deals with saving and loading session info; also manages users
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 01-20-2015
 */

public class Backend implements Serializable, BackendInterface{
	/**
	 * HashMap of user IDs and users
	 */
	private HashMap<String, User> userDB = new HashMap<String, User>();
	/**
	 * number of users
	 */
	int numUsers;
	
	/**
	 * Default Constructor
	 * 
	 */
	public Backend() {
		
	}
	
	/**
	 * Reads user.data file to populate user database with users and all data associate with them
	 * @throws@throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public HashMap<String, User> readUsers() throws FileNotFoundException, IOException, ClassNotFoundException {		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.storeDir + File.separator + this.storeFile));
		//System.out.println("write");
		return (HashMap<String, User>) ois.readObject();
	}
	
	/**
	 * Write users (including all constituent user data) to storage from memory
	 * @throws IOException 
	 */
	public void writeUsers(HashMap<String, User> userDB) throws IOException{
		//System.out.println("write");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.storeDir + File.separator + this.storeFile));
		//System.out.println("write");
		oos.writeObject(userDB);
		
	}
	
	/**
	 * Delete a user, identified by user ID
	 * @param userID is the user ID
	 */
	public void deleteUser(String userID){
		if(this.userDB.containsKey(userID)){
			this.userDB.remove(userID);
		}
	}
	
	/**
	 * Adds a user, identified by user ID
	 * @param u is user to be added.
	 */
	public void addUser(User u){
		this.userDB.put(u.getID(), u);
	}
	
	/**
	 * Gets a user, identified by user IDs
	 * @param userID is the user ID
	 */
	public User getUser(String userID) {
		if(userDB.containsKey(userID)){
			return this.userDB.get(userID);
		}
		else
			return null;
	}
	
	/**
	 * @return a HashMaps of existing users
	 */
	public HashMap<String, User> getUserDB() {
		return this.userDB;
	}
	
	/**
	 * sets a HashMaps of users
	 * @param userDB is a hashmap of users of the format <userIDs, user>
	 */
	public void setUserDB(HashMap<String, User> userDB) {
		this.userDB = userDB;
	}
}