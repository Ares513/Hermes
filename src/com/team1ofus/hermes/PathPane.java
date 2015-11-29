package com.team1ofus.hermes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.swing.JPanel;

class PathPane extends JPanel {
	private int tileWidth = BootstrapperConstants.TILE_WIDTH;
	private int tileHeight = BootstrapperConstants.TILE_HEIGHT;
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private Point offset = new Point(0,0);
	public double zoom = 1;
	public PathPane() {
		setOpaque(true);

	}
	public void zoom(double scale){
		zoom = scale;
		tileWidth =  BootstrapperConstants.TILE_WIDTH;
		tileHeight = BootstrapperConstants.TILE_HEIGHT;
		repaint();
	}

	
	
	void drawPath(ArrayList<CellPoint> path){
		pointsList.clear();
		if(path == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "CellPoint path is empty! Not drawing path.");
			return;
		}
		for(int c = 0; c < path.size(); c++){
			//System.out.println(c);
			pointsList.add(new Point((int) path.get(c).getPoint().getX(), (int) path.get(c).getPoint().getY()));
			pointsList.get(c).move(pointsList.get(c).x*tileWidth+tileWidth/2, pointsList.get(c).y*tileHeight+tileHeight/2);
		}
		validate();
		repaint();
	}

	//This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
	//Refactor this so paintComponent
	void drawLineSets(Graphics g){
		//DebugManagement.writeNotificationToLog("Points for drawing:" + " " + pointsList);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		float[] dashingPattern1 = {8f, 8f};
		Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
		g2d.setStroke(stroke1);
		for(int i = 0; i < pointsList.size()-1; i++){
			//g.fillRect(100, 100, 200, 200);
			//g.drawLine(pointsList.get(i).x-offset.x, pointsList.get(i).y-offset.y, pointsList.get(i+1).x-offset.x,pointsList.get(i+1).y-offset.y);
			//g2d.drawLine(pointsList.get(i).x-offset.x, pointsList.get(i).y-offset.y, pointsList.get(i+1).x-offset.x,pointsList.get(i+1).y-offset.y);
			g2d.drawLine( (int)(pointsList.get(i).x * zoom) - offset.x ,  (int)(pointsList.get(i).y * zoom)- offset.y , (int)(pointsList.get(i+1).x * zoom) - offset.x, (int)(pointsList.get(i+1).y * zoom) -offset.y);
		} 
	}
	
	@Override
	public void paintComponent(Graphics g) {
		drawLineSets(g);

	}
	
	public void setOffset(Point offset) {
		this.offset = offset;
		
	}
}