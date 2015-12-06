package data;

import java.util.ArrayList;

import com.team1ofus.apollo.Cell;
import com.team1ofus.apollo.HashCell;

import pathing.EntryPoint;
import pathing.EntryPointReference;
import pathing.LocationNameInfo;
import pathing.PathCell;

/*
 * Converts cells(maps) from Apollo to cells for A*
 */
public class CellConverter {
	public CellConverter(){	
	}
	public static ArrayList<PathCell> convertList(ArrayList<HashCell> arrayList) {
		ArrayList<PathCell> pathCells = new ArrayList<PathCell>();
		for(int i=0; i< arrayList.size(); i++) {
			com.team1ofus.apollo.HashCell current = arrayList.get(i);
			ArrayList<pathing.EntryPoint> entryPoints = new ArrayList<pathing.EntryPoint>();
			ArrayList<EntryPointReference> entryPointRefs = new ArrayList<EntryPointReference>();
			ArrayList<LocationNameInfo> locations = new ArrayList<LocationNameInfo>();
			for(com.team1ofus.apollo.LocationInfo l : arrayList.get(i).getListedLocations()) {
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
			for(com.team1ofus.apollo.EntryPoint e : arrayList.get(i).getEntryPoints()) {
				entryPoints.add(new pathing.EntryPoint(e.getName(), e.getLocation()));
				
			}
			PathCell converted = new PathCell(current.getID(), current.getDisplayName(),  current.getWidth(), current.getHeight(), current.getTiles(), entryPoints, locations, entryPointRefs);
			
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