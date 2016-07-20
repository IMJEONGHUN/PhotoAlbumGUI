package cs213.photoAlbum.GuiView;

/**
 * DisplayPhoto
 * 
 * Displays a single photo
 * 
 * @author Amar Bakir <wldkng14@yahoo.com>
 * @version 1.0
 * @since 04-11-2015
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cs213.photoAlbum.model.Photo;



public class DisplayPhoto extends JFrame implements Serializable{
	public static String albumName;
	public static JTextArea photoInfo;
	public static JButton close;
	public static JLabel label;
	
	private class ImageView extends JScrollPane {
		JPanel panel = new JPanel();
		Dimension originalSize = new Dimension();
		Image originalImage;
		JLabel iconLabel;
		
		public ImageView(ImageIcon icon) {
			this.originalImage =icon.getImage();
			//this.originalImage = originalImage.getScaledInstance(400, 200, Image.SCALE_DEFAULT);
			panel.setLayout(new BorderLayout());
			iconLabel = new JLabel(icon);
			panel.add(iconLabel);
			
			setViewportView(panel);
		}
	}
	
	
	
	public DisplayPhoto(String albumname, Photo photo) {
		super(photo.getName() + " in " + albumname);
		//getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT)
		ImageView disp = new ImageView(photo.getIcon());
		

		//setLayout(new GridLayout(3, 1));
		setBounds(100, 100, 500, 500);
		/*JPanel photoPanel = new JPanel(new GridLayout(1, 1));
		JPanel infoPanel = new JPanel(new GridLayout(2, 1));
		JPanel sPane = new JPanel(new GridLayout(2, 1));
		//JScrollPane sPane = new JScrollPane();
		*/
		JPanel image = new JPanel(new BorderLayout());
		JPanel text = new JPanel();
		//JPanel button = new JPanel(new GridLayout(3,1));
		JPanel button = new JPanel(new BorderLayout());
		JScrollPane scroll = new JScrollPane();
		
		//sPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.albumName = albumname;
		photoInfo = new JTextArea();
		close = new JButton("Close");
		label = new JLabel();
		
		label.setText("");
		label.setIcon(photo.getIcon());
		photoInfo.setText(createInfo(photo));
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				dispose();
            }
		});
		//add(label);
		button.add(disp, BorderLayout.CENTER);
		JScrollPane scroll2 = new JScrollPane(photoInfo);
		scroll2.setPreferredSize(new Dimension(500, 100));
		button.add(scroll2,BorderLayout.SOUTH);
		button.add(close, BorderLayout.NORTH);
		//add(scroll);
		add(button);
		/*sPane.add(photoInfo);
		photoPanel.add(label);
		infoPanel.add(sPane);
		infoPanel.add(close);
		
		add(photoPanel);
		add(infoPanel);
		*/
		
		photoInfo.setEditable(false);
	}
	
	public static String createInfo(Photo photo) {
		photo = GuiView.control.listPhotoInfo(photo.getName());
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
		sb.append("Tags:" + "\n");
		if( photo.getSortedTags() != null){
			for (int i = 0; i < photo.getSortedTags().size(); i++){
				sb.append(photo.getSortedTags().get(i).getType() + ":" + photo.getSortedTags().get(i).getData() + "\n");
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		DisplayPhoto test = new DisplayPhoto("", new Photo());
		test.pack();
		test.setResizable(false);
		test.setLocationRelativeTo(null);
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}
