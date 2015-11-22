package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
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
	public AStar(ArrayList<PathCell> cells) {
		events = new AStarInteractionEventObject();
		accessedCells = cells;
	}
	
	
	
	//maps which tiles have been added
		ArrayList<PathCell> accessedCells = new ArrayList<PathCell>(); 
		
		// Nodes that need to be explored
		ArrayList<Tile> frontier = new ArrayList<Tile>(); 
		
		// Nodes that have already been explored
		ArrayList<Tile> explored = new ArrayList<Tile>();
		
		
		/*
		 * Takes a start Cell "map", start point (the exact tile within a Cell, and the 
		 * end Cell and point.
		 * Returns the fastest path between two points as an ordered list of Tiles
		 */
		public ArrayList<CellPoint> getPath(int startCellIndex, Point startIndex, int endCellIndex, Point endIndex){
			
			PathCell startCell = accessedCells.get(startCellIndex); //What cell do we start in
			PathCell currentCell = startCell;
			PathCell endCell = accessedCells.get(endCellIndex);
			Tile currentTile = getTile(currentCell, startIndex); //The exact tile we start at
			Tile endTile = getTile(endCell, endIndex); // the tile we want to get to
			int tentativeCSF = 0; //combines the cost so far and the cost to enter a tile
								  //that is being explored
			
			int curX;
			int curY;
			int neiX;
			int neiY;
			int estTotalCost = getHeuristic(currentTile);//the expected path cost from start
														 //to finish based on the best known 
														 //path so far. Starts as just the 
														 //heuristic from start to finish
		
			this.frontier.add(currentTile); //the only thing in the frontier to start is the 
											//start node
			
			while(!frontier.isEmpty()){ //so long as the frontier is not empty
				currentTile = frontier.get(0); //the tile we want to explore is the tile with 
											   //the lowest expected path cost
											   //For now its BFS so we just take the first 
											   //element
				
				if(currentTile == endTile){ //if we are at the end: 

					return buildPath(endTile); //return the path
				}
				
				if(!(explored.contains(currentTile))){ //if the currentTile isnt already explored
					explored.add(currentTile); // add to explored
				}
				frontier.remove(currentTile); // remove the curTile from frontier so we dont check 
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

		private ArrayList<CellPoint> buildPath(Tile endTile) {
			ArrayList<CellPoint> pointPath = new ArrayList<CellPoint>();
			
			Tile currentTile = endTile;
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
}