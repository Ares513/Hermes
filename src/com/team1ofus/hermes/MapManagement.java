package com.team1ofus.hermes;

/*
 * Description:    	Holds the list of the different cells(maps), as well as the path that the A* algorithm has generated.
 
Attributes
cells List 
List of cells (maps)
pathMachine AStar 
he path that is generated by the A* algorithm 

Methods

Relationships
AStar               Composition                  Holds an AStar path        
PathUI             

 */

public class MapManagement {
	Cell[] cells = new Class[4];
	
	/* This event lets bootstrapper know when A* finishes computing a path and would like to display it. 
	 * 
	 */
	public class AStarPathCompleteEvent extends EventObject {
		private DirectionSet[] path;
		AStarPathCompleteEvent(source, id, aPath) {
			super(source, id);
			path = aPath;
		}
		public DirectionSet[] getDirectionSet() {
			return path;
		}
	}
	
	/* This event lets bootstrapper know when mapManagement wants to load a new cell from disk. 
	 * 
	 */
	public class RequesteCellEvent extends EventObject {
		private String cellName;
		RequesteCellEvent(source, id, aCellName) {
			super(source, id);
			cellName = aCellName;
		}
		public String getCellName() {
			return cellName;
		}
	}
}