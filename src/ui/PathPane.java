package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.GradientPaint;


import javax.swing.JPanel;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import pathing.CellPoint;
import pathing.PathCell;

//This panel paints the path over the map
class PathPane extends JPanel {
	private int tileWidth = BootstrapperConstants.TILE_WIDTH;
	private int tileHeight = BootstrapperConstants.TILE_HEIGHT;
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private Point offset = new Point(0,0);
	public double zoomScale = 1;
	public double prevScale = 1D;
	PathCell cell;
	int oldTotalWidth;
	int oldTotalHeight;
	int newTotalWidth;
	int newTotalHeight;
	int difWidth;
	int difHeight;
	String cellName = "";
	
	
	public PathPane() {
		setOpaque(true);

	}
	//This updates the zoomScale, which will be used when placing the drawing the path.
	public void zoom(double scale){
		zoomScale = scale;		
		repaint();
	}

	//Creates the list of points that will be used to draw the path
	void drawPath(ArrayList<CellPoint> path, String cellName){
		//pointsList.clear();
		if(path == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "CellPoint path is empty! Not drawing path.");
			return;
		}
		int tileWidth =  (int) ((int)BootstrapperConstants.TILE_WIDTH*zoomScale);
		int tileHeight = (int) ((int)BootstrapperConstants.TILE_HEIGHT*zoomScale);
		this.cellName = path.get(0).getCellName();
		for(int c = 0; c < path.size(); c++) {
			if(path.get(c).getCellName() == cellName) {
				//matches, add it.
				pointsList.add(new Point((int) path.get(c).getPoint().getX(), (int) path.get(c).getPoint().getY()));
				//pointsList.get(c).move(pointsList.get(c).x*tileWidth+tileWidth/2, pointsList.get(c).y*tileHeight+tileHeight/2);
			
			}
		}
		validate();
		repaint();
	}

	//This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
	void drawLineSets(Graphics g){
		//DebugManagement.writeNotificationToLog("Points for drawing:" + " " + pointsList);
		Graphics2D g2d = (Graphics2D) g;
		//Color lineColor = new Color(63, 128, 0);
		Color lineColor = new Color(255, 0, 0);
		g2d.setColor(lineColor);
		//GradientPaint redtowhite = new GradientPaint(0,0,Color.RED,100, 0,Color.WHITE);
		//g2d.setPaint(redtowhite);
		
		float[] dashingPattern1 = {8f, 8f};
		
		Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4f);//, dashingPattern1, 2.0f);
		g2d.setStroke(stroke1);
		AffineTransform transformer = new AffineTransform();
		transformer.translate(this.getWidth()/2, this.getHeight()/2);
		transformer.scale(zoomScale, zoomScale);
		transformer.translate(-this.getWidth()/2, -this.getHeight()/2);
		transformer.translate(offset.x, offset.y);
		g2d.setTransform(transformer);
		int tileWidth =  (int) ((int)BootstrapperConstants.TILE_WIDTH);
		int tileHeight = (int) ((int)BootstrapperConstants.TILE_HEIGHT);
		for(int i = 0; i < pointsList.size()-1; i++){
			Point p1 = pointsList.get(i);
			Point p2 = pointsList.get(i+1);
			if(cellName.equals("World"))
				g2d.setStroke(new BasicStroke(40));
			if(Math.sqrt(Math.pow(p1.getX()-p2.getX(), 2)+Math.pow(p1.getY()-p2.getY(), 2)) > 40)
				i++;
			else
				g2d.drawLine( (int)p1.x*tileWidth+tileWidth/2,  (int)p1.y*tileHeight+tileHeight/2, (int)(p2.x)*tileWidth+tileWidth/2, (int)(p2.y*tileHeight+tileHeight/2));
			
		} 
	}
	private Point convertPoint(double x, double y) {
		
		int xFinal = (int) ((int) x*zoomScale);
		int yFinal = (int) ((int) y*zoomScale);
		return new Point(xFinal, yFinal);
	}
	@Override
	public void paintComponent(Graphics g) {
		drawLineSets(g);

	}
	
	public void setOffset(Point offset) {
		this.offset = offset;
		
	}
	
	public void clearPath() {
		pointsList.clear();
	}
}