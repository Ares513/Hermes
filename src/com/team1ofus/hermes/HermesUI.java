package com.team1ofus.hermes;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Point;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.BevelBorder;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HermesUI extends JPanel{
	
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	private JTextField StartField;
	private JTextField DestinationField;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage map;
	private ImageIcon map2 = new ImageIcon(ClassLoader.getSystemResource("map.jpg")); //This is a reference to the "map.jpg" within the the SRC. Don't think this will be a long term solution, but it does load in an image.
	private Point mousePosition;
	private DrawMap gridMap;
	
	public HermesUI() {
		initialize();

	}

	
	/*
	 * initialize the Hermes UI
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
		//MapPanel is where the map is displayed
		
		
		JPanel MousePanel = new JPanel();
		JLabel mouseOut = new JLabel("#mouse#");
		MousePanel.add(mouseOut);
		
		//my stuff
		MyDrawPanel pathPanel = new MyDrawPanel();
		frameHermes.getContentPane().add(pathPanel);
		pathPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		
		gridMap = new DrawMap();
		gridMap.setBounds(0, 0, screenSize.width, screenSize.height);
		frameHermes.getContentPane().add(gridMap);
		
		JPanel controlBoard = new ControlPanel();
		controlBoard.setBackground(Color.GRAY);
		controlBoard.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		controlBoard.setBounds(frameHermes.getSize().width/2 -100, frameHermes.getSize().height - 100, 300, 100);
        frameHermes.getContentPane().add(controlBoard);
        
		frameHermes.setVisible(true);
	}
	
	 void drawPath(CellPoint[] path){
		 //Re-instantiate ArrayList of points to draw
		 //Iterate through direction set and add its points to an the ArrayList of points
		 repaint();
	    }

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawImage(map, p1.x, p1.y, null);
	//Allows us to paint the image within the JLabel	
	}
	
	class MyDrawPanel extends JPanel{
		//ArrayList<Point> pointsList = new ArrayList<Point>();
			
		    public MyDrawPanel() {
		    	//Will have to change pointsList to be whatever was passed in through the constructor
		        setOpaque(false);
		        
		    }
		    
		    //This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
		    void drawLineSets(Graphics g){ //ArrayList<Point> p ){
		    	//Should drawLineSets be called in UI Management as a part of the chain of events?
		    	//I dont think this will work because it needs the graphic?
		    	Graphics2D g2d = (Graphics2D) g;
		    	/*
		    	pointsList.add(new Point(200,200));
		     	pointsList.add(new Point(500,100));
		     	pointsList.add(new Point(800,500));
		     	pointsList.add(new Point(900,800));
		     	*/
		     	g2d.setColor(Color.BLUE);
		     	float[] dashingPattern1 = {8f, 8f};
		     	Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
		     	g2d.setStroke(stroke1);
		     	
		    	for(int i = 0; i < pointsList.size()-1; i++){
		            g2d.drawLine(pointsList.get(i).x, pointsList.get(i).y, pointsList.get(i+1).x,pointsList.get(i+1).y);
		    	} 
		    }
		 
		    //Need to make a function that does the repaint that can be called on by UIManagement
		    //
		   
		    public void paint(Graphics g) {
		        super.paint(g);
		    	drawLineSets(g); //pointsList);

		    }
		}

}