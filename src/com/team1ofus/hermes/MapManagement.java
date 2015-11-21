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
		//events.onPathComplete(directions);
		//skip a* entirely
		CellPoint[] dummyData;
		dummyData = new CellPoint[4];
		dummyData[0] = new CellPoint("test", new Point(30, 15));
		dummyData[1] = new CellPoint("test", new Point(31, 15));
		dummyData[2] = new CellPoint("test", new Point(32, 15));
		dummyData[3] = new CellPoint("test", new Point(32, 14));
		dummyData[4] = new CellPoint("test", new Point(33, 15));
		events.onPathComplete(dummyData);
	}
	@Override
	public void onPathReady(int cellIndex, Point first, Point second) {
		if(pathfindingSystem == null) {
			//how does this even happen?
			assert(false);
		}
		// TODO Auto-generated method stub
		pathfindingSystem.getPath(0, first, 0, second);
	}
	public void onWindowReady(int cellIndex, ArrayList<PathCell> loaded) {
		pathfindingSystem = new AStar(loaded);
		cells = loaded;
	}
}
