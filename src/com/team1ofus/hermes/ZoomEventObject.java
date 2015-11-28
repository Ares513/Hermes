package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.List;

public class ZoomEventObject {
	
private List<IZoomInteractionListener> listeners = new ArrayList<IZoomInteractionListener>();
	
	public void addListener(IZoomInteractionListener toAdd){
		listeners.add(toAdd);
		}
	public void doZoom(double scale){
		
		for(IZoomInteractionListener ZL : listeners){
			ZL.onZoomEvent(scale);
			System.out.println("zoom listeners called");
		}
	
	}
}


