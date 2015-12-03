package events;

import java.util.ArrayList;

import pathing.CellPoint;

//TODO Comments1
public interface IMapManagementInteractionListener {
	void doPathComplete(ArrayList<CellPoint> directions, int cost);
	
}
