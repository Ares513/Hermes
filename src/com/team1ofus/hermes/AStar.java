package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
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
public class AStar{
	public class AStarRequestCellEvent extends EventObject {
		private List<IAStarRequestCellListener> listeners = new ArrayList<IAStarRequestCellListener>(); //list of registered listeners
		public AStarRequestCellEvent(Object source) {
			super(source);
		}
		public synchronized void fire(String cellName) {
			for (IAStarRequestCellListener l : listeners) {
				l.onAStarRequestCellEvent(cellName);
			}
		}
		public synchronized void registerListener(IAStarRequestCellListener aListener) {
			listeners.add(aListener);
		}
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
		public ArrayList<Tile> getPath(PathCell startCell, Point startIndex, PathCell endCell, Point endIndex){
			PathCell currentCell = startCell; //What cell do we start in
			Tile currentTile = getTile(currentCell, startIndex); //The exact tile we start at
			Tile endTile = getTile(endCell, endIndex); // the tile we want to get to
			int costSoFar = 0; //the cost of the best known path so far (not complete until 
							   //A* returns
			int tentativeCSF = 0; //combines the cost so far and the cost to enter a tile
								  //that is being explored
//			int estTotalCost = getHeuristic(currentTile);//the expected path cost from start
														 //to finish based on the best known 
														 //path so far. Starts as just the 
														 //heuristic from start to finish
		
			
			this.frontier.add(currentTile); //the only thing in the frontier to start is the 
											//start node
			
			while(!frontier.isEmpty()){ //so long as the frontier is not empty
				currentTile = frontier.get(0); //the tile we want to explore is the tile with 
											   //the lowest expected path cost
											   //For now its BFS so order doesn't matter
				
				if(currentTile == endTile){ //if we are at the end: 
					return buildPath(endTile); //return the path
				}
				
				if(!(explored.contains(currentTile))){ //if the currentTile isnt already explored
					explored.add(currentTile); // add to explored
				}
				frontier.remove(currentTile); // remove from frontier
				
				 
				for(Tile aNeighbor: currentTile.getNeighbors()){
					if(explored.contains(aNeighbor)){
						continue;
					}
					tentativeCSF = costSoFar + aNeighbor.getTraverseCost();
					if(!frontier.contains(aNeighbor)){
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

		private ArrayList<Tile> buildPath(Tile endTile) {
			//maps which tiles have been added
			ArrayList<Tile> path = new ArrayList<Tile>(); 
			
			Tile currentTile = endTile;
			
			while(currentTile.getParent() != null){
				path.add(0, currentTile);
				currentTile = currentTile.getParent();
			}
			return path;
		}

		private Tile getTile(PathCell aCell, Point aIndex) {
			return null;
			
			//return startCell.2dArray(startIndex);
		}
}