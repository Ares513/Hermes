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
			PathCell converted = new PathCell(current.getID(),  current.getWidth(), current.getHeight(), current.getScale(), current.getTiles());
			pathCells.add(converted);
		}
		return pathCells;
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