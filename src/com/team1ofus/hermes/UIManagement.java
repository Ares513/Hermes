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
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize(allCells.get(selectedIndex));
		
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
	public void selectionMade(int selection, ArrayList<PathCell> allCells) {
		// TODO Auto-generated method stub
		
		events.doWindowReady(selection, allCells);
		begin(selection);
		
	}



}
