package com.team1ofus.hermes;

import java.util.ArrayList;

public interface IMapManagementInteractionListener {
	void onAStarPathCompleteEvent(ArrayList<CellPoint> arrayList);
}