
package events;

import java.util.ArrayList;

import pathing.CellPoint;

public interface IAStarInteractionListener {
	void onAStarRequestCellEvent(String cellName);
	void onAStarPathCompleteEvent(ArrayList<CellPoint> directions, int cost);
	void onFilterStepCompleteEvent(ArrayList<CellPoint> directions, int cost);
}

