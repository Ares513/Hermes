package com.team1ofus.hermes;

import javax.swing.*;


public class UIManagement implements HermesUIInterface,AStarPathCompleteListener{
	HermesUI window;
	HermesUIManagementEvent managementEvent; 
	public UIManagement(){
		initialize();    
		managementEvent = new HermesUIManagementEvent(); 
	}
	
	public JFrame frame; 

	@Override
	public void thereWasAClick(){
		System.out.println("Holy Shit There Was A Click");
		managementEvent.doManagementEvent();
	}

	
	void initialize() {
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize();
		
	}
	
	public void onAStarPathCompleteEvent(CellPoint[] directions) {
		window.drawPath(directions);
	}
}
