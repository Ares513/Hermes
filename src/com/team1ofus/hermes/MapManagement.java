package com.team1ofus.hermes;

import java.util.ArrayList;

public class MapManagement implements IAStarInteractionListener {
	MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells;
	public MapManagement(ArrayList<PathCell> dummyList) {
		pathfindingSystem = new AStar(dummyList);
		cells = dummyList;
	}
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAStarPathCompleteEvent(CellPoint[] directions) {
		// TODO Auto-generated method stub
		events.pathComplete(directions);
	}
	
}
