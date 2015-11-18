package com.team1ofus.hermes;

import java.awt.Point;

public class Wall extends Tile implements TileInterface {
	
	private static final long serialVersionUID = 1L;

	public Wall(String cellName,Point tilePoint){
		Tile.cellName = cellName;
		Tile.tilePoint = tilePoint;
		Tile.tileType = 1; // 1 means wall
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 1000000;
	}
}