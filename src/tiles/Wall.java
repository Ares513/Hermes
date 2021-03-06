package tiles;

import java.awt.Point;

import com.team1ofus.apollo.TILE_TYPE;

//A type of tile that represents a non-traversable tile
public class Wall extends Tile implements TileInterface {
	
	private static final long serialVersionUID = 1L;

	public Wall(String cellName,Point tilePoint){
		this.cellName = cellName;
		this.tilePoint = tilePoint;
		this.tileType = TILE_TYPE.WALL; // 1 means wall
	}
	
	//------------------------------------------------------------------------------
	
	public int getTraverseCost() {
		return 1000;
	}
}