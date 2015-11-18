
public class walkway extends Tile implements TileInterface {

	public walkway(byte cellNum,Point tilePoint){
		Tile.cellNum = cellNum;
		Tile.tilePoint = tilePoint;
		Tile.tileType = 0; // 0 means walkway
	}

	public int getTraverseCost() {
		return 5;
	}
	
}
