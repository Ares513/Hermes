package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class UIManagementInteractionEventObject { 
	public UIManagementInteractionEventObject() {
	}
	
private List<IUIManagementInteractionListener> managementListeners = new ArrayList<IUIManagementInteractionListener>();

public void addManagementListener(IUIManagementInteractionListener toAdd){
	managementListeners.add(toAdd);
	}
public void doPathReady(int cellIndex, Point first, Point second){
	
	for(IUIManagementInteractionListener UL : managementListeners){
		UL.onPathReady(cellIndex, first, second);
		}	

	}
}