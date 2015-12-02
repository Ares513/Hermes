package com.team1ofus.hermes;
import java.util.*;

//TODO Comments1
public class HumanInteractionEventObject { 
	public HumanInteractionEventObject() {
	}
	
	private List<IHumanInteractionListener> listeners = new ArrayList<IHumanInteractionListener>();
	//DebugID is to help narrow down who is adding listeners
	public void addListener(IHumanInteractionListener toAdd, String debugID){
		DebugManagement.writeNotificationToLog("HumanInteractionListener added, name " + debugID);
		listeners.add(toAdd);
		}
	public void doClick(CellPoint clicked){
		
		for(IHumanInteractionListener UL : listeners){
			DebugManagement.writeNotificationToLog("Calling tileClicked with value " + clicked.getCellName() + " at " + clicked.getPoint().toString());
			UL.onTileClicked(clicked);
		}
	
	}
}