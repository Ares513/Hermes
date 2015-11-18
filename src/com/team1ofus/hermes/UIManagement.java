package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;


public class UIManagement implements IHumanInteractionListener, IAStarInteractionListener{
	HermesUI window;
	Point first;
	Point second;
	public UIManagementInteractionEventObject events;
	private ArrayList<PathCell> allCells;
	public UIManagement(ArrayList<PathCell> allCells) {
		events = new UIManagementInteractionEventObject(); 
		this.allCells = allCells;
		begin();    

	}
	
	public JFrame frame; 

	void begin() {
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize(allCells.get(0));
		
	}
	
	public void onAStarPathCompleteEvent(CellPoint[] directions) {
		window.drawPath(directions);
	}

	@Override
	public void onTileClicked(int x, int y) {
		// TODO Auto-generated method stub
		if(first == null) {
			first = new Point(x, y);
			
		} else if(second == null) {
			second = new Point(x,y);
		} else if (first != null && second != null) {
			events.doPathReady(0, first, second);
			first = null;
			second = null;
		}
	}

	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
}
