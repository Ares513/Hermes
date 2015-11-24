package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;


public class UIManagement implements IHumanInteractionListener, IMapManagementInteractionListener, ILoaderInteractionListener {
	HermesUI window;
	Loader loader;
	Point first;
	Point second;
	public UIManagementInteractionEventObject events;
	private ArrayList<PathCell> allCells;
	public UIManagement(ArrayList<PathCell> allCells) {
		events = new UIManagementInteractionEventObject(); 
		this.allCells = allCells;
		loader = new Loader(allCells);  
		loader.events.addChooseListener(this);
	}
	
	public JFrame frame; 

	public void begin(int selectedIndex) {
		window = new HermesUI(allCells.get(selectedIndex));
		window.humanInteractive.addListener(this);
	
		
	}
	
	public void onAStarPathCompleteEvent(ArrayList<CellPoint> directions) {
		DebugManagement.writeNotificationToLog("Path received, contents "  + directions);

		window.getPathPanel().drawPath(directions);
	}

	@Override
	public void onTileClicked(int x, int y) {
		DebugManagement.writeNotificationToLog("Click processed at " + x + " , " + y);
		if(first == null) {
			first = new Point(x, y);
			DebugManagement.writeNotificationToLog("First point processed at " + x + " , " + y);
			
		} else if(second == null) {
			DebugManagement.writeNotificationToLog("Second point processed at " + x + " , " + y);
			second = new Point(x,y);
			DebugManagement.writeNotificationToLog("Two points selected. Sending doPath to listeners.");
			DebugManagement.writeNotificationToLog("Path points" + first + " , " + second);
			events.doPathReady(0, first, second);
			first = null;
			second = null;
		}
	}

	@Override
	public void selectionMade(int selection, ArrayList<PathCell> allCells) {
		// TODO Auto-generated method stub
		
		events.doWindowReady(selection, allCells);
		begin(selection);
		
	}



}
