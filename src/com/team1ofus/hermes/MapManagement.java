package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

public class MapManagement implements IUIManagementInteractionListener, IAStarInteractionListener {
	MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells;
	public MapManagement() {

	}
	
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAStarPathCompleteEvent(CellPoint[] directions) {
		// TODO Auto-generated method stub
		events.onPathComplete(directions);
	}
	@Override
	public void onPathReady(int cellIndex, Point first, Point second) {
		// TODO Auto-generated method stub
		pathfindingSystem.getPath(0, first, 0, second);
	}
	public void onWindowReady(int cellIndex, ArrayList<PathCell> loaded) {
		pathfindingSystem = new AStar(loaded);
		cells = loaded;
	}
}
