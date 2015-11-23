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
	final int tileWidth = BootstrapperConstants.TILE_WIDTH;
	final int tileHeight = BootstrapperConstants.TILE_HEIGHT;
	ArrayList<Point> pointsList = new ArrayList<Point>();
	private Point offset = new Point(0,0);
	public PathPane() {
		setOpaque(true);

	}
	void drawPath(CellPoint[] path){
		pointsList.clear();
		for(CellPoint c : path){
			c.getPoint().move(c.getPoint().x*tileWidth+tileWidth/2, c.getPoint().y*tileHeight+tileHeight/2);
			pointsList.add(c.getPoint());
		}
		validate();
		repaint();
	}

	//This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
	void drawLineSets(Graphics g){
		DebugManagement.writeNotificationToLog("Points for drawing:" + " " + pointsList);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		float[] dashingPattern1 = {8f, 8f};
		Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
		g2d.setStroke(stroke1);
		for(int i = 0; i < pointsList.size()-1; i++){
			//g.fillRect(100, 100, 200, 200);
			g.drawLine(pointsList.get(i).x-offset.x, pointsList.get(i).y-offset.y, pointsList.get(i+1).x-offset.x,pointsList.get(i+1).y-offset.y);
			g2d.drawLine(pointsList.get(i).x-offset.x, pointsList.get(i).y-offset.y, pointsList.get(i+1).x-offset.x,pointsList.get(i+1).y-offset.y);
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