package tiles;

import java.awt.Point;

import com.team1ofus.apollo.TILE_TYPE;

//A type of tile that represents a traversable tile
public class Walkway extends Tile implements TileInterface {

	private static final long serialVersionUID = 1L;

	public Walkway(String cellName,Point tilePoint){
		this.cellName = cellName;
		this.tilePoint = tilePoint;
		this.tileType = TILE_TYPE.PEDESTRIAN_WALKWAY;
	}

	public int getTraverseCost() {
		return 5;
	}

	
}