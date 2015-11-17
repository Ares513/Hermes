import java.util.ArrayList;

public interface TileInterface {
	
	public int getTraverseCost();
	
	public ArrayList<Tile> getNeighbors(); 
	
	public int getCSF();
	
	public Tile getParent();
	
	public void setParent(Tile parentTile);
	
	public void setCSF(int tentativeCSF);
	
	public void setETC(int ETC);

}
