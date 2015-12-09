package events;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import core.DebugManagement;
import pathing.CellPoint;

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
	public void AStarCompletePath(ArrayList<CellPoint> Path, int i){
		DebugManagement.writeNotificationToLog("A* path calculation complete; notifying listeners."); 
		for(IAStarInteractionListener l : listeners){
			l.onAStarPathCompleteEvent(Path, i);
		}
	}
	public void FilterStepComplete(ArrayList<CellPoint> finalPath, int cost) {
		for(IAStarInteractionListener l : listeners){
			l.onFilterStepCompleteEvent(finalPath, cost);
		}
	}
}
