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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;

import javax.swing.JLayeredPane;
import javax.swing.border.EtchedBorder;
import javax.swing.Box;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;

public class HermesUI extends JPanel{
	
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	private PathPane pathPanel;
	private PointPane pointPanel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int frameWidth = screenSize.width-200;
	private int frameHeight = screenSize.height-200;
	private MapPane gridMap;
	int scrollSpeed = 5;
	private PathCell currentCell;
	public HumanInteractionEventObject humanInteractive; 
	private Point first; //for showing in the UI which points were clicked.
	private Point second; 
	private JLayeredPane layeredPane;
	private boolean dragging;
	private Point lastDragLocation;
	private TextPane textPanel;
	private int panelSize = 230;
	private Box verticalBox;
	private JTextField startPoint;
	private JTextField destination;
	private JSeparator separator;
	private JLabel lblDirectionReadout;
	private JTextArea directionsTextPane;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private JScrollPane scrollPane;
	private double zoomScale;
	private JButton searchButton;
	private JPanel zoomPanel;
	private JButton btnPlus;
	private JButton btnMinus;
	private JButton zoomInButton;
	private JButton zoomOutBtn;
	private Box horizontalBox;
	private JTabbedPane tabbedPane;
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
				pointPanel.setFirst(first);
				pointPanel.setSecond(null);
				pathPanel.clearPath();
				repaintPanel();
			} else if(second == null) {
				
				second = new Point(picked.x,picked.y);
				gridMap.render.setSecond(second);
				pointPanel.setSecond(second);
				first = null;
				second = null;

				repaintPanel();
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
		frameHermes.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH, BootstrapperConstants.FRAME_HEIGHT);
		frameHermes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameHermes.getContentPane().setLayout(null);
		frameHermes.setMinimumSize(new Dimension(800,600));
		frameHermes.setLocation(frameWidth/2-frameWidth/2, frameHeight/2-frameHeight/2);
		JPanel MousegridMap = new JPanel();
		JLabel mouseOut = new JLabel("#mouse#");
		MousegridMap.add(mouseOut);;
		zoomPanel = new JPanel();
		zoomPanel.setBounds(66, 134, 134, -113);
		frameHermes.getContentPane().add(zoomPanel);
		zoomPanel.setLayout(new BorderLayout(0, 0));
		
		Box verticalBox_1 = Box.createVerticalBox();
		zoomPanel.add(verticalBox_1);
		
		btnPlus = new JButton("Plus");
		verticalBox_1.add(btnPlus);
		
		btnMinus = new JButton("Minus");
		verticalBox_1.add(btnMinus);
		JPanel interacactionpanel = new JPanel();
		interacactionpanel.setBounds(0, 0, BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		frameHermes.getContentPane().add(interacactionpanel);
		interacactionpanel.setLayout(null);
		
		verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(13, 5, 203, 400);
		interacactionpanel.add(verticalBox);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 30));
		verticalBox.add(verticalStrut_1);
		
		startPoint = new JTextField();
		verticalBox.add(startPoint);
		startPoint.setText("Startpoint");
		startPoint.setColumns(18);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 15));
		verticalBox.add(verticalStrut_2);
		
		destination = new JTextField();
		destination.setText("Destination");
		verticalBox.add(destination);
		destination.setColumns(18);
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 5));
		verticalBox.add(verticalStrut_3);
		
		searchButton = new JButton("Search");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchButton.setDoubleBuffered(true);
		verticalBox.add(searchButton);
		
		separator = new JSeparator();
		verticalBox.add(separator);
		
		lblDirectionReadout = new JLabel("Direction Readout");
		lblDirectionReadout.setAlignmentX(CENTER_ALIGNMENT);

		verticalBox.add(lblDirectionReadout);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		verticalBox.add(scrollPane);
		
		directionsTextPane = new JTextArea();
		scrollPane.setViewportView(directionsTextPane);
		directionsTextPane.setLineWrap(true);
		directionsTextPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		directionsTextPane.setText(createText());
		directionsTextPane.setEditable(false);
		directionsTextPane.setRows(20);
		directionsTextPane.setColumns(18);
		
		horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);
		
		zoomInButton = new JButton("");
		horizontalBox.add(zoomInButton);
		zoomInButton.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/zoomin25.png")));
		
		zoomOutBtn = new JButton("");
		horizontalBox.add(zoomOutBtn);
		zoomOutBtn.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/zoomout25.png")));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(230, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		frameHermes.getContentPane().add(tabbedPane);
		
				gridMap = new MapPane(currentCell);
				gridMap.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
				gridMap.setBounds(0, 0, frameWidth-panelSize, frameHeight);
				pathPanel = new PathPane();
				textPanel = new TextPane();
				
				pointPanel = new PointPane();

				
				pathPanel.setBounds(0, 0, frameWidth-panelSize, frameHeight);
				textPanel.setBounds(0, 0, frameWidth-panelSize, frameHeight);
				pointPanel.setBounds(0, 0, frameWidth-panelSize, frameHeight);
				textPanel.labelAllTiles(currentCell);
	
		
		layeredPane = new JLayeredPane();
		tabbedPane.addTab("New tab", null, layeredPane, null);
		layeredPane.add(gridMap);
		layeredPane.add(pathPanel);
		layeredPane.add(textPanel);
		layeredPane.add(pointPanel);
		layeredPane.setComponentZOrder(gridMap, 0);
		layeredPane.setComponentZOrder(pathPanel, 0);
		layeredPane.setComponentZOrder(textPanel, 0);
		layeredPane.setComponentZOrder(pointPanel, 0);
		//This handles map zooming by causing the Cell to re-render
		layeredPane.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            	int scalingNum = 2;//Increasing this number increases the amount of zoom one mousewheel "scroll" will zoom in for
            	int maxZoomOut = 1;
            	double maxZoomIn = 1.75D;
            	double zoomIncreaseFactor = (scalingNum/(double)BootstrapperConstants.TILE_WIDTH);
                double delta = -zoomIncreaseFactor * e.getPreciseWheelRotation();
                if(zoomScale + delta < maxZoomOut){
                	zoomScale = maxZoomOut;
                }
                else if(zoomScale >  maxZoomIn ){
                	zoomScale = maxZoomIn;
                }
                else{
                	System.out.println(zoomScale);
                    zoomScale += delta;
                    gridMap.render.zoom(zoomScale);
                    pathPanel.zoom(zoomScale);
                    //textPanel.zoom(zoomScale); TODO scale with text
                    pointPanel.zoom(zoomScale);
                    
                }
                frameHermes.revalidate();
        		frameHermes.repaint();
            }
        });
		

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
						gridMap.render.incrementOffset(x, y, frameWidth, frameHeight);
						pathPanel.setOffset(gridMap.render.offset);
						pointPanel.setOffset(gridMap.render.offset);
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
			Point picked = gridMap.render.pickTile(e.getX() , e.getY());
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
		frameHermes.getContentPane().add(zoomPanel);
		
		/*
		 * Temporary layered
		 */
		
		repaintPanel();
		
	}
	//This is a dummy method to check and make sure directions will be able to load well.
	//Can get rid of once we have directions.
	public String createText(){
		String text = null;
		Random randomGenerator = new Random();
		text = Integer.toString(randomGenerator.nextInt(10));
		return text;
	}
	
	public PathPane getPathPanel(){
		return pathPanel;
	}
	public PointPane getPointPane(){
		return pointPanel;
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
		pointPanel.setOffset(gridMap.render.offset);
		repaintPanel();
	}
	private void repaintPanel() {
		frameHermes.repaint();
	}
}










//CHAFF


