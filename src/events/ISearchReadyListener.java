package events;

import ui.Record;

public interface ISearchReadyListener {
	void onSearchReady(Record start, Record destination);
}
