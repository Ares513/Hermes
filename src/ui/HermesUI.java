package ui;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

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
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Predicate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;

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
import completely.AutocompleteEngine;
import core.BootstrapperConstants;
import core.DebugManagement;
import events.HumanInteractionEventObject;
import events.IHumanInteractionListener;
import events.SearchReadyEventObject;
import pathing.CellPoint;
import pathing.PathCell;

import com.sun.xml.internal.ws.api.pipe.Engine;
import com.team1ofus.apollo.EntryPoint;
import com.team1ofus.apollo.TILE_TYPE;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;
import java.awt.print.*;
import javax.swing.JTextPane;

//Holds all of the UI elements for the project
public class HermesUI extends JPanel implements IHumanInteractionListener{

	ArrayList<Point> pointsList = new ArrayList<Point>();
	private JFrame frameHermes;
	//private PathPane pathPanel;
	//private PointPane pointPanel;
	private int frameWidth = BootstrapperConstants.FRAME_WIDTH;
	private int frameHeight = BootstrapperConstants.FRAME_HEIGHT;
	//private MapPane gridMap;
	int scrollSpeed = 5;
	private ArrayList<PathCell> allCells = new ArrayList<PathCell>();
	public HumanInteractionEventObject humanInteractive; 
	private CellPoint first; //for showing in the UI which points were clicked.
	private CellPoint second; 
	//private JLayeredPane layeredPane;
	private boolean dragging;
	private Point lastDragLocation;
	//private TextPane textPanel;
	private int panelSize = 230;
	private Box verticalBox;
	private JComboBox<String> startPoint;
	private JComboBox<String> destination;
	private Record searchStartRecord = null; //an inelegant way to retain the information we need for search. 
	private Record searchEndRecord = null; //hopefully we can re-factor these at some point. If you want to please feel free to.
	private JSeparator separator;
	private JLabel lblDirectionReadout;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	private double zoomScale;
	private JButton searchButton;
	private JPanel zoomPanel;
	private JButton btnPlus;
	private JButton btnMinus;
	private JButton zoomInButton;
	private JButton zoomOutBtn;
	private JButton prevButton;
	private JButton nextButton;
	private Box horizontalBox;
	private MapTabbedPane<MapTabbedPane<MapLayeredPane>> tabbedPane;
	private ArrayList<Record> locationNameInfoRecords;
	public PrintToPrinter printer =new PrintToPrinter(); ;
	private AutocompleteEngine<Record> engine = new AutocompleteEngine.Builder<Record>()
            .setIndex(new ACAdapter())
            .setAnalyzer(new ACAnalyzer())
            .build();
	
	private SearchReadyEventObject searchEvents;
	private ArrayList<String> cellsInPath = new ArrayList<String>();
	private ArrayList<ArrayList<CellPoint>> segmentedPath = new ArrayList<ArrayList<CellPoint>>();
	private ArrayList<String> buildings = new ArrayList<String>();
	private ArrayList<ArrayList<String>> floors = new ArrayList<ArrayList<String>>();
	private HashMap<String, String> nameToDisplay = new HashMap<String, String>();
	private HashMap<String, String> buildingNames = new HashMap<String, String>();
	private int mapIndex = 0; // index of where one is in switching between maps in the path AStar returns
	private JTextPane instructionsTextPane;
    private StyledDocument instructionsDoc;         
    private JScrollPane scrollPane;
	public HermesUI(ArrayList<PathCell> viewCells, ArrayList<Record> locationNameInfoRecords) {
		this.locationNameInfoRecords = locationNameInfoRecords;
		
		this.searchEvents = new SearchReadyEventObject(); 
		this.humanInteractive = new HumanInteractionEventObject();
		initialize(viewCells);
	}
	/*
	 * initialize the Hermes UI
	 */
	public void initialize(ArrayList<PathCell> viewCells) {		
		allCells = viewCells;
		
		int buildingIterator = 0;
		for(int i = 0; i < allCells.size(); i++) {
			if(allCells.get(i).getName().substring(0, 2).equals("Wo"))
				nameToDisplay.put(allCells.get(i).getName(), "Campus");
			else
				nameToDisplay.put(allCells.get(i).getName(), allCells.get(i).getDisplayName());
			if(buildings.contains(buildingNames.get(allCells.get(i).getName().substring(0, 2))))
				floors.get(buildingIterator-1).add(nameToDisplay.get(allCells.get(i).getName()));
			else {
				mapBuildingName(allCells.get(i).getName().substring(0, 2), allCells.get(i).getDisplayName());
				buildings.add(buildingNames.get(allCells.get(i).getName().substring(0, 2)));
				floors.add(new ArrayList<String>());
				floors.get(buildingIterator).add(nameToDisplay.get(allCells.get(i).getName()));
				buildingIterator++;
			}
		}

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
	
	/*
	 * This splits the path and sends them off to the correct map
	 */
	public void drawPath(ArrayList<CellPoint> path){// Test points
		 //*/
		//searchStartRecord, searchEndRecord; 
		mapIndex = 0;
		int size = path.size(); 
		Point firstPoint = path.get(0).getPoint();
		Point lastPoint = path.get(size-1).getPoint(); 
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setFirst(null);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setSecond(null);
		//reset all tab colors
		for(int i = 0; i < tabbedPane.getTabCount(); i++) {
			tabbedPane.setForegroundAt(i, null);
			for(int j = 0; j < tabbedPane.getTabAt(i).getTabCount(); j++)
				tabbedPane.getTabAt(i).setForegroundAt(j, null);
			}

		 splitPath(path);
		 for(int i = 0; i < cellsInPath.size(); i++) {
			tabbedPane.setSelectedIndex(tabbedPane.getIndexOfTab(buildingNames.get(cellsInPath.get(i).substring(0, 2))));
			tabbedPane.getSelectedTabPane().setSelectedIndex(tabbedPane.getSelectedTabPane().getIndexOfTab(nameToDisplay.get(cellsInPath.get(i))));
			repaintPanel();
			tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane().drawPath(segmentedPath.get(i), cellsInPath.get(i));			 
			//tabbedPane.getSelectedTabPane().getSelectedTabPane().setOffset(new Point(segmentedPath.get(i).get(0).getPoint().x * BootstrapperConstants.TILE_WIDTH, segmentedPath.get(i).get(0).getPoint().y * BootstrapperConstants.TILE_HEIGHT));

			if(i == 0){ 
				tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setFirst(firstPoint);
			}
			if(i == cellsInPath.size() - 1){
				tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setSecond(lastPoint);
			}
			tabbedPane.getSelectedTabPane().setForegroundAt(tabbedPane.getSelectedTabPane().getSelectedIndex(), new Color(153, 0, 80));
			tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), new Color(153, 0, 80));
		}
	 	first = null; 
	 	second = null;
	 	
		if(cellsInPath.size() > 1) {
			prevButton.setEnabled(false);
			nextButton.setEnabled(true);
		} else {
			prevButton.setEnabled(false);
			nextButton.setEnabled(false);
		}
		
		tabbedPane.setSelectedIndex(tabbedPane.getIndexOfTab(buildingNames.get(cellsInPath.get(0).substring(0, 2))));
		tabbedPane.getSelectedTabPane().setSelectedIndex(tabbedPane.getSelectedTabPane().getIndexOfTab(nameToDisplay.get(cellsInPath.get(0))));
		
	}
	
	/*
	 * Splits path based on names in cellpoints
	 * cellsInPath contains the map names, segmentedPath paths for those respective maps
	 * sizes of those arrays are always the same, indexes always correspond
	 */
	private void splitPath(ArrayList<CellPoint> path) {
		cellsInPath.clear();
		segmentedPath.clear();
		
		int subpathStart = 0;
		int subpathFinish = path.size();
		cellsInPath.add(path.get(0).getCellName());
		
		for(int i = 1; i < path.size(); i++){
			if(!cellsInPath.contains(path.get(i).getCellName())) {
				subpathFinish = i - 1;
				segmentedPath.add(populateSubpath(path, subpathStart, subpathFinish));
				cellsInPath.add(path.get(i).getCellName());
				subpathStart = i;
			}
		}
		subpathFinish = path.size() - 1;
		segmentedPath.add(populateSubpath(path, subpathStart, subpathFinish));
	}
	
	/*
	 * Just populates a subpath
	 */
	private ArrayList<CellPoint> populateSubpath(ArrayList<CellPoint> path, int start, int finish) {
		ArrayList<CellPoint> subpath = new ArrayList<CellPoint>();
		for(int i = start; i <= finish; i++)
			subpath.add(path.get(i));
		return subpath;
	}
	
	/*
	 * Fills out map for building names
	 */
	private void mapBuildingName(String abbreviation, String displayName) {
		if((displayName.endsWith(" 1") || displayName.endsWith(" 2") || displayName.endsWith(" 3")) && !buildingNames.containsKey(abbreviation))
			buildingNames.put(abbreviation, displayName.substring(0, displayName.length() - 2));
		else if(abbreviation.equals("Wo"))
			buildingNames.put(abbreviation, "Campus Map");
	}

	//Allows us to paint the image within the JLabel	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().paintComponents(g);
	}

	//Builds the frames and panels for the UI, as well as adding the mouse events that will affect the UI
	private void buildControl(){
		frameHermes = new JFrame();
		frameHermes.setIconImage(Toolkit.getDefaultToolkit().getImage(HermesUI.class.getResource("/com/team1ofus/hermes/resources/setup_assistant.png")));
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
		verticalBox.setBounds(13, 5, 290, 537);
		interacactionpanel.add(verticalBox);

		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 2));
		verticalBox.add(verticalStrut_1);

		//start search ui stuff
		
	    DefaultComboBoxModel<String> modelForStart = new DefaultComboBoxModel<>(  );
	    locationNameInfoRecords.add(new Record(BootstrapperConstants.MALE_BATHROOM_IDENTIFIER, "No Cell Ref"));
	    locationNameInfoRecords.add(new Record(BootstrapperConstants.FEMALE_BATHROOM_IDENTIFIER, "No Cell Ref"));
	    locationNameInfoRecords.add(new Record(BootstrapperConstants.UNISEX_BATHROOM_IDENTIFIER, "No Cell Ref"));
	    locationNameInfoRecords.add(new Record(BootstrapperConstants.BENCH_IDENTIFIER, "No Cell Ref"));
	    
	    for (Record r:locationNameInfoRecords){
	    	//leave out bathroom, bench and autogen records
	    	boolean keep = true;
	    	for(String s : r.getFields()) {
	    		if(s.contains("AutoGen")) {
	    			keep = false;
	    		}
	    	}
	    	if(keep && r.getCellName() != "No Cell Ref") {
	    		modelForStart.addElement(r.getVal());
	    	    
	    	}
	    }
	    
	    startPoint = new JComboBox<String>();
	    startPoint.setMaximumSize(new Dimension(32767, 50));
	    startPoint.setModel(modelForStart);
		startPoint.setEditable(true);
		AutoCompleteDecorator.decorate( startPoint );

		JLabel lblStart = new JLabel("Starting point");
		lblStart.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblStart);
		verticalBox.add(startPoint);
		//startPoint.setText("Startpoint");
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 15));
		verticalBox.add(verticalStrut_2);

		//String[] destinations = new String[] {"AK", "FL", "SL"};
		// should be deep copy from modelForStart
	    DefaultComboBoxModel<String> modelForDestination = new DefaultComboBoxModel<>(  );
	    for (Record r:locationNameInfoRecords){
	    	//leave out auto generated values;
	    	boolean keep = true;
	    	for(String s : r.getFields()) {
	    		if(s.contains("AutoGen")) {
	    			keep = false;
	    		}
	    	}
	    	if(keep) {
	    		modelForDestination.addElement(r.getVal());
	    	    
	    	}
	    }
	    destination = new JComboBox<String>();
	    destination.setMaximumSize(new Dimension(32767, 26));
		destination.setModel(modelForDestination);
		destination.setEditable(true);
		AutoCompleteDecorator.decorate( destination );

		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblDestination);
		//destination.setText("Destination");
		verticalBox.add(destination);
		//destination.setColumns(18);

		//end search ui stuff
		
		verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 5));
		verticalBox.add(verticalStrut_3);

		searchButton = new JButton("Search");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchButton.setDoubleBuffered(true);
		searchButton.addActionListener(new SearchListener());
		verticalBox.add(searchButton);

		separator = new JSeparator();
		separator.setMaximumSize(new Dimension(32767, 2));
		verticalBox.add(separator);

		lblDirectionReadout = new JLabel("Direction Readout");
		lblDirectionReadout.setAlignmentX(CENTER_ALIGNMENT);

		verticalBox.add(lblDirectionReadout);
		
		scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(32, 100));
		scrollPane.setPreferredSize(new Dimension(2, 400));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		verticalBox.add(scrollPane);
		
		instructionsTextPane = new JTextPane();
		instructionsTextPane.setPreferredSize(new Dimension(6, 400));
		instructionsTextPane.setBounds(new Rectangle(0, 0, 0, 400));
		instructionsTextPane.setMinimumSize(new Dimension(6, 400));
		scrollPane.setViewportView(instructionsTextPane);
		instructionsTextPane.setEditable(false);
		instructionsDoc = instructionsTextPane.getStyledDocument();
		instructionsTextPane.setText("");
		
		Box mapSwitchHBox = Box.createHorizontalBox();
		verticalBox.add(mapSwitchHBox);
		
		prevButton = new JButton("Prev map");
		prevButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		prevButton.setEnabled(false);
		mapSwitchHBox.add(prevButton);

		nextButton = new JButton("Next map");
		nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextButton.setEnabled(false);
		mapSwitchHBox.add(nextButton);
		
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cellsInPath.size() > 0) {
					mapIndex--;
					tabbedPane.setSelectedIndex(tabbedPane.getIndexOfTab(buildingNames.get(cellsInPath.get(mapIndex).substring(0, 2))));
					tabbedPane.getSelectedTabPane().setSelectedIndex(tabbedPane.getSelectedTabPane().getIndexOfTab(nameToDisplay.get(cellsInPath.get(mapIndex))));
					if(mapIndex == 0) {
						prevButton.setEnabled(false);
					}
					if(mapIndex < cellsInPath.size() - 1)
						nextButton.setEnabled(true);
				}	
			}
		});

		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(cellsInPath.size() > 0) {
					mapIndex++;
					System.out.println("Index " + tabbedPane.getSelectedIndex());
					System.out.println("mapIndex " + mapIndex);
					System.out.println("Building name " + buildingNames.get(cellsInPath.get(mapIndex).substring(0, 2)));
					System.out.println("Index of tab we want " + tabbedPane.getIndexOfTab(buildingNames.get(cellsInPath.get(mapIndex).substring(0, 2))));
					tabbedPane.setSelectedIndex(tabbedPane.getIndexOfTab(buildingNames.get(cellsInPath.get(mapIndex).substring(0, 2))));
					tabbedPane.getSelectedTabPane().setSelectedIndex(tabbedPane.getSelectedTabPane().getIndexOfTab(nameToDisplay.get(cellsInPath.get(mapIndex))));
					if(mapIndex >= cellsInPath.size() - 1) {
						mapIndex = cellsInPath.size() - 1;
						nextButton.setEnabled(false);
					}
					if(mapIndex > 0)
						prevButton.setEnabled(true);
				}
			}
		});
		
		//printer = new PrintToPrinter(); 
		JButton printButton = new JButton("Print out Directions");
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 PrinterJob job = PrinterJob.getPrinterJob();
		         job.setPrintable(printer);
		         boolean ok = job.printDialog();
		         if (ok) {
		             try {
		                  job.print();
		             } catch (PrinterException ex) {
		              /* The job did not successfully complete */
		             }
		         }
			}
		});
		printButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(printButton);
		
		JSeparator separator_1 = new JSeparator();
		verticalBox.add(separator_1);
		 
		horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		zoomInButton = new JButton("");
		horizontalBox.add(zoomInButton);
		zoomInButton.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/resources/zoomin25.png")));
		//These event handlers will handle zooming in and out based on the zoom buttons TODO Make this work
		zoomInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int zoomin = -1;
				tabbedPane.getSelectedTabPane().getSelectedTabPane().zoom(zoomin);
			}
		});

		zoomOutBtn = new JButton("");
		horizontalBox.add(zoomOutBtn);
		zoomOutBtn.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/resources/zoomout25.png")));
		zoomOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int zoomout = 1;
				tabbedPane.getSelectedTabPane().getSelectedTabPane().zoom(zoomout);
			}
		});

		tabbedPane = new MapTabbedPane<MapTabbedPane<MapLayeredPane>>(JTabbedPane.TOP);
		tabbedPane.setBounds(BootstrapperConstants.PANEL_SIZE, 2, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE-10, BootstrapperConstants.FRAME_HEIGHT-30);
		frameHermes.getContentPane().add(tabbedPane);
		
		
		/*
		//TODO Make display the name of the cell, i.e. currentCell.getName()
		tabbedPane.addNewTab("All Cells", null, new MapTabbedPane<MapLayeredPane>(JTabbedPane.BOTTOM), null);
		tabbedPane.setSelectedIndex(0);
		for(int i=0; i<allCells.size(); i++) {
			tabbedPane.getSelectedTabPane().addNewTab(allCells.get(i).getName(), null, new MapLayeredPane(allCells.get(i)), null);
			tabbedPane.getSelectedTabPane().setSelectedIndex(i);
			tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this, "HermesUI to " + allCells.get(i).getName());
		}*/
		
		int cellIterator = 0;
		for(int i = 0; i < buildings.size(); i++) {
			tabbedPane.addNewTab(buildings.get(i), null, new MapTabbedPane<MapLayeredPane>(JTabbedPane.BOTTOM, buildings.get(i)), null);
			tabbedPane.setSelectedIndex(i);
			for(int j = 0; j < floors.get(i).size(); j++) {
				tabbedPane.getSelectedTabPane().addNewTab(floors.get(i).get(j), null, new MapLayeredPane(allCells.get(cellIterator)), null);
				tabbedPane.getSelectedTabPane().setSelectedIndex(j);
				tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this, "HermesUI to " + allCells.get(cellIterator).getName());
				cellIterator++;
			}
		}
		
		frameHermes.getContentPane().add(zoomPanel);

		/*
		 * Temporary layered
		 */

		repaintPanel();

	}
	
	public void directionText(ArrayList<Directions> directions){
		instructionsTextPane.setText(""); 
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, Color.BLACK);
	//	StyleConstants.set(keyWord, Color.BLACK);
		//StyleConstants.setBackground(keyWord, Color.YELLOW);
		StyleConstants.setBold(keyWord, true);
		
		SimpleAttributeSet lineBreak = new SimpleAttributeSet(); 
		StyleConstants.setForeground(lineBreak, Color.LIGHT_GRAY);
		//printInstructions = new ArrayList<String>(); 
		
			
		
		int size = directions.size(); 
		for(int i =0; i < size; i++){ 
			Directions current = directions.get(i); 
			
			String direction = current.getTurnInstruction(); 
			String iconName = current.getIcon(); 
			SimpleAttributeSet turnImage =new SimpleAttributeSet();
			Icon icon;  
			if(i == 0){ 
				icon = new ImageIcon ("Forward.png");
			}
			else { 
				icon = new ImageIcon (iconName);
			}
			JLabel label = new JLabel(icon); 
			
			StyleConstants.setComponent(turnImage, label);
			try{ 
			instructionsDoc.insertString(instructionsDoc.getLength(), " ",turnImage);
			instructionsDoc.insertString(instructionsDoc.getLength(), direction, keyWord);
			instructionsDoc.insertString(instructionsDoc.getLength(), "\n      ———————————————\n",lineBreak);
			
			System.out.println("tried to display instruction");
			} 
			catch(Exception e){ 
				System.out.println(e);
			}
			
			//printer.printInstructions.add(direction);
			
		} 
	
	}
	
	
	public PathPane getPathPanel(){
		return tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane();
	}
	public PointPane getPointPane(){
		return tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane();
	}

	//These keyboard events handle panning with the keyboard
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
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPathPane().setOffset(tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.getOffset());
		tabbedPane.getSelectedTabPane().getSelectedTabPane().getPointPane().setOffset(tabbedPane.getSelectedTabPane().getSelectedTabPane().getMapPane().render.getOffset());
		repaintPanel();
	}
	private void repaintPanel() {
		frameHermes.repaint();
	}

	@Override
	public void onTileClicked(CellPoint input) {
		DebugManagement.writeNotificationToLog("HermesUI processed a onTileClicked event.");
		//Ensure that the CellPoint's target cell is the same as the currently viewed cell.
		String currentCellName = tabbedPane.getSelectedTabPane().getSelectedTabPane().getCurrentCell().getName();
		DebugManagement.writeNotificationToLog("The cell name of the window was " + currentCellName + " and the input cell was " + input.getCellName());
		
		if(currentCellName == input.getCellName()) {
			humanInteractive.doClick(input);
			
		}
	}
	
	public void addListenerToSelectedTab() {
		tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this, "HermesUI through addListenerToSelectedTab");
	}
	
	class SearchListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String startLocation = (String) startPoint.getSelectedItem();
			DebugManagement.writeNotificationToLog("start location is : " + startLocation);
			String destLocation = (String) destination.getSelectedItem();
			searchStartRecord = locationNameInfoRecords.get(0);
			searchEndRecord = locationNameInfoRecords.get(0);
		    for (Record r:locationNameInfoRecords){
		    	if (r.getVal().equals(startLocation)){
		    		searchStartRecord = r;
		    	}
		    	if (r.getVal().equals(destLocation)){
		    		searchEndRecord = r;
		    	}
		    }

			searchEvents.doSearchReady(searchStartRecord, searchEndRecord);
			repaintPanel();
		}
		
	}
	
	public SearchReadyEventObject getSearchEvents(){
		return this.searchEvents;
	}

	@Override
	public void findNearestLocation(CellPoint start, String filter) {
		humanInteractive.findNearestLocation(start, filter);
	}
}