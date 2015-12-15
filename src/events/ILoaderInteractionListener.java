package events;

import java.util.ArrayList;

import pathing.PathCell;

//TODO Comments1
public interface ILoaderInteractionListener {
	void selectionMade(int selection, ArrayList<PathCell> allCells);
}
