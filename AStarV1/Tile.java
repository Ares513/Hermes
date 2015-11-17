import java.util.ArrayList;

public abstract class Tile implements TileInterface{
	
	public static byte cellNum = 0;
	
	public static Point tilePoint = null;

	public static byte tileType = 0; // 0 = walkway, 1 = wall
	
	public Tile parent = null;
	
	public int costSoFar = 10000000;
	
	public int estimatedTotalCost = 0;
//-------------------------------------------------
// Getters:
	
	public ArrayList<Tile> getNeighbors(){
		// Nodes that neighbor the current node
		ArrayList<Tile> neighbors = new ArrayList<Tile>();
		
		// need to populate neighbors
		
		return neighbors;	
	}

	public int getCSF(){
		
		return costSoFar;
	}
	
	public int getETC(){
		return estimatedTotalCost;
	}
	
	public Tile getParent(){
		return parent;
	}

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
