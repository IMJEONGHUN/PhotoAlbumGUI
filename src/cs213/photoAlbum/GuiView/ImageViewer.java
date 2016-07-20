package cs213.photoAlbum.GuiView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.Serializable;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageViewer extends JFrame {
	
	JSlider slider = new JSlider(0,100,100);
	JLabel percent = new JLabel("100%");
	
	private class ImageView extends JScrollPane implements Serializable{
		JPanel panel = new JPanel();
		Dimension originalSize = new Dimension();
		Image originalImage;
		JLabel iconLabel;
		
		public ImageView(ImageIcon icon) {
			this.originalImage =icon.getImage();
			
			panel.setLayout(new BorderLayout());
			iconLabel = new JLabel(icon);
			panel.add(iconLabel);
			
			setViewportView(panel);
			
			originalSize.width = icon.getIconWidth();
			originalSize.height = icon.getIconHeight();
		}
		
		public void update() {
			int min = slider.getMinimum();
			int max = slider.getMaximum();
			int span = max-min;
			int value = slider.getValue();
			double multiplier = (double)value/span;
			multiplier = multiplier == 0.0 ? 0.01 : multiplier;
			Image scaled = originalImage.getScaledInstance(
					(int)(originalSize.width*multiplier),
					(int)(originalSize.height*multiplier),
					Image.SCALE_FAST);
			iconLabel.setIcon(new ImageIcon(scaled));
		}	
	}

	ImageView imageView;
	
	public ImageViewer(String imgFile) {
		super("Image");
		
		ImageIcon icon = new ImageIcon(imgFile);
		imageView = new ImageView(icon);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Set Image Size: "));
		panel.add(slider);
		panel.add(percent);
		
		add(panel, BorderLayout.NORTH);
		add(imageView, BorderLayout.CENTER);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!slider.getValueIsAdjusting()) {
					percent.setText(slider.getValue() + "%");
					imageView.update();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter image file name: ");
		String imgFile = sc.nextLine();
		
		JFrame frame = new ImageViewer(imgFile);
		frame.setSize(400,300);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}