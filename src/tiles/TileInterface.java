package tiles;

public interface TileInterface {
	
	public int getTraverseCost(); 
	public int getCSF();
	public Tile getParent();
	public void setParent(Tile parentTile);
	public void setCSF(int tentativeCSF);
	public void setETC(int ETC);

}