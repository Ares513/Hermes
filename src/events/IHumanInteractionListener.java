package events;

import pathing.CellPoint;

//TODO Comments1
public interface IHumanInteractionListener { 
	void onTileClicked(CellPoint input);
	void findNearestLocation(CellPoint start, String filter);
}