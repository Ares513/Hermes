package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;


<<<<<<< HEAD

public class UIManagement implements IHumanInteractionListener, ILoaderInteractionListener{
=======
public class UIManagement implements IHumanInteractionListener, IMapManagementInteractionListener {
>>>>>>> refs/heads/HermesDev
	HermesUI window;
	Loader loadUI;
	Point first;
	Point second;
	public UIManagementInteractionEventObject events;
	private ArrayList<PathCell> allCells;
	public UIManagement(ArrayList<PathCell> allCells) {
		events = new UIManagementInteractionEventObject(); 
		this.allCells = allCells;
		  

	}
	
	public JFrame frame; 

<<<<<<< HEAD
	void begin() {
		loadUI = new Loader(allCells);
		loadUI.events.addChooseListener(this);
=======
	public void begin() {
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize(allCells.get(0));
>>>>>>> refs/heads/HermesDev
		
	}
	
	public void OnpathComplete(ArrayList<CellPoint> directions) {
		window.drawPath(directions);
	}

	@Override
	public void onTileClicked(int x, int y) {
		// TODO Auto-generated method stub
		if(first == null) {
			first = new Point(x, y);
		} else if(second == null) {
			second = new Point(x,y);
		} if (first != null && second != null) {
			System.out.println("calling first event");
			events.doPathReady(0, first, second);
			first = null;
			second = null;
		}
	}
<<<<<<< HEAD
/*
	@Override
	public void onAStarRequestCellEvent(String cellName) {
		// TODO Auto-generated method stub
		
	}
*/
	@Override
	public void selectionMade(PathCell selection, ArrayList<PathCell> allCells) {
		// TODO Auto-generated method stub
		window = new HermesUI();
		window.humanInteractive.addListener(this);
		window.initialize(selection);

	}
=======

>>>>>>> refs/heads/HermesDev


}
