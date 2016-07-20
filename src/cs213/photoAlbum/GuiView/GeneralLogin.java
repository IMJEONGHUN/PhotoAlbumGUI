package cs213.photoAlbum.GuiView;

/**
 * General Login
 * 
 * A login screen that directs users to to manage albums and photos
 * or directs an admin to a frame to manage users
 * 
 * @author Tolby Lew <tolbyflew@gmail.com>
 * @version 1.0
 * @since 04-11-2015
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.User;
import cs213.photoAlbum.GuiView.GuiView;

public class GeneralLogin extends JFrame implements Serializable{
	
	/**
	 * buttons to login or exit application
	 */
	JButton login, exit;
	/**
	 * Textfield to collect input
	 */
	JTextField input;
	/**
	 * Instructions on Use
	 */
	JLabel info;
	
	
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
			GeneralLogin frame = new GeneralLogin();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor
	 */
	public GeneralLogin() {
		// TODO Auto-generated constructor stub
		super("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		
		setLayout(new GridLayout(1, 0));
		JPanel mainPanel = new JPanel(new GridLayout(4, 1, 0 ,0) );
		info =  new JLabel("userID:");
		input =  new JTextField();
		login =  new JButton("login");
		exit =  new JButton("exit");
		
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(input.getText().equalsIgnoreCase("admin")){
					try {
						Admin frame = new Admin();
						frame.setVisible(true);
						dispose();
					} catch (Exception e1) {
						e1.printStackTrace();
					}	
				}else{
					ArrayList<User> users = new ArrayList<User>(GuiView.control.listUsers());
					boolean userExists = false;
					for(int i = 0; i<users.size(); i++){
						if(users.get(i).getID().equalsIgnoreCase(input.getText())){
							try {
								GuiView.control.setCurrentUser(users.get(i));
								ManageAlbums frame = new ManageAlbums();
								frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
								frame.setVisible(true);
								dispose();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						 return;
						}
					}
					if(userExists == false){
						JFrame temp = new JFrame();
						JOptionPane.showMessageDialog(temp, "User does not exist");
					}
				}
					
				
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					GuiView.backend.writeUsers(GuiView.backend.getUserDB());
				}catch (IOException e3){
					System.out.println("Error could not save");
					e3.printStackTrace();
				}
				 dispose();
				 System.exit(0);
			}
		});
		
		mainPanel.add(info);
		mainPanel.add(input);
		mainPanel.add(login);
		mainPanel.add(exit);
		
		add(mainPanel);
	}

}
