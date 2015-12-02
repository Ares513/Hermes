package com.team1ofus.hermes;
import java.util.*;

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