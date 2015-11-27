package com.team1ofus.hermes;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
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
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;

public class HermesUI extends JPanel{
	
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	private PathPane pathPanel;
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
	private Point first; //for showing in the UI which points were clicked.
	private Point second; 
	private JLayeredPane layeredPane;
	private boolean dragging;
	private Point lastDragLocation;
	private TextPane textPanel;
	private int panelSize = 230;
	public HermesUI(PathCell viewCell) {
		humanInteractive = new HumanInteractionEventObject();
		initialize(viewCell);
	}
	
	/*
	 * initialize the Hermes UI
	*/
	
	public void initialize(PathCell viewCell) {		
		currentCell = viewCell;
		buildControl();
		frameHermes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				doOffsetCalc(e);
			}
		});
			gridMap.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					doOffsetCalc(e);
				}
			});
        frameHermes.setVisible(true);
	}
	
	private void processClick(Point picked) {
		DebugManagement.writeNotificationToLog("Mouse clicked at " + picked.x + " , " + picked.y);
		if(gridMap.render.getTile(picked.x, picked.y).tileType == TILE_TYPE.PEDESTRIAN_WALKWAY) {
			//valid.
			if(first == null) {
				
				first = new Point(picked.x, picked.y);
				gridMap.render.setFirst(first);
				repaintPanel();;
			} else if(second == null) {
				
				second = new Point(picked.x,picked.y);
				gridMap.render.setSecond(second);
				first = null;
				second = null;

				repaintPanel();;
			}
			
			humanInteractive.doClick(picked.x, picked.y);
		}
	}
	//Would just skip this and go straight to MyPanel's drawPath, but I'm afraid that it will break and I don't have time to fix it
	 void drawPath(ArrayList<CellPoint> path){
		 pathPanel.drawPath(path);
		 repaintPanel();;
	    }

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		layeredPane.paintComponents(g);
	//Allows us to paint the image within the JLabel	
	}
	
	private void buildControl(){
		frameHermes = new JFrame();
		frameHermes.setIconImage(Toolkit.getDefaultToolkit().getImage(HermesUI.class.getResource("/com/team1ofus/hermes/setup_assistant.png")));
		frameHermes.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		frameHermes.addMouseListener(new MouseAdapter() {
		});
		frameHermes.setTitle("Hermes");
		frameHermes.setResizable(false);
		frameHermes.setBounds(0, 0, screenSize.width, screenSize.height);
		frameHermes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameHermes.getContentPane().setLayout(null);
		frameHermes.setMinimumSize(new Dimension(800,600));
		frameHermes.setLocation(screenSize.width/2-frameHermes.getSize().width/2, screenSize.height/2-frameHermes.getSize().height/2);
		JPanel MousegridMap = new JPanel();
		JLabel mouseOut = new JLabel("#mouse#");
		MousegridMap.add(mouseOut);

		gridMap = new DrawMap(currentCell);
		gridMap.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		gridMap.setBounds(230, 0, screenSize.width-230, screenSize.height);;
		pathPanel = new PathPane();
		textPanel = new TextPane();
		pathPanel.setBounds(230, 0, screenSize.width-230, screenSize.height);
		textPanel.setBounds(230, 0, screenSize.width-230, screenSize.height);
		textPanel.labelAllTiles(currentCell);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, panelSize, screenSize.height);
		frameHermes.getContentPane().add(panel);
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, screenSize.width, screenSize.height);
		layeredPane.add(gridMap);
		layeredPane.add(pathPanel);
		layeredPane.add(textPanel);
		layeredPane.setComponentZOrder(gridMap, 0);
		layeredPane.setComponentZOrder(pathPanel, 0);
		layeredPane.setComponentZOrder(textPanel, 0);
		frameHermes.getContentPane().add(layeredPane);
		
		
		//layeredPane.setBounds(0, 0, 1920, 1080);
		/*
		 * Temporary layered
		 */

		layeredPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {

			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(dragging) {
					//safety check
					
					if(lastDragLocation != null) {
						int x = (int) (-0.5*(e.getX() - lastDragLocation.getX()));
						int y = (int) (-0.5*(e.getY() - lastDragLocation.getY()));
						DebugManagement.writeNotificationToLog("Dragging occurred, dx dy " + x + " , " + y);
						gridMap.render.incrementOffset(x, y, frameHermes.getWidth(), frameHermes.getHeight());
						pathPanel.setOffset(gridMap.render.offset);
						repaintPanel();
						lastDragLocation = e.getPoint();
					} else {
						lastDragLocation = new Point(e.getX(), e.getY());
					}
				}
			}
			}
		);
		layeredPane.addMouseListener(new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			Point picked = gridMap.render.pickTile(e.getX() -panelSize, e.getY());
			if(SwingUtilities.isLeftMouseButton(e)) {
				processClick(picked);
			}  

		}			@Override
			public void mouseReleased(MouseEvent e) {
				//do the dragging here
			DebugManagement.writeNotificationToLog("Dragging disabled");
				dragging = false;
				lastDragLocation = null;
			}
			@Override
			public void mousePressed(MouseEvent e) {

					
				if(SwingUtilities.isRightMouseButton(e)) {
					//right click, they intend to drag
					DebugManagement.writeNotificationToLog("Dragging enabled");
					dragging = true;
				}

			}
		});
		
		repaintPanel();
		
	}
	
	public PathPane getPathPanel(){
		return pathPanel;
	}
	
	

	private void doOffsetCalc(KeyEvent e) {
		switch(e.getKeyCode()) {
		//some optimizations to be made here
		case KeyEvent.VK_LEFT:
			gridMap.render.incrementOffset(-1*scrollSpeed, 0, gridMap.getWidth(), gridMap.getHeight());
			break;
		case KeyEvent.VK_RIGHT:
			gridMap.render.incrementOffset(scrollSpeed, 0, gridMap.getWidth(), gridMap.getHeight());
			break;
		case KeyEvent.VK_DOWN:
			gridMap.render.incrementOffset(0, scrollSpeed, gridMap.getWidth(), gridMap.getHeight());
			break;
		case KeyEvent.VK_UP:
			gridMap.render.incrementOffset(0, -1*scrollSpeed, gridMap.getWidth(), gridMap.getHeight());
			break;
		default:
			break;
		}
		pathPanel.setOffset(gridMap.render.offset);
		repaintPanel();
	}
	private void repaintPanel() {
		frameHermes.repaint();;
	}
}










//CHAFF


