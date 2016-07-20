package cs213.photoAlbum.simpleview;

/**
 * CmdView Class.
 * Responsible for parsing input for the control as well as user management
 * Sends most logic handling to Controller object
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 01-20-2015
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.BackendInterface;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.model.Tag;
import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.control.ControlInterface;

public class CmdView {
	
	public static Backend backend = new Backend();
	public static Controller control = new Controller();
	
	/**
	 * the "Main Class"
	 * Launch the application.
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String args[]){
		
		//Command line args:
		String command = null;
		String userID = null;
		String userName = null;
		
		
		File theDir = new File("data");

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
		    //System.out.println("creating directory: ./data");
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		        
		        File userCheck = new File("./data/users");
		        
		        if (!userCheck.exists()){
		        	try {
						userCheck.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
		        }else{
		         //System.out.println("File already exists.");
		        }
		     } catch(SecurityException se){
		        //handle it
		     }        
		     if(result) {    
		    	 System.out.println("no users exist");
		     }
		  }else{
			  //System.out.println("Directory exists");
		  }
		
		//Backend stuff to load previous users
		try{
			CmdView.backend.setUserDB(backend.readUsers());
			//System.out.println("fuck all sh");
		} catch (FileNotFoundException e1){
			//System.out.println("e1");
		} catch (IOException e2){
			//System.out.println("e2");
		} catch (ClassNotFoundException e3){
			//System.out.println("e3");
		}
		
		if (args.length > 0){
			command = args[0];

			//list users
			if (command.equalsIgnoreCase("listusers")){
				ArrayList<User> users = new ArrayList<User>(control.listUsers());
				if(users.size() == 0){
					//System.out.println(users.size());
					//System.out.println("no users exist");
				}
				else{
					for(int i = 0; i<users.size(); i++){
						System.out.println(users.get(i).getID());
					}
				}
			}
			//add users
			else if (command.equalsIgnoreCase("adduser")){
				if(args.length ==3){
					userID = args[1];
					userName = args[2];
					//something to check if user exists already)
					if (control.addUser(userID, userName) == true){
						//System.out.println(userID);
						//System.out.println(userName);
						try{
							backend.writeUsers(backend.getUserDB());
						}catch (IOException e){
							System.out.println("Error: Unable to save new user");
						}
						
						System.out.println("created user "+ userID +" with name " + userName); 
					}
					else{
						System.out.println("user "+userID+" already exists with name " +userName);
					}	
				}
				else{
					System.out.println("Error: Invalid arguement number.");
				}
			}
			//delete users
			else if (command.equalsIgnoreCase("deleteuser")  == true){
				if(args.length ==2){
					userID = args[1];
					if(control.deleteUser(userID)){//delete user is successful
						try{
							backend.writeUsers(backend.getUserDB());
						}catch (IOException e){
							
							System.out.println("Error: Unable to save new user");
						}
						System.out.println("deleted user "+ userID );
					}
					else{
						System.out.println("user " + userID +" does not exist");
					}
				}
				else{
					System.out.println("Error: Invalid arguement number.");
				}
				
			}
			//login as an existing user
			else if (command.equalsIgnoreCase("login")  == true){
				if(args.length ==2){
					userID = args[1];
					if (control.login(userID)){//try to login user
						///interactiveMode activates  
						//System.out.println("Logged it");
						interactiveMode();
					}
					else{
						System.out.println("user "+ userID + " does not exist");
					}
					
				}
				else{
					System.out.println("Error: Invalid arguement number.");
				}
	
			}
			else{
				System.out.println("Error: Command not recognized");
			}
		}
		
		
	}
	
	public static void interactiveMode(){
		
		//Variables
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		boolean loginFlag = true;
		
		while(loginFlag){
			try{
				input = read.readLine();
				StringTokenizer st = new StringTokenizer(input);
				String[] tokens= new String[100];
				
				tokens[0] = st.nextToken();
				
				
				//command options
				if (tokens[0] != null){
					if(tokens[0].equalsIgnoreCase("createalbum")){
						try{
							//Get string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							
							//Must have name to assign album
							if(tokens[1] !=null){
								//successful creation
								if(control.createAlbum(tokens[1]) == true){
									System.out.println("created album for user " + control.getCurrentUser().getID().toString() +":");
             						System.out.println(tokens[1]);
								}
								//creation failed
								else{
									System.out.println("album exists for user" + control.getCurrentUser().getID().toString()+":");
             						System.out.println(tokens[1]);
								}
							}
							else{
								System.out.println("Error: Album name not provided");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Album name not provided");
						}
					}
					else if(tokens[0].equalsIgnoreCase("deletealbum")){
						//System.out.println("HERE");
						try{
							//Get string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							
							//Must have name to assign album
							if(tokens[1] !=null){
								//successful creation
								if(control.deleteAlbum(tokens[1]) == true){
									System.out.println("deleted album for user " + control.getCurrentUser().getID().toString() +":");
             						System.out.println(tokens[1]);
								}
								//deletion failed
								else{
									System.out.println("album does not exist for user" + control.getCurrentUser().getID().toString()+":");
             						System.out.println(tokens[1]);
								}
							}
							else{
								System.out.println("Error: Album name not provided");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Album name not provided");
						}
					}
					else if(tokens[0].equalsIgnoreCase("listalbums")){
						ArrayList<Album> albums = control.getUserAlbums();
						if(albums != null){
							System.out.println("Albums for user "+control.getCurrentUser().getID().toString() +":");
     						
     						for (int y = 0; y < albums.size(); y++){
     							if (albums.get(y).getPhotos().size() > 0){
     								System.out.println(albums.get(y).getName() + " number of photos: " +
     										albums.get(y).getPhotos().size()+", "+ control.getStartDate(albums.get(y)) +" - "+ control.getEndDate(albums.get(y)));
     							}
     							else{
     								System.out.println(albums.get(y).getName() + " number of photos: 0"+", "+ "MM/DD/YYYY-HH:MM:SS" + " - " +"MM/DD/YYYY-HH:MM:SS");
     							}
     							
     						}
						}
						else{
     						System.out.println("No albums exist for user " + control.getCurrentUser().getID().toString());
     					}
					}
					else if(tokens[0].equalsIgnoreCase("listphotos")){
						try{
							//Get string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							
							//Must have of assign album
							if(tokens[1] !=null){
								ArrayList<Photo> photos = control.getAlbumPhotos(tokens[1]);
								
								//successful retrieval of photos arraylist
								if(photos != null){
									System.out.println("photos for album " + tokens[1] +":");
             						for(int i = 0; i < photos.size(); i++){
             							System.out.println(photos.get(i).getName() + " - " + photos.get(i).getDateString());
             						}
								}
								//album doesn't exist
								else{
									System.out.println("Album "+ tokens[1] + " does not exist.");
								}
							}
							else{
								System.out.println("Error: Album name not provided");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Album name not provided");
						}
						
					}
					else if(tokens[0].equalsIgnoreCase("addphoto")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							//Get caption string within quotes
							tokens[2] = st.nextToken("\"");
							tokens[2] = st.nextToken("\"");
							//Get album name string within quotes
							tokens[3] = st.nextToken("\"");
							tokens[3] = st.nextToken("\"");
							
							//Must have of all fields specified
							if (tokens[1] != null && tokens[2] != null && tokens[3] != null){

								
								File pic = new File(tokens[1]);
								if(!pic.exists()){
									System.out.println("File " + tokens[1] + " does not exist");
								}
								else{
									ArrayList<Photo> photos = control.getAlbumPhotos(tokens[1]);
									int validIn = control.addPhoto(tokens[1], tokens[2], tokens[3], pic);
								
									//all valid fields found
									if(validIn == 1){
										System.out.println("Added photo "+ tokens[1]);
										System.out.println(tokens[2] + " - Album:"+ tokens[3]);
									}
									//duplicates exists
									else if (validIn == -3){
										System.out.println("Photo "+ tokens[1] + " already exists in album " + tokens[2]);
									}
									//No Album found
									else if(validIn == -2){
										System.out.println("Album "+ tokens[3] + " does not exist");
									}
								}
							}
							else{
								System.out.println("Error: Invalid arguements");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid arguements");
						}
					}
					else if(tokens[0].equalsIgnoreCase("movephoto")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							//Get old album within quotes
							tokens[2] = st.nextToken("\"");
							tokens[2] = st.nextToken("\"");
							//Get new album within quotes
							tokens[3] = st.nextToken("\"");
							tokens[3] = st.nextToken("\"");
							
							//Must have of all fields specified
							if (tokens[1] != null && tokens[2] != null && tokens[3] != null){
								
								File pic = new File("NULL");
								
								ArrayList<Photo> photos = control.getAlbumPhotos(tokens[1]);
								int validIn = control.movePhoto(tokens[1], tokens[2], tokens[3]);
								
								//all valid fields found
								if(validIn == 1){
									System.out.println("Moved photo " + tokens[1] + ":");
         							System.out.println(tokens[1] + " - From album " + tokens[2] + " to album "+ tokens[3]);
								} else if (validIn == -1){
									System.out.println("Error: album "+ tokens[2] + " does not exist.");
								} else if (validIn == -2){
									System.out.println("Error: album "+ tokens[3] + " does not exist.");
								} else if (validIn == -3){
									System.out.println("Error: photo already exists in album: " + tokens[3]);
								} else {
     								System.out.println("Photo "+tokens[1]+" does not exist in "+ tokens[2]);
								}
							}
							else{
								System.out.println("Error: Invalid arguements");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid arguements");
						}
					}
					else if(tokens[0].equalsIgnoreCase("removephoto")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							//Get album name string within quotes
							tokens[2] = st.nextToken("\"");
							tokens[2] = st.nextToken("\"");
							
							//Must have of all fields specified
							if (tokens[1] != null && tokens[2] != null){
								int returnVal = control.removePhoto(tokens[1], tokens[2]);
								
								//all valid fields found
								if(returnVal == 1) {
									System.out.println("Removed photo:");
	      							System.out.println(tokens[1] + " - From album " + tokens[2]);
								} else if (returnVal == -1) {
									 System.out.println("Album "+ tokens[2] + " does not exist");
								} else if (returnVal == -2) {
									 System.out.println("File "+ tokens[1] + " does not exist");
								}
							} else {
								System.out.println("Error: Invalid arguements");
							}
							//;
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid arguements");
						}
					}
					else if(tokens[0].equalsIgnoreCase("addtag")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							//Get tag type 
							tokens[2] = st.nextToken(":");
							 tokens[2] = tokens[2].replace("\"", " ");
							 tokens[2] =  tokens[2].trim().toLowerCase();
							//Get tag value 
							 tokens[3] = st.nextToken("\"");
							 tokens[3] = st.nextToken("\"");
							 tokens[3] = tokens[3].trim().toLowerCase();
							
			
							//Must have of all fields specified
							if (tokens[1] != null && tokens[2] != null && tokens[3] != null){
								
								//System.out.println(tokens[1]+tokens[2]+tokens[3]);
								int result = control.addTag(tokens[1], tokens[2], tokens[3] );
      							
      							if (result == 1){
      								System.out.println("Added Tag:");
      								System.out.println(tokens[1] + " " + tokens[2] + ":" + tokens[3]);
      							}else if (result == -1){
      								System.out.println("Tag already exists for " + tokens[1] + " " + tokens[2] + ":"+ tokens[3] );
      							}else{
      								System.out.println("Photo "+ tokens[2]+" does not exist ");
      							}
								
							}
							else{
								System.out.println("Error: Invalid format of arguements");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid format of arguements");
						}	
					}
					else if(tokens[0].equalsIgnoreCase("deletetag")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							//Get tag type 
							tokens[2] = st.nextToken(":");
							 tokens[2] = tokens[2].replace("\"", " ");
							 tokens[2] =  tokens[2].trim().toLowerCase();
							//Get tag value 
							 tokens[3] = st.nextToken("\"");
							 tokens[3] = st.nextToken("\"");
							 tokens[3] = tokens[3].trim().toLowerCase();
							
							//Must have of all fields specified
							if (tokens[1] != null && tokens[2] != null && tokens[3] != null){
								
								int result = control.deleteTag(tokens[1], tokens[2], tokens[3] );
      							
      							if (result == 1){
      								System.out.println("Deleted tag:");
      								System.out.println(tokens[1] + " " + tokens[2] +":" + tokens[3]);
      							}else if (result == -1){
      								System.out.println("Tag does not exist for "+ tokens[1] + " " + tokens[2] +":" + tokens[3]);
      							}
								
							}
							else{
								System.out.println("Error: Invalid format of arguements");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid format of arguements");
						}	
					}
					else if(tokens[0].equalsIgnoreCase("listphotoinfo")){
						try{
							//Get photo name string within quotes
							tokens[1] = st.nextToken("\"");
							tokens[1] = st.nextToken("\"");
							
							if (tokens[1] != null){
								
								Photo photo;
	     						photo = control.listPhotoInfo(tokens[1]);
	     						if (photo != null){
	     							System.out.println("Photo file name:"+photo.getName());
	     							System.out.print("Album:");
	     							
	     							for (int i = 0; i < photo.getAlbums().size(); i++){
	     								System.out.print(photo.getAlbums().get(i));
	     								
	     								if (i != photo.getAlbums().size()-1){
	     									System.out.print(", ");
	     								}
	     								
	     							}
	     							System.out.println();
	     							
	     							System.out.println("Date: "+ photo.getDateString());
	     							System.out.println("Caption: " + photo.getCaption());
	     							System.out.println("Tags:");
	     							for (int i = 0; i < photo.getSortedTags().size(); i++){
	     								System.out.println(photo.getSortedTags().get(i).getType() + ":" + photo.getSortedTags().get(i).getData());
	     							}
	     						}else{
	     							System.out.println("Photo "+tokens[1]+" does not exist");
	     						}
								
							}
							else{
								System.out.println("Error: Invalid arguements");
							}
						}
						catch(NoSuchElementException e){
							System.out.println("Error: Invalid arguements");
						}	
					}
					else if (tokens[0].equalsIgnoreCase("getphotosbydate")){
    					 
     					try {
      						 tokens[1] = st.nextToken();
      						tokens[2] = st.nextToken();
      						 //System.out.println(argumentsArray[1]);
      						 
      						 if (tokens[1] != null && tokens[2] != null){
          						if (validateDateFormat(tokens[1]) == true && validateDateFormat(tokens[2]) == true){
          							
          							ArrayList<Photo> photos = new ArrayList<Photo>();
          							try {
     									photos = control.getPhotosByDate(tokens[1], tokens[2]);
     									System.out.println("Photos for user "+control.getCurrentUser().getID().toString()+ " in range "+
     											tokens[1] + " to " + tokens[2]);
     									for (int i = 0; i < photos.size(); i++){
     										System.out.print(photos.get(i).getCaption()+"- Album:");
     										for (int x = 0; x < photos.get(i).getAlbums().size(); x++){
     											System.out.print(photos.get(i).getAlbums().get(x));
     											if (x != photos.get(i).getAlbums().size()-1){ System.out.print(", "); }
     										}
     										System.out.println(" - Date:"+photos.get(i).getDateString());
     									}
     								} catch (ParseException e) {
     									//e.printStackTrace();
     									System.out.println("Error: could not read date.");
     								}
          						
          						}else{
          							System.out.println("Error: The dates were not entered in the correct format.");
          						}
          					 }else{
         						 System.out.println("Error: Start date and end date must both be entered.");
         					 }
     					} catch (NoSuchElementException e){
     						System.out.println("Error: Start date and end date must both be entered.");
     					}
     				 }
					
					else if (tokens[0].equalsIgnoreCase("getphotosbytag")){
    					ArrayList<String> tagArray = new ArrayList<String>();
    					String searchString = input;
    					searchString = searchString.substring(14);
    					 
    					try {
    						//Adds all tags to tagArray
							while (st.hasMoreTokens()){
								tagArray.add(st.nextToken(","));	
	 						}
								
							ArrayList<String> tagFull = new ArrayList<String>();
							ArrayList<String> tagLess = new ArrayList<String>();
							for (int i = 0; i < tagArray.size(); i++) {
								if (tagArray.get(i).contains(":")) {
									String s = tagArray.get(i).substring(0, tagArray.get(i).indexOf(":")  ) + ":" + tagArray.get(i).substring(tagArray.get(i).indexOf(":") + 2, tagArray.get(i).length() - 1);
									s = s.trim().toLowerCase();
									//System.out.println(s);
									tagFull.add(s);		
								}
								else {
									//tagArray.get(i).trim().substring(1, tagArray.get(i).trim().length() - 1)
									//st.nextToken("\"");
									//String s = st.nextToken("\"").toLowerCase();
									String s =tagArray.get(i).trim().substring(1, tagArray.get(i).trim().length() - 1).toLowerCase();
									//System.out.println(s);
									tagLess.add(s);
								}
							}				
							 
							ArrayList<Photo> photos = new ArrayList<Photo>();
							
							
							photos = control.getPhotosByTag(tagFull, tagLess);
							if (photos != null){
								//Photos for user <user id> with tags <search string>: 
								//	<caption> - Album: <albumName>[,<albumName>]... - Date: <date> 
								System.out.println("Photos for user "+control.getCurrentUser().getID().toString()+ " with tags "+searchString+":");
								//System.out.println("photos size = " + photos.size());
								for (int i = 0; i < photos.size(); i++){
									System.out.print(photos.get(i).getCaption()+"- Album:");
									for (int x = 0; x < photos.get(i).getAlbums().size(); x++){
										System.out.print(photos.get(i).getAlbums().get(x));
										if (x != photos.get(i).getAlbums().size()-1){
											System.out.print(", ");
											}
									}
									System.out.println(" - Date:"+photos.get(i).getDateString());
								}
							}else{
								//nothing
							}
							 
						}catch (NoSuchElementException e){
							System.out.println("Error: Invalid input. Tags not in the correct format.");
						} 
				 
    				}else if (tokens[0].equalsIgnoreCase("recaption")){
					 try{
						 //Photoname path
 						 tokens[1] = st.nextToken("\"");
     					 tokens[1] = st.nextToken("\"");
     					
     					 //New Caption
     					 tokens[2] = st.nextToken("\"");
     					 tokens[2] = st.nextToken("\"");

     					 boolean result = control.recaptionPhoto(tokens[1], tokens[2]);
     					 if(result){
     						System.out.println("Photo " +tokens[1] + " recaptioned to: " + tokens[2]);
     						
     					 }
     					 else{
     						System.out.println("Error recaptioning photo.");
     					 }
					 }catch (NoSuchElementException e){
						 System.out.println("Error: invalid number of arguments."); 
					 }
				}
					else if(tokens[0].equalsIgnoreCase("logout")){
						control.logout();
					}
					else{
						System.out.println("Error: Invalid input");
					}
					
				}
				else{
					System.out.println("Error: Invalid input");
				}
			} catch (IOException e1){
				System.out.println("Error: Invalid input");
			} catch (NoSuchElementException e2){
				System.out.println("Error: Invalid input");
			}
		}
		
	}
	
	/**
	 * Checks that input dates are of the format: MM/DD/YYYY-HH:MM:SS
	 * DOES NOT CHECK IF DATE IS VALID, I.E. if Feb 31 is a valid date
	 * @param date is inputed date
	 * @returns true if date is of the correct format, false otherwise
	 */
	public static boolean validateDateFormat(String date){
		
		int month = 0, day = 0, year = 0, hour = 0 , minute = 0, seconds = 0;
		
		//Checks for correct separators
		if(date.length() != 19){
			return false;
		}
		if(date.charAt(2) != '/'){
			return false;
		}
		if(date.charAt(5) != '/'){
			return false;
		}
		if(date.charAt(10) != '-'){
			return false;
		}
		if(date.charAt(13) != ':'){
			return false;
		}
		if(date.charAt(16) != ':'){
			return false;
		}
		
		//check that what is in between separators are numbers
		try{
			month = Integer.parseInt(date.substring(0, 2));
			day = Integer.parseInt(date.substring(3, 5));
			year = Integer.parseInt(date.substring(6, 10));
			hour = Integer.parseInt(date.substring(11, 13));
			minute = Integer.parseInt(date.substring(14, 16));
			seconds = Integer.parseInt(date.substring(17, 19));
			if(month <1 || month >12){
				return false;
			}
			else if(day < 1 || day>31){
				if((month == 4 ||
						month == 6 ||
						month == 9 ||
						month == 11) && (day > 30)){
					return false;
				}
				else if(month == 2){
					if(day == 29){
						int leap = Math.abs(2016 - year);
						if(leap%4 != 0){
							return false;
						}
					}else if(day > 29){
						return false;
					}
				}
			}
			else if(year < 0){
				return false;
			}
			else if(hour < 0 || hour > 23){
				return false;
			}
			else if(minute < 0 || minute > 59){
				return false;
			}
			else if(seconds < 0 || seconds > 59){
				return false;
			}
			
			
			
		}
		catch (NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Checks that input contains a tag, marked by ':'
	 * @return true if tag exists in inputt, false otherwise
	 */
	public static boolean tagExists(String input){
		if(input.contains(":")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * parses 'type field' from input tag, assuming that first ':'separates 'type' from 'value' 
	 * @param input
	 * @return the parsed type
	 */
	public static String parseType(String input){
		int i = 0;
		String parsedType;
		for (i = 0; i < input.length(); i++){
			if (input.charAt(i) == ':'){
				break;
			}
		}
		
		parsedType = input.substring(0, i);
		return parsedType;
	}
	
	/**
	 * parses 'value field' from input tag, assuming that first ':'separates 'type' from 'value' 
	 * @param input
	 * @return the parsed value
	 */
	public static String parseValue(String input){
		int i = 0;
		String parsedVal;
		for (i = 0; i < input.length(); i++){
			if (input.charAt(i) == ':'){
				break;
			}
		}
		
		parsedVal = input.substring(i+1, input.length());
		return parsedVal;
	}
}