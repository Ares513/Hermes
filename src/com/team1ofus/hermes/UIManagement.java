package com.team1ofus.hermes;

public class UIManagement implements AStarPathCompleteListener{
	HermesUI window;
	public UIManagement() {
		initialize();
	}
	
	void initialize() {
		window = new HermesUI();
	}
	
	public void onAStarPathCompleteEvent(CellPoint[] directions) {
		window.drawPath(directions);
	}
}
