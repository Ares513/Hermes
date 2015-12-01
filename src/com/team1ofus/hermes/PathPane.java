package com.team1ofus.hermes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.awt.GradientPaint;


import javax.swing.JPanel;

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
	
	
	public PathPane() {
		setOpaque(true);

	}
	//This updates the zoomScale, which will be used when placing the drawing the path.
	public void zoom(double scale, PathCell passedCell){
		//cell = new CellRenderer(passedCell);
		cell = passedCell;
		zoomScale = scale;
		
		//Not doing anything right now
		oldTotalWidth= (int)((BootstrapperConstants.TILE_WIDTH * cell.tiles.length ) * prevScale);
		//DebugManagement.writeNotificationToLog("The previous total width was:");
		//System.out.println(oldTotalWidth);
		oldTotalHeight= (int)((BootstrapperConstants.TILE_HEIGHT * cell.tiles[1].length ) * prevScale);
		//DebugManagement.writeNotificationToLog("The previous total height was:");
		//System.out.println(oldTotalHeight);
		newTotalWidth= (int)((BootstrapperConstants.TILE_WIDTH * cell.tiles.length ) * scale);
		//DebugManagement.writeNotificationToLog("The current total width is:");
		//System.out.println(newTotalWidth);
		newTotalHeight= (int)((BootstrapperConstants.TILE_HEIGHT * cell.tiles[1].length ) * scale);
		//DebugManagement.writeNotificationToLog("The current total height is:");
		//System.out.println(newTotalHeight);
		difWidth = (newTotalWidth - oldTotalWidth)/2;
		//DebugManagement.writeNotificationToLog("The current x offset is:");
		//System.out.println(difWidth);
		difHeight = (newTotalHeight - oldTotalHeight)/2;
		//DebugManagement.writeNotificationToLog("The current y offset is:");
		//System.out.println(difHeight);
		//incrementOffset(difWidth,difHeight, fwidth, fheight);
		prevScale = scale;
		
		
		repaint();
	}

	//Creates the list of points that will be used to draw the path
	void drawPath(ArrayList<CellPoint> path){
		pointsList.clear();
		if(path == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "CellPoint path is empty! Not drawing path.");
			return;
		}
		for(int c = 0; c < path.size(); c++){
			pointsList.add(new Point((int) path.get(c).getPoint().getX(), (int) path.get(c).getPoint().getY()));
			pointsList.get(c).move(pointsList.get(c).x*tileWidth+tileWidth/2, pointsList.get(c).y*tileHeight+tileHeight/2);
		}
		validate();
		repaint();
	}

	//This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
	void drawLineSets(Graphics g){
		//DebugManagement.writeNotificationToLog("Points for drawing:" + " " + pointsList);
		Graphics2D g2d = (Graphics2D) g;
		Color lineColor = new Color(120,221,255);
		g2d.setColor(lineColor);
		//GradientPaint redtowhite = new GradientPaint(0,0,Color.RED,100, 0,Color.WHITE);
		//g2d.setPaint(redtowhite);
		
		float[] dashingPattern1 = {8f, 8f};
		
		Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 4f);//, dashingPattern1, 2.0f);
		g2d.setStroke(stroke1);
		for(int i = 0; i < pointsList.size()-1; i++){
			Point p1 = convertPoint(pointsList.get(i).x, pointsList.get(i).y);
			Point p2 = convertPoint(pointsList.get(i+1).x, pointsList.get(i+1).y);
			g2d.drawLine( (int)p1.x - offset.x,  (int)p1.y - offset.y , (int)(p2.x - offset.x) , (int)(p2.y - offset.y));
			
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