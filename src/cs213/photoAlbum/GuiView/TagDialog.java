package cs213.photoAlbum.GuiView;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cs213.photoAlbum.control.Controller;
import cs213.photoAlbum.model.Backend;

public class TagDialog extends JFrame implements Serializable{
	public static String albumName;
	public static String photoName;
	public static JLabel typePromptLabel;
	public static JLabel valuePromptLabel;
	public static JTextArea typeInputArea;
	public static JTextArea valueInputArea;
	public static JButton confirm;
	public static JButton cancel;
	
	public TagDialog(String albumname, String photoname) {
		super("New Tag for " + photoname);
		
		setLayout(new GridLayout(1, 1));
		setBounds(100, 100, 400, 400);
		JPanel panel = new JPanel(new GridLayout(4, 0, 0, 5));
		JPanel promptPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		
		photoName = photoname;
		albumName = albumname;
		typePromptLabel = new JLabel("New Tag Type:");
		valuePromptLabel = new JLabel("New Tag Value:");
		typeInputArea = new JTextArea();
		valueInputArea = new JTextArea();
		confirm = new JButton("Confirm");
		cancel = new JButton("Cancel");
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				if(typeInputArea.getText().length() == 0 || valueInputArea.getText().length() ==0){
					JFrame temp = new JFrame();
					JOptionPane.showMessageDialog(temp,"Error: All fields must be complete");
				}
				else{
					int result = GuiView.control.addTag(photoName, typeInputArea.getText(), valueInputArea.getText());
					if (result == -1) {
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Tag already exists!");
					} else if (result == -2) {
						JFrame dialog = new JFrame("Error!");
						JOptionPane.showMessageDialog(dialog, "Photo does not exist!");
					}
					//GuiView.save();
					TagWindow window = new TagWindow(albumName, photoName);
					window.setVisible(true);
					dispose();
				}
            }
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
            {
				TagWindow window = new TagWindow(albumName, photoName);
				window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				dispose();
            }
		});
		
		promptPanel.add(typePromptLabel);
		promptPanel.add(valuePromptLabel);
		inputPanel.add(typeInputArea);
		inputPanel.add(valueInputArea);
		panel.add(promptPanel);
		panel.add(inputPanel);
		panel.add(confirm);
		panel.add(cancel);
		
		add(panel);
	}
	
	public static void main(String[] args) {
		TagDialog test = new TagDialog("test album", "test photo");
		test.setDefaultCloseOperation(EXIT_ON_CLOSE);
		test.setVisible(true);
	}
}