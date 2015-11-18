package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;


/*
 * Description:    	Will handle how the data is managed
 
Attributes
 
Methods
loadAllCells                      Cell                Will load cells within a map
loadCellPath                      Cell                Will load cell path of generated path 
loadInstructionDictionary                       Will load the instructions of generated path 
InstructionDictionary                              Stores the path directions based on algorithm output
 */

public class DataManagement implements RequestCellListener {
	public class CellLoadedEvent extends EventObject {
		private List<ICellLoadedListener> listeners = new ArrayList<ICellLoadedListener>(); //list of registered listeners
		CellLoadedEvent(Object source) {
			super(source);
		}
		public synchronized void fire(Cell cell) {
			for (ICellLoadedListener l : listeners) {
				l.onCellLoaded(cell);
			}
		}
		public synchronized void registerListener(ICellLoadedListener aListener) {
			listeners.add(aListener);
		}
	}
	
	public DataManagement() {
		
	}
	public void onRequestCellEvent(String cellName) {
		
	}
}