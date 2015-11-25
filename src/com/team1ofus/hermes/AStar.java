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

	public AStar(ArrayList<PathCell> cells) {
		events = new AStarInteractionEventObject();
		accessedCells = cells;
		//maps which tiles have been added
		accessedCells = new ArrayList<PathCell>(); 
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
		return output;
	}

		
		/*
		 * Takes a start Cell "map", start point (the exact tile within a Cell, and the 
		 * end Cell and point.
		 * Returns the fastest path between two points as an ordered list of Tiles
		 */
		public ArrayList<CellPoint> getPath(CellPoint startCellPoint, CellPoint endCellPoint){
			
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
//			int estTotalCost = getHeuristic(currentTile);
			int estTotalCost = getHeuristic(currentPoint, endPoint);//the expected path cost from start
														 //to finish based on the best known 
														 //path so far. Starts as just the 
														 //heuristic from start to finish
		
//			this.frontier.add(currentTile); //the only thing in the frontier to start is the 
											//start node
			this.frontier.add(startCellPoint);
			
			while(!frontier.isEmpty()){ //so long as the frontier is not empty
				System.out.println(currentPoint.getPoint());
				System.out.println(explored.size());
				System.out.println(frontier.size());
				currentPoint = frontier.get(0); //the tile we want to explore is the tile with 
											   //the lowest expected path cost
											   //For now its BFS so we just take the first 
											   //element
				
				if(currentPoint.equals(endPoint)){ //if we are at the end: 

					return buildPath(endPoint); //return the path
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
				CellPoint neighborPoint;
				double moveMultiplier;
				for(int neiX = curX-1; neiX <= curX+1; neiX++){
					for(int neiY = curY-1; neiY <= curY+1; neiY++){
						neighborPoint = new CellPoint(currentPoint.getCellName(), new Point(neiX, neiY));
						neighborTile = getTileInfo(neighborPoint);
						if(!neighborTile.canBeEntered(currentPoint, neighborPoint)){
							continue;
						}
						for(CellPoint aPoint: explored){
							if(neighborPoint.getCellName().equals(aPoint.getCellName()) && 
							   neighborPoint.getPoint().equals(aPoint.getPoint())){
								continue;
							}
						}
						moveMultiplier = 1;
						if((curX != neiX) && (curY != neiY)){ //&& (currentTile.getCellName() == aNeighbor.getCellName())){
							moveMultiplier = 1.41; // sqrt(2)
						}
						tentativeCSF = (int) (currentTile.getCostSoFar() + (moveMultiplier*neighborTile.getTraverseCost()));
						
						if(!frontier.contains(neighborPoint)){
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
			}
			System.out.println("No Path Found");
			return null;
		
		}

//		private int getHeuristic(Tile currentTile) {
//			return 0;
//		}

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
//			System.out.println("A* ran");
//			events.completePath(pointPath);
			return pointPath;
		}

//		private Tile getTile(PathCell aCell, Point aIndex) {
//			return aCell.getTile(aIndex);
//		}
		
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
		
		private TileInfo getTileInfo(CellPoint aCellPoint){
			return cellMap.get(aCellPoint.getCellName())[(int) aCellPoint.getPoint().getX()][(int) aCellPoint.getPoint().getY()];
		}
		
		private int getHeuristic(CellPoint current, CellPoint end){
			return 0;
		}
}








