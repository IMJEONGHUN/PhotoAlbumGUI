package cs213.photoAlbum.GuiView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

public class ManageAlbum extends JFrame implements Serializable{
	public static JList<Photo> photos;
	public static String albumName;
	public static JButton addPhoto;
	public static JButton removePhoto;
	public static JButton tagPhoto;
	public static JButton recaptionPhoto;
	public static JButton movePhoto;
	public static JButton startSlideshow;
	public static JButton close;
	
	public ManageAlbum(String albumname) {
		super("Manage Album " + albumname);
		
		setLayout(new GridLayout(2, 1));
		setBounds(100, 100, 500, 500);
		JPanel photosPanel = new JPanel(new GridLayout(1, 1));
		JPanel otherPanel = new JPanel(new GridLayout(1, 3));
		JPanel photoOptionsPanel = new JPanel(new GridLayout(4, 1));
		JPanel otherOptionsPanel = new JPanel(new GridLayout(3, 1));
		
		
		albumName = albumname;
		addPhoto = new JButton("Add Photo");
		removePhoto = new JButton("Remove Photo");
		tagPhoto = new JButton("Tag Photo");
		recaptionPhoto = new JButton("Re-Caption Photo");
		movePhoto = new JButton("Move Photo");
		startSlideshow = new JButton("Start Slideshow");
		close = new JButton("Close");
		
		Renderer rend = new Renderer(albumname);
		this.photos = rend.list;
	    JScrollPane scroll = new JScrollPane(photos);
	    scroll.setPreferredSize(new Dimension(300, 400));
		
		//photos.setCellRenderer(new MyCellRenderer());
		/*Album album = GuiView.control.getCurrentUser().getMyAlbums().get(albumName);
		try{
			Photo [] p = new Photo [album.getPhotos().values().size()];
			photos.setListData(p);
		}catch(NullPointerException e){
			
		}
		updateDisplay(GuiView.control.getCurrentUser().getMyAlbums().get(albumName));
		*/
	        
	        
	        
	        
		rend.list.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	Photo selectedItem = GuiView.control.getAlbumPhotos(albumName).get(photos.getSelectedIndex());
		        	DisplayPhoto window = new DisplayPhoto(albumName, selectedItem);
		        	window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		        	window.setVisible(true);
		         }
		    }
		});
		
		recaptionPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String photoName = JOptionPane.showInputDialog("Enter the photo name:");
			    String caption = JOptionPane.showInputDialog("Enter photo caption:");
			    try{
				    
			    	boolean result = GuiView.control.recaptionPhoto(photoName, caption);
					 if(result){
						JFrame dialog = new JFrame("Success!");
						JOptionPane.showMessageDialog(dialog, "Photo " +photoName + " recaptioned to: " + caption);
						
					 }
					 else{
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Error recaptioning photo!");
					 }
			    }catch(NullPointerException e3){
			     	JFrame dialog = new JFrame("Error!");
					JOptionPane.showMessageDialog(dialog, "Error: Invalid input");
			    }
			}
		});
		
		addPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String input = JOptionPane.showInputDialog("Enter the photo name:");
				String photoName;
				
				if (!input.contains("/") && !input.contains("\\")) {
					photoName = "./data/" + input;
				} else {
					photoName = input;
				}
				
		       // String albumName = JOptionPane.showInputDialog("Enter album name:");
		        String caption = JOptionPane.showInputDialog("Enter photo caption:");
		        File pic = null;
		        try{
		        	pic = new File(photoName);
		        	if(!pic.exists()){
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "File does not exist!");
					}
					else{
						int result = 0;
						try {
							result = GuiView.control.addPhoto(photoName, caption, albumName, pic);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					
						//duplicates exists
						if (result == -1){
							JFrame dialog = new JFrame("Error!");
							JOptionPane.showMessageDialog(dialog, "Photo "+ photoName + " already exists in album " + albumName + "!");
						}
						//No Album found
						else if(result == -2){
							JFrame dialog = new JFrame("Error!");
							JOptionPane.showMessageDialog(dialog, "Album "+ albumName + " does not exist!");
						}
					}
					updateDisplay(GuiView.control.getCurrentUser().getMyAlbums().get(albumName));
		        }catch(NullPointerException e2){
		        	JFrame dialog = new JFrame("Error!");
					JOptionPane.showMessageDialog(dialog, "Error: Invalid input");
		        }
				
            }
		});
		
		/*removePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				GuiView.control.removePhoto(photos.getSelectedValue().getName(), albumName);
				updateDisplay(GuiView.control.getCurrentUser().getMyAlbums().get(albumName));
            }
		});*/
		
		removePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					String photoName = JOptionPane.showInputDialog("Enter the photo name:");
					//Must have of all fields specified
					if (photoName != null && albumName != null){
						int returnVal = GuiView.control.removePhoto(photoName, albumName);
						
						//all valid fields found
						if(returnVal == 1) {
							JFrame dialog = new JFrame();
							JOptionPane.showMessageDialog(dialog,"Removed Photo:"+ photoName + " - From album " + albumName);
						} else if (returnVal == -1) {
							JFrame dialog = new JFrame("Error!");
							JOptionPane.showMessageDialog(dialog, "Album "+ albumName + " does not exist");
						} else if (returnVal == -2) {
							JFrame dialog = new JFrame("Error!");
							JOptionPane.showMessageDialog(dialog, "File "+ photoName + " does not exist");
						}
						updateDisplay(GuiView.control.getCurrentUser().getMyAlbums().get(albumName));
					} else {
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Invalid Inputs");
					}
				}
				catch(NullPointerException e5){
					JFrame dialog = new JFrame("Error!");
					JOptionPane.showMessageDialog(dialog, "Error: Invalid input");
				}
            }
		});
		
		tagPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try{
					TagWindow window = new TagWindow(albumName, rend.photos.get(photos.getSelectedIndex()).getName());
					window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					window.setVisible(true);
					dispose();
				}catch(ArrayIndexOutOfBoundsException e5){
					JFrame dialog = new JFrame("Error!");
					JOptionPane.showMessageDialog(dialog, "Please select a photo");
				}
            }
		});
		
		movePhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				MovePhoto window = new MovePhoto(albumName);
				window.pack();
				window.setResizable(false);
				window.setLocationRelativeTo(null);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		
		startSlideshow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Photo> photos = new ArrayList<Photo>(GuiView.control.getCurrentUser().getMyAlbums().get(albumName).getPhotos().values());
				DisplaySequence window = new DisplaySequence(albumName, photos);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
            }
		});
		
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ManageAlbums window = new ManageAlbums();
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		
		photosPanel.add(scroll);
		photoOptionsPanel.add(addPhoto);
		photoOptionsPanel.add(removePhoto);
		photoOptionsPanel.add(tagPhoto);
		photoOptionsPanel.add(recaptionPhoto);
		otherOptionsPanel.add(movePhoto);
		otherOptionsPanel.add(startSlideshow);
		otherOptionsPanel.add(close);
		otherPanel.add(photoOptionsPanel);
		otherPanel.add(otherOptionsPanel);
		
		//otherPanel.setPreferredSize(new Dimension(500, 200));
		
		add(photosPanel);
		add(otherPanel);
	}
	
	public void updateDisplay(Album album) {
		Photo [] p = new Photo [album.getPhotos().values().size()];
		
		for(int i =0; i < album.getPhotos().values().size(); i++){
			p[i] = (Photo) album.getPhotos().values().toArray()[i];
		}
	
		photos.setListData(p);
		//GuiView.save();
	}
	
	public static void main(String[] args) {
		ManageAlbum test = new ManageAlbum("test");
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}