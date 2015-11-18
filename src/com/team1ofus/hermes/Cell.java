package com.team1ofus.hermes;

import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private static final long serialVersionUID = 3L;
	public DataTile[][] tiles;
	//minimum required information
	double scaling  = 1;
	public Cell(int width, int height, double scaling, TILE_TYPE defaultTile, String name) {
		id = name;
		//identifier
		tiles = new DataTile[width][height];
		fillTiles(TILE_TYPE.WALL);
		
	}
	private void fillTiles(TILE_TYPE fillTile) {
		for(int i=0; i<tiles[0].length; i++) {
			for(int j=0; j<tiles[1].length; j++) {
				tiles[i][j] = new DataTile(fillTile);
			}
		}
	}
	public String getID() {
		return id;
	}
	public void setID(String inID) {
		id = inID;
	}
	public int getWidth() {
		return tiles[0].length;
	}
	public int getHeight() {
		return tiles[1].length;
	}
	public double getScale() { 
		return scaling; 
	}
}
