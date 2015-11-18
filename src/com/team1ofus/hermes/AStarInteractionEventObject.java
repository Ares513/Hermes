package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class AStarInteractionEventObject {
	private List<IAStarRequestCellListener> listeners = new ArrayList<IAStarRequestCellListener>(); //list of registered listeners
	public AStarInteractionEventObject() {
		// TODO Auto-generated constructor stub
	}
	public void fire(String cellName) {
		for (IAStarRequestCellListener l : listeners) {
			l.onAStarRequestCellEvent(cellName);
		}
	}
	public void registerListener(IAStarRequestCellListener aListener) {
		listeners.add(aListener);
	}
}
