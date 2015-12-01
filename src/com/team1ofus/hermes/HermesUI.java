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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;

public class HermesUI extends JPanel implements IHumanInteractionListener{
	
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	//private PathPane pathPanel;
	//private PointPane pointPanel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int frameWidth = screenSize.width-200;
	private int frameHeight = screenSize.height-200;
	//private MapPane gridMap;
	int scrollSpeed = 5;
	private PathCell currentCell;
	public HumanInteractionEventObject humanInteractive; 
	public ZoomEventObject zoomEvent;
	private Point first; //for showing in the UI which points were clicked.
	private Point second; 
	//private JLayeredPane layeredPane;
	private boolean dragging;
	private Point lastDragLocation;
	//private TextPane textPanel;
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
	private MapTabbedPane<MapTabbedPane<MapTabPane>> tabbedPane;
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
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					doOffsetCalc(e);
				}
			});
        frameHermes.setVisible(true);
	}
	//Would just skip this and go straight to MyPanel's drawPath, but I'm afraid that it will break and I don't have time to fix it
	 void drawPath(ArrayList<CellPoint> path){
		 tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane().drawPath(path);
		 repaintPanel();;
	    }

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().paintComponents(g);
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
		
		tabbedPane = new MapTabbedPane<MapTabbedPane<MapTabPane>>(JTabbedPane.TOP);
		tabbedPane.setBounds(BootstrapperConstants.PANEL_SIZE, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE-10, BootstrapperConstants.FRAME_HEIGHT-30);
		frameHermes.getContentPane().add(tabbedPane);
		
		
		
		//TODO Make display the name of the cell, i.e. currentCell.getName()
		tabbedPane.addNewTab("New tab", null, new MapTabbedPane<MapTabPane>(JTabbedPane.BOTTOM), null);
		tabbedPane.getSelectedTabPane().addNewTab("Tab in tab", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this);
		/*//Adding more tabs for testing
		tabbedPane.addNewTab("tab 2", null, new MapTabbedPane<MapTabPane>(JTabbedPane.BOTTOM), null);
		tabbedPane.setSelectedIndex(1);
		tabbedPane.getSelectedTabPane().addNewTab("1", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().addNewTab("2", null, new MapTabPane(currentCell), null);
		
		tabbedPane.addNewTab("super tab 3", null, new MapTabbedPane<MapTabPane>(JTabbedPane.BOTTOM), null);
		tabbedPane.setSelectedIndex(2);
		tabbedPane.getSelectedTabPane().addNewTab("poop", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().addNewTab("shit", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().addNewTab("scat", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().setSelectedIndex(2);
		tabbedPane.setSelectedIndex(0);*/
		
		//If the textpane's change, add HermesUI as a listener TODO: this is probably wrong
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				addListenerToSelectedTab();
				tabbedPane.getSelectedTabPane().addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						addListenerToSelectedTab();
						
					}
					
				});
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
		return tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane();
	}
	public PointPane getPointPane(){
		return tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane();
	}
	

	private void doOffsetCalc(KeyEvent e) {
		switch(e.getKeyCode()) {
		//some optimizations to be made here
		case KeyEvent.VK_LEFT:
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.incrementOffset(-1*scrollSpeed, 0, tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getWidth(), tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getHeight());
			break;
		case KeyEvent.VK_RIGHT:
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.incrementOffset(scrollSpeed, 0, tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getWidth(), tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getHeight());
			break;
		case KeyEvent.VK_DOWN:
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.incrementOffset(0, scrollSpeed, tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getWidth(), tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getHeight());
			break;
		case KeyEvent.VK_UP:
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.incrementOffset(0, -1*scrollSpeed, tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getWidth(), tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().getHeight());
			break;
		default:
			break;
		}
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane().setOffset(tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.offset);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setOffset(tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.offset);
		repaintPanel();
	}
	private void repaintPanel() {
		frameHermes.repaint();
	}

	@Override
	public void onTileClicked(int x, int y) {
		humanInteractive.doClick(x, y);
	}
	
	public void addListenerToSelectedTab() {
		tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this);
	}
}










//CHAFF


