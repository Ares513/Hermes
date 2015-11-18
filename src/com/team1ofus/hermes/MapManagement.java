package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

public class MapManagement implements IUIManagementInteractionListener, IAStarInteractionListener {
	UIManagement UIManage; 
	MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells;
	
	
	public MapManagement(ArrayList<PathCell> dummyList) {
		pathfindingSystem = new AStar(dummyList);
		cells = dummyList;
		UIManage = new UIManagement(cells);
		begin();
	}
	private void begin(){ 
		UIManage.events.addManagementListener(this);
		UIManage.begin();
		//while(true){}
	}
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAStarPathCompleteEvent(ArrayList<CellPoint> directions) {
		// TODO Auto-generated method stub
		events.doPathComplete(directions);
	}
	@Override
	public void onPathReady(int cellIndex, Point first, Point second) {
		// TODO Auto-generated method stub
		System.out.println("about to compute path");
		pathfindingSystem.getPath(0, first, 0, second);
		System.out.println("pathfound");
	}
	
}
