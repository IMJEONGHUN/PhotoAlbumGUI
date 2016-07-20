package cs213.photoAlbum.GuiView;

/**
 * Search
 * 
 * Allows users to search photos belonging to them by tag or by date
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 04-11-2015
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.simpleview.CmdView;

public class Search extends JFrame{
	/**
	 * JLabel with user instructions
	 */
	public static JLabel prompt;
	/**
	 * Field to accept input
	 */
	public static JTextField input;
	/**
	 * Buttons for functionality
	 */
	public static JButton byTag, byDate, close;
	
	/**
	 * the "Main Class"
	 * for testing purposes, not to be actually implemented
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {

		try {
			Search frame = new Search();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor
	 */
	public Search() {
		// TODO Auto-generated constructor stub
		super("Search");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 375, 200);
		
		setLayout(new GridLayout(1, 0));
		JPanel mainPanel = new JPanel(new GridLayout(5, 1, 0 ,0) );
		prompt = new JLabel("Search Parameter(s): [Enter the Tag(s) or Date Range");
		input = new JTextField();
		byTag =  new JButton("Search by Tag");
		byDate =  new JButton("Search by Date");
		close =  new JButton("Close");
		
		byTag.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				StringTokenizer st = new StringTokenizer(input.getText());
				ArrayList<String> tagArray = new ArrayList<String>();
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
							tagFull.add(s);		
						}
						else {
							String s =tagArray.get(i).trim().substring(1, tagArray.get(i).trim().length() - 1).toLowerCase();
							tagLess.add(s);
						}
					}				
					 
					ArrayList<Photo> photos = GuiView.control.getPhotosByTag(tagFull, tagLess);
					try {
						SearchResults frame = new SearchResults("Photos by tag", photos);
						frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
						frame.setVisible(true);
						dispose();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}catch (NoSuchElementException e6){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Error: Invalid input. Tags not in the correct format.");
				} 
			}
		});
		
		byDate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					String date1, date2;
					StringTokenizer st = new StringTokenizer(input.getText());
					date1 = st.nextToken();
					date2 = st.nextToken();
						 
					if (date1 != null && date2 != null){
 						if (CmdView.validateDateFormat(date1) == true && CmdView.validateDateFormat(date2) == true){ 							
 							ArrayList<Photo> photos = new ArrayList<Photo>();
 							try {
								photos = GuiView.control.getPhotosByDate(date1, date2);
								try {
									SearchResults frame = new SearchResults("Photos by Date", photos);
									frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
									frame.setVisible(true);
									dispose();
								} catch (Exception e2) {
									e2.printStackTrace();
								}
							} catch (ParseException e7) {
								JFrame temp = new JFrame();
		 						JOptionPane.showMessageDialog(temp,"Error: could not read date.");
							}
 						}else{
 							JFrame temp = new JFrame();
 							JOptionPane.showMessageDialog(temp,"Error: The dates were not entered in the correct format.");
 						}
 					 }else{
 						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp,"Error: Start date and end date must both be entered.");
					 }
				} catch (NoSuchElementException e3){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Error: Start date and end date must both be entered.");
				}
			}
		});
		
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					ManageAlbums frame = new ManageAlbums();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
					dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		mainPanel.add(prompt);
		mainPanel.add(input);
		mainPanel.add(byTag);
		mainPanel.add(byDate);
		mainPanel.add(close);
		
		add(mainPanel);
	}
}
