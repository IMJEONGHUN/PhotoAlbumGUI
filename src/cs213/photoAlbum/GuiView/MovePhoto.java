package cs213.photoAlbum.GuiView;
/**
 * MovePhoto
 * 
 * Allows for the movement of photos from one album to another
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 04-11-2015
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.model.Photo;

public class MovePhoto extends JFrame implements Serializable{
	/**
	 * albumname used to remember which album is being manages
	 */
	public static String albumName;
	/**
	 * JLabels to label textfields 
	 */
	public static JLabel pInfo, a1Info, a2Info;
	/**
	 * Textfields to accept input
	 */
	public static JTextField photo, album1, album2;
	/**
	 * buttons to trigger functionality
	 */
	public static JButton confirm, cancel;
	
	/**
	 * the "Main Class"
	 * for testing purposes, not to be actually implemented
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		try {
			MovePhoto frame = new MovePhoto("albumname");
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor
	 */
	public MovePhoto(String albumName) {
		// TODO Auto-generated constructor stub
		super("MovePhoto");
		this.albumName = albumName;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 200);
		
		setLayout(new GridLayout(3, 1));
		JPanel top = new JPanel(new GridLayout(2, 1, 0 ,0) );
		JPanel middle = new JPanel(new GridLayout(1, 0, 0 ,0) );
		JPanel mLeft = new JPanel(new GridLayout(1, 2, 0 ,0) );
		JPanel mRight = new JPanel(new GridLayout(1, 2, 0 ,0) );
		JPanel bottom = new JPanel(new GridLayout(1, 2, 0 ,0) );

		
		pInfo = new JLabel("Photo Name:");
		a1Info = new JLabel("From Album:");
		a2Info = new JLabel("To Album:");
		
		photo = new JTextField();
		album1 = new JTextField();
		album2 = new JTextField();
		
		confirm = new JButton("confirm");
		cancel = new JButton("cancel");
		
		confirm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					if (photo.getText() != null && album1.getText() != null && album2.getText() != null){
						
						File pic = new File("NULL");
						
						ArrayList<Photo> photos = GuiView.control.getAlbumPhotos(album1.getText());
						int validIn = GuiView.control.movePhoto(photo.getText(), album1.getText(), album2.getText());
						
						//all valid fields found
						if(validIn == 1){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Moved photo " + photo.getText() + ": \n"
									+ photo.getText() + " - From album " + album1.getText() + " to album "+ album2.getText());
							GuiView.save();
						} else if (validIn == -1){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Error1: album "+ album1.getText() + " does not exist.");
						} else if (validIn == -2){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Error2: album "+ album2.getText() + " does not exist.");
						} else if (validIn == -3){
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Error: photo already exists in album: " + album2.getText());
						} else {
							JFrame temp = new JFrame();
							JOptionPane.showMessageDialog(temp,"Photo "+ photo.getText()+" does not exist in "+ album2.getText());
						}
						photo.setText("");
						album1.setText("");
						album2.setText("");
						try {
							ManageAlbum frame = new ManageAlbum(albumName);
							frame.setVisible(true);
							frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
							dispose();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					else{
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Error: Invalid arguements");
					}
				}
				catch(NoSuchElementException e3){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Error: Invalid arguements");
				}
			}
		});
		
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ManageAlbum frame = new ManageAlbum(albumName);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		
		top.add(pInfo);
		top.add(photo);
		
		mLeft.add(a1Info);
		mLeft.add(album1);
		
		mRight.add(a2Info);
		mRight.add(album2);
		
		bottom.add(confirm);
		bottom.add(cancel);
		
		middle.add(mLeft);
		middle.add(mRight);
		
		add(top);
		add(middle);
		add(bottom);
	}

}
