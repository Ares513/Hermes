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
import com.team1ofus.apollo.TILE_TYPE;
public class PathCell{
	
	public String cellName = null;
	String displayName;
    private static final long serialVersionUID = 1L;
    public Tile[][] tiles;
    
    //end minimum required information
    
    double scaling;
    
    private ArrayList<EntryPoint> entryPoints = new ArrayList<EntryPoint>(); //places which you can enter into this cell at. 
    private ArrayList<LocationNameInfo> namedPoints = new ArrayList<LocationNameInfo>();
    private ArrayList<EntryPointReference> entryPointRefs = new ArrayList<EntryPointReference>(); //places from which you can leave this cell.
    
    public PathCell(String name, int width, int height, double scaling, TILE_TYPE defaultTile) {
    	DebugManagement.writeLineToLog(SEVERITY_LEVEL.WARNING, "A map was generated with a default tile format! Maps should be loaded from memory.");
        this.cellName = name;
        tiles = new Tile[width][height];
    	for(int i=0; i<width; i++) {
    		if(defaultTile.equals(TILE_TYPE.PEDESTRIAN_WALKWAY)){
	    		for(int j=0; j<height; j++) {
	    			tiles[i][j] = new Walkway(name, new Point(i, j));
	    		}
    		}
    		else{
    			for(int j=0; j<height; j++) {
	    			tiles[i][j] = new Wall(name, new Point(i, j));
	    		}
    		}
    	}
    	this.scaling = scaling;
           
    }
    
    //an unpleasantly long one for 
	public PathCell(String name, String display, int width, int height, com.team1ofus.apollo.DataTile[][] dataTiles, ArrayList<EntryPoint> entryPoints,
			ArrayList<LocationNameInfo> namedPoints, ArrayList<EntryPointReference> entryPointRefs) {
        this.cellName = name;
        this.displayName = display;
        tiles = new Tile[width][height];
    	for(int i=0; i<width; i++) {
    		for(int j=0; j<height; j++) {
    			TILE_TYPE type = dataTiles[i][j].getType();
    			
    			switch(type) {
    			case WALL:
       				tiles[i][j] = new Wall(name, new Point(i, j));
    				tiles[i][j].tileType = type;
    			case PEDESTRIAN_WALKWAY:
    				tiles[i][j] = new Walkway(name, new Point(i, j));
    				tiles[i][j].tileType = type;
    				break;
    			case IMPASSABLE:
       				tiles[i][j] = new Wall(name, new Point(i, j));
    				tiles[i][j].tileType = type;
    				break;
    			default:
    				tiles[i][j] = new Walkway(name, new Point(i, j));
    				tiles[i][j].tileType = type;
    				break;
    			}
    			if(type == com.team1ofus.apollo.TILE_TYPE.WALL) {
 
    			}
    			if(type == com.team1ofus.apollo.TILE_TYPE.PEDESTRIAN_WALKWAY) {
    			}
    			
    		}
    	}
    	this.scaling = scaling;
		this.entryPoints = entryPoints;
		this.namedPoints = namedPoints;
		this.entryPointRefs = entryPointRefs;
    }
    /*
     * Not safe for out of bounds calls.
     */
    public Tile getTile(Point tilePoint) {
        int x = (int)tilePoint.getX();
        int y = (int)tilePoint.getY();
        return this.tiles[x][y];
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
	public String getDisplayName() {
		return displayName;
	}
	public ArrayList<EntryPoint> getEntryPoints(){
		return entryPoints;
	}
	
	public void addEntryPoint(EntryPoint anEntryPoint){
		entryPoints.add(anEntryPoint);
	}
	public void addEntryPoints(ArrayList<EntryPoint> entryPoints){
		for (EntryPoint ep : entryPoints) {
			entryPoints.add(ep);
		}
	}
	
	public ArrayList<LocationNameInfo> getLocationNameInfo(){
		return namedPoints;
	}
	public void addLocationNameInfo(LocationNameInfo aLocationNameInfo){
		namedPoints.add(aLocationNameInfo);
	}
	public void addLocationNameInfos(ArrayList<LocationNameInfo> locationNameInfos){
		for (LocationNameInfo lni : locationNameInfos){
			namedPoints.add(lni);
		}
	}
	
	public ArrayList<EntryPointReference> getEntryPointReferences(){
		return entryPointRefs;
	}
	public void addEntryPointReference(EntryPointReference anEntryPointRef){
		entryPointRefs.add(anEntryPointRef);
	}
	public void addEntryPointReferences(ArrayList<EntryPointReference> entryPointRefs){
		for (EntryPointReference epf : entryPointRefs){
			entryPointRefs.add(epf);
		}
	}
}