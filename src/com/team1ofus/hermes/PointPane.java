package com.team1ofus.hermes;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PointPane extends JPanel{
	
	private Point first;
	private Point end;
	private BufferedImage marker;
	private Point offset = new Point(0,0);

	
	PointPane() throws IOException{
		first = new Point(0,0);
		end = new Point(0,0);
		marker = ImageIO.read(new File("marker.png"));
		repaint();
	}
	
	void drawPoint(Graphics g){
		g.drawImage(marker,first.x,first.y,32,51, null);
	}
	
	
	public void setFirst(Point inPoint) {
		first = inPoint;
		System.out.println("point:" + inPoint );
	}
	public void setSecond(Point inPoint) {
		end = inPoint;
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		drawPoint(g);

	}
}
