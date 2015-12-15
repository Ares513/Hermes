package pathing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

import core.DebugManagement;
import core.SEVERITY_LEVEL;
import events.IAStarInteractionListener;
import events.IUIManagementInteractionListener;
import events.MapManagementInteractionEventObject;

//TODO Comments1
//Write comments for events in MapManagement/summary of what MapManagment does
public class MapManagement implements IUIManagementInteractionListener, IAStarInteractionListener {
	public MapManagementInteractionEventObject events = new MapManagementInteractionEventObject();
	AStar pathfindingSystem;
	ArrayList<PathCell> cells = new ArrayList<PathCell>();
	boolean suppressOutEvents = false; //true when doing internal operations that require A* event handling.
	ArrayList<TempPathResult> filterResults = new ArrayList<TempPathResult>();
	public MapManagement(ArrayList<PathCell> cells) {
		this.cells = cells;
	}
	
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onAStarPathCompleteEvent(ArrayList<CellPoint> directions, int cost) {
		if(!suppressOutEvents) {
			events.doPathComplete(directions, cost);
			
		}
	}

	@Override
	public void onPathReady(CellPoint first, CellPoint second, AStarConfigOptions configs) {
		if(pathfindingSystem == null) {//how does this even happen?
			assert(false);
		}
		if(first == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.ERROR, "First point is null in onPathReady!");
		}
		if(second == null) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.ERROR, "Second point is null in onPathReady!");
				
		}
		DebugManagement.writeNotificationToLog("onPathReady event called in MapManagement. Values " + first.toString() + second.toString());
		pathfindingSystem = new AStar(cells, configs);
		pathfindingSystem.events.registerListener(this);
		pathfindingSystem.getPath(first, second, false);
	}
	public void onWindowReady(ArrayList<PathCell> loaded) {
		//dummy event, protecting future
	}

	@Override
	public void onFindRequestReady(CellPoint first, String filter, AStarConfigOptions configs) {
		//Finds the shortest path to the nearest bathroom.
		DebugManagement.writeNotificationToLog(filter);
		ArrayList<CellPoint> targets = new ArrayList<CellPoint>(); //build a list of points that contain the filter.
		for(PathCell c : cells) {
			//Iterate over cells, checking locations at each.
			for(LocationNameInfo l : c.getLocationNameInfo()) {
				//locations of the current cell
				for(String s : l.getNames()) {
					//check for contains.
					if(s.contains(filter)) {
						targets.add(new CellPoint(c.getName(), l.getPoint()));
						continue;
						//duplicates need not apply
					}
				}
			}
		}
		
		//okay, so now we have all of our targets. No problem.
		//Generate a path for each of them, storing them in the handy dandy TempPathResult.
		
		for(CellPoint c : targets) {
			pathfindingSystem = new AStar(cells, configs);
			pathfindingSystem.events.registerListener(this);
			pathfindingSystem.getPath(first, c, true);
		}
		Collections.sort(filterResults);
		if(filterResults.size() > 0) {
			onAStarPathCompleteEvent(filterResults.get(0).directions, filterResults.get(0).cost);
			filterResults.clear();
		}
	}
	class TempPathResult implements Comparable<TempPathResult> {
		ArrayList<CellPoint> directions;
		int cost;
		public TempPathResult(ArrayList<CellPoint> directions, int cost) {
			this.directions = directions;
			this.cost = cost;
		}
		@Override
		public int compareTo(TempPathResult o) {
		
			return cost - o.cost;
		}

	}
	@Override
	public void onFilterStepCompleteEvent(ArrayList<CellPoint> directions, int cost) {
		// TODO Auto-generated method stub
		if(directions != null) {
			filterResults.add(new TempPathResult(directions, cost));
			
		}
		
	}
}
