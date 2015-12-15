package tiles;

import java.util.ArrayList;
import java.awt.Point;
import com.team1ofus.apollo.TILE_TYPE;

import pathing.CellPoint;
import pathing.PathCell;
public abstract class Tile implements TileInterface{
	
	public static byte cellNum = 0;
	public Point tilePoint = null;
	public TILE_TYPE tileType = TILE_TYPE.WALL;
	public String cellName;
	public Tile parent = null;
	public int costSoFar = 0;
	public int estimatedTotalCost = 0;
//-------------------------------------------------
// Getters:
	
	//Retrieves the tiles neighbors for use in A*
	public ArrayList<Tile> getNeighbors(PathCell curCell){
		// Nodes that neighbor the current node
		ArrayList<Tile> neighbors = new ArrayList<Tile>();
		int tileX = (int)this.getPoint().getX();
		int tileY = (int)this.getPoint().getY();
		Point curPoint = new Point();
		Tile curTile = this;
		
		for(int i = tileX-1; i < tileX+2; i++){
			for(int j = tileY-1; j < tileY+2; j++){
				curPoint.setLocation(i, j);
				curTile = curCell.getTile(curPoint);
				if(this.getPoint().equals(curPoint)){
					continue;
				}
				if(curTile.getTileType().equals(TILE_TYPE.WALL)){
					continue;
				}
				else{
					neighbors.add(curTile);
				}
			}
		}
		return neighbors;	
	}

	public int getCSF(){
		return this.costSoFar;
	}
	
	public int getETC(){
		return this.estimatedTotalCost;
	}
	
	public Tile getParent(){
		return this.parent;
	}
	
	public TILE_TYPE getTileType() {
		return this.tileType;
	}
	
	public Point getPoint() {
		return this.tilePoint;
	}
	
	public String getCellName(){
		return this.cellName;
	}
	public CellPoint getCellPoint() {
		CellPoint curCellPoint = new CellPoint(this.getCellName(),this.getPoint());
		return curCellPoint;
	} 
	public abstract int getTraverseCost();

// Setters:
	public void setParent(Tile parentTile){
		parent = parentTile;
	}

	public void setCSF(int tentativeCSF){
		costSoFar = tentativeCSF;
	}

	public void setETC(int ETC){
		estimatedTotalCost = ETC;
	}

}