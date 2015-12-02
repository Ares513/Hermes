package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//TODO Comments1
public class UIManagementInteractionEventObject { 
	public UIManagementInteractionEventObject() {
	}
	
private List<IUIManagementInteractionListener> managementListeners = new ArrayList<IUIManagementInteractionListener>();

public void addManagementListener(IUIManagementInteractionListener toAdd) {

	managementListeners.add(toAdd);
	}
public void doPathReady(CellPoint first, CellPoint second){
	
	for(IUIManagementInteractionListener UL : managementListeners){
		UL.onPathReady(first, second);
		DebugManagement.writeNotificationToLog("both_Have_Been_Clicked");
		}	

	}
public void doWindowReady(ArrayList<PathCell> allCells){
	
	for(IUIManagementInteractionListener UL : managementListeners){
		 UL.onWindowReady(allCells);
	}	

}
}