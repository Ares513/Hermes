package tiles;

import java.awt.Point;

import com.team1ofus.apollo.TILE_TYPE;

//A type of tile that represents a non-traversable tile
public class Road extends Tile implements TileInterface {
	
	private static final long serialVersionUID = 1L;

	public Road(String cellName,Point tilePoint){
		this.cellName = cellName;
		this.tilePoint = tilePoint;
		this.tileType = TILE_TYPE.EXTRA_TILE_TYPE_1;
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 7;
	}
}