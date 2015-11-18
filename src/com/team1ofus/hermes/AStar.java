package com.team1ofus.hermes;

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
}