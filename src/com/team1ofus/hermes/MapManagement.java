package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

//TODO Comments1
//Write comments for events in MapManagement/summary of what MapManagment does
public class MapManagement implements IUIManagementInteractionListener, IAStarInteractionListener {
	MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells = new ArrayList<PathCell>();
	public MapManagement(ArrayList<PathCell> cells) {
		this.cells = cells;
	}
	
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onAStarPathCompleteEvent(ArrayList<CellPoint> directions) {
		events.doPathComplete(directions);
	}

	@Override
	public void onPathReady(CellPoint first, CellPoint second) {
		if(pathfindingSystem == null) {//how does this even happen?
			assert(false);
		}
		DebugManagement.writeNotificationToLog("onPathReady event called in MapManagement. Values " + first.toString() + second.toString());
		pathfindingSystem = new AStar(cells);
		pathfindingSystem.events.registerListener(this);
		pathfindingSystem.getPath(first, second);
	}
	public void onWindowReady(ArrayList<PathCell> loaded) {
		//dummy event, protecting future
	}
}
