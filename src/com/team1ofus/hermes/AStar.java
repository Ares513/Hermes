package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;

/*
 * Attributes
accessedCells[] Cell
keeps track of the cells that have been added to the active maps.
frontier[] locationInfo
The list of nodes that are adjacent to the explored nodes but have not yet been explored themselves yet.
visited[] locationInfo
The list of nodes that have been visited already.

Methods
getPath(Cell, startCell, Point startIndex, Cell endCell, Point endIndex)
returns the ordered list of nodes that constitute a path from one given location to another.

 */

public class AStar { 
	AStarInteractionEventObject events;
	HashMap<String, TileInfo[][]> cellMap;
	ArrayList<PathCell> accessedCells; 
	// Nodes that need to be explored
	ArrayList<CellPoint> frontier;
	// Nodes that have already been explored
	ArrayList<CellPoint> explored;
	private boolean alreadyRan = false;

	public AStar(ArrayList<PathCell> cells) {
		events = new AStarInteractionEventObject();
		accessedCells = cells;
		// Nodes that need to be explored
		frontier = new ArrayList<CellPoint>(); 
		// Nodes that have already been explored
		explored = new ArrayList<CellPoint>();
		cellMap = new HashMap<String, TileInfo[][]>();
		for(PathCell cell: cells){
			cellMap.put(cell.getName(), makeTileInfoArray(cell));
		}
	}
	private class TileInfo {
		private TILE_TYPE tileType = TILE_TYPE.WALL; 
		private CellPoint parent = null;
		private int costSoFar = 0;
		private int estimatedTotalCost = 0;
		private int traverseCost = 1000000;
		private CellPoint offPageNeighbor = null;
		
		
		private TileInfo(TILE_TYPE newTileType, int newTraverseCost){
			parent = null;
			costSoFar = 0;
			estimatedTotalCost = 0;
			traverseCost = newTraverseCost;
			tileType = newTileType;
		}
	
		public boolean canBeEntered(CellPoint from, CellPoint to){
			return (this.tileType != TILE_TYPE.WALL);
		}
		
		private TILE_TYPE getTileType(){
			return this.tileType;
		}
		
		private CellPoint getParent(){
			return this.parent;
		}
		
		private void setParent(CellPoint newParent){
			this.parent = newParent;
		}
		private int getCostSoFar(){
			return this.costSoFar;
		}
		private void setCostSoFar(int newCSF){
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
		
		private void setOffPageNeighbor(CellPoint newOPN){
			this.offPageNeighbor = newOPN;
		}
		private CellPoint getOffPageNeighbor(){
			return this.offPageNeighbor;
		}
	}
	
	private TileInfo getTileInfo(CellPoint aCellPoint){
		return cellMap.get(aCellPoint.getCellName())[(int) aCellPoint.getPoint().getX()][(int) aCellPoint.getPoint().getY()];
	}
	
	// makes a 2d array of size x,y filled with blank TileInfos
	// this is a function right now so that its more flexible if needed.
	private TileInfo[][] makeTileInfoArray(PathCell newCell){
		TileInfo[][] output = new TileInfo[newCell.tiles.length][newCell.tiles[0].length];
		for(int i = 0; i < newCell.tiles.length; i++){
			for(int j = 0; j < newCell.tiles[0].length; j++){
				output[i][j] = new TileInfo(newCell.getTile(new Point(i,j)).getTileType(), 
											newCell.getTile(new Point(i,j)).getTraverseCost());
			}
		}
		//find the tiles who have references to an entry point in another cell, 
		//and then give them a cell point which is the point they reference.
		//This won't work with incremental cell loading, if we implement that.
		for(EntryPointReference erf : newCell.getEntryPointReferences()){
			for (PathCell pc: accessedCells){
				if ((pc.getName()).equals(erf.getTargetCell())){
					for (EntryPoint ep : pc.getEntryPoints()){
						if ((ep.getId()).equals(erf.getId())){
							output[(int) erf.getLoc().getX()][(int) erf.getLoc().getY()].setOffPageNeighbor(new CellPoint(pc.getName(), ep.getLoc()));
						}
					}
				}
			}
		}
		return output;
	}

		
		/*
		 * Takes a start Cell "map", start point (the exact tile within a Cell, and the 
		 * end Cell and point.
		 * Returns the fastest path between two points as an ordered list of Tiles
		 */
		public ArrayList<CellPoint> getPath(CellPoint startCellPoint, CellPoint endCellPoint){
			if(alreadyRan == true){ // A* needs to be reinitialized each time it runs. this checks that
				System.out.println("You done Broke shit, A* already ran");
				DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "you done broke shit, A* already ran."
						+ "\n A* will try to continue but it wont do anything");
			}
			alreadyRan = true;
			CellPoint currentPoint = startCellPoint;
			TileInfo currentTile = getTileInfo(startCellPoint);
			if(currentTile.getTileType().equals(TILE_TYPE.WALL)){
				return null;
			}
			CellPoint endPoint = endCellPoint;
			int tentativeCSF = 0; //combines the cost so far and the cost to enter a tile
								  //that is being explored
			
			int curX;
			int curY;
			int estTotalCost = getHeuristic(currentPoint, endPoint);//the expected path cost from start
														 //to finish based on the best known 
														 //path so far. Starts as just the 
														 //heuristic from start to finish
		
//			this.frontier.add(currentTile); //the only thing in the frontier to start is the 
											//start node
			this.frontier.add(startCellPoint);
			
			while(!frontier.isEmpty()){ //so long as the frontier is not empty
				currentPoint = frontier.get(0); //the tile we want to explore is the tile with 
											   //the lowest expected path cost
											   //For now its BFS so we just take the first 
											   //element
				

				if(currentPoint.equals(endPoint)){ //if we are at the end: 
					ArrayList<CellPoint> FinalPath = buildPath(endPoint); 
					events.AStarCompletePath(FinalPath); // Fires A* event. 
					return FinalPath; 

				}
				
				
				if(!(explored.contains(currentPoint))){ //if the currentTile isnt already explored
					explored.add(currentPoint); // add to explored
				}
				frontier.remove(currentPoint); // remove the curTile from frontier so we dont check 
											  // it again
				curX = (int) currentPoint.getPoint().getX(); // just for readability
				curY = (int) currentPoint.getPoint().getY(); // and convenience
				
				TileInfo neighborTile;
				currentTile = getTileInfo(currentPoint);
				double moveMultiplier;
				for(CellPoint neighborPoint: getNeighbors(currentPoint)){
					neighborTile = getTileInfo(neighborPoint);
					if(!neighborTile.canBeEntered(currentPoint, neighborPoint)){
						continue;
					}
					if(neighborPoint.isIn(explored)){
						continue;
					}
				
					moveMultiplier = 1;
					if((curX != (int)neighborPoint.getPoint().getX()) && (curY != neighborPoint.getPoint().getY())){ //&& (currentTile.getCellName() == aNeighbor.getCellName())){
						moveMultiplier = 1.41; // sqrt(2)
					}
					tentativeCSF = (int) (currentTile.getCostSoFar() + (moveMultiplier*neighborTile.getTraverseCost()));
					
					if(!neighborPoint.isIn(frontier)){
						neighborTile.setParent(currentPoint);
						neighborTile.setCostSoFar(tentativeCSF);
						neighborTile.setEstimatedTotalCost(tentativeCSF+ getHeuristic(neighborPoint,endPoint));
						frontier.add(neighborPoint);
					}
					else if(tentativeCSF >= neighborTile.getCostSoFar()){
						continue;
					}
					neighborTile.setParent(currentPoint);
					neighborTile.setCostSoFar(tentativeCSF);
					neighborTile.setEstimatedTotalCost(tentativeCSF+ getHeuristic(neighborPoint,endPoint));
				}
			}
			System.out.println("No Path Found");
			return null;
		
		}

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
			return 0;
		}
		
		private ArrayList<CellPoint> getNeighbors(CellPoint currentPoint){
			ArrayList<CellPoint> output = new ArrayList<CellPoint>();
			int curX = (int) currentPoint.getPoint().getX();
			int curY = (int) currentPoint.getPoint().getY();
			for(int neiX = curX-1; neiX <= curX+1; neiX++){
				for(int neiY = curY-1; neiY <= curY+1; neiY++){
					output.add(new CellPoint(currentPoint.getCellName(), new Point(neiX, neiY)));
				}
			}
			if(getTileInfo(currentPoint).getOffPageNeighbor() != null){
				System.out.println("found off page connection");
				output.add(getTileInfo(currentPoint).getOffPageNeighbor());
			}
			
			return output;
		}
}








