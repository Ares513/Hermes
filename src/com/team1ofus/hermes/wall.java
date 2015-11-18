package com.team1ofus.hermes;

import java.awt.Point;

import com.team1ofus.hermes.Tile;

public class wall extends Tile implements TileInterface {
	
	public wall(byte cellNum, Point tilePoint){
		Tile.cellNum = cellNum;
		Tile.tilePoint = tilePoint;
		Tile.tileType = 1; // 1 means wall
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 1000000;
	}
}
