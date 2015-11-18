package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.EventObject;
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
import java.util.List;


public class MapManagement implements IAStarRequestCellListener, ICellLoadedListener {

	ArrayList<PathCell> cells = new ArrayList<PathCell>();
	private AStarPathCompleteEvent AStarDone = new AStarPathCompleteEvent(this);
	private RequestCellEvent RequestCell = new RequestCellEvent(this);
	private AStar pathfinder = new AStar();
	
	public MapManagement() {
		
	}
	
	/* This event lets bootstrapper know when A* finishes computing a path and wants the ui to display it. 
	 * 
	 */
	public class AStarPathCompleteEvent extends EventObject {
		private List<AStarPathCompleteListener> listeners = new ArrayList<AStarPathCompleteListener>(); //list of registered listeners
		AStarPathCompleteEvent(Object source) {
			super(source);
		}
		public synchronized void fire(CellPoint[] path) {
			for (AStarPathCompleteListener l : listeners) {
				l.onAStarPathCompleteEvent(path);
			}
		}
		public synchronized void registerListener(AStarPathCompleteListener aListener) {
			listeners.add(aListener);
		}
	}
	
	/* This event lets bootstrapper know when mapManagement wants to load a new cell from disk. 
	 * 
	 */
	public class RequestCellEvent extends EventObject {
		private List<RequestCellListener> listeners = new ArrayList<RequestCellListener>(); //list of registered listeners
		RequestCellEvent(Object source) {
			super(source);
		}
		public synchronized void fire(String cellName) {
			for (RequestCellListener l : listeners) {
				l.onRequestCellEvent(cellName);
			}
		}
		public synchronized void registerListener(RequestCellListener aListener) {
			listeners.add(aListener);
		}
	}
	
	//event getters (used by classes that want to register to the listeners)
	public AStarPathCompleteEvent getAStarPathCompletedEvent() {
		return AStarDone;
	}
	public RequestCellEvent getRequestCellEvent() {
		return RequestCell;

	}
	
	//event handlers
	public void onAStarRequestCellEvent(String cellName) {
		RequestCell.fire(cellName);
	}
	public void onCellLoaded(PathCell cell) {
		pathfinder.addCell(cell);
	}
}