package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PointPane extends JPanel{

	private Point first;
	private Point second;
	private BufferedImage marker;
	private Point offset;
	public int width = BootstrapperConstants.TILE_WIDTH;
	public int height = BootstrapperConstants.TILE_HEIGHT;
	public double zoomScale = 1;

	PointPane() throws IOException{
		offset = new Point(0,0);
		marker = ImageIO.read(new File("marker.png"));
	}
	
	//This updates the zoomScale, which will be used when placing the drawing the point.
	public void zoom(double scale){
		zoomScale = scale;
		repaint();
	}
	
	//Draws the point according to where the user has clicked. The marker scales based on the zoom factor, we well as the panning offset.
	void drawPoint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		this.removeAll();
		int newWidth = (int) (marker.getWidth() * zoomScale);
		int newHeight = (int)(marker.getHeight() *zoomScale);
			if(first != null){
			g2d.drawImage(marker, (int)(first.x * zoomScale) - offset.x, (int)(first.y * zoomScale) - offset.y - height/2, newWidth, newHeight, this);
		}
		if(second != null){
			g2d.drawImage(marker, (int)(second.x * zoomScale)- offset.x,(int)(second.y * zoomScale) - offset.y - height/2, newWidth, newHeight, this);
		}
	}

	//Sets first's x and y to the x,y coordinates that have been clicked on
	public void setFirst(Point inPoint) {
		if(inPoint == null)
			first = null;
		else
			first = new Point((int) inPoint.getX()* width,(int) inPoint.getY()*height);
		second = null;
		System.out.println("First point:" + inPoint );
		repaint();
	}
	//Sets second's x and y to the x,y coordinates that have been clicked on
	public void setSecond(Point inPoint) {
		if(inPoint == null)
			second = null;
		else
			second = new Point((int) inPoint.getX()*width,(int) inPoint.getY()*height);
		System.out.println("Second point:" + inPoint );
		repaint();
	}

	
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("paintComponent got called in PointPane");
		Graphics2D g2d = (Graphics2D) g.create();
		drawPoint(g2d);

	}
	public void setOffset(Point offset) {
		this.offset = offset;

	}
}
