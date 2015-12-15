package events;

import pathing.AStarConfigOptions;
import pathing.CellPoint;

//TODO Comments1
public interface IHumanInteractionListener { 
	void onTileClicked(CellPoint input);
	void findNearestLocation(CellPoint start, String filter, AStarConfigOptions configs);
}