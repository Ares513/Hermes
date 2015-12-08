package pathing;
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
import java.util.HashMap;

import com.team1ofus.apollo.TILE_TYPE;

import core.DebugManagement;
import core.SEVERITY_LEVEL;
import tiles.Bench;
import tiles.Bush;
import tiles.Classroom;
import tiles.Congested;
import tiles.Door;
import tiles.Elevator;
import tiles.FemaleBathroom;
import tiles.Grass;
import tiles.HorizontalLeftStairs;
import tiles.HorizontalRightStairs;
import tiles.Impassable;
import tiles.Linoleum;
import tiles.MaleBathroom;
import tiles.Road;
import tiles.Tile;
import tiles.Tree;
import tiles.UnisexBathroom;
import tiles.Unplowed;
import tiles.VerticalDownStairs;
import tiles.VerticalUpStairs;
import tiles.Walkway;
import tiles.Wall;
public class PathCell{
	
	public String cellName = null;
	String displayName;
    private static final long serialVersionUID = 1L;
    public HashMap<Point, Tile> tiles = new HashMap<Point, Tile>();
    
    //end minimum required information
    
    double scaling;
    
    private ArrayList<EntryPoint> entryPoints = new ArrayList<EntryPoint>(); //places which you can enter into this cell at. 
    private ArrayList<LocationNameInfo> namedPoints = new ArrayList<LocationNameInfo>();
    private ArrayList<EntryPointReference> entryPointRefs = new ArrayList<EntryPointReference>(); //places from which you can leave this cell.
	private int width;
	private int height;
    
    public PathCell(String name, int width, int height, double scaling) {
    	DebugManagement.writeLineToLog(SEVERITY_LEVEL.WARNING, "A map was generated with a default tile format! Maps should be loaded from memory.");
        this.cellName = name;
    	this.scaling = scaling;
           
    }
    
    //an unpleasantly long one for 
	public PathCell(String name, String display, int width, int height, HashMap<Point, TILE_TYPE> dataTiles, ArrayList<EntryPoint> entryPoints,
			ArrayList<LocationNameInfo> namedPoints, ArrayList<EntryPointReference> entryPointRefs) {
		tiles = new HashMap<Point, Tile>();
        this.cellName = name;
        this.displayName = display;
        this.width = width;
        this.height = height;
        if(display == null) {
        	this.displayName = name; //prevent null case
        }
		this.entryPoints = entryPoints;
		this.namedPoints = namedPoints;
		this.entryPointRefs = entryPointRefs;
		for(Point p : dataTiles.keySet()) {
			TILE_TYPE type = dataTiles.get(p);
			if(type.equals(TILE_TYPE.PEDESTRIAN_WALKWAY)){
    			tiles.put(p,new Walkway(name, p));
    		}
    		else if(type.equals(TILE_TYPE.DOOR)){
    			tiles.put(p,new Door(name, p));
    		}
    		else if(type.equals(TILE_TYPE.GRASS)){
    			tiles.put(p,new Grass(name, p));
    		}
    		else if(type.equals(TILE_TYPE.CONGESTED)){
    			tiles.put(p,new Congested(name, p));
    		}
    		else if(type.equals(TILE_TYPE.VERTICAL_UP_STAIRS)){
    			tiles.put(p,new VerticalUpStairs(name, p));
    		}
    		else if(type.equals(TILE_TYPE.VERTICAL_DOWN_STAIRS)){
    			tiles.put(p,new VerticalDownStairs(name, p));
    		}
    		else if(type.equals(TILE_TYPE.HORIZONTAL_LEFT_STAIRS)){
    			tiles.put(p,new HorizontalLeftStairs(name, p));
    		}
    		else if(type.equals(TILE_TYPE.HORIZONTAL_RIGHT_STAIRS)){
    			tiles.put(p,new HorizontalRightStairs(name, p));
    		}
    		else if(type.equals(TILE_TYPE.IMPASSABLE)){
    			tiles.put(p,new Impassable(name, p));
    		}
    		else if(type.equals(TILE_TYPE.MALE_BATHROOM)){
    			tiles.put(p,new MaleBathroom(name, p));
    			ArrayList<String> bathroomNames = new ArrayList<String>();
    			bathroomNames.add("AutoGen Male Bathroom " + this.getDisplayName());
    			bathroomNames.add("AutoGen Men's Room " + this.getDisplayName());
    			namedPoints.add(new LocationNameInfo(p, bathroomNames));
    		}
    		else if(type.equals(TILE_TYPE.FEMALE_BATHROOM)){
    			tiles.put(p,new FemaleBathroom(name, p));
    			ArrayList<String> bathroomNames = new ArrayList<String>();
    			bathroomNames.add("AutoGen Female Bathroom " + this.getDisplayName());
    			bathroomNames.add("AutoGen Women's Room " + this.getDisplayName());
    			namedPoints.add(new LocationNameInfo(p, bathroomNames));
    		}
    		else if(type.equals(TILE_TYPE.UNISEX_BATHROOM)){
    			tiles.put(p,new UnisexBathroom(name, p));
    			ArrayList<String> bathroomNames = new ArrayList<String>();
    			bathroomNames.add("AutoGen Unisex Bathroom " + this.getDisplayName());
    			bathroomNames.add("AutoGen Unisex Room " + this.getDisplayName());
    			namedPoints.add(new LocationNameInfo(p, bathroomNames));
    		}
    		else if(type.equals(TILE_TYPE.BENCH)){
    			tiles.put(p,new Bench(name, p));
    			ArrayList<String> benchNames = new ArrayList<String>();
    			benchNames.add("AutoGen Bench " + this.getDisplayName());
    			benchNames.add("AutoGen Place To Sit " + this.getDisplayName());
    			namedPoints.add(new LocationNameInfo(p, benchNames));
    		}
    		else if(type.equals(TILE_TYPE.TREE)){
    			tiles.put(p,new Tree(name, p));
    		}
    		else if(type.equals(TILE_TYPE.BUSH)){
    			tiles.put(p,new Bush(name, p));
    		}
    		else if(type.equals(TILE_TYPE.LINOLEUM)){
    			tiles.put(p,new Linoleum(name, p));
    		}
    		else if(type.equals(TILE_TYPE.ELEVATOR)){
    			tiles.put(p,new Elevator(name, p));
    		}
    		else if(type.equals(TILE_TYPE.UNPLOWED)){
    			tiles.put(p,new Unplowed(name, p));
    		}
    		else if(type.equals(TILE_TYPE.CLASSROOM)){
    			tiles.put(p,new Classroom(name, p));
    		}
    		else if(type.equals(TILE_TYPE.EXTRA_TILE_TYPE_1)){
    			tiles.put(p,new Road(name, p));
    		}
    		else{
    			tiles.put(p,new Wall(name, p));
    		}

		}

    }
    /*
     * Not safe for out of bounds calls.
     */
    public Tile getTile(Point tilePoint) {
    	Tile output = tiles.get(tilePoint);
        return output;
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//this code was commented out by Forrest Cinelli on Dec 7 2015 at 11pm. If it's been a long time and this code has not been missed, feel free to delete it. 
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
}