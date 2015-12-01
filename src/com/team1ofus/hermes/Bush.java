package com.team1ofus.hermes;

import java.awt.Point;

import com.team1ofus.apollo.TILE_TYPE;

//A type of tile that represents a non-traversable tile
public class Bush extends Tile implements TileInterface {
	
	private static final long serialVersionUID = 1L;

	public Bush(String cellName,Point tilePoint){
		this.cellName = cellName;
		this.tilePoint = tilePoint;
		this.tileType = TILE_TYPE.BUSH;
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 200;
	}
}