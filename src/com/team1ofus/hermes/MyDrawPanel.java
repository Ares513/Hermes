package com.team1ofus.hermes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MyDrawPanel extends JPanel{
ArrayList<Point> pointsList = new ArrayList<Point>();
	
    public MyDrawPanel() {
    	//Will have to change pointsList to be whatever was passed in through the constructor
        setOpaque(false);
        
    }
    
    //This function draws lines between the points specified in the ArrayList points list, which has been generated from A* algorithm
    void drawLineSets(Graphics g){
    	Graphics2D g2d = (Graphics2D) g;
    	pointsList.add(new Point(200,200));
     	pointsList.add(new Point(500,100));
     	pointsList.add(new Point(800,500));
     	pointsList.add(new Point(900,800));
     	g2d.setColor(Color.BLUE);
     	float[] dashingPattern1 = {8f, 8f};
     	Stroke stroke1 = new BasicStroke(6f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
     	g2d.setStroke(stroke1);
     	
    	for(int i = 0; i < pointsList.size()-1; i++){
            g2d.drawLine(pointsList.get(i).x, pointsList.get(i).y, pointsList.get(i+1).x,pointsList.get(i+1).y);
    	}

    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLineSets(g);
    }
}