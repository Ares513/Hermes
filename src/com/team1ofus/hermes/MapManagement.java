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
	public void onAStarPathCompleteEvent(ArrayList<CellPoint> directions) {
		// TODO Auto-generated method stub
		//events.onPathComplete(directions);
		//skip a* entirely
		CellPoint[] dummyData;
		dummyData = new CellPoint[5];
		dummyData[0] = new CellPoint("test", new Point(1, 1));
		dummyData[1] = new CellPoint("test", new Point(2, 1));
		dummyData[2] = new CellPoint("test", new Point(3, 1));
		dummyData[3] = new CellPoint("test", new Point(4, 1));
		dummyData[4] = new CellPoint("test", new Point(5, 1));
		events.onPathComplete(directions);
	}
	//onAStarPathCompleteEvent
	//onAStarPathCompleteEvent
	
	@Override
	public void onPathReady(int cellIndex, Point first, Point second) {
		if(pathfindingSystem == null) {
			//how does this even happen?
			assert(false);
		}
		onAStarPathCompleteEvent(pathfindingSystem.getPath(cellIndex, first, cellIndex, second)); //force fire event
		// TODO Auto-generated method stub
		//pathfindingSystem.getPath(0, first, 0, second);
		//
	}
	public void onWindowReady(int cellIndex, ArrayList<PathCell> loaded) {
		pathfindingSystem = new AStar(loaded);
		pathfindingSystem.events.registerListener(this);
		
		cells = loaded;
	}
}
