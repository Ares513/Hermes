package com.team1ofus.hermes;
import java.util.*;

//TODO Comments1
public class HumanInteractionEventObject { 
	public HumanInteractionEventObject() {
	}
	
	private List<IHumanInteractionListener> listeners = new ArrayList<IHumanInteractionListener>();
	
	public void addListener(IHumanInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void doClick(CellPoint clicked){
		
		for(IHumanInteractionListener UL : listeners){
			DebugManagement.writeNotificationToLog("Calling tileClicked with value " + clicked.toString());
			UL.onTileClicked(clicked);
		}
	
	}
}