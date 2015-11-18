package com.team1ofus.hermes;
/*
 * Description:    	A map
 
Attributes
cellName  String  
Name of this map
cellShortName String The abbreviated name of this map
scale double Length in meters of the side of each tile
baseCostMultiplier double Multiplier to allow the cost of tiles to vary based on size
tiles Tile[][] 2D Array of tiles that make up the representation of this map
destinations LocationInfo[] Array of available destinations on this map
exits LocationInfo[] Array of exits this map has to another map
 
Methods
getPossibleTraversals(int, int) Point[] Gets all neighbor tiles that can be traveled to
 
Relationships
Interface: Tile 1-to-many A cell is made up of a grid of tiles
LocationInfo 1-to-many  A cell has many destinations and exits
 */

import java.awt.Point;
import java.util.ArrayList;

public class PathCell{
	
	public String cellName = null;
    private static final long serialVersionUID = 1L;
    public Tile[][] tiles;
    //minimum required information
    double scaling;
    
    public PathCell(String name, int width, int height, double scaling, TILE_TYPE defaultTile) {
        this.cellName = name;
        tiles = new Tile[width][height];
    	for(int i=0; i<width; i++) {
    		for(int j=0; j<height; j++) {
    			tiles[i][j] = new Wall(name, new Point(i, j));
    		}
    	}
    	this.scaling = scaling;
           
    }
    public PathCell(String name, int width, int height, double scaling, com.team1ofus.apollo.DataTile[][] dataTiles) {
        this.cellName = name;
        tiles = new Tile[width][height];
    	for(int i=0; i<width; i++) {
    		for(int j=0; j<height; j++) {
    			switch(dataTiles[i][j].getType()) {
    			case WALL:
    				tiles[i][j] = new Wall(name, new Point(i, j));
    				tiles[i][j].tileType = TILE_TYPE.WALL;
    			case PEDESTRIAN_WALKWAY:
    				tiles[i][j] = new Walkway(name, new Point(i, j));
    				tiles[i][j].tileType = TILE_TYPE.PEDESTRIAN_WALKWAY;
    			}
    			
    		}
    	}
    	this.scaling = scaling;
           
    }
    /*
     * Not safe for out of bounds calls.
     */
    public Tile getTile(Point tilePoint) {
        
        return tiles[(int)tilePoint.getX()][(int)tilePoint.getY()];
    }
    
	public ArrayList<Tile> getPossibleTraversals(Point tilePoint){
    	ArrayList<Tile> openNeighbors = new ArrayList<Tile>();
    	Point curPoint = tilePoint;
    	Tile curTile = null;
    	int tileX = (int)tilePoint.getX();
    	int tileY = (int)tilePoint.getY();
    	
    	for(int i= tileX-1; i<=tileX+1; i++){
    		for(int j= tileY-1; j <=tileY+1; j++){
    			curPoint.setLocation(i,j);
    			curTile = getTile(curPoint);
    			
    			if(curPoint == tilePoint){
    				continue;
    			}
    			else if(curTile.getTileType() == TILE_TYPE.WALL){ // 0 means wall
    				continue;
    			}
    			else{
    				openNeighbors.add(curTile);
    			}
    		}
    	}
    	
    	
		return openNeighbors;
    }
	
	public String getName(){
		return cellName;
	}
}
