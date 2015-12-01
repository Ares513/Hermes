package com.team1ofus.hermes;

import java.util.ArrayList;

//TODO Comments1

public class LoaderInteractionEventObject {
	
	public ArrayList<ILoaderInteractionListener> listeners = new ArrayList<ILoaderInteractionListener>();
	public synchronized void addChooseListener(ILoaderInteractionListener input) {
		listeners.add(input);
	}
	public synchronized void removeChooseListener(ILoaderInteractionListener input) {
		listeners.remove(input);
	}
	public void selectionMade(int selection, ArrayList<PathCell> allCells) {
		for(ILoaderInteractionListener listener : listeners) {
			listener.selectionMade(selection, allCells);
		}
	}
	
	public LoaderInteractionEventObject() {
	}
}
