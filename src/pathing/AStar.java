package pathing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.team1ofus.apollo.TILE_TYPE;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import events.AStarInteractionEventObject;
import tiles.Tile;

public class AStar { 
	AStarInteractionEventObject events;
	HashMap<String, HashMap<Point, TileInfo>> cellMap;
	ArrayList<PathCell> accessedCells; 
	// Nodes that need to be explored
	ArrayList<CellPoint> frontier;
	// Nodes that have already been explored
	//ArrayList<CellPoint> explored;
	HashSet<CellPoint> explored;
	HashMap<String, Point> buildingLocations;
	private boolean alreadyRan = false;
	private String overworldName;
	private AStarConfigOptions configs;
	
	public AStar(ArrayList<PathCell> cells, String overworldName, AStarConfigOptions configs){
		this.configs = configs;
		events = new AStarInteractionEventObject();
		accessedCells = cells;
		frontier = new ArrayList<CellPoint>(); 
		//explored = new ArrayList<CellPoint>();
		explored = new HashSet<CellPoint>();
		cellMap = new HashMap<String, HashMap<Point,TileInfo>>();
		buildingLocations = new HashMap<String, Point>(cells.size() - 1);
		if (overworldName.length() >= 2){
			this.overworldName = overworldName;
		}
		else {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.WARNING, "Insufficiently Long overworld map name supplied to A*. A* Will ignore it and use the default name 'World' instead.");
			this.overworldName = "World";
		}
		
		for (PathCell candidate : accessedCells){
			if (candidate.getName().equals(overworldName)){
				for (EntryPointReference erf : candidate.getEntryPointReferences()){
					buildingLocations.put(erf.getTargetCell().substring(0, 2), erf.getLoc());
				}
			}
		}
		DebugManagement.writeNotificationToLog("*******************");
		for(String buildingID: buildingLocations.keySet()){
			DebugManagement.writeNotificationToLog(buildingID);
			//System.out.println(buildingLocations.get(buildingID));
		}
		DebugManagement.writeNotificationToLog("*******************");
		
		//Pyramid of Doom. Please prepare and make all human sacrifices here.
		//TODO: refactor this so it's human readable.
		for(PathCell cell: accessedCells){
//			DebugManagement.writeNotificationToLog("Created a new TileInfoArray for " + cell.getName());
			cellMap.put(cell.getName(), new HashMap<Point,TileInfo>());
			
			for(EntryPointReference ref : cell.getEntryPointReferences()){
				for (PathCell pc: accessedCells){
//					DebugManagement.writeNotificationToLog("CellName " + pc.getName() + " compared to " + ref.getTargetCell());
					if ((pc.getName()).equals(ref.getTargetCell())){
//						DebugManagement.writeNotificationToLog("Matched " + pc.getName() + " to " + ref.getTargetCell());
						
						for (EntryPoint ep : pc.getEntryPoints()){
//							DebugManagement.writeNotificationToLog(ep.getId() + " comparing to " + ref.getEntryPointID());
							if ((ep.getId()).equals(ref.getEntryPointID())){
//								DebugManagement.writeNotificationToLog("EntryPoint " + ep.getId() + " linked to " + ref.getEntryPointID());
								TileInfo currentTileInfo = cellMap.get(cell.getName()).get(ref.getLoc());
								if (currentTileInfo == null){
									//DebugManagement.writeNotificationToLog("ref is null:" + String.valueOf(ref == null));
									//DebugManagement.writeNotificationToLog("ref loc is null:" + String.valueOf(ref.getLoc() == null));
									//DebugManagement.writeNotificationToLog("cell_tile_type at ref is null:" + String.valueOf(cell.getTile(ref.getLoc()) == null));
									
									// stops shit from breaking if someone puts an off page connection in a wall
									if(cell.getTile(ref.getLoc()) == null){
//										DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "someone stuck an off page connection in a wall");
										continue;
									}
									currentTileInfo = new TileInfo(cell.getTile(ref.getLoc()).getTileType(), cell.getTile(ref.getLoc()).getTraverseCost());
								}
								currentTileInfo.addOffPageNeighbor(new CellPoint(ref.getTargetCell(), ep.getLoc()));
								cellMap.get(cell.getName()).put(ref.getLoc(), currentTileInfo);
							}
						}
					}
				}
			}
		}
	}
	public AStar(ArrayList<PathCell> cells, AStarConfigOptions configs) {
		this(cells, "World", configs);
		
	}
	private class TileInfo implements Comparable<TileInfo> {
		private TILE_TYPE tileType = TILE_TYPE.WALL; 
		private CellPoint parent = null;
		private int costSoFar = 0;
		private int estimatedTotalCost = 0;
		private int traverseCost = 1000000;
		private ArrayList<CellPoint> offPageNeighbors = new ArrayList<CellPoint>();
//		private boolean explored = false;

		private TileInfo(TILE_TYPE newTileType, int newTraverseCost){
			parent = null;
			costSoFar = 0;
			estimatedTotalCost = 0;
			traverseCost = newTraverseCost;
			tileType = newTileType;
//			this.creator = creator;
		}

		public boolean canBeEntered(CellPoint from, CellPoint to){
			return (this.tileType != TILE_TYPE.WALL);
		}

		private TILE_TYPE getTileType(){
			return this.tileType;
		}
//		private CellPoint getCreator() {
//			return creator;
//		}
		private CellPoint getParent(){
			return this.parent;
		}
		
//		private boolean getExplored(){
//			return this.explored;
//		}
//		
//		private void setExplored(boolean status){
//			this.explored = status;
//		}

		private void setParent(CellPoint newParent){
			this.parent = newParent;
		}
		private int getCostSoFar(){
			return this.costSoFar;
		}
		private void setCostSoFar(int newCSF){
			this.costSoFar = newCSF;
		}
		private void updateCostSoFarDebug(int newCSF) {
			this.costSoFar = newCSF;
		}
		private int getEstimatedTotalCost(){
			return this.estimatedTotalCost;
		}
		private void setEstimatedTotalCost(int newETC){
			this.estimatedTotalCost = newETC;
		}
		private int getTraverseCost(){
			return this.traverseCost;
		}

		private void addOffPageNeighbor(CellPoint newOPN){
			this.offPageNeighbors.add(newOPN);
		}
		private ArrayList<CellPoint> getOffPageNeighbors(){
			return this.offPageNeighbors;
		}
		public int compareTo(TileInfo o){
		     return(getCostSoFar() - o.getCostSoFar());
		}

	}

	private TileInfo getTileInfo(CellPoint aCellPoint){
		TileInfo output = cellMap.get(aCellPoint.getCellName()).get(aCellPoint.getPoint());
		if(output == null){
			for(PathCell aPC: accessedCells){
				if(aPC.getName().equals(aCellPoint.getCellName())){
					TileInfo newTI;
					//case where it's a wall
					if (aPC.getTile(aCellPoint.getPoint()) == null){
						newTI = new TileInfo(TILE_TYPE.WALL, 1000000000);
					}
					//case where it's anything besides a wall
					else {
						newTI = new TileInfo(aPC.getTile(aCellPoint.getPoint()).getTileType(), aPC.getTile(aCellPoint.getPoint()).getTraverseCost());
					}
					return newTI;
				}
			}
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "getTileInfo is returning as null");
		}
		
		return output;
	}

	/*
	 * Makes a 2d array of size x,y filled with blank TileInfos
	 * This is a function right now so that its more flexible if needed.
	 */
//	private TileInfo[][] makeTileInfoArray(PathCell newCell){
//		TileInfo[][] output = new TileInfo[newCell.tiles.length][newCell.tiles[0].length];
//		for(int i = 0; i < newCell.tiles.length; i++){
//			for(int j = 0; j < newCell.tiles[0].length; j++){
//				output[i][j] = new TileInfo(newCell.getTile(new Point(i,j)).getTileType(), 
//						newCell.getTile(new Point(i,j)).getTraverseCost(), new CellPoint(newCell.cellName, new Point(i,j)));
//			
//			}
//		}
//		/*
//		Find the tiles who have references to an entry point in another cell, 
//		and then give them a cell point which is the point they reference.
//		This won't work with incremental cell loading, if we implement that.
//		 */
//
//		for(EntryPointReference ref : newCell.getEntryPointReferences()){
//			for (PathCell pc: accessedCells){
//				DebugManagement.writeNotificationToLog("CellName " + pc.getName() + " compared to " + ref.getTargetCell());
//				if ((pc.getName()).equals(ref.getTargetCell())){
//					DebugManagement.writeNotificationToLog("Matched " + pc.getName() + " to " + ref.getTargetCell());
//					
//					for (EntryPoint ep : pc.getEntryPoints()){
//						DebugManagement.writeNotificationToLog(ep.getId() + " comparing to " + ref.getEntryPointID());
//						if ((ep.getId()).equals(ref.getEntryPointID())){
//							DebugManagement.writeNotificationToLog("EntryPoint " + ep.getId() + " linked to " + ref.getEntryPointID());
//							output[(int) ref.getLoc().getX()][(int) ref.getLoc().getY()].setOffPageNeighbor(new CellPoint(pc.getName(), ep.getLoc()));
//						}
//					}
//				}
//			}
//		}
//		return output;
//	}

	/*
	 * Takes a start Cell "map", start point (the exact tile within a Cell, and the 
	 * end Cell and point.
	 * Returns the fastest path between two points as an ordered list of Tiles
	 */
	public ArrayList<CellPoint> getPath(CellPoint startCellPoint, CellPoint endCellPoint, boolean isFiltering){
		if(alreadyRan == true){ // A* needs to be reinitialized each time it runs. this checks that
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "you done broke shit, A* already ran."
					+ "\n A* will try to continue but it wont do anything");
		}
		DebugManagement.writeNotificationToLog("First point " + startCellPoint.getCellName() + " " + startCellPoint.getPoint().toString());
		DebugManagement.writeNotificationToLog("End point " + endCellPoint.getCellName() + " " + endCellPoint.getPoint().toString());
		
		alreadyRan = true;
		CellPoint currentPoint = startCellPoint;
		cellMap.get(currentPoint.getCellName()).put(currentPoint.getPoint(), buildTileInfo(currentPoint));
		TileInfo currentTile = getTileInfo(startCellPoint);
		if(isIllegalType(currentTile.getTileType())){
			DebugManagement.writeNotificationToLog("Starting Tile is an illegal Type, Return is Null");
			return null;
		}
		CellPoint endPoint = endCellPoint;
		int tentativeCSF = 0; //combines the cost so far and the cost to enter a tile that is being explored		
		int curX;
		int curY;
		/*The expected path cost from start to finish based on the best known 
			path so far. Starts as just the heuristic from start to finish
		 */
		
		//the only thing in the frontier to start is the start node
		this.frontier.add(startCellPoint);
		
		/*So long as the frontier is not empty the tile we want to explore is the tile with 
		 * For now its BFS so we just take the first element*/
		DebugManagement.writeNotificationToLog("Entering A* main while loop");
//		int i = 0;
		while(!frontier.isEmpty()) {
//			i++;
//			DebugManagement.writeNotificationToLog("Explored: " + String.valueOf(i));
			frontier = sortByCost(frontier);
			currentPoint = frontier.get(0);
//			DebugManagement.writeNotificationToLog(currentPoint.getCellName());
			if(currentPoint.equals(endPoint)){ //if we are at the end: 
				ArrayList<CellPoint> roughPath = buildPath(endPoint); 
				ArrayList<CellPoint> FinalPath = cleanPath(roughPath);
				if(isFiltering) {
					events.FilterStepComplete(FinalPath, getTileInfo(currentPoint).getCostSoFar());
				} else {
					events.AStarCompletePath(FinalPath, getTileInfo(currentPoint).getCostSoFar()); // Fires A* event. 
					
				}
				DebugManagement.writeNotificationToLog("A* has calculated and returned path");
				return FinalPath;
			}
			if(!(explored.contains(currentPoint))){ //if the currentTile isnt already explored
				explored.add(currentPoint); // add to explored
			}
//			if(!currentTile.getExplored()){
//				currentTile.setExplored(true);
//			}
			frontier.remove(currentPoint); // remove the curTile from frontier so we dont check it again, just for readability and convenience
			curX = (int) currentPoint.getPoint().getX();
			curY = (int) currentPoint.getPoint().getY(); 

			TileInfo neighborTile;
			currentTile = getTileInfo(currentPoint);
			double moveMultiplier;
			for(CellPoint neighborPoint: getNeighbors(currentPoint)){
				cellMap.get(neighborPoint.getCellName()).putIfAbsent(neighborPoint.getPoint(), buildTileInfo(neighborPoint));
				
				neighborTile = getTileInfo(neighborPoint);
				if(neighborTile == null){
					DebugManagement.writeNotificationToLog("****neighborTile is null****");
				}
				if(!neighborTile.canBeEntered(currentPoint, neighborPoint)){
					continue;
				}
				if(/*neighborPoint.isIn(explored)*/ explored.contains(neighborPoint)){
					continue;
				}
				
				moveMultiplier = 1;
				if((curX != (int)neighborPoint.getPoint().getX()) && (curY != neighborPoint.getPoint().getY())){ 
					moveMultiplier = 1.41; // sqrt(2)
				}
				for(CellPoint each: getNeighbors(neighborPoint)){
					TILE_TYPE neighborType = getTileInfo(each).getTileType();
					if((neighborType != TILE_TYPE.PEDESTRIAN_WALKWAY) ||
							(neighborType != TILE_TYPE.DOOR)){
						moveMultiplier = moveMultiplier*1.5;
						break;
					}
				}
				
				tentativeCSF = (int) (currentTile.getCostSoFar() + (moveMultiplier*neighborTile.getTraverseCost()));
				if(!neighborPoint.isIn(frontier)){
					neighborTile.setParent(currentPoint);
					neighborTile.updateCostSoFarDebug(tentativeCSF);
					neighborTile.setEstimatedTotalCost(tentativeCSF + getHeuristic(neighborPoint,endPoint));
					cellMap.get(neighborPoint.getCellName()).put(neighborPoint.getPoint(), neighborTile);
					if(neighborPoint == null || neighborTile == null){
						DebugManagement.writeNotificationToLog("before adding to frontier, neighborPoint is null");
						//shouldn't we address this?
					}
					frontier.add(neighborPoint);
				}
				else if(tentativeCSF < neighborTile.getCostSoFar()){
					neighborTile.setParent(currentPoint);
					neighborTile.updateCostSoFarDebug(tentativeCSF);
					neighborTile.setEstimatedTotalCost(tentativeCSF+ getHeuristic(neighborPoint,endPoint));
					cellMap.get(neighborPoint.getCellName()).put(neighborPoint.getPoint(), neighborTile);

				}
				//do nothing if the cost is higher.
			}
			//DebugManagement.writeNotificationToLog("A* Loop is still going...");
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.SEVERE, "No path found!");
		return null;
	}
	
	private ArrayList<CellPoint> cleanPath(ArrayList<CellPoint> roughPath) {
		ArrayList<CellPoint> cleanPath = new ArrayList<CellPoint>();
		String currentCellName = roughPath.get(0).getCellName();
		String aCellName = null;
		ArrayList<CellPoint> tilesInCurCell = new ArrayList<CellPoint>();
		for(CellPoint each: roughPath){
			aCellName = each.getCellName(); 
			if(!(aCellName.equals(currentCellName))){
				currentCellName = aCellName;
				if(tilesInCurCell.size() > 2){
					cleanPath.addAll(tilesInCurCell);
				}
				tilesInCurCell = new ArrayList<CellPoint>();
			} 
			tilesInCurCell.add(each);
		}
		cleanPath.addAll(tilesInCurCell);
//		DebugManagement.writeNotificationToLog(String.valueOf(cleanPath.size()));
		return cleanPath;
		
		/*
		aCellName = each.getCellName(); 
		if(aCellName.equals(currentCellName)){
			tilesInCurCell.add(each);
		}
		else{
			currentCellName = aCellName;
			if(tilesInCurCell.size() > 2){
				cleanPath.addAll(tilesInCurCell);
			}
			tilesInCurCell = new ArrayList<CellPoint>();
			tilesInCurCell.add(each);
		} */
	}
	
	private TileInfo buildTileInfo(CellPoint currentPoint) {
		PathCell currentCell = null;
		Tile currentTile = null;
		TileInfo currentTileInfo = null;
		for(PathCell each: accessedCells){
			if(each.getName().equals(currentPoint.getCellName())){
				currentCell = each;
			}
		}
		currentTile = currentCell.getTile(currentPoint.getPoint());

		if(currentTile != null){
			currentTileInfo = new TileInfo(currentTile.getTileType(), currentTile.getTraverseCost());// currentPoint);
		}
		else{
			currentTileInfo = new TileInfo(TILE_TYPE.WALL, 1000000); //currentPoint);
		}
	
		return currentTileInfo;
	}

	private ArrayList<CellPoint> sortByCost(ArrayList<CellPoint> input) {
		/* This is a cell point collections can sort. This means that 
		 * it implicitly references tile infos. We need to think of a 
		 * better way to do this.*/
		class SortableCellPoint extends CellPoint implements Comparable<SortableCellPoint>{
			CellPoint cp;
			private SortableCellPoint(CellPoint cp){
				super(cp.getCellName(), cp.getPoint());
				this.cp = cp;
			}
			public int compareTo(SortableCellPoint o){
			     return(getTileInfo(this).getEstimatedTotalCost() - getTileInfo(o).getEstimatedTotalCost());
			}
			public CellPoint getCellPoint(){
				return cp;
			}
		}
		
		ArrayList<SortableCellPoint> working = new ArrayList<SortableCellPoint>();
		ArrayList<CellPoint> output = new ArrayList<CellPoint>();
		
		for(CellPoint c : input) {
			working.add(new SortableCellPoint(c));
		}
		
		Collections.sort(working);
	
		for(SortableCellPoint t : working) {
			//sorted- now re-add
			output.add(t.getCellPoint());
		}
		return output;
	}
	//Builds the path of points that will be returned and displayed visually
	private ArrayList<CellPoint> buildPath(CellPoint endPoint) {
		ArrayList<CellPoint> pointPath = new ArrayList<CellPoint>();

		TileInfo currentTile = getTileInfo(endPoint);
		CellPoint currentPoint = endPoint;

		while(currentTile.getParent() != null){ //while not start tile	
			pointPath.add(currentPoint);
			currentPoint = currentTile.getParent();
			currentTile = getTileInfo(currentPoint);
		}
		pointPath.add(currentPoint);
		Collections.reverse(pointPath);
		return pointPath;
	}

	public void addCell(PathCell aCell){
		if(!accessedCells.contains(aCell)){
			accessedCells.add(aCell);
		}
	}

	public PathCell selectCell(String aCellName){
		for(PathCell aCell:accessedCells){
			if(aCell.getName() == aCellName){
				return aCell;
			}
		}
		return null;
	}


		private int getHeuristic(CellPoint current, CellPoint end){
			Point endCoords = getCoords(end);
			DebugManagement.writeNotificationToLog(String.valueOf(getCoords(end)==null));
			Point currentCoords = getCoords(current);
			
			if((endCoords == null) || (currentCoords == null)){
				
				DebugManagement.writeNotificationToLog("A map is not connected to world. Its either:");
				DebugManagement.writeNotificationToLog(current.getCellName());
				DebugManagement.writeNotificationToLog(end.getCellName());
				return 0;
			}
			
			return 8*(int)Math.sqrt((Math.pow((endCoords.getX() - currentCoords.getX()), 2) + Math.pow((endCoords.getY() - currentCoords.getY()), 2)));
		}

		private Point getCoords(CellPoint aCP) {
			String buildingName = aCP.getCellName().substring(0, 2);
			DebugManagement.writeNotificationToLog(buildingName);
			if(buildingName.equals(overworldName.substring(0, 2))){
				return aCP.getPoint();
			}
			return getCellCoords(buildingName);
		}

		private Point getCellCoords(String buildingName) {
			for(String each: buildingLocations.keySet()){
				if (each.equals(buildingName)){
					return buildingLocations.get(buildingName);
				}
			}
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "Warning get cell Coords returned null");
			return null;
		}

		private ArrayList<CellPoint> getNeighbors(CellPoint currentPoint){
			ArrayList<CellPoint> output = new ArrayList<CellPoint>();
			int curX = (int) currentPoint.getPoint().getX();
			int curY = (int) currentPoint.getPoint().getY();
			for(int neiX = curX-1; neiX <= curX+1; neiX++){
				for(int neiY = curY-1; neiY <= curY+1; neiY++){
					CellPoint neighbor = new CellPoint(currentPoint.getCellName(), new Point(neiX, neiY));
					// This prevents impassables or trees from being pathed through.
					TileInfo neighborTile = getTileInfo(neighbor);
					
					if((neighborTile == null) || isIllegalType(neighborTile.getTileType())){
						continue;
					}
					output.add(new CellPoint(currentPoint.getCellName(), new Point(neiX, neiY)));
				}
			}
			
			if(getTileInfo(currentPoint).getOffPageNeighbors() != null){
				// we commented this out because it doesnt work...
//				DebugManagement.writeNotificationToLog("Off cell neighbor found at " + getTileInfo(currentPoint).getOffPageNeighbors().getCellName() + " " + getTileInfo(currentPoint).getOffPageNeighbor().getPoint()); 
				output.addAll(getTileInfo(currentPoint).getOffPageNeighbors());
			}
			
			return output;
		}
		//generate a list of all known illegals
		private boolean isIllegalType(TILE_TYPE tileType) {
			HashSet<TILE_TYPE> illegals = new HashSet<TILE_TYPE>();
			illegals.add(TILE_TYPE.WALL);
			illegals.add(TILE_TYPE.IMPASSABLE);
			illegals.add(TILE_TYPE.UNPLOWED);
			illegals.add(TILE_TYPE.TREE);
			illegals.add(TILE_TYPE.GRASS);
			
			if(this.configs.getIsLateForClass()){
				illegals.remove(TILE_TYPE.GRASS);
			}

			if(this.configs.getIsHandicapped()){
				illegals.add(TILE_TYPE.HORIZONTAL_LEFT_STAIRS);
				illegals.add(TILE_TYPE.HORIZONTAL_RIGHT_STAIRS);
				illegals.add(TILE_TYPE.VERTICAL_DOWN_STAIRS);
				illegals.add(TILE_TYPE.VERTICAL_UP_STAIRS);
				if(!illegals.contains(TILE_TYPE.GRASS)){
					illegals.add(TILE_TYPE.GRASS);
				}
			}
			
			return illegals.contains(tileType);
		}
		
		/*utility function to get a particular path cell from accessed cells knowing only its name*/
		
}
