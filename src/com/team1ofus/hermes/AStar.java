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
	}
	private class TileInfo {
		private TILE_TYPE tileType = TILE_TYPE.WALL; 
		private CellPoint parent = null;
		private int costSoFar = 0;
		private int estimatedTotalCost = 0;
		
		private TileInfo(){
			parent = null;
			costSoFar = 0;
			estimatedTotalCost = 0;
		}
		
		
		// makes a 2d array of size x,y filled with blank TileInfos
		// this is a function right now so that its more flexible if needed.
		private TileInfo[][] makeTiInArray(PathCell newCell){
			TileInfo[][] output = new TileInfo[newCell.tiles.length][newCell.tiles[0].length];
			for(int i = 0; i < newCell.tiles.length; i++){
				for(int j = 0; j < newCell.tiles[0].length; j++){
					output[i][j].setTileType(newCell.tiles[i][j].getTileType());
				}
			}
			return output;
		}
		
		private TILE_TYPE getTileType(){
			return this.tileType;
		}
		
		private void setTileType(TILE_TYPE aType){
			this.tileType = aType;
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
	}
	
	
	
//	//maps which tiles have been added
//		ArrayList<PathCell> accessedCells = new ArrayList<PathCell>(); 
//		
//		// Nodes that need to be explored
//		ArrayList<Tile> frontier = new ArrayList<Tile>(); 
//		
//		// Nodes that have already been explored
//		ArrayList<Tile> explored = new ArrayList<Tile>();
		
		
		/*
		 * Takes a start Cell "map", start point (the exact tile within a Cell, and the 
		 * end Cell and point.
		 * Returns the fastest path between two points as an ordered list of Tiles
		 */
		public ArrayList<CellPoint> getPath(String startCellName, Point startIndex, String endCellName, Point endIndex){
			
//			PathCell startCell = accessedCells.get(startCellIndex); //What cell do we start in
//			PathCell currentCell = startCell;
			
//			PathCell endCell = accessedCells.get(endCellIndex);
//			Tile currentTile = getTile(currentCell, startIndex); //The exact tile we start at
			CellPoint currentPoint = new CellPoint(startCellName,startIndex);
			CellPoint endPoint = new CellPoint(endCellName, endIndex);
//			Tile endTile = getTile(endCell, endIndex); // the tile we want to get to
			int tentativeCSF = 0; //combines the cost so far and the cost to enter a tile
								  //that is being explored
			
			int curX;
			int curY;
			int neiX;
			int neiY;
//			int estTotalCost = getHeuristic(currentTile);
			int estTotalCost = getHeuristic(startCellName, startIndex, endCellName, endIndex);//the expected path cost from start
														 //to finish based on the best known 
														 //path so far. Starts as just the 
														 //heuristic from start to finish
		
//			this.frontier.add(currentTile); //the only thing in the frontier to start is the 
											//start node
			this.frontier.add(new CellPoint(startCellName, startIndex));
			
			while(!frontier.isEmpty()){ //so long as the frontier is not empty
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
					 
				for(Tile aNeighbor: currentTile.getNeighbors(currentCell)){
					if(explored.contains(aNeighbor)){
						continue;
					}
					curX = (int) currentTile.getCellPoint().getPoint().getX();
					curY = (int) currentTile.getCellPoint().getPoint().getY();
					neiX = (int) aNeighbor.getCellPoint().getPoint().getX();
					neiY = (int) aNeighbor.getCellPoint().getPoint().getY();
					if((curX != neiX) && (curY != neiY)){ //&& (currentTile.getCellName() == aNeighbor.getCellName())){
						tentativeCSF = currentTile.getCSF() + (int)(1.41*aNeighbor.getTraverseCost());
					}
					else{
						tentativeCSF = currentTile.getCSF() + aNeighbor.getTraverseCost();
					}
					if(!frontier.contains(aNeighbor)){
						aNeighbor.setParent(currentTile);
						aNeighbor.setCSF(tentativeCSF);
						aNeighbor.setETC(tentativeCSF+ getHeuristic(aNeighbor));
						frontier.add(aNeighbor);
					}
					else if(tentativeCSF >= aNeighbor.getCSF()){
						continue;
					}
					aNeighbor.setParent(currentTile);
					aNeighbor.setCSF(tentativeCSF);
					aNeighbor.setETC(tentativeCSF+ getHeuristic(aNeighbor));
				}
			}
			System.out.println("No Path Found");
			return null;
		
		}

		private int getHeuristic(Tile currentTile) {
			return 0;
		}

		private ArrayList<CellPoint> buildPath(CellPoint endPoint) {
			ArrayList<CellPoint> pointPath = new ArrayList<CellPoint>();
			
			Tile currentTile = endPoint;
			CellPoint currentPoint = currentTile.getCellPoint();
			
			while(currentTile.getParent() != null){
					currentPoint = currentTile.getCellPoint();
					pointPath.add(currentPoint);
					currentTile = currentTile.getParent();
			}
			pointPath.add(currentTile.getCellPoint());
			Collections.reverse(pointPath);
//			System.out.println("A* ran");
//			events.completePath(pointPath);
			return pointPath;
		}

		private Tile getTile(PathCell aCell, Point aIndex) {
			return aCell.getTile(aIndex);
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
		
		private TileInfo getTileInfo(String aCellName, Point aPoint){
			return cellMap.get(aCellName)[(int) aPoint.getX()][(int) aPoint.getY()];
		}
		
		private int getHeuristic(String curCell, Point curPoint, String endCell, Point endPoint){
			return 0;
		}
}








