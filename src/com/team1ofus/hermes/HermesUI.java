package com.team1ofus.hermes;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;

public class HermesUI extends JPanel{
	private JFrame frameHermes;
	private JTextField StartField;
	private JTextField DestinationField;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage map;
	//private ImageIcon map2 = new ImageIcon("images/map.jpg");
	private Point mousePosition;
	
	public HermesUI() {
		initialize();

	}

	/*
	 * initialize the Heremes UI
	*/
	
	private void initialize() {
		frameHermes = new JFrame();
		frameHermes.setTitle("Hermes");
		frameHermes.setBounds(0, 0, screenSize.width - 200, screenSize.height - 200);
		frameHermes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameHermes.getContentPane().setLayout(null);
		frameHermes.setMinimumSize(new Dimension(800,600));
		frameHermes.setLocation(screenSize.width/2-frameHermes.getSize().width/2, screenSize.height/2-frameHermes.getSize().height/2);
		
		JPanel DestinationPanel = new JPanel();
		DestinationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		DestinationPanel.setBackground(Color.LIGHT_GRAY);
		DestinationPanel.setBounds(6, 6, screenSize.width - (screenSize.width - 134), screenSize.height - (screenSize.height - 100));
		frameHermes.getContentPane().add(DestinationPanel);
		
		StartField = new JTextField();
		StartField.setText("Startpoint");
		DestinationPanel.add(StartField);
		StartField.setColumns(10);
		
		DestinationField = new JTextField();
		DestinationField.setText("Destination");
		DestinationPanel.add(DestinationField);
		DestinationField.setColumns(10);
		
		JButton addDestination = new JButton("Add Destination");
		addDestination.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		addDestination.setBorder(BorderFactory.createEmptyBorder());   
		DestinationPanel.add(addDestination);
		
		JPanel MapPanel = new JPanel();
		MapPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		//JLabel mapPlacer = new JLabel("",map2,JLabel.CENTER);
		//MapPanel.add(mapPlacer);
		
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
				
			}
		});
		
		
		frameHermes.getContentPane().add(MapPanel);
		frameHermes.setVisible(true);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(map, 0, 0, null);
		
	}
	
}