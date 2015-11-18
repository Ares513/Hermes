package com.team1ofus.hermes;

import javax.swing.*;


public class UIManagement implements IHumanInteractionListener, IAStarInteractionListener{
	HermesUI window;
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
		
	}

	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
}
