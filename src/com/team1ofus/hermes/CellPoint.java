package com.team1ofus.hermes;

import java.awt.Point;

/* contains a set of unique points (in the form (Cell, (x, y))) which specify a complete path. The exact form this data will take has not yet been determined. 
 */
public class CellPoint {
	private String cellName;
	private Point point;
	
	public CellPoint(String aCellName, Point aPoint){
		cellName = aCellName;
		point = aPoint;
	}
	
	public String getCellName(){
		return cellName;
	}
	
	public Point getPoint(){
		return point;
	}
}