package com.team1ofus.hermes;
/*
 * converts cells from Apollo to cells for A*
 */
public class CellConverter {
	public CellConverter(){	
	}
	
//	private static byte tileType(DataTile ConvertTiletype){
//		if(ConvertTiletype.getType() == TILE_TYPE.WALL){ // currently the only non-traversible. 
//			return 1;
//		}
//		else {
//			return 0; 
//		}
//	}
	public static PathCell convertCell (Cell newCell){ 
		int convertWidth = newCell.getWidth(); 
		int convertHeight = newCell.getHeight(); 
		double convertScaling = newCell.getScale(); 
		String name = newCell.getID();		
//		byte convertTyleType; 
//		for(int i=0; i<newCell.getWidth(); i++) {
//			for(int j=0; j<newCell.getHeight(); j++) {
//				convertTyleType = tileType(newCell.tiles[i][j]);
//			}
//		}
		return new PathCell(name,convertWidth, convertHeight, convertScaling, TILE_TYPE.WALL);
	}
	
}