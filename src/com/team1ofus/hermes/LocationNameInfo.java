package com.team1ofus.hermes;

import java.util.List;
import java.awt.Point;
import java.util.ArrayList;

/* This class is designed to associate location names, like 'AK116' or 'The Fountain' with
 * actual CellPoint locations that our program understands. It should be used by UI classes
 * exclusively, though DataManagement may have to instantiate these. 
 * Author: Forrest Cinelli
 * */
public class LocationNameInfo {
	private CellPoint loc;
	private List<String> aliases;
	
	public LocationNameInfo(CellPoint aLoc, List<String> listOfNames) {
		loc = aLoc;
		aliases = listOfNames;
	}
	
	public Point getPoint() {
		return loc.getPoint();
	}
	public String getCellName() {
		return loc.getCellName();
	}
	public ArrayList<String> getNames() {
		return (ArrayList) aliases;
	}
	public boolean isCalled(String candidate) {
		for (String name : aliases) {
			if (name.equals(candidate))
				return true;
		}
		return false;
	}
}
