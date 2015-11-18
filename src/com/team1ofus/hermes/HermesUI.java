package com.team1ofus.hermes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.BorderFactory;

import javax.swing.ImageIcon;

import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

public class HermesUI extends JPanel{
	private JFrame frameHermes;
	private JTextField StartField;
	private JTextField DestinationField;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage map;
	private ImageIcon map2 = new ImageIcon(ClassLoader.getSystemResource("map.jpg")); //This is a reference to the "map.jpg" within the the SRC. Don't think this will be a long term solution, but it does load in an image.
	private Point mousePosition;
	public HermesHumanInteractiveEvent humanInteractive; 
	
	public HermesUI() {
		humanInteractive = new HermesHumanInteractiveEvent(); 
		initialize();

	}

	/*
	 * initialize the Heremes UI
	*/
	
	private void initialize() {
		
		frameHermes = new JFrame();
		frameHermes.setTitle("Hermes");
		frameHermes.setBounds(0, 0, screenSize.width - 200, screenSize.height - 200);
		//Set screen size to be the size of the display - 200
		//kind of arbitrary
		frameHermes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameHermes.getContentPane().setLayout(null);
		frameHermes.setMinimumSize(new Dimension(800,600));
		frameHermes.setLocation(screenSize.width/2-frameHermes.getSize().width/2, screenSize.height/2-frameHermes.getSize().height/2);
		//Sets the frame to start in the center of the screen
		
		JPanel DestinationPanel = new JPanel();
		DestinationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		DestinationPanel.setBackground(Color.LIGHT_GRAY);
		DestinationPanel.setBounds(6, 6, screenSize.width - (screenSize.width - 134), screenSize.height - (screenSize.height - 100));
		frameHermes.getContentPane().add(DestinationPanel);
		//Destination panel placed in the upper right corner of the frameHermes
		
		StartField = new JTextField();
		StartField.setText("Startpoint");
		DestinationPanel.add(StartField);
		StartField.setColumns(10);
		
		DestinationField = new JTextField();
		DestinationField.setText("Destination");
		DestinationPanel.add(DestinationField);
		DestinationField.setColumns(10);
		//Builds the two input fields and displays them in the DestinationPanel
		
		
		JButton addDestination = new JButton("Add Destination");
		addDestination.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		addDestination.setBorder(BorderFactory.createEmptyBorder());   
		DestinationPanel.add(addDestination);
		//Not complete yet, but will end up adding another field for more destinations
		
		JPanel MapPanel = new JPanel();
		MapPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		JLabel mapPlacer = new JLabel("",map2,JLabel.CENTER);
		
		//To import an image you need to do a janky thing.
		//Basically you place an image inside a JLabel, override the paintComponent method, and place it in the panel
		
		MapPanel.add(mapPlacer);
		//MapPanel is where the map is displayed
		
		
		JPanel MousePanel = new JPanel();
		JLabel mouseOut = new JLabel("#mouse#");
		MousePanel.add(mouseOut);
		
		/*
		MapPanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosition = new Point(e.getX(), e.getY());
				mouseOut.setText(mousePosition.toString()); 
			}
		});
		*/
		
		MapPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				humanInteractive.doClick();
				//System.out.println("clicked");
			}
		});
		
		//These are the listeners
		
		frameHermes.getContentPane().add(MapPanel);
		frameHermes.setVisible(true);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(map, 0, 0, null);
	//Allows us to paint the image within the JLabel	
	}
	
}