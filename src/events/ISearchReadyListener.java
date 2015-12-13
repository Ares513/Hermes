package events;

import pathing.AStarConfigOptions;
import ui.Record;

public interface ISearchReadyListener {
	void onSearchReady(Record start, Record destination, AStarConfigOptions configs);
}
