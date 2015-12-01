package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

//This class creates the UI and passes on events that will trigger changes in the UI
public class UIManagement implements IHumanInteractionListener, IMapManagementInteractionListener, ILoaderInteractionListener {
	HermesUI window;
	Loader loader;
	Point first;
	Point second;
	PrintDirections printList; 
	public UIManagementInteractionEventObject events;
	private ArrayList<PathCell> allCells;
	private ArrayList<LocationNameInfo> allLocationNameInfos;
	private ArrayList<Record> locationNameInfoRecords; //exists just for search autocomplete (the 'completely' library)
	
	public UIManagement(ArrayList<PathCell> allCells) {
		events = new UIManagementInteractionEventObject(); 
		this.allCells = allCells;
		loader = new Loader(allCells);  
		loader.events.addChooseListener(this);
		printList = new PrintDirections(); 
		allLocationNameInfos = new ArrayList<LocationNameInfo>();
		for (PathCell aCell : allCells){
			allLocationNameInfos.addAll(aCell.getLocationNameInfo());
		}
		locationNameInfoRecords = new ArrayList<Record>();
		String cellName;
		for (LocationNameInfo lni : allLocationNameInfos){
			cellName = lni.getCellName();
			for (String str : lni.getNames()){
				locationNameInfoRecords.add(new Record(str, cellName));
			}
		}
	}
	
	public JFrame frame; 
	public void begin(int selectedIndex) {
		window = new HermesUI(allCells.get(selectedIndex));
		window.humanInteractive.addListener(this);
	}
	
	public void doPathComplete(ArrayList<CellPoint> directions) {
		DebugManagement.writeNotificationToLog("Path received, contents "  + directions);
		window.getPathPanel().drawPath(directions);
		ArrayList<String> listOfDirections = printList.parseDirections(directions);
		//window.ListOfDirections = listOfDirections; 
		window.directionText(listOfDirections);
	}

	@Override
	public void onTileClicked(int x, int y) {
		DebugManagement.writeNotificationToLog("Click processed at " + x + " , " + y);
		if(first == null) {
			first = new Point(x, y);
			window.directionsTextPane.setText(""); // clears directions pane after first click. 
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
