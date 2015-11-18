package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.UUID;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private static final long serialVersionUID = 4L;
	private DataTile[][] tiles;
	//minimum required information
	double scaling  = 1;
	private int fixedWidth;
	private int fixedHeight;
	public Cell(int width, int height, double scaling, TILE_TYPE defaultTile, String name) {
		id = name;
		//identifier
		tiles = new DataTile[width][height];
		fillTiles(TILE_TYPE.WALL, width, height);
		fixedWidth = width;
		fixedHeight = height;
	}
	private void fillTiles(TILE_TYPE fillTile, int width, int height) {
		for(int i=0; i< width; i++) {
			for(int j=0; j< height; j++) {
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
		return fixedWidth;
	}
	public int getHeight() {
		return fixedHeight;
	}
	public void setTile(int x, int y, TILE_TYPE tileToSet) {
		int xActual = x;
		int yActual = y;
		int actualWidth = getWidth() - 1;
		if(x > actualWidth) {
			xActual = actualWidth;
		}
		int actualHeight = getHeight() - 1;
		if(y > actualHeight) {
			yActual = actualHeight;
		}
		tiles[xActual][yActual] = new DataTile(tileToSet);
	}
	public DataTile getTile(int x, int y) {
			
			int xActual = x;
			int yActual = y;
			int actualWidth = getWidth() - 1;
			if(x > actualWidth) {
				xActual = actualWidth;
			}
			int actualHeight = getHeight() - 1;
			if(y > actualHeight) {
				yActual = actualHeight;
			}
			return tiles[xActual][yActual];
	}
	public double getScale() {
		// TODO Auto-generated method stub
		return scaling;
	}
	public DataTile[][] getTiles() {
		return tiles;
	}
}
