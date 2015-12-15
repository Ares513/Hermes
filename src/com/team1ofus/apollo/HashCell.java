package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Predicate;

import core.DebugManagement;
import core.SEVERITY_LEVEL;

public class HashCell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private String displayName;
	private static final long serialVersionUID = 8L;
	private HashMap<Point, TILE_TYPE> tiles = new HashMap<Point, TILE_TYPE>();
	//minimum required information
	private int fixedWidth;
	private int fixedHeight;
	private ArrayList<LocationInfo> listedLocations = new ArrayList<LocationInfo>(); //Specific locations, i.e fountain. No cell association variable if it doesn't associate to anything.
	private ArrayList<EntryPoint> entryPoints = new ArrayList<EntryPoint>(); //wrap it in ArrayList whenever needed
	//psuedo-set; duplicate terms cannot be added
	public HashCell(int width, int height, String name, String displayName, ArrayList<LocationInfo> arrayList, ArrayList<EntryPoint> arrayList2) {
		id = name;
		//identifier
		this.listedLocations = arrayList;
		this.entryPoints = arrayList2;
		fixedWidth = width;
		fixedHeight = height;
		//We'll use fixedWidth and fixedHeight to cap the width and height of edited points.
		arrayList2 = new ArrayList<EntryPoint>();
		arrayList = new ArrayList<LocationInfo>();
	}
	public ArrayList<LocationInfo> getListedLocations() {
		return listedLocations;
	}
	public ArrayList<EntryPoint> getEntryPoints() {
		return entryPoints;
	}
	private void fillTiles(TILE_TYPE fillTile, int width, int height) {
		for(int i=0; i< width; i++) {
			for(int j=0; j< height; j++) {
				//deprecated
			}
		}
	}
	public String getID() {
		return id;
	}
	public void setID(String inID) {
		id = inID;
	}
	public void setDisplay(String inDisplay) {
		displayName = inDisplay;
	}
	public int getWidth() {
		return fixedWidth;
	}
	public HashMap<Point, TILE_TYPE> getTiles() {
		return tiles;
	}
	public int getHeight() {
		return fixedHeight;
	}
	public TILE_TYPE tileAt(Point p) {
		return tiles.get(p);
	}
	public String getDisplayName() {
		return displayName;
	}
	public void appendLocation(Point loc, String text) {
		for(int i=0; i<listedLocations.size(); i++) {
			if(listedLocations.get(i).getLocation() == loc) {
				//location matched. duplicate locations not allowed, this must be it.
				listedLocations.get(i).addAlias(text);
				return;
			}
		}
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "An attempt was made to append where there was nothing to append.");
		//nothing happens.
	} 
	/*
	 * Duplicate locations are deleted and overwritten.
	 */
	public void addLocation(LocationInfo input) {
		removeLocation(input.getLocation(), input.getCellReference());
		listedLocations.add(input);
	}
	public void addEntryPoint(EntryPoint input) {
		removeEntry(input.getLocation());
		entryPoints.add(input); //duplicates can't be added, it's a HashSet
	}
	public void removeLocation(Point input, String ref) {
		
		listedLocations.removeIf(isLocationEqual(input, ref));
	}
	public void removePointLocation(Point input) {
		for(int i=0; i<listedLocations.size(); i++) {
			if(listedLocations.get(i).getLocation().equals(input)) {
				listedLocations.remove(i);
				DebugManagement.writeNotificationToLog("Deleted a point!");
				i=0;
			}
		}
		listedLocations.removeIf(isLocationPointEqual(input));
		
	}
	public void removeEntry(Point input) {
		entryPoints.removeIf(isEntryEqual(input));
	}
	public static Predicate<EntryPoint> isEntryEqual(Point filter) {
	    return p -> p.getLocation().equals(filter);
	}
	public static Predicate<LocationInfo> isLocationEqual(Point filter, String ref) {
	    return p -> p.getLocation().equals(filter) && p.getCellReference().equals(ref);
	}
	public static Predicate<LocationInfo> isLocationPointEqual(Point filter) {
	    return p -> p.getLocation().equals(filter);
	}
	public void setTile(int x, int y, TILE_TYPE tileToSet) {
		int xActual = x;
		int yActual = y;
		int actualWidth = getWidth() - 1;
		if(x > actualWidth) {
			xActual = actualWidth;
		}
		int actualHeight = getHeight() - 1;
		if(y > actualHeight) {
			yActual = actualHeight;
		}
		tiles.put(new Point(xActual, yActual), tileToSet);
		
	}
	public TILE_TYPE getTile(int x, int y) {
			
			int xActual = x;
			int yActual = y;
			int actualWidth = getWidth() - 1;
			if(x > actualWidth) {
				xActual = actualWidth;
			}
			int actualHeight = getHeight() - 1;
			if(y > actualHeight) {
				yActual = actualHeight;
			}
			Point p = new Point(xActual, yActual);
			if(tiles.containsKey(p)) {
				return tiles.get(new Point(xActual, yActual));
			} else {
				return TILE_TYPE.WALL;
			}
			
	}


}
