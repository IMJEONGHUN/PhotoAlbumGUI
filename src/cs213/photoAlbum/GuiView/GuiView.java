package cs213.photoAlbum.GuiView;

import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.Photo;

public class GuiView implements Serializable{

	
	public static Backend backend = new Backend();
	public static Controller control = new Controller();
	
	
	public GuiView() {
		// TODO Auto-generated constructor stub
	}
	

	public static void main(String[] args) {
		try{
			GuiView.backend.setUserDB(backend.readUsers());

		} catch (FileNotFoundException e1){

		} catch (IOException e2){

		} catch (ClassNotFoundException e3){

		}
		
		try {
			GeneralLogin frame = new GeneralLogin();
			frame.setVisible(true);
			//frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void save(){
		try{
			GuiView.backend.writeUsers(GuiView.backend.getUserDB());
		}catch (IOException e4){
			System.out.println("Error: Unable to save");
		}
	}
	
	public static class MyCellRenderer extends JLabel implements ListCellRenderer<Object> {
		JLabel label;

		public Component getListCellRendererComponent(
				JList<? extends Object> list, Object photo, int index,
				boolean isSelected, boolean cellHasFocus) {
			// TODO Auto-generated method stub
			label = new JLabel();
			label.setIcon(((Photo)photo).getIcon());
			return this;
		}
	 }
}

