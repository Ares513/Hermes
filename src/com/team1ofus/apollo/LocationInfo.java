package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class LocationInfo implements Serializable {
	private String cellReference;
	private String entryPoint; //Name of the entry point on the other map
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> aliases = new ArrayList<String>(); //names it can be referred to as
	private Point loc; //location on screen
	
	public LocationInfo(String first, Point location, String cellID, String entryPoint) {
		loc = location;
		aliases.add(first);
		cellReference = cellID;
		this.entryPoint = entryPoint;
		com.team1ofus.hermes.DebugManagement.writeNotificationToLog("Created a new location with " + cellID +  " reference with " + first + " at " + location + " entry point " + entryPoint);
	}
	public LocationInfo(String first, Point location) {
		loc = location;
		aliases.add(first);
		com.team1ofus.hermes.DebugManagement.writeNotificationToLog("Created a new location with no reference with " + first + " at " + location);
	}
	public void addAlias(String name) {
		//TODO: prevent duplicate names.
		aliases.add(name);
	}
	public void setLocation(Point location) {
		loc = location;
		
	}
	public Point getLocation() {
		return loc;
	}
	public String getCellReference() {
		return cellReference;
	}
	public String getEntryPoint() {
		return entryPoint;
	}
	public ArrayList<String> getAliases() {
		return aliases;
	}
}
