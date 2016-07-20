package cs213.photoAlbum.GuiView;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs213.photoAlbum.GuiView.GuiView.MyCellRenderer;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;

public class SearchResults extends JFrame implements Serializable{
	JList<Photo> photosList;
	Album album;
	JButton createAlbum;
	JButton startSlideshow;
	JButton close;
	
	public SearchResults(String title, ArrayList<Photo> photos) {
		super(title);
		setBounds(100, 100, 500, 500);
		setLayout(new GridLayout(2, 1));
		
		JPanel resultsPanel = new JPanel(new GridLayout(1, 1));
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		
		createAlbum = new JButton("Create Album");
		startSlideshow = new JButton("Start Slideshow");
		close = new JButton("Close");
		photosList = new JList<Photo>();
		Photo[] pArray = new Photo[photos.size()];
    	for(int i = 0; i< photos.size(); i++){
    		pArray[i] = photos.get(i);
    	}
    	
    	Renderer rend = new Renderer(photos);
		JList pList = rend.list;
	    JScrollPane scroll = new JScrollPane(pList);
	    scroll.setPreferredSize(new Dimension(300, 400));
    	
	    resultsPanel.add(scroll);
	    
		photosList.setListData(pArray);
		
		photosList.setCellRenderer(new MyCellRenderer());
		
		photosList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	Photo selectedItem = photosList.getSelectedValue();
		        	DisplayPhoto window = new DisplayPhoto(album.getName(), selectedItem);
		        	window.pack();
		        	window.setResizable(false);
		        	window.setLocationRelativeTo(null);
		        	window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		        	window.setVisible(true);
		         }
		    }
		});
		
		createAlbum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				String aName = JOptionPane.showInputDialog("Enter the album's name:");
				boolean result = GuiView.control.createAlbum(aName);
				if (!result) {
					JFrame dialog = new JFrame("Error!");
					JOptionPane.showMessageDialog(dialog, "Album already exists!");
				}
				try{
					for(int i = 0; i< photos.size(); i++){
						GuiView.control.addPhoto(photos.get(i).getName(), photos.get(i).getCaption(), aName, photos.get(i).getFile());
					}
	            } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
		});
		
		startSlideshow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				DisplaySequence window = new DisplaySequence("searchresults", photos);
				window.pack();
				window.setResizable(false);
				window.setLocationRelativeTo(null);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
            }
		});
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				Search window = new Search();
				window.pack();
				window.setResizable(false);
				window.setLocationRelativeTo(null);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		
		
		buttonPanel.add(createAlbum);
		buttonPanel.add(startSlideshow);
		buttonPanel.add(close);
		
		add(resultsPanel);
		add(buttonPanel);
	}
	
	public static void main(String[] args) {
		SearchResults test = new SearchResults("Search Result Window", new ArrayList<Photo>());
		test.pack();
		test.setResizable(false);
		test.setLocationRelativeTo(null);
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}
