package events;

import pathing.CellPoint;

//TODO Comments1

public interface AStarPathCompleteListener {
	void onAStarPathCompleteEvent(CellPoint[] directions);
}
