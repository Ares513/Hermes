package com.team1ofus.hermes;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.DefaultComboBoxModel;
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
import java.util.Random;
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
import com.sun.xml.internal.ws.api.pipe.Engine;
import com.team1ofus.apollo.TILE_TYPE;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTabbedPane;
import java.awt.print.*;

//Holds all of the UI elements for the project
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
	private Point first; //for showing in the UI which points were clicked.
	private Point second; 
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
	public JTextArea directionsTextPane;
	private Component verticalStrut_1;
	private Component verticalStrut_2;
	private Component verticalStrut_3;
	public JScrollPane scrollPane;
	private double zoomScale;
	private JButton searchButton;
	private JPanel zoomPanel;
	private JButton btnPlus;
	private JButton btnMinus;
	private JButton zoomInButton;
	private JButton zoomOutBtn;
	private Box horizontalBox;
	private JButton removeButton;
	private MapTabbedPane<MapTabbedPane<MapTabPane>> tabbedPane;
	private ArrayList<Record> locationNameInfoRecords;
	public PrintToPrinter printer =new PrintToPrinter(); ;
	private AutocompleteEngine<Record> engine = new AutocompleteEngine.Builder<Record>()
            .setIndex(new ACAdapter())
            .setAnalyzer(new ACAnalyzer())
            .build();
	
	private SearchReadyEventObject searchEvents;
            
	public HermesUI(PathCell viewCell, ArrayList<Record> locationNameInfoRecords) {
		this.locationNameInfoRecords = locationNameInfoRecords;
		
		this.searchEvents = new SearchReadyEventObject(); 
		this.humanInteractive = new HumanInteractionEventObject();
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

	//Allows us to paint the image within the JLabel	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().paintComponents(g);
	}

	//Builds the frames and panels for the UI, as well as adding the mouse events that will affect the UI
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
		verticalBox.setBounds(13, 5, 275, 537);
		interacactionpanel.add(verticalBox);

		verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 30));
		verticalBox.add(verticalStrut_1);

	    DefaultComboBoxModel<String> modelForStart = new DefaultComboBoxModel<>(  );
	    for (Record r:locationNameInfoRecords){
	    	modelForStart.addElement(r.getVal());
	    }
	    startPoint = new JComboBox<String>();
	    startPoint.setModel(modelForStart);
		startPoint.setEditable(true);
		AutoCompleteDecorator.decorate( startPoint );

		verticalBox.add(startPoint);
		//startPoint.setText("Startpoint");
		startPoint.addKeyListener(new KeyListenerForStart());
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		verticalStrut_2.setPreferredSize(new Dimension(0, 15));
		verticalBox.add(verticalStrut_2);

		//String[] destinations = new String[] {"AK", "FL", "SL"};
		// deep copy from modelForStart
	    DefaultComboBoxModel<String> modelForDestination = new DefaultComboBoxModel<>(  );
	    for (Record r:locationNameInfoRecords){
	    	modelForDestination.addElement(r.getVal());
	    }
	    destination = new JComboBox<String>();
		destination.setModel(modelForDestination);
		destination.setEditable(true);
		AutoCompleteDecorator.decorate( destination );

		
		//destination.setText("Destination");
		verticalBox.add(destination);
		destination.addKeyListener(new KeyListenerForDestination());
		//destination.setColumns(18);

		verticalStrut_3 = Box.createVerticalStrut(20);
		verticalStrut_3.setPreferredSize(new Dimension(0, 5));
		verticalBox.add(verticalStrut_3);

		searchButton = new JButton("Search");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchButton.setDoubleBuffered(true);
		searchButton.addActionListener(new SearchListener());
		verticalBox.add(searchButton);

		separator = new JSeparator();
		verticalBox.add(separator);
		
		removeButton = new JButton("Close this building");
		removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeButton.setDoubleBuffered(true);
		verticalBox.add(removeButton);

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
		//directionsTextPane.setText(createText());
		directionsTextPane.setEditable(false);
		directionsTextPane.setRows(20);
		directionsTextPane.setColumns(18);
		
		
		//printer = new PrintToPrinter(); 
		JButton printButton = new JButton("Print out Directions");
		printButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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
		zoomInButton.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/zoomin25.png")));
		//These event handlers will handle zooming in and out based on the zoom buttons TODO Make this work
		zoomInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Plus Button pressed");
				int zoomin = -1;
				tabbedPane.getSelectedTabPane().getSelectedTabPane().zoom(zoomin);
			}
		});

		zoomOutBtn = new JButton("");
		horizontalBox.add(zoomOutBtn);
		zoomOutBtn.setIcon(new ImageIcon(HermesUI.class.getResource("/com/team1ofus/hermes/zoomout25.png")));
		zoomOutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Minus Button pressed");
				int zoomout = 1;
				tabbedPane.getSelectedTabPane().getSelectedTabPane().zoom(zoomout);
			}
		});

		tabbedPane = new MapTabbedPane<MapTabbedPane<MapTabPane>>(JTabbedPane.TOP);
		tabbedPane.setBounds(BootstrapperConstants.PANEL_SIZE, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE-10, BootstrapperConstants.FRAME_HEIGHT-30);
		frameHermes.getContentPane().add(tabbedPane);
		
		
		
		//TODO Make display the name of the cell, i.e. currentCell.getName()
		tabbedPane.addNewTab("New tab", null, new MapTabbedPane<MapTabPane>(JTabbedPane.BOTTOM), null);
		tabbedPane.getSelectedTabPane().addNewTab("Tab in tab", null, new MapTabPane(currentCell), null);
		tabbedPane.getSelectedTabPane().getSelectedTabPane().humanInteractive.addListener(this);
		//Adding more tabs for testing
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
		tabbedPane.setSelectedIndex(0);
		
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
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabbedPane.getSelectedIndex() != 0)
					tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
				else
					DebugManagement.writeNotificationToLog("Can't delete the main pane");
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
	public void directionText(ArrayList<String> directions){
		directionsTextPane.setText("");
		printer.printInstructions = new ArrayList<String>(); 
		int size = directions.size(); 
		for(int i =0; i < size; i++){ 
			String direction = directions.get(i); 
			directionsTextPane.append(direction);
			directionsTextPane.append("\n-------------");
			directionsTextPane.append("\n");
			printer.printInstructions.add(direction);
			
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
	
	/* CustomKeyListener for the Startpoint, each time a key is pressed return a list of matching from the database
	 * 
	 */
	 class CustomKeyListener implements KeyListener{
	      public void keyTyped(KeyEvent e) {
	      }

	      public void keyPressed(KeyEvent e) {
	      }
	      
	      public void updateResultPoint(String[] possibleDestinations){
	    	  
	      }
	      public void updateTargetRecord(Record r){
	    	  
	      }
	      public JComboBox getComboBox(){
	    	  return startPoint;
	      }

	      public void keyReleased(KeyEvent e) {
	    	  String input = (String) getComboBox().getSelectedItem();
	    	  System.out.println(input);
	    	  
	    	  DebugManagement.writeNotificationToLog(input);
	    	  List<Record> result = engine.search(input);
	    	  DebugManagement.writeNotificationToLog("Result size is: " + result.size());
	    	  String[] possibleDestinations = new String[result.size()];
	    	  for (int i = 0; i < result.size(); i++){
	    		  possibleDestinations[i] = result.get(i).getVal();
	    		  DebugManagement.writeNotificationToLog("The possible location is: " + possibleDestinations[i]);
	    		  if (input.equals(possibleDestinations[i])){
	    			  updateTargetRecord(result.get(i));
	    		  }
	    	  }
	    	  updateResultPoint(possibleDestinations);
	      }   
	   }
	
	class KeyListenerForStart implements KeyListener{
		public void updateResultPoint(String[] possibleDestinations){
			startPoint = new JComboBox<String>(possibleDestinations);
		}
		public void updateTargetRecord(Record r){
			searchStartRecord = r;
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			  JComboBox cd = (JComboBox) e.getSource();
	    	  String input = (String) cd.getSelectedItem();
	    	  System.out.println(input);
	    	  
	    	  DebugManagement.writeNotificationToLog(input);
	    	  List<Record> result = engine.search(input);
	    	  DebugManagement.writeNotificationToLog("Result size is: " + result.size());
	    	  String[] possibleDestinations = new String[result.size()];
	    	  for (int i = 0; i < result.size(); i++){
	    		  possibleDestinations[i] = result.get(i).getVal();
	    		  DebugManagement.writeNotificationToLog("The possible location is: " + possibleDestinations[i]);
	    		  if (input.equals(possibleDestinations[i])){
	    			  updateTargetRecord(result.get(i));
	    		  }
	    	  }
	    	  updateResultPoint(possibleDestinations);
			
		}
	}
	
	class KeyListenerForDestination extends CustomKeyListener{
		public void updateResultPoint(String[] possibleDestinations){
			destination = new JComboBox<String>(possibleDestinations);
		}
		public void updateTargetRecord(Record r){
			searchEndRecord = r;
		}
		public JComboBox getComboBox(){
	    	  return destination;
	      }
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
		}
		
	}
	
	public SearchReadyEventObject getSearchEvents(){
		return this.searchEvents;
	}
}