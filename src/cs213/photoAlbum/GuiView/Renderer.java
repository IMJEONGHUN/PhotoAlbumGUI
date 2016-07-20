package cs213.photoAlbum.GuiView;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;
/*
 * 
 * 
 * 
 Renderer rend = new Renderer(albumname);
		JScrollPane scroll = new JScrollPane(rend.list);
        scroll.setPreferredSize(new Dimension(300, 400));
 */
public class Renderer extends DefaultListCellRenderer implements Serializable {
    //private Map<String, ImageIcon> imageMap;
    public JList<Photo> list = null;
    public ArrayList<Photo> photos = null;

    public Renderer(String albumName) {
    	this.photos = GuiView.control.getAlbumPhotos(albumName);
        /*String[] photoNames = new String[photos.size()];
    	for(int i =0; i< photos.size(); i++){
    		photoNames[i] = photos.get(i).getName();
    	}*/
    	
    	Photo[] photoNames = new Photo[photos.size()];
    	for(int i = 0; i< photos.size(); i++){
    		photoNames[i] = photos.get(i);
    	}
    	
        //imageMap = createImageMap(photos);
        JList<Photo> list = new JList<Photo>(photoNames);
        list.setCellRenderer(new DisplayListRenderer());
        this.list = list;
        /*
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(300, 400));
        
        JFrame frame = new JFrame();
        frame.add(scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }

    public Renderer(ArrayList<Photo> photos) {
    	this.photos = photos;
        /*String[] photoNames = new String[photos.size()];
    	for(int i =0; i< photos.size(); i++){
    		photoNames[i] = photos.get(i).getName();
    	}*/
    	
    	Photo[] photoNames = new Photo[photos.size()];
    	for(int i = 0; i< photos.size(); i++){
    		photoNames[i] = photos.get(i);
    	}
    	
        //imageMap = createImageMap(photos);
        JList<Photo> list = new JList<Photo>(photoNames);
        list.setCellRenderer(new DisplayListRenderer());
        this.list = list;
        /*
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(300, 400));
        
        JFrame frame = new JFrame();
        frame.add(scroll);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        */
    }
    
    public class DisplayListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.BOLD, 10);

        @Override
        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            //label.setIcon(imageMap.get((String) value));
            label.setIcon(new ImageIcon(((Photo) value).getIcon().getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
            label.setText(((Photo) value).getName());
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }

    private Map<String, ImageIcon> createImageMap(ArrayList<Photo> list) {
        Map<String, ImageIcon> map = new HashMap<>();
        try {
        	for(int i = 0; i< list.size(); i++){
        		ImageIcon temp = null;
        		 Image img = list.get(i).getIcon().getImage() ;  
        		   Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;  
        		   temp = new ImageIcon( newimg );
        		map.put(list.get(i).getName(), temp);
        	}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }
}