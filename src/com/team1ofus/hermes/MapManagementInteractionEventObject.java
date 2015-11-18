package com.team1ofus.hermes;
import java.util.*;
//import java.util.ArrayList;
public class MapManagementInteractionEventObject { 
	public MapManagementInteractionEventObject() {
	}
	
	private List<IMapManagementInteractionListener> listeners = new ArrayList<IMapManagementInteractionListener>();
	
	public void addListener(IMapManagementInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void doPathComplete(ArrayList<CellPoint> directions) {
		
		for(IMapManagementInteractionListener UL : listeners){
			UL.onPathComplete(directions);
			
		}
	
	}
}