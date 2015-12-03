package events;
import java.util.*;

import core.DebugManagement;
import pathing.CellPoint;

//TODO Comments1
public class MapManagementInteractionEventObject { 
	public MapManagementInteractionEventObject() {
	}
	
	private List<IMapManagementInteractionListener> listeners = new ArrayList<IMapManagementInteractionListener>();
	
	public void addListener(IMapManagementInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void doPathComplete(ArrayList<CellPoint> directions, int cost) {
		
		for(IMapManagementInteractionListener UL : listeners){
			UL.doPathComplete(directions, cost);
			DebugManagement.writeNotificationToLog("onAStarPathCompleteEvent Called again");
			
		}
	
	}
}