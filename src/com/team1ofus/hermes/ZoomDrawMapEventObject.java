package com.team1ofus.hermes;

import java.util.ArrayList;
import java.util.List;

public class ZoomDrawMapEventObject {
private List<IZoomCellRenderListener> listeners = new ArrayList<IZoomCellRenderListener>();
	
	public void addListener(IZoomCellRenderListener toAdd){
		listeners.add(toAdd);
		}
	public void doZoomPass(double scale){
		
		for(IZoomCellRenderListener ZL : listeners){
			ZL.onZoomPass(scale);
			System.out.println("zoom pass listeners called");
		}
	
	}

}