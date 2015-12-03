package events;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import core.DebugManagement;
import pathing.CellPoint;
import pathing.PathCell;

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
public void findNearestLocation(CellPoint start, String filter) {
	for(IUIManagementInteractionListener UL : managementListeners){
		 UL.onFindRequestReady(start, filter);
	}	
}
}