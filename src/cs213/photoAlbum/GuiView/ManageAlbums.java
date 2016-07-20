package cs213.photoAlbum.GuiView;

/**
 * ManageAlbums
 * 
 * Allows non-admin users to create, delete, rename, open, and search albums
 * Also allows user to logout and return to the GeneralLogin screen
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 04-11-2015
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import cs213.photoAlbum.model.Album;

public class ManageAlbums extends JFrame implements Serializable{
	
	/**
	 * JList to display albums belonging to user
	 */
	public static JList display;
	/**
	 * Model to put JList in
	 */
	public static DefaultListModel model;
	/**
	 * Buttons to manage albums
	 */
	public static JButton create, delete, rename, open, search, logout, showInfo;
	
	/**
	 * the "Main Class"
	 * for testing purposes, not to be actually implemented
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		try {
			ManageAlbums frame = new ManageAlbums();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor
	 * 
	 */
	public ManageAlbums() {
		// TODO Auto-generated constructor stub
		super("ManageAlbums");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		
		setLayout(new GridLayout(2, 1));
		JPanel top = new JPanel(new GridLayout(1, 0, 0 ,0) );
		JPanel middle = new JPanel(new GridLayout(1, 2, 0 ,0) );
		JPanel mLeft = new JPanel(new GridLayout(3, 1, 0 ,0) );
		JPanel mRight = new JPanel(new GridLayout(3, 1, 0 ,0) );
		JPanel bottom = new JPanel(new BorderLayout());
		
		create = new JButton("create");
		delete = new JButton("delete");
		rename = new JButton("rename");
		open = new JButton("open");
		search = new JButton("search");
		logout = new JButton("logout");
		showInfo = new JButton("show album info");
		
		model = new DefaultListModel();
		display = new JList();
		display.setModel(model);
		display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		display.setVisibleRowCount(15);
		display.setSelectedIndex(0);
		
		//show albums
		listalbums();
		
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				 String newAlbum = JOptionPane.showInputDialog("Enter a name for the new album");
				 if(newAlbum !=null){
						//successful creation
						if(GuiView.control.createAlbum(newAlbum) == true){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp, "Created album for user " + GuiView.control.getCurrentUser().getID().toString() +": " + newAlbum);
						}
						//creation failed
						else{
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"album exists for user" + GuiView.control.getCurrentUser().getID().toString()+": " + newAlbum);
						}
				 }
				 else{
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Error: Album name not provided. Action Aborted.");
				 }
				 listalbums();
			}
		});
		
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				 String targetAlbum = null;
				try{
						targetAlbum = (String) model.getElementAt(display.getSelectedIndex());
					}
				 catch (ArrayIndexOutOfBoundsException e3){
						targetAlbum = null;
					}
				 if(targetAlbum !=null){
						//successful deletion
						if(GuiView.control.deleteAlbum(targetAlbum) == true){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp, "Deleted album for user " + GuiView.control.getCurrentUser().getID().toString() +": " + targetAlbum);
						}
						//deletion failed
						else{
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"album does not exist for user" + GuiView.control.getCurrentUser().getID().toString()+": " + targetAlbum);

						}
				 }
				 else{
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Error: Album name not provided. Action Aborted.");
				 }
				 listalbums();
			}	
		});
		
		rename.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ArrayList<Album> albums = GuiView.control.getUserAlbums();
				String targetAlbum;
				try{
					targetAlbum = (String) model.getElementAt(display.getSelectedIndex());
				}catch (ArrayIndexOutOfBoundsException e3){
					targetAlbum = null;
				}
				String newName = "";
				if(targetAlbum != null){
					newName = (String)JOptionPane.showInputDialog("Enter the new album name");
				}
				boolean rep = false;
				if(newName == null || newName.length() ==0){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Error: No rename provided. Action aborted");
				}
				else if(targetAlbum !=null){
					for(int i = 0; i< GuiView.control.getUserAlbums().size();i++){
						if(newName.equals(GuiView.control.getUserAlbums().get(i).getName())){
							rep = true;
						}
					}
					if(rep){
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Error: New name already exists. Action aborted");	
					}else{
						GuiView.control.createAlbum(newName);
						GuiView.control.getCurrentUser().getMyAlbums().get(newName).setPhotoList(
							GuiView.control.getCurrentUser().getMyAlbums().get(targetAlbum).getPhotos()		
							);
						GuiView.control.deleteAlbum(targetAlbum);
					}
				}
				else{
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Please select an album");
				}
				listalbums();
			}	
		});
		
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				try {
					
					String toOpen = null;
					try{
						toOpen = (String) model.getElementAt(display.getSelectedIndex());
					}catch (ArrayIndexOutOfBoundsException e3){
						toOpen = null;
					}
					if(toOpen != null){
						
						ArrayList<Album> albums = GuiView.control.getUserAlbums();
						int target = -1;
						for(int i = 0; i <albums.size(); i++){
							if(albums.get(i).getName().toLowerCase().equalsIgnoreCase(toOpen.toLowerCase())){
								target = 1;
							}
						}
						if(target == 1){
							ManageAlbum frame = new ManageAlbum(toOpen);
							frame.setVisible(true);
							frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
							dispose();
						}	else{
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Error: Invalid Input. Action Aborted.");
						}
					}else{
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Please Select an Album");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				try {
					Search frame = new Search();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
			}
		});
		
		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				//GuiView.save();
				GeneralLogin frame = new GeneralLogin();
				frame.setVisible(true);
				frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				dispose();
			}
		});
		
		showInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				ArrayList<Album> albums = GuiView.control.getUserAlbums();
				String targetAlbum;
				try{
					targetAlbum = (String) model.getElementAt(display.getSelectedIndex());
				}catch (ArrayIndexOutOfBoundsException e3){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Please Select an Album");
				}
				if(albums.get(display.getSelectedIndex()).getPhotos().size()> 0){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Title: " + albums.get(display.getSelectedIndex()).getName()+ ", Photos:"
							+ albums.get(display.getSelectedIndex()).getPhotos().size()+
							", From: "+ GuiView.control.getStartDate(albums.get(display.getSelectedIndex()))
							+ " - " + GuiView.control.getEndDate(albums.get(display.getSelectedIndex())));
					display.setModel(model);
				}
				else{
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Title: " + albums.get(display.getSelectedIndex()).getName() + ", No Photos");
				}
				
			}
		});
		
		
		
		JScrollPane listScrollPane = new JScrollPane(display);
		
		top.add(listScrollPane);
		
		mLeft.add(create);
		mLeft.add(delete);
		mLeft.add(rename);
		
		mRight.add(open);
		mRight.add(search);
		mRight.add(showInfo);

		middle.add(mLeft);
		middle.add(mRight);
		
		bottom.add(logout, BorderLayout.CENTER);
		middle.add(bottom);
		add(top);
		add(middle);
		//add(bottom);
	}
	
	public static void listalbums(){
		ArrayList<Album> albums = GuiView.control.getUserAlbums();
		if(albums == null){
			model.removeAllElements();
			model.addElement("No albums");
		}
		else{
			model.removeAllElements();
			for(int i = 0; i<albums.size(); i++){	
				model.addElement(albums.get(i).getName());
				/*if(albums.get(i).getPhotos().size()> 0){
					model.addElement("Title: " + albums.get(i).getName()+ ", Photos:"
							+ albums.get(i).getPhotos().size()+
							", From: "+ GuiView.control.getStartDate(albums.get(i))
							+ " - " + GuiView.control.getEndDate(albums.get(i)));
					display.setModel(model);
				}
				else{
					model.addElement("Title: " + albums.get(i).getName() + ", No Photos");
				}*/
			}
		}
		//GuiView.save();
	}
}
