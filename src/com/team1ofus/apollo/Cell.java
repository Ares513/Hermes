package com.team1ofus.apollo;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Predicate;

public class Cell implements Serializable {
	/**
	 * 
	 */
	private String id;
	private String displayName;
	private static final long serialVersionUID = 7L;
	private DataTile[][] tiles;
	//minimum required information
	double scaling  = 1;
	private int fixedWidth;
	private int fixedHeight;
	private ArrayList<LocationInfo> listedLocations; //Specific locations, i.e fountain. No cell association variable if it doesn't associate to anything.
	private ArrayList<EntryPoint> entryPoints; //wrap it in ArrayList whenever needed
	//psuedo-set; duplicate terms cannot be added
	public Cell(int width, int height, double scaling, TILE_TYPE defaultTile, String name, String displayName) {
		id = name;
		//identifier
		tiles = new DataTile[width][height];
		fillTiles(TILE_TYPE.WALL, width, height);
		fixedWidth = width;
		fixedHeight = height;
		entryPoints = new ArrayList<EntryPoint>();
		listedLocations = new ArrayList<LocationInfo>();
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
				tiles[i][j] = new DataTile(fillTile);
				
			}
		}
	}
	public String getID() {
		return id;
	}
	public void setID(String inID) {
		id = inID;
	}
	public int getWidth() {
		return fixedWidth;
	}
	public int getHeight() {
		return fixedHeight;
	}
	public DataTile[][] getTile() {
		return tiles;
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
		
		//nothing happens.
	} 
	/*
	 * Duplicate locations are deleted and overwritten.
	 */
	public void addLocation(LocationInfo input) {
		removeLocation(input.getLocation());
		listedLocations.add(input);
	}
	public void addEntryPoint(EntryPoint input) {
		removeLocation(input.getLocation());
		entryPoints.add(input); //duplicates can't be added, it's a HashSet
	}
	public void removeLocation(Point input) {
		System.out.println(1);
		listedLocations.removeIf(isLocationEqual(input));
	}
	public void removeEntry(Point input) {
		entryPoints.removeIf(isEntryEqual(input));
	}
	public static Predicate<EntryPoint> isEntryEqual(Point filter) {
	    return p -> p.getLocation().equals(filter);
	}
	public static Predicate<LocationInfo> isLocationEqual(Point filter) {
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
		tiles[xActual][yActual] = new DataTile(tileToSet);
	}
	public DataTile getTile(int x, int y) {
			
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
			return tiles[xActual][yActual];
	}

}
