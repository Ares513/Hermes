package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

//TODO Comments1
//Write comments for events in MapManagement/summary of what MapManagment does
public class MapManagement implements IUIManagementInteractionListener, IAStarInteractionListener {
	MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells = new ArrayList<PathCell>();
	public MapManagement() {
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
	public void onPathReady(int cellIndex, Point first, Point second) {
		if(pathfindingSystem == null) {//how does this even happen?
			assert(false);
		}
		pathfindingSystem = new AStar(cells);
		pathfindingSystem.events.registerListener(this);
		CellPoint a = new CellPoint(cells.get(cellIndex).getName(), first);
		CellPoint b = new CellPoint(cells.get(cellIndex).getName(), second);
		pathfindingSystem.getPath(a,b);
	}
	public void onWindowReady(int cellIndex, ArrayList<PathCell> loaded) {
		pathfindingSystem = new AStar(loaded);
		pathfindingSystem.events.registerListener(this);
		cells.addAll(loaded);
	}
}
