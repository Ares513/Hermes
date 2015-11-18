package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class UIManagementInteractionEventObject { 
	public UIManagementInteractionEventObject() {
	}
	
public List<IUIManagementInteractionListener> managementListeners = new ArrayList<IUIManagementInteractionListener>();

public void addManagementListener(IUIManagementInteractionListener toAdd){
	managementListeners.add(toAdd);
	System.out.println(managementListeners.size());
	}
public void doPathReady(int cellIndex, Point first, Point second){
	System.out.println("listener Called");
	System.out.println(managementListeners.size());
	for(IUIManagementInteractionListener UL : managementListeners){
		System.out.println("about to send notification");
		UL.onPathReady(cellIndex, first, second);
		System.out.println("I sent the notification out");
		}	

	}
}