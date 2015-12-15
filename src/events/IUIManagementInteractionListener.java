package events;

import java.util.ArrayList;

import pathing.AStarConfigOptions;
import pathing.CellPoint;
import pathing.PathCell;

//TODO Comments1
public interface IUIManagementInteractionListener { 
	void onPathReady(CellPoint first, CellPoint second, AStarConfigOptions configs);
	void onWindowReady(ArrayList<PathCell> loaded);
	void onFindRequestReady(CellPoint first, String filter, AStarConfigOptions configs);
}