package ui;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import events.IHumanInteractionListener;
import events.IMapManagementInteractionListener;
import events.ISearchReadyListener;
import events.UIManagementInteractionEventObject;
import pathing.CellPoint;
import pathing.LocationNameInfo;
import pathing.PathCell;

//This class creates the UI and passes on events that will trigger changes in the UI
public class UIManagement implements IHumanInteractionListener, IMapManagementInteractionListener, ISearchReadyListener {
	HermesUI window;
	CellPoint first;
	CellPoint second;
	PrintDirections printList; 
	public UIManagementInteractionEventObject events;
	private ArrayList<PathCell> allCells;
	private ArrayList<LocationNameInfo> allLocationNameInfos;
	private ArrayList<Record> locationNameInfoRecords; //exists just for search autocomplete (the 'completely' library)
	
	public UIManagement(ArrayList<PathCell> allCells) {
		events = new UIManagementInteractionEventObject(); 
		this.allCells = allCells;
		
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
		begin();
	}
	
	public JFrame frame; 
	public void begin() {
		window = new HermesUI(allCells, locationNameInfoRecords);
		window.humanInteractive.addListener(this, "HermesUI");
		window.getSearchEvents().addListener(this);
		events.doWindowReady(allCells);
	}
	
	public void doPathComplete(ArrayList<CellPoint> directions, int cost) {
		DebugManagement.writeNotificationToLog("Path received, contents "  + directions + " total cost " + Integer.toString(cost) + " units");
		window.drawPath(directions);
		ArrayList<Directions> listOfDirections = printList.parseDirections(directions);
		window.directionText(listOfDirections);
	}

	@Override
	public void onTileClicked(CellPoint input) {
		DebugManagement.writeNotificationToLog("Click processed at " + input.getPoint().x + " , " + input.getPoint().y);
		if(first == null) {
			first = input;
		//	window.directionsTextPane.setText(""); // clears directions pane after first click. 
			DebugManagement.writeNotificationToLog("First point processed at " + input.getPoint().x + " , " + input.getPoint().y);
			
		} else if(second == null) {
			DebugManagement.writeNotificationToLog("Second point processed at " +  input.getPoint().x + " , " + input.getPoint().y);
			second = input;
			DebugManagement.writeNotificationToLog("Two points selected. Sending doPath to listeners.");
			DebugManagement.writeNotificationToLog("Path points" + first + " , " + second);
			events.doPathReady(first, second);
			first = null;
			second = null;
		}
	}
	
	//runs when user picks a destination point using the search feature
	@Override
	public void onSearchReady(Record start, Record destination){
		first = null;
		second = null;
		DebugManagement.writeNotificationToLog("Entering function onSearchReady. \nStartRecord: " + start.getVal() + "\nDestRecord: " + destination.getVal());
		CellPoint startPoint = null;
		CellPoint destPoint = null;
		for (PathCell pc : allCells){
			if (pc.getName().equals(start.getCellName())){
				startPoint = locationRecordToPoint(pc, start);
				//startPoint.getPoint()
				//window.getPointPane().setFirst(startPoint.getPoint());
				
				
			}
		}
		for (PathCell pc : allCells){
			if(destination.getFields().contains(BootstrapperConstants.MALE_BATHROOM_IDENTIFIER)) {
				findNearestLocation(startPoint, "Male Bathroom");
				return; //we're done.
			}
			DebugManagement.writeNotificationToLog(destination.getFields().toString());
			DebugManagement.writeNotificationToLog(Boolean.toString(destination.getFields().contains(BootstrapperConstants.FEMALE_BATHROOM_IDENTIFIER)));
			if(destination.getFields().contains(BootstrapperConstants.FEMALE_BATHROOM_IDENTIFIER)) {
				findNearestLocation(startPoint, "Female Bathroom");
				return; //we're done.
			}
			if(destination.getFields().contains(BootstrapperConstants.UNISEX_BATHROOM_IDENTIFIER)) {
				findNearestLocation(startPoint, "Unisex Bathroom");
				return; //we're done.
			}
			if(destination.getFields().contains(BootstrapperConstants.BENCH_IDENTIFIER)) {
				findNearestLocation(startPoint, "Bench");
				return; //we're done.
			}
			if (pc.getName().equals(destination.getCellName())){
				destPoint = locationRecordToPoint(pc, destination);
				//window.getPointPane().setSecond(destPoint.getPoint());
			}
		}
		events.doPathReady(startPoint, destPoint);
		first = null;
		second = null;
	}
	/* converts a record output by search into a point to send to A*
	 * Will need to be refactored to return a cellPoint soon (before multimap pathing is done).
	 * */
	public CellPoint locationRecordToPoint(PathCell pc, Record r){
		for (LocationNameInfo lni : pc.getLocationNameInfo()){
			if (lni.getNames().contains(r.getVal())){
				return new CellPoint(pc.cellName, lni.getPoint());
			}
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.FATAL, "Location Record not found. Returning Null. This will almost certainly cause a crash.");
		return null;
	}

	@Override
	public void findNearestLocation(CellPoint start, String filter) {
		events.findNearestLocation(start, filter);
	}
}
