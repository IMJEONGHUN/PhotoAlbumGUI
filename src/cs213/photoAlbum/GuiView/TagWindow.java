package cs213.photoAlbum.GuiView;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.Tag;

public class TagWindow extends JFrame implements Serializable{
	public static String albumName;
	public static String photoName;
	public static JList<Tag> tagsDisplay;
	public static DefaultListModel<Tag> model;
	public static JButton addTag;
	public static JButton removeTag;
	public static JButton close;
	public static ArrayList<Tag>tags = null;
	public TagWindow(String albumname, String photoname) {
		super("Tags for " + photoname);
		setBounds(100, 100, 400, 200);
		setLayout(new GridLayout(1, 0));
		
		JPanel panel = new JPanel(new GridLayout(1, 0, 0, 0));
		JPanel panel2 = new JPanel(new GridLayout(3, 0, 0, 0));
		this.photoName = photoname;
		this.albumName = albumname;
		tagsDisplay = new JList<Tag>();
		model = new DefaultListModel<Tag>();
		tagsDisplay.setModel(model);
		tagsDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tagsDisplay.setVisibleRowCount(15);
		tagsDisplay.setSelectedIndex(0);
		addTag = new JButton("Add Tag");
		removeTag = new JButton("Remove Tag");
		close = new JButton("Close");
		
		Album album = GuiView.control.getCurrentUser().getMyAlbums().get(albumName);
		Photo photo = album.getPhotos().get(photoName);
		
		this.tags = new ArrayList<Tag>(photo.getTags().values());
		for (int i = 0; i < tags.size(); i++) {
			model.addElement(tags.get(i));
		}
		tagsDisplay.setModel(model);
		
		addTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				
				TagDialog window = new TagDialog(albumName, photoName);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		
		removeTag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				try{
					int result = GuiView.control.deleteTag(photo.getName(), tagsDisplay.getSelectedValue().getType(), tagsDisplay.getSelectedValue().getData());
					tags =  new ArrayList<Tag>(photo.getTags().values());
					listTags();
					if (result == -1) {
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Tag already exists!");
					} else if (result == -2) {
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Photo does not exist!");
					}
				}catch(NullPointerException e7){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Please Select a tag");
				}
				
            }	
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				ManageAlbum window = new ManageAlbum(albumName);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		JScrollPane scroll = new JScrollPane(tagsDisplay);
		//scroll.add(tagsDisplay);
		//add(scroll);
		panel.add(scroll);
		//panel.add(tagsDisplay);
		panel2.add(addTag);
		panel2.add(removeTag);
		panel2.add(close);
		
		add(panel);
		add(panel2);
	}
	
	
	public static void listTags(){
		if(tags == null){
			model.removeAllElements();
			//model.addElement("No tags");
		}
		else{
			model.removeAllElements();
			for(int i = 0; i<tags.size(); i++){	
				model.addElement(tags.get(i));
				tagsDisplay.setModel(model);
			}
		}
		GuiView.save();
	}
	
	public static void main(String[] args) {
		TagWindow test = new TagWindow("test Album", "test Photo");
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}