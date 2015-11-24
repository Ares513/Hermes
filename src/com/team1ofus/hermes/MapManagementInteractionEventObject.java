package com.team1ofus.hermes;
import java.util.*;
public class MapManagementInteractionEventObject { 
	public MapManagementInteractionEventObject() {
	}
	
	private List<IMapManagementInteractionListener> listeners = new ArrayList<IMapManagementInteractionListener>();
	
	public void addListener(IMapManagementInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void onPathComplete(ArrayList<CellPoint> directions) {
		
		for(IMapManagementInteractionListener UL : listeners){
			UL.onAStarPathCompleteEvent(directions);
			DebugManagement.writeNotificationToLog("onAStarPathCompleteEvent Called again");
			
		}
	
	}
}