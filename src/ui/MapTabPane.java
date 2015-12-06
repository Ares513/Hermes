package ui;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.team1ofus.apollo.TILE_TYPE;

import core.BootstrapperConstants;
import core.DebugManagement;
import events.HumanInteractionEventObject;
import pathing.CellPoint;
import pathing.PathCell;

public class MapTabPane extends JLayeredPane {
	
	private MapPane mapPanel;
	private PathPane pathPanel;
	private TextPane textPanel;
	private PointPane pointPanel;
	private String name;
	private PathCell currentCell;
	private double zoomScale = 1;
	private boolean dragging;
	private Point lastDragLocation;
	private Point first;
	private Point second;
	public HumanInteractionEventObject humanInteractive; 
	private double maxZoomOutx;
	private double maxZoomOuty ;
	private double maxZoomOutF;
	private double maxZoomOut = 0.01;
	private double maxZoomIn = 3D;
	private int scalingNum = 1;

	
	public MapTabPane(PathCell cell) {
		humanInteractive = new HumanInteractionEventObject();

		currentCell = cell;
		mapPanel = new MapPane(currentCell);
		pathPanel = new PathPane();
		textPanel = new TextPane(currentCell);
		pointPanel = new PointPane();
		name = "shit";
		maxZoomOutx = (BootstrapperConstants.FRAME_WIDTH/ ((currentCell.getWidth()) *(BootstrapperConstants.TILE_WIDTH)));
		
		maxZoomOuty = (BootstrapperConstants.FRAME_HEIGHT/ ((currentCell.getHeight()) *(BootstrapperConstants.TILE_HEIGHT)));
		//maxZoomOut = (maxZoomOutx < maxZoomOuty) ? maxZoomOutx : maxZoomOuty;
		//maxZoomOut = (maxZoomOutF) * 10;// * 0.5;//An estimate for the max zoomout
		
		System.out.println(maxZoomOut);
		
		initialize();
		buildControl();
	}
	
	private void initialize() {
		mapPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		mapPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		pathPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		textPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		pointPanel.setBounds(0, 0, BootstrapperConstants.FRAME_WIDTH-BootstrapperConstants.PANEL_SIZE, BootstrapperConstants.FRAME_HEIGHT);
		textPanel.labelAllTiles(currentCell);
		
		add(mapPanel);
		add(pathPanel);
		add(textPanel);
		add(pointPanel);
		setComponentZOrder(mapPanel, 0);
		setComponentZOrder(pathPanel, 0);
		setComponentZOrder(textPanel, 0);
		setComponentZOrder(pointPanel, 0);
	}
	
	private void buildControl() {
		//This handles map zooming by causing the Cell to re-render
		this.addMouseWheelListener(new MouseAdapter() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					//scalingNum = 2;//Increasing this number increases the amount of zoom one mousewheel "scroll" will zoom in for
				    double zoomChangeFactor = (scalingNum/(double)BootstrapperConstants.TILE_WIDTH);
					double delta = -zoomChangeFactor * e.getPreciseWheelRotation();
					if(zoomScale + delta <= maxZoomOut){
						zoomScale = maxZoomOut;
					}
					else if(zoomScale + delta >=  maxZoomIn ){
						zoomScale = maxZoomIn;
					}
					else{
						System.out.println(zoomScale);
						zoomScale += delta;
						mapPanel.render.zoom(zoomScale, mapPanel.getWidth(), mapPanel.getHeight());
						//mapPanel.render.zoom(zoomScale, BootstrapperConstants.FRAME_WIDTH, BootstrapperConstants.FRAME_WIDTH);
						pathPanel.zoom(zoomScale);
						//textPanel.zoom(zoomScale); TODO scale with text
						pointPanel.zoom(zoomScale);
						pathPanel.setOffset(mapPanel.render.offset);
						pathPanel.repaint();
						pointPanel.setOffset(mapPanel.render.offset);
						textPanel.zoomScale = zoomScale;
						textPanel.repaint();
						repaintPanel();
						repaint();
					}
				}
			});
				
				//This mouse event controls map panning through mouse dragging
				this.addMouseMotionListener(new MouseMotionAdapter() {
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
								mapPanel.render.incrementOffset(x, y, mapPanel.getWidth(), mapPanel.getHeight());
								pathPanel.setOffset(mapPanel.render.offset);
								pathPanel.repaint();
								textPanel.offset = mapPanel.render.offset;
								pointPanel.setOffset(mapPanel.render.offset);
								repaintPanel();
								lastDragLocation = e.getPoint();
							} else {
								lastDragLocation = new Point(e.getX(), e.getY());
							}
						}
					}
				}
				);
				//This mouse event handles point selection
				this.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseClicked(MouseEvent e) {
						Point picked = mapPanel.render.pickTile(e.getX() , e.getY());
						if(SwingUtilities.isLeftMouseButton(e)) {
							DebugManagement.writeNotificationToLog("Point picked at " + picked.toString() + " in cell " + currentCell.cellName);
							processClick(picked);
						}  
					}
					@Override
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
	}

	public MapPane getMapPane() {
		return mapPanel;
	}

	public PathPane getPathPane() {
		return pathPanel;
	}

	public TextPane getTextPane() {
		return textPanel;
	}

	public PointPane getPointPane() {
		return pointPanel;
	}

	public String getName() {
		return name;
	}

	public PathCell getCurrentCell() {
		return currentCell;
	}
	public void setOffset(Point input) {
		mapPanel.render.offset = input;
		pathPanel.setOffset(mapPanel.render.offset);
		pathPanel.repaint();
		textPanel.offset = mapPanel.render.offset;
		pointPanel.setOffset(mapPanel.render.offset);
		repaintPanel();
		
	}
	public void zoom(int zoomDirection){

	   // scalingNum = 1;//Increasing this number increases the amount of zoom one mousewheel "scroll" will zoom in for
		double zoomChangeFactor = (scalingNum/(double)BootstrapperConstants.TILE_WIDTH);
	    double delta = -zoomChangeFactor * zoomDirection;
		if(zoomScale + delta <= maxZoomOut){
			zoomScale = maxZoomOut;
		}
		else if(zoomScale + delta >=  maxZoomIn ){
			zoomScale = maxZoomIn;
		}
		else{
			zoomScale += delta;
			mapPanel.render.zoom(zoomScale, BootstrapperConstants.FRAME_WIDTH, BootstrapperConstants.FRAME_HEIGHT);
			pathPanel.zoom(zoomScale);
			//textPanel.zoom(zoomScale); TODO scale with text
			pointPanel.zoom(zoomScale);
			pathPanel.setOffset(mapPanel.render.offset);
			pathPanel.repaint();
			pointPanel.setOffset(mapPanel.render.offset);
			textPanel.offset = mapPanel.render.offset;
			
			repaintPanel();
		
			repaint();
		}
	
		
	}
	
	private void processClick(Point picked) {
		DebugManagement.writeNotificationToLog("Mouse clicked at " + picked.x + " , " + picked.y);
		if(mapPanel.render.getTile(picked.x, picked.y).tileType != TILE_TYPE.WALL || mapPanel.render.getTile(picked.x, picked.y).tileType != TILE_TYPE.IMPASSABLE) {
			//valid.
			if(first == null) {
			//	first.getPoint();
				first = new Point(picked.x, picked.y);
				mapPanel.render.setFirst(first);
				pointPanel.setFirst(first);
				pointPanel.setSecond(null);
				pathPanel.clearPath();
				repaintPanel();
			} else if(second == null) {

				second = new Point(picked.x,picked.y);
				mapPanel.render.setSecond(second);
				pointPanel.setSecond(second);
				first = null;
				second = null;

				repaintPanel();
			}
			humanInteractive.doClick(new CellPoint(currentCell.getName(), picked));
		}
	}
		
	private void repaintPanel() {
		this.repaint();
	}
}
