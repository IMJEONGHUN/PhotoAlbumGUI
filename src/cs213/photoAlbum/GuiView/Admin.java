package cs213.photoAlbum.GuiView;


/**
 * Admin
 * 
 * Allows admins to manage users
 * Can create users, delete users, and logout to bring back up the General Login
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 04-11-2015
 */

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.GuiView.GuiView;

public class Admin extends JFrame implements Serializable{
	/**
	 * Lists users in the system (admin not included)
	 */
	public static JList<String> display;
	/**
	 * Model for JList
	 */
	public static DefaultListModel<String> model;
	/**
	 * Buttons for functionality
	 */
	public static JButton listUsers, createUser, deleteUser, logout;
	
	/**
	 * the "Main Class"
	 * for testing purposes, not to be actually implemented
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		try{
			GuiView.backend.setUserDB(GuiView.backend.readUsers());

		} catch (FileNotFoundException e1){

		} catch (IOException e2){

		} catch (ClassNotFoundException e3){

		}
		try {
			Admin frame = new Admin();
			frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor
	 */
	public Admin() {
		// TODO Auto-generated constructor stub
		super("Welcome Admin");

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 400, 400);
		setLayout(new GridLayout(0, 1));
		JPanel mainPanel = new JPanel(new GridLayout(5, 1, 0 ,0) );
		JPanel dPanel = new JPanel(new GridLayout(1, 0, 0 ,0) );
		listUsers =  new JButton("list users");
		createUser =  new JButton("create user");
		deleteUser =  new JButton("delete selected user");
		logout =  new JButton("logout");
		
		model = new DefaultListModel();
		display = new JList();
		display.setModel(model);
		display.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		display.setVisibleRowCount(15);
		display.setSelectedIndex(0);
		//display.setPreferredSize(new Dimension(300, 300));
		
		listUsers.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				ArrayList<User> users = new ArrayList<User>(GuiView.control.listUsers());
				if(users.size() == 0){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp, "No Users Exist");
				}
				else{
					model.removeAllElements();
					for(int i = 0; i<users.size(); i++){	
						model.addElement(users.get(i).getID());
						display.setModel(model);
					}
				}
			
			}
		});
		
		createUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		        String newID = JOptionPane.showInputDialog("Enter a new user ID");
		        String newUName = JOptionPane.showInputDialog("Enter the corresponding user name");
		        if(newID == null || newUName == null ||newID == "" || newUName == ""){
		        	JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp, "Error: Incomplete input; Action Aborted");
		        }
		        else if (GuiView.control.addUser(newID, newUName) == true){
		        	model.addElement(newID);
		        	try{
						GuiView.backend.writeUsers(GuiView.backend.getUserDB());
					}catch (IOException e4){
						System.out.println("Error: Unable to save");
					}
				}
				else{
					
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp, "userID "+newID+" already exists");
				}	
			}
		});
		
		deleteUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				 //String userID = JOptionPane.showInputDialog("Enter a new user ID");
				String userID = display.getSelectedValue();
				int pos = display.getSelectedIndex();
				 if(userID == null){
			        	JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp, "Error: Incomplete input; Action Aborted");
			     }
				 else if(GuiView.control.deleteUser(userID)){//delete user is successful
					model.remove(pos);
					try{
						GuiView.backend.writeUsers(GuiView.backend.getUserDB());
					}catch (IOException e5){
						
						System.out.println("Error: Unable to save new user");
					}
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp, "deleted user "+ userID );
				}
				else{
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp, "user " + userID +" does not exist");
				}
			}
		});
		

		logout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					GuiView.backend.writeUsers(GuiView.backend.getUserDB());
				}catch (IOException e4){
					System.out.println("Error: Unable to save");
				}
				GeneralLogin frame = new GeneralLogin();
				frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				frame.setVisible(true);
				dispose();
			}
		});
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1d;
		constraints.weighty = 1d;
		
		//GridBagConstraints c = new GridBagConstraints();
		//c.anchor = GridBagConstraints.PAGE_END;
		dPanel.add(display);
		mainPanel.add(listUsers);
		mainPanel.add(createUser);
		mainPanel.add(deleteUser);
		mainPanel.add(logout);
		add(dPanel);
		add(mainPanel);
		

		
	}

}
