package cs213.photoAlbum.GuiView;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

public class DisplaySequence extends JFrame implements Serializable{
	int index = 0;
	public static String albumName;
	public static JTextArea photoInfo;
	public static JButton previous;
	public static JButton next;
	public static JButton close;
	public static JLabel label;
	
	public DisplaySequence(String albumname, ArrayList<Photo> photos) {
		super("Slideshow of " + albumname);
		this.albumName = albumName;
		setLayout(new GridLayout(2, 1));
		
		JPanel photoPanel = new JPanel(new GridLayout(1, 1));
		JPanel otherPanel = new JPanel(new GridLayout(1, 3));
		JPanel prevPanel = new JPanel(new GridLayout(1, 1));
		JPanel infoPanel = new JPanel(new GridLayout(2, 1));
		JPanel nextPanel = new JPanel(new GridLayout(1, 1));
		JScrollPane sPane = new JScrollPane();
		
		sPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		this.albumName = albumname;
		photoInfo = new JTextArea();
		previous = new JButton("Previous");
		next = new JButton("Next");
		close = new JButton("Close");
		label = new JLabel();
		//ArrayList<Photo> photos = new ArrayList<Photo>(GuiView.control.getCurrentUser().getMyAlbums().get(album.albumName).getPhotos().values());
		
		previous.setEnabled(false);
		label.setText("");
		ImageIcon temp = null;
		
		if (photos == null || photos.size() == 0) {
			previous.setEnabled(false);
			next.setEnabled(false);
		} else {
			Image img = photos.get(0).getIcon().getImage();  
			Image newimg = img.getScaledInstance(400, 300,  java.awt.Image.SCALE_SMOOTH) ;  
			temp = new ImageIcon(newimg);
			label.setIcon(temp);
			label.setHorizontalAlignment(SwingConstants.CENTER);;
			photoInfo.setText(DisplayPhoto.createInfo(photos.get(0)));
		}
		//System.out.println(photoInfo.getText());
		setBounds(100, 100, 800, 500);
		
		if (index == photos.size() - 1) {
			next.setEnabled(false);
		}
		
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				index--;
				ImageIcon temp = null;
				 Image img = photos.get(index).getIcon().getImage() ;  
				   Image newimg = img.getScaledInstance( 400, 300,  java.awt.Image.SCALE_SMOOTH ) ;  
				   temp = new ImageIcon( newimg );
				label.setIcon(temp);
				//label.setIcon(photos.get(index).getIcon());
				photoInfo.setText(createInfo(photos.get(index)));
				if (index == 0) {
					previous.setEnabled(false);
				}		
				next.setEnabled(true);
            }
		});
		
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				index++;
				ImageIcon temp = null;
				 Image img = photos.get(index).getIcon().getImage() ;  
				   Image newimg = img.getScaledInstance( 400, 300,  java.awt.Image.SCALE_SMOOTH ) ;  
				   temp = new ImageIcon( newimg );
				label.setIcon(temp);
				//label.setIcon(photos.get(index).getIcon());
				photoInfo.setText(createInfo(photos.get(index)));
				if (index == photos.size() - 1) {
					next.setEnabled(false);
				}
				previous.setEnabled(true);
            }
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
            }
		});
		
		photoPanel.add(label);
		prevPanel.add(previous);
		 sPane = new JScrollPane(photoInfo);
		//sPane.add(photoInfo);
		infoPanel.add(sPane);
		infoPanel.add(close);
		nextPanel.add(next);
		otherPanel.add(prevPanel);
		otherPanel.add(infoPanel);
		otherPanel.add(nextPanel);
		
		add(photoPanel);
		add(otherPanel);
		
		photoInfo.setEditable(false);
	}
	
	public String createInfo(Photo photo) {
		StringBuilder sb = new StringBuilder();
		sb.append("Photo file name: "+ photo.getName() +"\n");
		sb.append("Album: ");
		
		for (int i = 0; i < photo.getAlbums().size(); i++){
			sb.append(photo.getAlbums().get(i));
			
			if (i != photo.getAlbums().size()-1){
				sb.append(", ");
			}
			
		}
		sb.append("\n");
		
		sb.append("Date: "+ photo.getDateString() + "\n");
		sb.append("Caption: " + photo.getCaption() + "\n");
		sb.append("Tags: \n");
		try{
			for (int i = 0; i < photo.getSortedTags().size(); i++){
				sb.append(photo.getSortedTags().get(i).getType() + ":" + photo.getSortedTags().get(i).getData() + "\n");
			}
		}catch (NullPointerException e){
			System.out.println("null tags");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		DisplaySequence test = new DisplaySequence("", new ArrayList<Photo>());
		test.pack();
		test.setResizable(false);
		test.setLocationRelativeTo(null);
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}
