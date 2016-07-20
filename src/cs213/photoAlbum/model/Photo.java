package cs213.photoAlbum.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Photo Class.
 * Represents the photo object, stores albums photo is located in, contains time data
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 01-20-2015
 */

public class Photo implements Serializable{

	/**
	 * Name of the Photo
	 */
	private String name;
	/**
	 * Absolute pathname of the Photo
	 */
	private String absPathName;
	/**
	 * Caption of Photo
	 */
	private String caption;
	/**
	 * Date photo was Taken
	 */
	private Calendar date;
	/**
	 * Time photo was Taken
	 */
	private Date time;
	/**
	 * The file corresponding to the object
	 */
	private File pic;
	/**
	 * The image for this pic
	 */
	//private ImageIc image;
	/**
	 * The imageicon for this pic
	 */
	private ImageIcon icon;
	/**
	 * Tags added to each photo
	 */
	private HashMap<String, Tag> tags;
	/**
	 * Tags added to each photo by value
	 */
	private ArrayList<String> tagsByVal;
	/**
	 * Sorted tags List
	 */
	private ArrayList<Tag> sortedTags;
	/**
	 * Albums the photo is located in
	 */
	private ArrayList<String> albums;
	
	

	/**
	 * Default Constructor
	 */
	public Photo() {
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param name is the name of the photo
	 * @param caption is the caption of the photo
	 * @param date is the time and date the photo was created
	 * @param pic is the reference to the actual file in the future.
	 * @throws IOException 
	 */
	public Photo(String name, String caption, String album, File pic) throws IOException {
		this.name = name;
		this.absPathName = pic.getCanonicalPath();
		this.caption = caption;
		icon = new ImageIcon(this.absPathName);
		//image = icon.getImage();
		//icon = new ImageIcon(image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
		albums = new ArrayList <String>();
		albums.add(album);
		date = new GregorianCalendar();
		//date.set(Calendar.MILLISECOND,0);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		String s = sdf.format(pic.lastModified());
		try {
			this.time = sdf.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//date.getTime();
		this.pic = pic;
		//System.out.println("yup");
		this.tags= new HashMap<String,Tag>();
		this.tagsByVal = new ArrayList<String>();

	}
	
	/**
	 * set a Photo's name
	 * @param nName is the edited name of the photo
	 */
	public void setName(String nName) {
		this.name = nName;
	}
	
	/**
	 * get a Photo's name
	 * @return the name of the photo
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * get a Photo's path
	 * @return the name of the photo
	 */
	public String getPath() {
		return this.absPathName;
	}
	
	/**
	 * get a Photo's file
	 * @return the name of the photo
	 */
	public File getFile() {
		return this.pic;
	}
	
	/**
	 * get a Photo's icon
	 * @return the name of the photo
	 */
	public ImageIcon getIcon() {
		return this.icon;
	}
	
	/**
	 * get a Photo's image
	 * @return the name of the photo
	 */
	/*public Image getImage() {
		return this.image;
	}
	*/
	/**
	 * Edit a Photo's caption
	 * @param caption is the edited caption of the photo
	 */
	public void setCaption(String nCaption) {
		this.caption = nCaption;
	}
	
	/**
	 * Get a Photo's caption
	 * @return the caption of the photo
	 */
	public String getCaption() {
		return this.caption;
	}
	/**
	 * adds what album the photo is in
	 * @param nAlbum is the new album the photo is being placed in
	 */
	public void addAlbum(String nAlbum) {
		this.albums.add(nAlbum);
	}
	
	/**
	 * removes what album the photo is in
	 * @param album is the album the photo is being removed from
	 */
	public void deleteAlbum(String album) {
		albums.remove(album);
	}
	
	/**
	 * gets the list of albums the photo is contained in
	 * @returns the list of albums the photo is contained in
	 */
	public ArrayList<String> getAlbums() {
		return this.albums;
	}
	
	/**
	 * sets the list of albums the photo is contained in
	 */
	public void setAlbums() {
		this.albums = albums;
	}
	/**
	 * Get a Photo's calendar
	 * @return the date of the photo
	 */
	public Date getCalendar() {
		return this.time;
	}
	
	/**
	 * Get a Photo's calendar as a string, formatted as specified in the spec
	 * @return the date of the photo
	 */
	public String getDateString(){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		return df.format(this.time);
	}
	
	/**
	 * gets the list of tags the photo is contained in
	 * @returns the list of tags the photo is contained in
	 */
	public HashMap<String, Tag> getTags() {
		/*System.out.println("we are here");
		if(tags == null){
			System.out.println("we are null");
		}*/
		
		return this.tags;
	}
	
	/**
	 * gets the list of tags the photo is contained in sorted by value
	 * @returns the a HashMap of tags the photo is contained in sorted by value
	 */
	public ArrayList<String> getTagsByVal() {
		return this.tagsByVal;
	}

	/**
	 * sets the list of tags the photo is contained in
	 * @returns the list of albums the photo is contained in
	 */
	public void setTags(HashMap<String, Tag> tags) {
		this.tags = tags;
	}
	
	
	/* 
	 * Print a Photo's information
	 * @param fileName is the user input fileName
	 *
	public void listInfo(String fileName) {
		System.out.println("Photo file name: " + this.name); 
		System.out.print("Album: ");
		for(int i = 0; i <this.albums.size(); i++){
			System.out.print(this.albums.get(i) + ", ");
		}
		System.out.println();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
		System.out.println("Date: " + df.format(this.date));
		System.out.println("Caption: " + this.caption); 
		System.out.println("Tags: ");
		for(int i = 0; i <this.tags.size(); i++){
			System.out.println(this.tags.get(i));
		}
	}*/
	
	/**
	 * addsTag
	 * @param t is the tag object to be added.
	 */
	public void addTag(Tag t) {
		StringBuilder sb = new StringBuilder();
		String key = "";
		sb.append(t.getType());
		sb.append(":");
		sb.append(t.getData());
		key = sb.toString();
		this.tags.put(key, t);
		
		if (!this.tagsByVal.contains(t.getData())) {
			this.tagsByVal.add(t.getData());
		}
	}
	
	/**
	 * deleteTag
	 * @param t is the tag to be deleted
	 */
	public void deleteTag(String t) {
		if(this.tags.containsKey(t)){
			this.tags.remove(t);
		}
	}
	
	/**
	 * sets list of tags sorted by type and value
	 * @return a list of sorted lag
	 */
	public void setTagsList(ArrayList<Tag> tagsList) {
		this.sortedTags = tagsList;
	}
	
	/**
	 * returns list of tags sorted by type and value
	 * @return a list of sorted lag
	 */
	public ArrayList<Tag> getSortedTags() {
		return this.sortedTags;
	}
		
}