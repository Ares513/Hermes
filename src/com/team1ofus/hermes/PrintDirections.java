package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

public class PrintDirections { 
	public PrintDirections() { 
	}
	
	
	public void parseDirections(ArrayList<CellPoint> AStarDirections){
		int numberOfSteps = AStarDirections.size(); 
		for(int i = 0; i < numberOfSteps; i++){ 
			CellPoint iterationI = AStarDirections.get(i);
			Point thisPoint = iterationI.getPoint();
			double xCoord = thisPoint.getX();
			double yCoord = thisPoint.getY();
			
			System.out.print(xCoord);
			System.out.print(",");
			System.out.println(yCoord);
		}
	}
	private double getDifference(CellPoint currentP, CellPoint previousP){ 
		Point currentPoint = currentP.getPoint();
		double xC = currentPoint.getX();
		double yC = currentPoint.getY();
		Point previousPoint = previousP.getPoint();
		double xP = previousPoint.getX(); 
		double yP = previousPoint.getY(); 
		
		double diffX = xC - xP; 
		double diffY = yC - yP; 
	  
		return 0; 
	}
	
}

/*
 * (0,0)(1,0)(2,0)(3,0)(4,0) 
 * (0,1)(1,1)(2,1)(3,1)(4,1)
 * (0,2)(1,2)(2,2)(3,2)(4,2)
 * (0,3)(1,3)(2,3)(3,3)(4,3)
 * (0,4)(1,4)(2,4)(3,4)(4,4)
 * 
 */ 
