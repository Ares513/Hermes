
package com.team1ofus.hermes;

public interface IAStarInteractionListener {
	void onAStarRequestCellEvent(String cellName);
	void onAStarPathCompleteEvent(CellPoint[] directions);
}

