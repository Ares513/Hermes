package events;

import java.awt.Point;
import java.util.ArrayList;

import pathing.CellPoint;
import pathing.PathCell;

//TODO Comments1
public interface IUIManagementInteractionListener { 
	void onPathReady(CellPoint first, CellPoint second);
	void onWindowReady(ArrayList<PathCell> loaded);
	void onFindRequestReady(CellPoint first, String filter);
}