import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class MenuPage extends JFrame {
	
	public MenuPage() {

		setTitle("Heckmeck am Bratwurmeck");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 575);
		setLocationRelativeTo(null);
		setResizable(false);
		
		JLayeredPane layeredpane = new JLayeredPane();
		add(layeredpane);

		JLabel pictureLabel = new JLabel();
		pictureLabel.setIcon(new ImageIcon(MenuPage.class.getResource("/resource/menu.png")));
		pictureLabel.setBounds(0, -5, 950, 550);
		
		JPanel contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBounds(400, 410, 150, 100);
		GridLayout layout = new GridLayout(2, 1);
		layout.setVgap(10);
		contentPane.setLayout(layout);
		
		JButton playButton = new JButton("Play");
		playButton.setFocusPainted(false);
		playButton.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(playButton);
		
		JButton quitButton = new JButton("Quit");
		quitButton.setFocusPainted(false);
		quitButton.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(quitButton);

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
				new GamePage().setVisible(true);
			}
		});

		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				dispose();
			}
		});
		
		layeredpane.add(pictureLabel, new Integer(0), 0);
		layeredpane.add(contentPane, new Integer(1), 0);
	}
	
	public static void main(String args[]) {
		new MenuPage().setVisible(true);
	}
}
