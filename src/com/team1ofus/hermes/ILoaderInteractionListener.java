package com.team1ofus.hermes;

import java.util.ArrayList;


public interface ILoaderInteractionListener {
	void selectionMade(PathCell selection, ArrayList<PathCell> allCells);
}