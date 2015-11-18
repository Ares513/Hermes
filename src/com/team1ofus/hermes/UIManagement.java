package com.team1ofus.hermes;

import java.awt.Point;

import javax.swing.*;


public class UIManagement implements IHumanInteractionListener, IAStarInteractionListener{
	HermesUI window;
	Point first;
	Point second;
	public UIManagementInteractionEventObject events;
	public UIManagement(){
		begin();    
		events = new UIManagementInteractionEventObject(); 
	}
	
	public JFrame frame; 

	void begin() {
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize();
		
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
			events.doPathReady(first, second);
			first = null;
			second = null;
		}
	}

	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
}
