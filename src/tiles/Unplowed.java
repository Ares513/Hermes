package tiles;

import java.awt.Point;

import com.team1ofus.apollo.TILE_TYPE;

import tiles.Tile;

//A type of tile that represents a non-traversable tile
public class Unplowed extends Tile implements TileInterface {
	
	private static final long serialVersionUID = 1L;

	public Unplowed(String cellName,Point tilePoint){
		this.cellName = cellName;
		this.tilePoint = tilePoint;
		this.tileType = TILE_TYPE.UNPLOWED;
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 30;
	}
}