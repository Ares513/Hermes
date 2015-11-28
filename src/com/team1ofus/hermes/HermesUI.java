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

public class HermesUI extends JPanel{
	
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	private PathPane pathPanel;
	private PointPane pointPanel;
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
	public ZoomEventObject zoomEvent;
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
	private Component verticalStrut;
	private JTextArea directionsTextPane;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private JButton searchButton;
	private Component verticalStrut_3;
	private JScrollPane scrollPane;
	private double zoomScale;
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
		try {
			pointPanel = new PointPane();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pathPanel.setBounds(230, 0, screenSize.width-230, screenSize.height);
		textPanel.setBounds(230, 0, screenSize.width-230, screenSize.height);
		pointPanel.setBounds(230, 0, screenSize.width-230, screenSize.height);
		textPanel.labelAllTiles(currentCell);
		JPanel interacactionpanel = new JPanel();
		interacactionpanel.setBounds(0, 0, panelSize, screenSize.height);
		frameHermes.getContentPane().add(interacactionpanel);
		
		verticalBox = Box.createVerticalBox();
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
		searchButton.setDoubleBuffered(true);
		verticalBox.add(searchButton);
		
		separator = new JSeparator();
		verticalBox.add(separator);
		
		verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 650));
		verticalBox.add(verticalStrut);
		
		lblDirectionReadout = new JLabel("Direction Readout");
		lblDirectionReadout.setAlignmentX(CENTER_ALIGNMENT);

		verticalBox.add(lblDirectionReadout);
		
		scrollPane = new JScrollPane();
		verticalBox.add(scrollPane);
		
		directionsTextPane = new JTextArea();
		scrollPane.setViewportView(directionsTextPane);
		directionsTextPane.setLineWrap(true);
		directionsTextPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		directionsTextPane.setText("This will have directions\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam commodo eget diam egestas ullamcorper. Aliquam erat volutpat. Nunc gravida, dolor congue vehicula efficitur, erat lorem pulvinar nibh, hendrerit tincidunt risus arcu id tortor. Aenean tempor nisi et vulputate gravida. Suspendisse blandit mi dui, a fringilla purus suscipit sit amet. Duis ac sollicitudin odio. Praesent mollis elementum dolor, vel iaculis nisi eleifend nec. Morbi dapibus elit sapien, non suscipit lacus tristique vitae. Pellentesque faucibus tempus mauris ut egestas. Phasellus tincidunt lacus massa, vitae hendrerit orci accumsan quis.\n\nAenean ac convallis mi. Duis scelerisque sapien tortor, eu vulputate ipsum maximus vulputate. Nulla pharetra facilisis blandit. Nulla maximus justo dapibus gravida aliquet. Cras et volutpat lectus. Praesent nec lacus in ligula aliquet tristique quis quis lectus. Fusce lacinia dui metus, maximus dictum ipsum porttitor et. Nullam nulla eros, fermentum et dolor at, molestie pharetra odio. Nam nec mi et dolor efficitur viverra. Donec suscipit erat sit amet eros euismod semper. Maecenas at lacus lectus. Suspendisse sed scelerisque ligula, lobortis aliquet leo. Duis scelerisque varius nibh, ultrices porttitor lorem molestie sed. Morbi blandit eget orci et vehicula. Aenean vehicula semper quam vitae gravida. Mauris lacinia sit amet erat ut lacinia.");
		directionsTextPane.setEditable(false);
		directionsTextPane.setRows(20);
		directionsTextPane.setColumns(18);
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, screenSize.width, screenSize.height);
		layeredPane.add(gridMap);
		layeredPane.add(pathPanel);
		layeredPane.add(textPanel);
		layeredPane.add(pointPanel);
		layeredPane.setComponentZOrder(gridMap, 0);
		layeredPane.setComponentZOrder(pathPanel, 0);
		layeredPane.setComponentZOrder(textPanel, 0);
		layeredPane.setComponentZOrder(pointPanel, 0);
		frameHermes.getContentPane().add(layeredPane);

		//layeredPane.setBounds(0, 0, 1920, 1080);
		/*
		 * Temporary layered
		 */
		layeredPane.addMouseWheelListener(new MouseAdapter() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double delta = -0.05f * e.getPreciseWheelRotation();
                //System.out.println(delta);
                if(zoomScale + delta < 1){
                	zoomScale =1;
                }
                else{
                    zoomScale += delta;
                    DebugManagement.writeNotificationToLog("zoomScale is");
                    System.out.println(zoomScale);
                    zoomEvent.addListener(gridMap);
                    zoomEvent.doZoom(zoomScale);
                }
                revalidate();
                repaint();
             
                //Event object should fire here adding the appropriate listeners to the list
                //Need to make ZoomEventObject
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
						gridMap.render.incrementOffset(x, y, frameHermes.getWidth(), frameHermes.getHeight());
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


