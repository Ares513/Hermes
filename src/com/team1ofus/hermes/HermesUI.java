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
import java.awt.event.KeyAdapter;
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
	private MyDrawPanel pathPanel;
	private JTextField StartField;
	private JTextField DestinationField;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage map;
	private Point mousePosition;
	private DrawMap gridMap;
	int scrollSpeed = 5;
	private JLabel lblOffset;
	private PathCell currentCell;
	public HumanInteractionEventObject humanInteractive; 
	
	public HermesUI() {
		humanInteractive = new HumanInteractionEventObject(); 
	}

	
	/*
	 * initialize the Hermes UI
	*/
	
	public void initialize(PathCell viewCell) {		
		currentCell = viewCell;
		buildControl();
	
		gridMap.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e);
			}
		});
		frameHermes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e);
			}
		});
		

		/*
		JPanel controlBoard = new ControlgridMap();
		controlBoard.setBackground(Color.GRAY);
		controlBoard.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		controlBoard.setBounds(frameHermes.getSize().width/2 -100, frameHermes.getSize().height - 100, 300, 100);
        frameHermes.getContentPane().add(controlBoard);
        */

        /*
MapgridMap.addMouseMotionListener(new MouseMotionAdapter() {
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition = new Point(e.getX(), e.getY());
		mouseOut.setText(mousePosition.toString()); 
	}
});
*/
        /*
         * The following few lines were originally in HermesUI and then were taken out 
         * I'm (Aaron) pretty sure that they are needed for the mouse click events. 
         * So i put them back in
         */
		JPanel MapgridMap = new JPanel();
		MapgridMap.setBounds(0, 0, screenSize.width, screenSize.height);
		MapgridMap.addMouseListener(new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point picked = gridMap.render.pickTile(e.getX(), e.getY());
			humanInteractive.doClick(e.getX(), e.getY());
			}
		});
		frameHermes.getContentPane().add(MapgridMap); 
        frameHermes.setVisible(true);
        //CellPoint[] array = {new CellPoint("Map", new Point(200, 600)), new CellPoint("Map", new Point(800, 750)), new CellPoint("Map", new Point(1000, 750)), new CellPoint("Map", new Point(1000, 500))};
        //drawPath(array);
	}
	//Would just skip this and go straight to MyPanel's drawPath, but I'm afraid that it will break and I don't have time to fix it
	 void drawPath(CellPoint[] path){
		 pathPanel.drawPath(path);
		 repaint();
	    }

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawImage(map, p1.x, p1.y, null);
	//Allows us to paint the image within the JLabel	
	}
	
	private void buildControl(){
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
		
		/*JPanel DestinationgridMap = new JPanel();
		DestinationgridMap.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		DestinationgridMap.setBackground(Color.LIGHT_GRAY);
		DestinationgridMap.setBounds(6, 6, screenSize.width - (screenSize.width - 134), screenSize.height - (screenSize.height - 100));
		frameHermes.getContentPane().add(DestinationgridMap);
		//Destination gridMap placed in the upper right corner of the frameHermes
		
		StartField = new JTextField();
		StartField.setText("Startpoint");
		DestinationgridMap.add(StartField);
		StartField.setColumns(10);
		
		DestinationField = new JTextField();
		DestinationField.setText("Destination");
		DestinationgridMap.add(DestinationField);
		DestinationField.setColumns(10);
		//Builds the two input fields and displays them in the DestinationgridMap
		
		
		JButton addDestination = new JButton("Add Destination");
		addDestination.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		addDestination.setBorder(BorderFactory.createEmptyBorder());   
		DestinationgridMap.add(addDestination);
		//MapgridMap is where the map is displayed*/
		
		
		JPanel MousegridMap = new JPanel();
		JLabel mouseOut = new JLabel("#mouse#");
		MousegridMap.add(mouseOut);
			/*
		MyDrawgridMap pathgridMap = new MyDrawgridMap();
		frameHermes.getContentPane().add(pathgridMap);
		pathgridMap.setBounds(0, 0, screenSize.width, screenSize.height);
		*/
		pathPanel = new MyDrawPanel();
		frameHermes.getContentPane().add(pathPanel);
		pathPanel.setBounds(0, 0, screenSize.width, screenSize.height);
		
		
		gridMap = new DrawMap(currentCell);
		gridMap.setBounds(0, 0, frameHermes.getWidth(), frameHermes.getHeight());
		frameHermes.getContentPane().add(gridMap);

	}
	
	public MyDrawPanel getPathPanel(){
		return pathPanel;
	}
	
	class MyDrawPanel extends JPanel{
		Point offset = new Point(0, 0);

		public MyDrawPanel() {
			setOpaque(false);

		}
		void drawPath(CellPoint[] path){
			pointsList = new ArrayList<Point>();
			for(CellPoint c : path){
				pointsList.add(c.getPoint());
			}
			validate();
			repaint();
		}

		//This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
		void drawLineSets(Graphics g){ 
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLUE);
			float[] dashingPattern1 = {8f, 8f};
			Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
			g2d.setStroke(stroke1);
			//System.out.println(pointsList);

			for(int i = 0; i < pointsList.size()-1; i++){
				g2d.drawLine(pointsList.get(i).x, pointsList.get(i).y, pointsList.get(i+1).x,pointsList.get(i+1).y);
			} 
		}

		public void paint(Graphics g) {
			super.paint(g);
			drawLineSets(g);

		}
		
		public void incrementOffset(int dx, int dy) {
			offset = new Point(gridMap.render.getOffset().x + dx, gridMap.render.getOffset().y + dy);
			System.out.println(gridMap.render.getOffset().y);
			if(offset.x < 0) {
				offset.x = 0;
				dx = 0;
			} else if(offset.x > gridMap.render.drawnCell.tiles.length * gridMap.render.width - gridMap.getWidth()) {
				offset.x = gridMap.render.drawnCell.tiles.length * gridMap.render.width - gridMap.getWidth();
				dx = 0;
			}
			if(offset.y < 0) {
				offset.y = 0;
				dy = 0;
			} else if(offset.y > gridMap.render.drawnCell.tiles[1].length * gridMap.render.height - gridMap.getHeight()) {
				offset.y = gridMap.render.drawnCell.tiles[1].length * gridMap.render.height - gridMap.getHeight();
				dy = 0;
			}
			System.out.println(dx + "," + dy);
			for(Point p : pointsList)
				p.translate(-dx, -dy);
		}
	}

	private void doOffsetCalc(KeyEvent e) {
		switch(e.getKeyCode()) {
		//some optimizations to be made here
		case KeyEvent.VK_LEFT:
			gridMap.render.incrementOffset(-1*scrollSpeed, 0, gridMap.getWidth(), gridMap.getHeight());
			pathPanel.incrementOffset(-1*scrollSpeed, 0);
			break;
		case KeyEvent.VK_RIGHT:
			gridMap.render.incrementOffset(scrollSpeed, 0, gridMap.getWidth(), gridMap.getHeight());
			pathPanel.incrementOffset(scrollSpeed, 0);
			break;
		case KeyEvent.VK_DOWN:
			gridMap.render.incrementOffset(0, scrollSpeed, gridMap.getWidth(), gridMap.getHeight());
			pathPanel.incrementOffset(0, scrollSpeed);
			break;
		case KeyEvent.VK_UP:
			gridMap.render.incrementOffset(0, -1*scrollSpeed, gridMap.getWidth(), gridMap.getHeight());
			pathPanel.incrementOffset(0, -1*scrollSpeed);
			break;
		default:
			break;
		}
		pathPanel.validate();
		pathPanel.repaint();
		gridMap.repaint(0, 0, gridMap.getWidth() + 100, gridMap.getHeight() + 100);
		
	}
}