package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.List;

public class HermesUIManagementEvent { 
	public HermesUIManagementEvent() {
	}
	
private List<HermesUIManagementInterface> managementListeners = new ArrayList<HermesUIManagementInterface>();

public void addManagementListener(HermesUIManagementInterface toAdd){
	managementListeners.add(toAdd);
	}
public void doManagementEvent(){
	System.out.println("clicked");
	
	for(HermesUIManagementInterface UL : managementListeners){
		UL.managementEvent();
		System.out.println("Management listeners called");
		}	

	}
}