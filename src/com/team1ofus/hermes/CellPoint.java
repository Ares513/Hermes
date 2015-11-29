package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

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
	
	public boolean equals(CellPoint that){
		return (this.getCellName().equals(that.getCellName()) && this.getPoint().equals(that.getPoint()));
	}

	public boolean isIn(ArrayList<CellPoint> frontier) {
		for(CellPoint each: frontier){
			if(this.equals(each)){
				return true;
			}
		}
		return false;
	}
	
	
}