package com.team1ofus.hermes;

import java.util.ArrayList;

import com.team1ofus.apollo.Cell;

/*
 * Converts cells(maps) from Apollo to cells for A*
 */
public class CellConverter {
	public CellConverter(){	
	}
	public static ArrayList<PathCell> convertList(ArrayList<Cell> data) {
		ArrayList<PathCell> pathCells = new ArrayList<PathCell>();
		for(int i=0; i< data.size(); i++) {
			com.team1ofus.apollo.Cell current = data.get(i);
			ArrayList<com.team1ofus.hermes.EntryPoint> entryPoints = new ArrayList<com.team1ofus.hermes.EntryPoint>();
			ArrayList<EntryPointReference> entryPointRefs = new ArrayList<EntryPointReference>();
			ArrayList<LocationNameInfo> locations = new ArrayList<LocationNameInfo>();
			for(com.team1ofus.apollo.LocationInfo l : data.get(i).getListedLocations()) {
				if(isNullOrBlank(l.getEntryPoint())) {
					//no entry point, it's a LocationNameInfo
					LocationNameInfo convertedLoc = new LocationNameInfo(l.getLocation(), l.getAliases());
					locations.add(convertedLoc);
				} else {
					//not empty, it's a EntryPointReference
					//first only
					EntryPointReference convertedRef = new EntryPointReference(l.getEntryPoint(), l.getCellReference(), l.getLocation());
					entryPointRefs.add(convertedRef);
				}
			}
			for(com.team1ofus.apollo.EntryPoint e : data.get(i).getEntryPoints()) {
				entryPoints.add(new com.team1ofus.hermes.EntryPoint(e.getName(), e.getLocation()));
				
			}
			PathCell converted = new PathCell(current.getID(), current.getDisplayName(),  current.getWidth(), current.getHeight(), current.getTile(), entryPoints, locations, entryPointRefs);
			pathCells.add(converted);
		}
		return pathCells;
	}
	public static boolean isNullOrBlank(String param) { 
	    return param == null || param.trim().length() == 0;
	}
}

//Chaff
//TODO Delete?
//private static byte tileType(DataTile ConvertTiletype){
//if(ConvertTiletype.getType() == TILE_TYPE.WALL){ // currently the only non-traversible. 
//	return 1;
//}
//else {
//	return 0; 
//}
//}