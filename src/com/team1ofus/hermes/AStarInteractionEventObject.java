package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

//TODO Comments1

public class AStarInteractionEventObject {
	private List<IAStarInteractionListener> listeners = new ArrayList<IAStarInteractionListener>(); //list of registered listeners
	public AStarInteractionEventObject() {
		// TODO Auto-generated constructor stub
	}
	public void onRequestCell(String cellName) {
		for (IAStarInteractionListener l : listeners) {
			l.onAStarRequestCellEvent(cellName);
		}
	}
	public void registerListener(IAStarInteractionListener aListener) {
		listeners.add(aListener);
	}
	public void AStarCompletePath(ArrayList<CellPoint> Path){

		for(IAStarInteractionListener l : listeners){
			l.onAStarPathCompleteEvent(Path);
		}
	}
}
