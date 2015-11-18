package com.team1ofus.hermes;

import java.util.ArrayList;
import java.awt.Point;

public abstract class Tile implements TileInterface{
	
	public static byte cellNum = 0;
	
	public Point tilePoint = null;

	public byte tileType = 0; // 0 = walkway, 1 = wall

	public String cellName;
	
	public Tile parent = null;
	
	public int costSoFar = 10000000;
	
	public int estimatedTotalCost = 0;
//-------------------------------------------------
// Getters:
	
	public ArrayList<Tile> getNeighbors(){
		// Nodes that neighbor the current node
		ArrayList<Tile> neighbors = new ArrayList<Tile>();
		
		// need to populate neighbors
		
		return neighbors;	
	}

	public int getCSF(){
		return this.costSoFar;
	}
	
	public int getETC(){
		return this.estimatedTotalCost;
	}
	
	public Tile getParent(){
		return this.parent;
	}
	
	public int getTileType() {
		return this.tileType;
	}
	
	public Point getPoint() {
		return this.tilePoint;
	}
	
	public String getCellName(){
		return this.cellName;
	}
	public CellPoint getCellPoint() {
		CellPoint curCellPoint = new CellPoint(this.getCellName(),this.getPoint());
		return curCellPoint;
	}

// Setters:
	
	public void setParent(Tile parentTile){
		parent = parentTile;
	}

	public void setCSF(int tentativeCSF){
		costSoFar = tentativeCSF;
	}
	

	public void setETC(int ETC){
		estimatedTotalCost = ETC;
	}



}
