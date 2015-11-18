
package com.team1ofus.hermes;
import java.util.ArrayList;
public interface IAStarInteractionListener {
	void onAStarRequestCellEvent(String cellName);
	void onAStarPathCompleteEvent(ArrayList<CellPoint> directions);
}

