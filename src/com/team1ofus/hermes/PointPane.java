package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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


	
	 PointPane() throws IOException{
		first = new Point(0,0);
		second = new Point(0,0);
		offset = new Point(0,0);
		marker = ImageIO.read(new File("marker.png"));
	}
	
	void drawPoint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		if(first != null){
			g2d.drawImage(marker,first.x - offset.x,first.y - offset.y,this);
		}
		else{
			//g2d.drawImage(marker,second.x - offset.x,second.y - offset.y,this);
		}
	}
	
	public void setFirst(Point inPoint) {
		first = inPoint;
		System.out.println("First point:" + inPoint );
	}
	public void setSecond(Point inPoint) {
		second = inPoint;
		System.out.println("Second point:" + inPoint );
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("paintComponent got called in PointPane");
		drawPoint(g);

	}
	public void setOffset(Point offset) {
		this.offset = offset;
		
	}
}
