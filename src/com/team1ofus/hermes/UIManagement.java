package com.team1ofus.hermes;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

//This class creates the UI and passes on events that will trigger changes in the UI
public class UIManagement implements IHumanInteractionListener, IMapManagementInteractionListener, ILoaderInteractionListener, ISearchReadyListener {
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
		locationNameInfoRecords = new ArrayList<Record>();
		allLocationNameInfos = new ArrayList<LocationNameInfo>();
		for (PathCell aCell : allCells){
			allLocationNameInfos.addAll(aCell.getLocationNameInfo());
			for (LocationNameInfo lni : aCell.getLocationNameInfo()){
				for(String s : lni.getNames()) {
					locationNameInfoRecords.add(new Record(s, aCell.getName()));
					
				}
			}
		}
		
		for (LocationNameInfo lni : allLocationNameInfos){
		
			for (String str : lni.getNames()){
				
			}
		}
	}
	
	public JFrame frame; 
	public void begin(int selectedIndex) {
		window = new HermesUI(allCells.get(selectedIndex), locationNameInfoRecords);
		window.humanInteractive.addListener(this);//should be refactored
		window.getSearchEvents().addListener(this);
	}
	
	public void doPathComplete(ArrayList<CellPoint> directions) {
		DebugManagement.writeNotificationToLog("Path received, contents "  + directions);
		window.getPathPanel().drawPath(directions);
		ArrayList<String> listOfDirections = printList.parseDirections(directions);
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
	
	//runs when user picks a destination point using the search feature
	@Override
	public void onSearchReady(Record start, Record destination){
		DebugManagement.writeNotificationToLog("Entering function onSearchReady. \nStartRecord: " + start.getVal() + "\nDestRecord: " + destination.getVal());
		Point startPoint = null;
		Point destPoint = null;
		for (PathCell pc : allCells){
			if (pc.getName().equals(start.getCellName())){
				startPoint = locationRecordToPoint(pc, start);
				window.getPointPane().setFirst(startPoint);
				
			}
		}
		for (PathCell pc : allCells){
			if (pc.getName().equals(destination.getCellName())){
				destPoint = locationRecordToPoint(pc, destination);
				window.getPointPane().setSecond(destPoint);
			}
		}
		events.doPathReady(0, startPoint, destPoint);
		first = null;
		second = null;
	}
	/* converts a record output by search into a point to send to A*
	 * Will need to be refactored to return a cellPoint soon (before multimap pathing is done).
	 * */
	public Point locationRecordToPoint(PathCell pc, Record r){
		for (LocationNameInfo lni : pc.getLocationNameInfo()){
			if (lni.getNames().contains(r.getVal())){
				return lni.getPoint();
			}
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "Location Record not found. Returning Null. This will almost certainly cause a crash.");
		return null;
	}

	@Override
	public void selectionMade(int selection, ArrayList<PathCell> allCells) {
		// TODO Auto-generated method stub
		events.doWindowReady(selection, allCells);
		begin(selection);
		
	}
}
